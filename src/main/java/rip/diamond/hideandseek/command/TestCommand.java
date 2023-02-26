package rip.diamond.hideandseek.command;

import me.goodestenglish.api.util.command.Command;
import me.goodestenglish.api.util.command.CommandArgs;
import me.goodestenglish.api.util.command.argument.CommandArguments;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.game.disguise.DisguiseData;
import rip.diamond.hideandseek.player.GamePlayer;
import rip.diamond.hideandseek.util.Util;

public class TestCommand extends Command {
    @CommandArgs(name = "test", permission = "*")
    public void execute(CommandArguments command) {
        Player player = command.getPlayer();

        GamePlayer gamePlayer = HideAndSeek.INSTANCE.getGame().getGamePlayer(player);
        gamePlayer.setDisguises(new DisguiseData());
        Util.disguise(player);
    }
}
