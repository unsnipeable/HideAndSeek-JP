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
@RequiredArgsConstructor
public class DisguiseData {

    private final DisguiseTypes type;
    private final String data;
    private final String name;
    @Setter private Disguise disguise;

    public DisguiseData() {
        Game game = HideAndSeek.INSTANCE.getGame();

        this.type = Util.random(DisguiseTypes.BLOCK, DisguiseTypes.MOB);
        if (this.type == DisguiseTypes.BLOCK) {
            String[] data = Util.random(HideAndSeek.INSTANCE.getMapFile().getStringList("maps." + game.getSettings().getMap() + ".disguises.blocks")).split(":");
            this.data = data[0];
            this.name = data[1];
        } else if (this.type == DisguiseTypes.MOB) {
            String[] data = Util.random(HideAndSeek.INSTANCE.getMapFile().getStringList("maps." + game.getSettings().getMap() + ".disguises.mobs")).split(":");
            this.data = data[0];
            this.name = data[1];
        } else {
            throw new NullPointerException("Cannot find a suitable DisguiseTypes");
        }
    }

}
