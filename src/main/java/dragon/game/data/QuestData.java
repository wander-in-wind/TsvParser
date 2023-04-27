package dragon.game.data;


import dragon.game.data.enums.QuestContent;
import dragon.game.data.enums.LogicType;
import dragon.game.data.enums.QuestCond;
import dragon.game.data.enums.QuestExec;
import dragon.util.tsv.annotations.TsvColumnName;

import java.util.Map;

public class QuestData {
    @TsvColumnName("子任务ID")
    int subId;
    @TsvColumnName("父任务ID")
    int mainId;
    @TsvColumnName("序列")
    int order;
    long descTextMapHash;
    @TsvColumnName("禁止进入联机")
    boolean isMpBlock;
    @TsvColumnName("显示状态")
    ShowType showType;
    @TsvColumnName("[领取条件]组合")
    LogicType acceptCondComb;
    @TsvColumnName("[完成条件]组合")
    LogicType finishCondComb;
    @TsvColumnName("[失败条件]组合")
    LogicType failCondComb;
    @TsvColumnName("领取条件")
    QuestAcceptCondition[] acceptCond;
    @TsvColumnName("完成条件")
    QuestContentCondition[] finishCond;
    @TsvColumnName("失败条件")
    QuestContentCondition[] failCond;
    @TsvColumnName("开始执行")
    QuestExecParam[] beginExec;
    @TsvColumnName("执行")
    QuestExecParam[] finishExec;
    @TsvColumnName("失败执行")
    QuestExecParam[] failExec;
    @TsvColumnName("任务指引")
    Guide guide;
    //TODO: enum showGuide
    @TsvColumnName("完成父任务")
    boolean finishParent;
    @TsvColumnName("失败父任务")
    boolean failParent;
    @TsvColumnName("是存档点")
    boolean isRewind;
    @TsvColumnName("任务道具")
    QuestItem[] gainItems;
    @TsvColumnName("独占NPC")
    int[] exclusiveNpcList;
    @TsvColumnName("共享NPC")
    int[] sharedNpcList;
    @TsvColumnName("试用角色列表")
    int[] trialAvatarList;
    @TsvColumnName("独占情境ID")
    int[]exclusivePlaceList;
    @TsvColumnName("独占优先级")
    int exclusivePriority;
    @TsvColumnName("加载技能组")
    String loadAbilityGroup;
    @TsvColumnName("加载队伍技能组")
    String loadTeamAbilityGroup;
    @TsvColumnName("CoopPointID")
    int[] coopPointIdList;
    @TsvColumnName("刷新是否限定单机")
    boolean isRefreshLimitedToSinglePlayer;
    /*@TsvColumnName("关卡映射")
    Map<Integer,Integer> levelMap;*/

    public static class QuestItem{
        @TsvColumnName("ID")
        int id;
        @TsvColumnName("数量")
        int count;
    }
    public enum ShowType {
        QUEST_SHOW(0),
        QUEST_HIDDEN(1);
        private final int value;

        ShowType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    public static class QuestExecParam {
        @TsvColumnName("类型")
        QuestExec type;
        @TsvColumnName("参数")
        String[] param;
        @TsvColumnName("次数")
        String count;
    }

    public static class QuestAcceptCondition {
        @TsvColumnName("类型")
        private QuestContent type;
        @TsvColumnName("参数")
        private int[] param;
        @TsvColumnName("复杂参数")
        private String paramStr;
        @TsvColumnName("次数")
        private int count;
    }

    public static class QuestContentCondition {
        @TsvColumnName("类型")
        private QuestContent type;
        @TsvColumnName("参数")
        private int[] param;
        @TsvColumnName("复杂参数")
        private String paramStr;
        @TsvColumnName("次数")
        private int count;
    }

    public static class Guide {
        @TsvColumnName("类型")
        private String type;
        @TsvColumnName("参数")
        private String[] param;
        @TsvColumnName("自动开启或关闭")
        private boolean autoGuide;
        @TsvColumnName("场景ID")
        private int guideScene;

        //TODO: guideStyle, guideLayer
    }


    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public int getMainId() {
        return mainId;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getDescTextMapHash() {
        return descTextMapHash;
    }

    public void setDescTextMapHash(long descTextMapHash) {
        this.descTextMapHash = descTextMapHash;
    }

    public boolean isMpBlock() {
        return isMpBlock;
    }

    public void setMpBlock(boolean mpBlock) {
        isMpBlock = mpBlock;
    }

    public ShowType getShowType() {
        return showType;
    }

    public void setShowType(ShowType showType) {
        this.showType = showType;
    }

    public LogicType getAcceptCondComb() {
        return acceptCondComb;
    }

    public void setAcceptCondComb(LogicType acceptCondComb) {
        this.acceptCondComb = acceptCondComb;
    }

    public LogicType getFinishCondComb() {
        return finishCondComb;
    }

    public void setFinishCondComb(LogicType finishCondComb) {
        this.finishCondComb = finishCondComb;
    }

    public LogicType getFailCondComb() {
        return failCondComb;
    }

    public void setFailCondComb(LogicType failCondComb) {
        this.failCondComb = failCondComb;
    }

    public QuestAcceptCondition[] getAcceptCond() {
        return acceptCond;
    }

    public void setAcceptCond(QuestAcceptCondition[] acceptCond) {
        this.acceptCond = acceptCond;
    }

    public QuestContentCondition[] getFinishCond() {
        return finishCond;
    }

    public void setFinishCond(QuestContentCondition[] finishCond) {
        this.finishCond = finishCond;
    }

    public QuestContentCondition[] getFailCond() {
        return failCond;
    }

    public void setFailCond(QuestContentCondition[] failCond) {
        this.failCond = failCond;
    }

    public QuestExecParam[] getBeginExec() {
        return beginExec;
    }

    public void setBeginExec(QuestExecParam[] beginExec) {
        this.beginExec = beginExec;
    }

    public QuestExecParam[] getFinishExec() {
        return finishExec;
    }

    public void setFinishExec(QuestExecParam[] finishExec) {
        this.finishExec = finishExec;
    }

    public QuestExecParam[] getFailExec() {
        return failExec;
    }

    public void setFailExec(QuestExecParam[] failExec) {
        this.failExec = failExec;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public boolean isFinishParent() {
        return finishParent;
    }

    public void setFinishParent(boolean finishParent) {
        this.finishParent = finishParent;
    }

    public boolean isFailParent() {
        return failParent;
    }

    public void setFailParent(boolean failParent) {
        this.failParent = failParent;
    }

    public boolean isRewind() {
        return isRewind;
    }

    public void setRewind(boolean rewind) {
        isRewind = rewind;
    }

    public QuestItem[] getGainItems() {
        return gainItems;
    }

    public void setGainItems(QuestItem[] gainItems) {
        this.gainItems = gainItems;
    }

    public int[] getExclusiveNpcList() {
        return exclusiveNpcList;
    }

    public void setExclusiveNpcList(int[] exclusiveNpcList) {
        this.exclusiveNpcList = exclusiveNpcList;
    }

    public int[] getSharedNpcList() {
        return sharedNpcList;
    }

    public void setSharedNpcList(int[] sharedNpcList) {
        this.sharedNpcList = sharedNpcList;
    }

    public int[] getTrialAvatarList() {
        return trialAvatarList;
    }

    public void setTrialAvatarList(int[] trialAvatarList) {
        this.trialAvatarList = trialAvatarList;
    }

    public int[] getExclusivePlaceList() {
        return exclusivePlaceList;
    }

    public void setExclusivePlaceList(int[] exclusivePlaceList) {
        this.exclusivePlaceList = exclusivePlaceList;
    }

    public int getExclusivePriority() {
        return exclusivePriority;
    }

    public void setExclusivePriority(int exclusivePriority) {
        this.exclusivePriority = exclusivePriority;
    }

    public String getLoadAbilityGroup() {
        return loadAbilityGroup;
    }

    public void setLoadAbilityGroup(String loadAbilityGroup) {
        this.loadAbilityGroup = loadAbilityGroup;
    }

    public String getLoadTeamAbilityGroup() {
        return loadTeamAbilityGroup;
    }

    public void setLoadTeamAbilityGroup(String loadTeamAbilityGroup) {
        this.loadTeamAbilityGroup = loadTeamAbilityGroup;
    }

    public int[] getCoopPointIdList() {
        return coopPointIdList;
    }

    public void setCoopPointIdList(int[] coopPointIdList) {
        this.coopPointIdList = coopPointIdList;
    }

    public boolean isRefreshLimitedToSinglePlayer() {
        return isRefreshLimitedToSinglePlayer;
    }

    public void setRefreshLimitedToSinglePlayer(boolean refreshLimitedToSinglePlayer) {
        isRefreshLimitedToSinglePlayer = refreshLimitedToSinglePlayer;
    }
}
