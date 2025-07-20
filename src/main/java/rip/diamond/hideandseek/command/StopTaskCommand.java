package rip.diamond.hideandseek.command;

import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.command.Command;
import me.goodestenglish.api.util.command.CommandArgs;
import me.goodestenglish.api.util.command.argument.CommandArguments;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.HideAndSeek;

public class StopTaskCommand extends Command {
    @CommandArgs(name = "stoptask", permission = "hideandseek.command.stoptask")
    public void execute(CommandArguments command) {
        Player player = command.getPlayer();

        HideAndSeek.INSTANCE.getGame().getCurrentTask().cancel();
        HideAndSeek.INSTANCE.getGame().setCurrentTask(null);

        Common.sendMessage(player, "<green>実行中のBukkitTaskを停止しました");
    }
}
