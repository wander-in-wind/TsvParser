package dragon.game.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestType {

	AQ (0),
	FQ (1),
	LQ (2),
	EQ (3),
	DQ (4),
	IQ (5),
	VQ (6),
	WQ (7);

	private final int value;

}
