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
            Common.sendMessage(player, "<red>カウントダウンを開始しようとしたときにエラーが発生しました: マップがまだ選択されていません! '/settings'コマンドを使用してマップを選択してください！");
            return;
        }

        if (args.length == 1 && Checker.isInteger(args[0])) {
            HideAndSeek.INSTANCE.getGame().startCountdown(Integer.parseInt(args[0]));

            Common.sendMessage(player, "<green>カウントダウンが始まります！");
            return;
        }

        Common.sendMessage(player, "<red>使い方: /countdown <int>");
    }
}
