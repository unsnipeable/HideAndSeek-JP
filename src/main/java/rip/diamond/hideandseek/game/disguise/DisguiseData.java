package rip.diamond.hideandseek.game.disguise;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.enums.DisguiseTypes;
import rip.diamond.hideandseek.game.Game;
import rip.diamond.hideandseek.util.Util;

@Getter
@Setter
public class DisguiseData {

    private DisguiseTypes type;
    private String data;
    private Disguise disguise;

    public DisguiseData() {
        Game game = HideAndSeek.INSTANCE.getGame();

        this.type = Util.random(DisguiseTypes.BLOCK, DisguiseTypes.MOB);
        if (this.type == DisguiseTypes.BLOCK) {
            this.data = Util.random(HideAndSeek.INSTANCE.getMapFile().getStringList("maps." + game.getSettings().getMap() + ".disguises.blocks"));
        } else if (this.type == DisguiseTypes.MOB) {
            this.data = Util.random(HideAndSeek.INSTANCE.getMapFile().getStringList("maps." + game.getSettings().getMap() + ".disguises.mobs"));
        } else {
            throw new NullPointerException("Cannot find a suitable DisguiseTypes");
        }
    }

    public DisguiseData(DisguiseTypes type, String data) {
        this.type = type;
        this.data = data;
    }

}
