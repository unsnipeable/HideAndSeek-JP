package rip.diamond.hideandseek.command;

import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.command.Command;
import me.goodestenglish.api.util.command.CommandArgs;
import me.goodestenglish.api.util.command.argument.CommandArguments;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.player.GamePlayer;

public class StopDisguiseCommand extends Command {
    @CommandArgs(name = "stopdisguise", permission = "hideandseek.command.stopdisguise")
    public void execute(CommandArguments command) {
        Player player = command.getPlayer();
        GamePlayer gamePlayer = HideAndSeek.INSTANCE.getGame().getGamePlayer(player);

        if (gamePlayer.getDisguises() == null) {
            Common.sendMessage(player, "<red>你沒有一個 DisguiseData");
            return;
        }

        if (gamePlayer.getDisguises().getDisguise() != null) {
            Common.sendMessage(player, "<red>你沒有一個 Disguise");
            return;
        }

        gamePlayer.getDisguises().getDisguise().stopDisguise();
        Common.sendMessage(player, "<green>成功");
    }
}
