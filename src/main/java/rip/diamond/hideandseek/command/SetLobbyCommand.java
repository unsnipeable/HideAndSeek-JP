package rip.diamond.hideandseek.command;

import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.command.Command;
import me.goodestenglish.api.util.command.CommandArgs;
import me.goodestenglish.api.util.command.argument.CommandArguments;
import me.goodestenglish.api.util.serialization.LocationSerialization;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.Config;
import rip.diamond.hideandseek.HideAndSeek;

public class SetLobbyCommand extends Command {
    @CommandArgs(name = "setlobby", permission = "hideandseek.command.setlobby")
    public void execute(CommandArguments command) {
        Player player = command.getPlayer();
        Location location = player.getLocation();

        Config.LOBBY_LOCATION = location;
        HideAndSeek.INSTANCE.getConfigFile().getConfiguration().set("lobby-location", LocationSerialization.serializeLocation(location));
        HideAndSeek.INSTANCE.getConfigFile().save();

        Common.sendMessage(player, "<green>ロビーを設定しました!");
    }
}
