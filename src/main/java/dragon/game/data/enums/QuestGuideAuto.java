package dragon.game.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestGuideAuto {

	QUEST_GUIDE_AUTO_NONE(0),
	QUEST_GUIDE_AUTO_ENABLE(1),
	QUEST_GUIDE_AUTO_DISABLE(2);

	private final int value;

}
