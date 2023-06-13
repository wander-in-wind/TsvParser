package dragon.util.tsv;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import dragon.game.data.MainQuestData;
import dragon.game.data.QuestData;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, List<MainQuestData>> mainQuestDataFileList = MapUtil.newConcurrentHashMap();
        Map<String, List<QuestData>> questDataFileList = MapUtil.newConcurrentHashMap();
        var url = ResourceUtil.getResources("").get(1);

        // serialize quest data directory
        var questFiles = FileUtil.ls("%s%s".formatted(url.getPath(), "/tsv/questData"));
        for (File file : questFiles) {
            if (file.isFile()) {
                var questDataList = new TsvParser(FileUtil.getReader(file, StandardCharsets.UTF_8)).parse(QuestData.class);
                questDataFileList.put(file.getName(), questDataList);
            }
        }

        // serialize main quest data directory
        var mainQuestFiles = FileUtil.ls("%s%s".formatted(url.getPath(), "/tsv/mainQuestData"));
        for (File file : mainQuestFiles) {
            if (file.isFile()) {
                var mainQuestDataList = new TsvParser(FileUtil.getReader(file, StandardCharsets.UTF_8)).parse(MainQuestData.class);
                mainQuestDataFileList.put(file.getName(), mainQuestDataList);
            }
        }

        // handle main quest data bind
        mainQuestDataFileList.forEach((mainKey, mainValue) ->
        mainValue.forEach(mainQuestData -> {
            try {
                questDataFileList.forEach((questKey, questValue) -> {
                    var subQuests = questValue.stream().filter(item -> ObjectUtil.equals(mainQuestData.getId(), item.getMainId())).toList();
                    mainQuestData.getSubQuests().addAll(subQuests);
                });
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(Include.NON_DEFAULT) //用于忽略默认的primitive字段
                        .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE) //直接根据字段反序列化，不会根据getter，用于防止boolean吃掉is，如isRewind
                        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                var writer = new FileWriter("%s%s.json".formatted("src/main/resources/json/", mainQuestData.getId()));
                mapper.writerWithDefaultPrettyPrinter()

                        .writeValue(writer, mainQuestData);
                writer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
