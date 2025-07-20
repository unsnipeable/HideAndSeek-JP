package rip.diamond.hideandseek.menu;

import me.goodestenglish.api.menu.Button;
import me.goodestenglish.api.menu.pagination.PaginatedMenu;
import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.HeadUtil;
import me.goodestenglish.api.util.ItemBuilder;
import me.goodestenglish.api.util.Symbols;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.enums.GameRole;
import rip.diamond.hideandseek.player.GamePlayer;

import java.util.*;

public class RoleSelectAdminMenu extends PaginatedMenu {

    @Override
    public Component getPrePaginatedTitle(Player player) {
        return Common.text("プレイヤーIDを設定する");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player viewer) {
        final Map<Integer, Button> buttons = new HashMap<>();

        for (GamePlayer gamePlayer : HideAndSeek.INSTANCE.getGame().getPlayers().values()) {
            Player player = gamePlayer.getPlayer();
            GameRole role = gamePlayer.getRole();

            ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD).headTexture(HeadUtil.getValue(player)).name(role.getColor() + player.getName());
            builder.lore("");
            builder.lore(Arrays.stream(GameRole.values()).map(gr -> (role == gr ? " <green>" + Symbols.ARROW_RIGHT + " " : "   ") + gr.getColoredName()).toList());
            builder.lore("", "<yellow>クリックして切り替える: " + player.getName() + "");

            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return builder.build();
                }

                @Override
                public void clicked(Player player, ClickType clickType) {
                    int index = role.ordinal();
                    int maxIndex = GameRole.values().length;

                    if (index + 1 == maxIndex) {
                        gamePlayer.setRole(GameRole.values()[0]);
                        return;
                    }
                    gamePlayer.setRole(GameRole.values()[index + 1]);
                    openMenu(player);
                }
            });
        }

        return buttons;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(3, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.STRUCTURE_VOID).name("<yellow>" + GameRole.NONE + "人数: <aqua>" + HideAndSeek.INSTANCE.getGame().getPlayers().values().stream().filter(gp -> gp.getRole() == GameRole.NONE).count()).build();
            }
        });
        buttons.put(4, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.COW_SPAWN_EGG).name("<yellow>" + GameRole.HIDER + "人数: <aqua>" + HideAndSeek.INSTANCE.getGame().getPlayers().values().stream().filter(gp -> gp.getRole() == GameRole.HIDER).count()).build();
            }
        });
        buttons.put(5, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.DIAMOND_SWORD).name("<yellow>" + GameRole.SEEKER + "人数: <aqua>" + HideAndSeek.INSTANCE.getGame().getPlayers().values().stream().filter(gp -> gp.getRole() == GameRole.SEEKER).count()).build();
            }
        });

        return buttons;
    }
}
