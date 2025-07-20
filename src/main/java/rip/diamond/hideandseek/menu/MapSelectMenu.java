package rip.diamond.hideandseek.menu;

import lombok.RequiredArgsConstructor;
import me.goodestenglish.api.menu.Button;
import me.goodestenglish.api.menu.Menu;
import me.goodestenglish.api.menu.pagination.PaginatedMenu;
import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.game.Game;

import java.io.File;
import java.util.*;

@RequiredArgsConstructor
public class MapSelectMenu extends PaginatedMenu {

    private final Menu backMenu;

    @Override
    public Component getPrePaginatedTitle(Player player) {
        return Common.text("マップ選択");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();
        final Game game = HideAndSeek.INSTANCE.getGame();
        final List<File> mapFolder = Arrays.stream(Objects.requireNonNull(new File("plugins/" + HideAndSeek.INSTANCE.getDescription().getName() + "/maps/").listFiles())).filter(File::isDirectory).toList();
        final ConfigurationSection mapsSelection = HideAndSeek.INSTANCE.getMapFile().getConfiguration().getConfigurationSection("maps");

        assert mapsSelection != null;

        for (File file : mapFolder) {
            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder(Material.MAP).name("<green>" + file.getName()).lore("", mapsSelection.contains(file.getName()) ? "<yellow>クリックしてマップを選択してください!" : "<red>マップはまだ設定されていません").build();
                }

                @Override
                public void clicked(Player player, ClickType clickType) {
                    if (mapsSelection.contains(file.getName())) {
                        game.getSettings().setMap(file.getName());
                        backMenu.openMenu(player);
                    }
                }
            });
        }

        return buttons;
    }
}
