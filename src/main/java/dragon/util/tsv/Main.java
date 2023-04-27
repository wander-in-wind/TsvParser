package dragon.util.tsv;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.map.MapUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dragon.game.data.MainQuestData;
import dragon.game.data.QuestData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        var gson = new GsonBuilder().setPrettyPrinting().create();
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
                    var subQuests = questValue.stream().filter(item -> mainQuestData.getId() == item.getMainId()).collect(Collectors.toList());
                    mainQuestData.setSubQuests(subQuests);
                });
                var outputPath = Paths.get("%s%s.json".formatted( "src/main/resources/json/", mainQuestData.getId()));
                var writer = new FileWriter(outputPath.toFile());
                gson.toJson(mainQuestData, writer);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
