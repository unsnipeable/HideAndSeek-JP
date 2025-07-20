package rip.diamond.hideandseek.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.goodestenglish.api.util.Common;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.enums.GameRole;
import rip.diamond.hideandseek.game.disguise.DisguiseData;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class GamePlayer {

    private final UUID uniqueID;
    private GameRole role = GameRole.NONE;
    private DisguiseData disguises;

    public void reset() {
        role = GameRole.NONE;
        disguises = null;
    }

    public void setDisguises(DisguiseData disguises) {
        this.disguises = disguises;
        Common.sendMessage(getPlayer(), "<aqua>もしあなたが隠れる側なら、" + disguises.getData() + "<yellow>に変身されます!");
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueID);
    }

    public boolean isOnline() {
        return getPlayer() != null && getPlayer().isOnline();
    }

}
