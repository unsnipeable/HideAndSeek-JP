package rip.diamond.hideandseek.command;

import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.command.Command;
import me.goodestenglish.api.util.command.CommandArgs;
import me.goodestenglish.api.util.command.argument.CommandArguments;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.menu.DisguiseSelectMenu;

public class DisguiseSelectCommand extends Command {
    @CommandArgs(name = "disguiseselect")
    public void execute(CommandArguments command) {
        Player player = command.getPlayer();

        if (HideAndSeek.INSTANCE.getGame().getSettings().getMap() == null) {
            Common.sendMessage(player, "<red>主辦必須要先選擇一個地圖才能選擇偽裝!");
            return;
        }

        new DisguiseSelectMenu().openMenu(player);
    }
}
