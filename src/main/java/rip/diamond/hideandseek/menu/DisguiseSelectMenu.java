package rip.diamond.hideandseek.menu;

import me.goodestenglish.api.menu.Button;
import me.goodestenglish.api.menu.pagination.PaginatedMenu;
import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.enums.DisguiseTypes;
import rip.diamond.hideandseek.game.Game;
import rip.diamond.hideandseek.game.disguise.DisguiseData;

import java.util.HashMap;
import java.util.Map;

public class DisguiseSelectMenu extends PaginatedMenu {
    @Override
    public Component getPrePaginatedTitle(Player player) {
        return Common.text("偽裝選擇");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();
        final Game game = HideAndSeek.INSTANCE.getGame();

        if (game.getSettings().getMap() == null) {
            return buttons;
        }

        for (String data : HideAndSeek.INSTANCE.getMapFile().getStringList("maps." + game.getSettings().getMap() + ".disguises.blocks")) {
            String block = data.split(":")[0];
            String name = data.split(":")[1];
            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder(Material.valueOf(block)).name("<green>" + name).lore("", "<yellow>點擊選擇偽裝成為該方塊!").build();
                }

                @Override
                public void clicked(Player player, ClickType clickType) {
                    game.getGamePlayer(player).setDisguises(new DisguiseData(DisguiseTypes.BLOCK, block, name));
                    player.closeInventory();
                }
            });
        }

        for (String data : HideAndSeek.INSTANCE.getMapFile().getStringList("maps." + game.getSettings().getMap() + ".disguises.mobs")) {
            String mob = data.split(":")[0];
            String name = data.split(":")[1];
            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder(Material.valueOf(mob + "_SPAWN_EGG")).name("<green>" + name).lore("", "<yellow>點擊選擇偽裝成為該生物!").build();
                }

                @Override
                public void clicked(Player player, ClickType clickType) {
                    game.getGamePlayer(player).setDisguises(new DisguiseData(DisguiseTypes.MOB, mob, name));
                    player.closeInventory();
                }
            });
        }

        return buttons;
    }
}
