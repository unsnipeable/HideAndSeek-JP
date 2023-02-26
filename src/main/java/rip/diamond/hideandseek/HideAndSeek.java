package rip.diamond.hideandseek;

import lombok.Getter;
import me.goodestenglish.api.DiamondAPI;
import me.goodestenglish.api.util.BasicConfigFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import rip.diamond.hideandseek.command.*;
import rip.diamond.hideandseek.game.Game;
import rip.diamond.hideandseek.game.GameListener;

import java.util.Arrays;

@Getter
public final class HideAndSeek extends JavaPlugin {

    public static HideAndSeek INSTANCE;

    private BasicConfigFile configFile;
    private BasicConfigFile mapFile;

    private Game game;

    @Override
    public void onEnable() {
        INSTANCE = this;

        DiamondAPI.get().enable(this);

        configFile = new BasicConfigFile(this, "config.yml");
        mapFile = new BasicConfigFile(this, "map.yml");

        game = new Game();

        Arrays.asList(new GameListener()).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        new CountdownCommand();
        new DisguiseSelectCommand();
        new RoleSelectCommand();
        new SetLobbyCommand();
        new SettingsCommand();
        new StopDisguiseCommand();
        new StopTaskCommand();
        new TestCommand();
    }

    @Override
    public void onDisable() {
        DiamondAPI.get().disable();
    }
}
