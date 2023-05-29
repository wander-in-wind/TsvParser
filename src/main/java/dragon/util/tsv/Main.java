package dragon.util.tsv;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dragon.game.data.MainQuestData;
import dragon.game.data.QuestData;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, List<MainQuestData>> mainQuestDataFileList = MapUtil.newConcurrentHashMap();
        Map<String, List<QuestData>> questDataFileList = MapUtil.newConcurrentHashMap();
        var url = ResourceUtil.getResources("").get(1);

        // serialize quest data directory
        var questFiles = FileUtil.ls("%s%s".formatted(url.getPath(), "/tsv/questData"));
        for (File file : questFiles) {
            if (file.isFile()) {
                var questDataList = new TsvParser(FileUtil.getReader(file, Charset.forName("UTF-8"))).parse(QuestData.class);
                questDataFileList.put(file.getName(), questDataList);
            }
        }

        // serialize main quest data directory
        var mainQuestFiles = FileUtil.ls("%s%s".formatted(url.getPath(), "/tsv/mainQuestData"));
        for (File file : mainQuestFiles) {
            if (file.isFile()) {
                var mainQuestDataList = new TsvParser(FileUtil.getReader(file, Charset.forName("UTF-8"))).parse(MainQuestData.class);
                mainQuestDataFileList.put(file.getName(), mainQuestDataList);
            }
        }

        // handle main quest data bind
        mainQuestDataFileList.forEach((mainKey, mainValue) ->
        mainValue.forEach(mainQuestData -> {
            try {
                questDataFileList.forEach((questKey, questValue)-> {
                    var subQuests = questValue.stream().filter(item -> ObjectUtil.equals(mainQuestData.getId(), item.getMainId())).collect(Collectors.toList());
                    mainQuestData.getSubQuests().addAll(subQuests);
                });
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                var outputPath = Paths.get("%s%s.json".formatted( "src/main/resources/json/", mainQuestData.getId()));
                FileUtil.writeUtf8String(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mainQuestData), outputPath.toFile());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
