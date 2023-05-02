package dragon.util.tsv;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import dragon.util.tsv.annotations.TsvColumnIndex;
import dragon.util.tsv.annotations.TsvColumnName;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TsvParser {

    private final Map<String, Integer> columnNameMap;
    private final BufferedReader reader;
    private final Map<Class<?>, Map<Integer, Enum<?>>> enumCache = new HashMap();
    private String[] columns;
    //if it is not -1, parser must use the column to parse and prevent recursion.
    private int sigSpecColumn = -1;

    public TsvParser(BufferedReader tsvReader) throws Exception {
        reader = tsvReader;
        var tableHead = tsvReader.readLine();
        String[] columnNames = tableHead.split("\t");
        int columnCount = columnNames.length;
        columnNameMap = new HashMap();
        for (int i = 0; i < columnCount; ++i)
            columnNameMap.put(columnNames[i], i);
    }

    public <T> List<T> parse(Class<T> tClass) throws Exception {
        if (tClass.isArray() || isPrimitive(tClass)) return null;
        List<T> objects = new ArrayList();
        String line;
        while ((line = reader.readLine()) != null) {
            //beware of multiple tabs symbol,java compiler will be treated one
            columns = line.split("\t");
            objects.add(createObject(tClass, ""));
        }
        return objects;
    }

    private <T> T createObject(Class<T> tClass, String prefix) throws Exception {
        if (ObjectUtil.isEmpty(tClass)) return null;
        var constructor = tClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        T object = constructor.newInstance();

        boolean success = false;
        for (var field : tClass.getDeclaredFields()) {
            field.setAccessible(true);
            var type = field.getType();

            String fieldName;
            var definedColumName = field.getAnnotation(TsvColumnName.class);
            if (definedColumName != null) fieldName = definedColumName.value();
            else fieldName = field.getName();

            var definedColumIndex = field.getAnnotation(TsvColumnIndex.class);
            if (definedColumIndex != null) sigSpecColumn = definedColumIndex.value();

            Object value;
            if (type.isArray()) {
                var list = createList(type.getComponentType(), prefix, fieldName);
                if (list != null) value = list.toArray();
                else value = null;
            } else if (type == List.class) {
                value = createList(type.getComponentType(), prefix, fieldName);
            } else if (type == Map.class) {
                // todo: unImplementation
                value = null;
            } else if (isPrimitive(type)) {
                value = createPrimitive(type, prefix + fieldName);
            } else {
                value = createObject(type, "%s[%s]".formatted(prefix, fieldName));
            }
            if (value != null) {
                field.set(object, Convert.convert(type, value));
                success = true;
            }

            sigSpecColumn = -1;
        }
        if (!success) return null;
        else return object;
    }

    private List<Object> createList(Class<?> tClass, String prefix, String fieldName) throws Exception {
        if (ObjectUtil.isEmpty(tClass)) return null;
        //[Primitive Type, single] single column list, like `npcList`
        if ((sigSpecColumn != -1 || columnNameMap.containsKey(prefix + fieldName)) && isPrimitive(tClass)) {
            List<Object> list = new ArrayList();
            int index = sigSpecColumn == -1 ? columnNameMap.get(prefix + fieldName) : sigSpecColumn;
            if (!checkArrayLength(index)) return null;
            var content = columns[index].split(",");
            for (var c : content) {
                var object = parsePrimitive(tClass, c);
                if (object != null) list.add(object);
            }
            if (list.isEmpty()) return null;
            else return list;
        }

        //[Primitive Type, multiple] like `param1`, `param2`
        if (isPrimitive(tClass)) {
            List<Object> list = new ArrayList();
            var baseName = prefix + fieldName;
            boolean success = false;
            for (int i = 1;; ++i) {
                var key = baseName + i;
                if (columnNameMap.containsKey(key) && checkArrayLength(columnNameMap.get(key))) {
                    success = true;
                    var value = parsePrimitive(tClass, columns[columnNameMap.get(key)]);
                    if (value != null) list.add(value);
                } else break;
            }
            if (success) {
                if (list.isEmpty()) return null;
                else return list;
            }
        }

        //[Non-Primitive Type, single] Primitive Type, multiple `[gainItems]id`, `[gainItems]count`
        boolean success = false;
        int length = 0;
        for (var field : tClass.getDeclaredFields()) {
            var columnName = "%s[%s]%s".formatted(prefix, fieldName, field.getName());
            if (columnNameMap.containsKey(columnName) && checkArrayLength(columnNameMap.get(columnName))) {
                var content = columns[columnNameMap.get(columnName)];
                success = true;
                if (content!=null && !content.isEmpty()) {
                    length = content.split(",").length;
                    break;
                }
            }
        }
        if (success) {
            if (length == 0) return null;
            List<Object> list = new ArrayList(length);
            var constructor = tClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            for (int i = 0; i < length; ++i) list.add(constructor.newInstance());
            for (var field : tClass.getDeclaredFields()) {
                var columnName = "%s[%s]%s".formatted(prefix, fieldName, field.getName());
                field.setAccessible(true);
                if (columnNameMap.containsKey(columnName) && checkArrayLength(columnNameMap.get(columnName))) {
                    var content = columns[columnNameMap.get(columnName)];
                    if (content.isEmpty()) continue;
                    var values = content.split(",");
                    for (int i = 0; i < length && i < values.length; ++i) {
                        field.set(list.get(i), parsePrimitive(field.getType(), values[i]));
                    }
                }
            }
            return list;
        }

        //[Non-Primitive Type, multiple] number related list, like `[finishExec]1param1`
        if (!isPrimitive(tClass)) {
            List<Object> list = new ArrayList();
            for (int i = 1; ; ++i) {
                var base = "%s[%s]%d".formatted(prefix, fieldName, i);
                var object = createObject(tClass, base);
                if (object != null) list.add(object);
                else break;
            }
            if (list.isEmpty()) return null;
            else return list;
        }
        return null;
    }

    private <T> Object createPrimitive(Class<T> tClass, String columnName) throws Exception {
        if (sigSpecColumn != -1 && checkArrayLength(sigSpecColumn))
            return parsePrimitive(tClass, columns[sigSpecColumn]);
        if (columnNameMap.containsKey(columnName) && checkArrayLength(columnNameMap.get(columnName))) {
            return parsePrimitive(tClass, columns[columnNameMap.get(columnName)]);
        } else return null;
    }

    private boolean isPrimitive(Class<?> type) {
        return type != null && (type.isPrimitive() || type == String.class || type.isEnum());
    }

    private Object parsePrimitive(Class<?> type, String v) throws Exception {
        v = StrUtil.trim(v);
        if (StrUtil.isBlank(v)) return null;
        if (type.isEnum()) {
            Map<Integer, Enum<?>> enumMap;
            //try with string name
            try {
                var value = Enum.valueOf((Class<? extends Enum>) type, v);
                return value;
            } catch (Exception ignored) {}

            //try with int value
            if (enumCache.containsKey(type)) {
                enumMap = enumCache.get(type);
            } else {
                enumMap = new HashMap<>();
                for (var e : type.getEnumConstants()) {
                    var f = e.getClass().getDeclaredField("value");
                    f.setAccessible(true);
                    enumMap.put(f.getInt(e), (Enum<?>) e);
                }
                enumCache.put(type, enumMap);
            }
            //if the value is empty, the first one in the enumeration is taken
            if (v.isEmpty()) return enumMap.get(0);
            int num = Integer.parseInt(v);
            return enumMap.get(num);
        } else if (type == int.class) {
            return Integer.parseInt(v);
        } else if (type == double.class) {
            return Double.parseDouble(v);
        } else if (type == boolean.class) {
            if (v.equals("1")) return true;
            return Boolean.parseBoolean(v);
        } else if (type == long.class) {
            return Long.parseLong(v);
        } else if (type == byte.class) {
            return Byte.parseByte(v);
        } else if (type == short.class) {
            return Short.parseShort(v);
        } else if (type == float.class) {
            return Float.parseFloat(v);
        } else if (type == char.class) {
            return v.charAt(0);
        } else if (type == String.class) {
            return v;
        }
        return null;
    }

    /**
     * for check beware of multiple tabs symbol,java compiler will be treated one
     * Is it within the current columns range
     * @param index get columns index
     * @return the @{code columns} the is within Range
     */
    private Boolean checkArrayLength(Integer index){
        return (index >= 0 && index < columns.length) ? true : false;
    }

    /**
     * Gets all properties declared in the current class and its parent class
     * Find @{code class} of param all the parents recursive
     * Returns @{code field} of class
     *
     * @param clazz reflect class
     * @return the @{code field} value represented by the class argument.
     */
    public static Field[] getAllFields(Class<?> clazz) {
        // gets all the properties declared by the current class
        Field[] declaredFields = clazz.getDeclaredFields();

        // gets the parent class of the current class
        Class<?> superClass = clazz.getSuperclass();

        // If the parent class is not object then recursive
        if (superClass != Object.class) {
            Field[] superFields = getAllFields(superClass);

            // merge properties of current class and parent class
            Field[] fields = new Field[declaredFields.length + superFields.length];
            System.arraycopy(declaredFields, 0, fields, 0, declaredFields.length);
            System.arraycopy(superFields, 0, fields, declaredFields.length, superFields.length);

            return fields;
        } else {
            return declaredFields;
        }
    }

}
