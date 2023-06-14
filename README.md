# TsvParser
A smart column name based TSV parser.

Just simply use annotation `@TsvColumnName` to specify the column name, and you needn't do anything else.

If the annotation is missing, parser will use field name as the column name.

Besides, the parser can handle many types of array columns, you can deserialize any array gracefully:
- The array is in a single column list and elements are separate by comma, like `npcList`:`1,2,3`
- The type of the array elements is an Object, each field is in a single list, separated by comma, like:`[gainItems]id`, `[gainItems]count`
    ``` java
    public static class QuestItem{
        @TsvColumnName("ID")
        int id;
        @TsvColumnName("数量")
        int count;
    }
    ```
    ``` java
    @TsvColumnName("任务道具")
    QuestItem[] gainItems;
    ```
- The array is in multiple columns, like:`[finishExec]1param1`,`[finishExec]1param2`,`[finishExec]2param1`,`[finishExec]2type
  ```java
    public static class QuestExecParam {
        @TsvColumnName("类型")
        QuestExec type;
        @TsvColumnName("参数")
        String[] param;
        @TsvColumnName("次数")
        String count;
    }
    ```
    ```java
    @TsvColumnName("失败执行")
    QuestExecParam[] failExec;
    ```

Usage:
```java
List<QuestData> questDataList = new TsvParser(bufferedReader).parse(QuestData.class);
```
