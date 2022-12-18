package rip.diamond.hideandseek.command;

import me.goodestenglish.api.util.command.Command;
import me.goodestenglish.api.util.command.CommandArgs;
import me.goodestenglish.api.util.command.argument.CommandArguments;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.menu.RoleSelectAdminMenu;
import rip.diamond.hideandseek.menu.RoleSelectMenu;

public class RoleSelectCommand extends Command {
    @CommandArgs(name = "roleselect")
    public void execute(CommandArguments command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 1 && args[0].equalsIgnoreCase("admin") && player.hasPermission("hideandseek.command.roleselect.admin")) {
            new RoleSelectAdminMenu().openMenu(player);
            return;
        }

        new RoleSelectMenu().openMenu(player);
    }
}
