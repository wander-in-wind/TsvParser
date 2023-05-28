package dragon.game.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShowQuestGuideType {
    QUEST_GUIDE_ITEM_ENABLE(0),
    QUEST_GUIDE_ITEM_DISABLE(1),
    QUEST_GUIDE_ITEM_MOVE_HIDE(2);
    private final int value;
}
