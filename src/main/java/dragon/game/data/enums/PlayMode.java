package dragon.game.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayMode {

    PLAY_MODE_ALL(0),
    PLAY_MODE_SINGLE(1),
    PLAY_MODE_MULTIPLE(2),
    PLAY_MODE_HOST(3),
    PLAY_MODE_GUEST(4);

    private final int value;
}
