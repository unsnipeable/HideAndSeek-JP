package rip.diamond.hideandseek.command;

import me.goodestenglish.api.util.command.Command;
import me.goodestenglish.api.util.command.CommandArgs;
import me.goodestenglish.api.util.command.argument.CommandArguments;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.menu.GameSettingsMenu;

public class SettingsCommand extends Command {
    @CommandArgs(name = "settings", permission = "hideandseek.command.settings")
    public void execute(CommandArguments command) {
        Player player = command.getPlayer();

        new GameSettingsMenu().openMenu(player);
    }
}
