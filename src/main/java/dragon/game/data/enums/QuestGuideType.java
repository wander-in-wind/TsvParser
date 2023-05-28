package dragon.game.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestGuideType {

	QUEST_GUIDE_NONE(0),
	QUEST_GUIDE_LOCATION(1),
	QUEST_GUIDE_NPC(2),
	QUEST_GUIDE_GADGET(3),
	QUEST_GUIDE_SHOW_OR_HIDE_NPC(4),
	QUEST_GUIDE_DUNGEON_ENTRY(5);


	private final int value;

}
