package dragon.game.data;


import cn.hutool.core.collection.CollectionUtil;
import dragon.game.data.enums.LogicType;
import dragon.game.data.enums.QuestContent;
import dragon.game.data.enums.QuestExec;
import dragon.util.tsv.annotations.TsvColumnName;

import java.util.List;

public class MainQuestData {


    @TsvColumnName("父任务ID")
    int id;

    @TsvColumnName("合集ID")
    int ICLLDPJFIMA;

    @TsvColumnName("父任务组")
    int series;

    @TsvColumnName("Lua脚本路径")
    String luaPath;

    @TsvColumnName("任务奖励RewardID")
    int[] rewardIdList;

    @TsvColumnName("章节ID")
    int chapterId;

    @TsvColumnName("子任务")
    List<QuestData> subQuests = CollectionUtil.newArrayList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getICLLDPJFIMA() {
        return ICLLDPJFIMA;
    }

    public void setICLLDPJFIMA(int ICLLDPJFIMA) {
        this.ICLLDPJFIMA = ICLLDPJFIMA;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public String getLuaPath() {
        return luaPath;
    }

    public void setLuaPath(String luaPath) {
        this.luaPath = luaPath;
    }

    public int[] getRewardIdList() {
        return rewardIdList;
    }

    public void setRewardIdList(int[] rewardIdList) {
        this.rewardIdList = rewardIdList;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public List<QuestData> getSubQuests() {
        return subQuests;
    }

    public void setSubQuests(List<QuestData> subQuests) {
        this.subQuests = subQuests;
    }


}
