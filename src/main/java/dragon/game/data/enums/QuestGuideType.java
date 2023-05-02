package dragon.game.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestGuideType {

	QUEST_GUIDE_NONE (0),
	QUEST_GUIDE_LOCATION (1),
	QUEST_GUIDE_NPC (2);

	private final int value;

}
