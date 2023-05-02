package dragon.game.data;


import dragon.game.data.enums.*;
import dragon.util.tsv.annotations.TsvColumnName;
import lombok.Data;

import java.util.Map;

@Data
public class QuestData {


    @TsvColumnName("子任务ID")
    int subId;
    @TsvColumnName("父任务ID")
    int mainId;
    @TsvColumnName("序列")
    int order;
    @TsvColumnName("子任务组")
    int subIdSet;
    @TsvColumnName("禁止进入联机")
    boolean isMpBlock;
    @TsvColumnName("显示状态")
    QuestShowType showType;
    @TsvColumnName("[领取条件]组合")
    LogicType acceptCondComb;
    @TsvColumnName("领取条件")
    QuestAcceptCondition[] acceptCond;
    @TsvColumnName("[完成条件]组合")
    LogicType finishCondComb;
    @TsvColumnName("完成条件")
    QuestContentCondition[] finishCond;
    @TsvColumnName("[失败条件]组合")
    LogicType failCondComb;
    @TsvColumnName("失败条件")
    QuestContentCondition[] failCond;
    @TsvColumnName("任务指引")
    Guide guide;
    @TsvColumnName("场景内指引")
    boolean showGuide;
    @TsvColumnName("完成父任务")
    boolean finishParent;
    @TsvColumnName("失败父任务")
    boolean failParent;
    @TsvColumnName("是存档点")
    boolean isRewind;
    @TsvColumnName("任务道具")
    QuestItem[] gainItems;
    @TsvColumnName("执行")
    QuestExecParam[] finishExec;
    @TsvColumnName("失败执行")
    QuestExecParam[] failExec;
    @TsvColumnName("开始执行")
    QuestExecParam[] beginExec;
    @TsvColumnName("独占NPC")
    int[] exclusiveNpcList;
    @TsvColumnName("共享NPC")
    int[] sharedNpcList;
    @TsvColumnName("独占优先级")
    int exclusivePriority;
    @TsvColumnName("试用角色列表")
    int[] trialAvatarList;
    @TsvColumnName("加载技能组")
    String loadAbilityGroup;
    @TsvColumnName("加载队伍技能组")
    String loadTeamAbilityGroup;
    @TsvColumnName("独占情境ID")
    int[]exclusivePlaceList;
    @TsvColumnName("CoopPointID")
    int[] coopPointIdList;
    @TsvColumnName("刷新是否限定单机")
    boolean isRefreshLimitedToSinglePlayer;
    @TsvColumnName("关卡映射")
    Map<Integer,Integer> levelMap;

    public static class QuestItem{
        @TsvColumnName("ID")
        int id;
        @TsvColumnName("数量")
        int count;
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
        private QuestGuideType type;
        @TsvColumnName("自动开启或关闭")
        private QuestGuideAuto autoGuide;
        @TsvColumnName("参数")
        private String[] param;
        @TsvColumnName("场景ID")
        private int guideScene;
        @TsvColumnName("样式")
        private QuestGuideStyle guideStyle;
        @TsvColumnName("显示层级")
        private QuestGuideLayer guideLayer;
    }

}
