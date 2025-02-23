package rip.diamond.hideandseek.command;

import me.goodestenglish.api.util.Checker;
import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.command.Command;
import me.goodestenglish.api.util.command.CommandArgs;
import me.goodestenglish.api.util.command.argument.CommandArguments;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.HideAndSeek;

public class CountdownCommand extends Command {
    @CommandArgs(name = "countdown", permission = "hideandseek.command.countdown")
    public void execute(CommandArguments command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (HideAndSeek.INSTANCE.getGame().getSettings().getMap() == null) {
            Common.sendMessage(player, "<red>嘗試開始倒數時發生錯誤: 地圖還沒選擇, 請使用指令 '/settings' 設置地圖");
            return;
        }

        if (args.length == 1 && Checker.isInteger(args[0])) {
            HideAndSeek.INSTANCE.getGame().startCountdown(Integer.parseInt(args[0]));

            Common.sendMessage(player, "<green>成功開始倒數");
            return;
        }

        Common.sendMessage(player, "<red>指令用法: /countdown <int>");
    }
}
