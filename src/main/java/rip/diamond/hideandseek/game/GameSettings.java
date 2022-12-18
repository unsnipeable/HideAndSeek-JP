package rip.diamond.hideandseek.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSettings {

    private boolean allowPublicRoleSelect = true;
    private String map = null;
    private int maxSeekers = -1;

}
