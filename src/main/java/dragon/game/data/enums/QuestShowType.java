package dragon.game.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestShowType {

	QUEST_SHOW (0),
	QUEST_HIDDEN (1);

	private final int value;

}
