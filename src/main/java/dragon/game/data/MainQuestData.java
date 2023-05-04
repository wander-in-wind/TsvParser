package dragon.game.data;


import cn.hutool.core.collection.CollectionUtil;
import dragon.game.data.enums.*;
import dragon.util.tsv.annotations.TsvColumnName;
import lombok.Data;

import java.util.List;

@Data
public class MainQuestData {
    @TsvColumnName("父任务ID")
    int id;
    @TsvColumnName("合集ID")
    int collectionId;
    @TsvColumnName("父任务组")
    int series;
    @TsvColumnName("任务类型")
    QuestType type;
    @TsvColumnName("启用模式")
    boolean enabled;
    @TsvColumnName("Lua脚本路径")
    String luaPath;
    @TsvColumnName("推荐等级")
    int recommendLevel;
    @TsvColumnName("可重复")
    boolean repeatable;
    @TsvColumnName("任务奖励RewardID")
    int[] rewardIdList;
    @TsvColumnName("章节ID")
    int chapterId;
    @TsvColumnName("子任务")
    List<QuestData> subQuests = CollectionUtil.newArrayList();
    @TsvColumnName("事件ID")
    int eventId;
    @TsvColumnName("关联活动ID")
    int associateActivity;
    @TsvColumnName("活动类型")
    ActivityType activityType;
    @TsvColumnName("VideoKey")
    long videoKey;
}
