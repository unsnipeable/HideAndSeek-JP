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
    private boolean dead = false;

    public void reset() {
        role = GameRole.NONE;
        disguises = null;
        dead = false;
    }

    public void setDisguises(DisguiseData disguises) {
        this.disguises = disguises;
        Common.sendMessage(getPlayer(), "<yellow>如果你是躲藏者, 你將會在遊戲裏偽裝成為<aqua>" + disguises.getData() + "<yellow>!");
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueID);
    }

    public boolean isOnline() {
        return getPlayer() != null && getPlayer().isOnline();
    }

}
