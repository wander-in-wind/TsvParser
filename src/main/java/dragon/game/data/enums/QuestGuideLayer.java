package dragon.game.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestGuideLayer {

    QUEST_GUIDE_LAYER_NONE(0),
    QUEST_GUIDE_LAYER_UI(1),
    QUEST_GUIDE_LAYER_SCENE(2);

    private final int value;

}
