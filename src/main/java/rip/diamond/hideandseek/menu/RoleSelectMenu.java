package rip.diamond.hideandseek.menu;

import me.goodestenglish.api.menu.Button;
import me.goodestenglish.api.menu.Menu;
import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.enums.GameRole;
import rip.diamond.hideandseek.game.Game;
import rip.diamond.hideandseek.player.GamePlayer;

import java.util.HashMap;
import java.util.Map;

public class RoleSelectMenu extends Menu {

    @Override
    public Component getTitle(Player player) {
        return Common.text("選擇身份");
    }

    @Override
    public int getSize() {
        return 9*3;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();
        GamePlayer gamePlayer = HideAndSeek.INSTANCE.getGame().getGamePlayer(player);

        buttons.put(11, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.COW_SPAWN_EGG).name(GameRole.HIDER.getColoredName()).lore("<gray>躲藏者需逃避尋找者的追殺", "<gray>並在限時之內沒被尋找者擊殺為勝利", "", gamePlayer.getRole() == GameRole.HIDER ? "<green>已選擇" : "<yellow>點擊設置身份為" + GameRole.HIDER.getColoredName()).glow(gamePlayer.getRole() == GameRole.HIDER).build();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                selectRole(gamePlayer, GameRole.HIDER);
                openMenu(player);
            }
        });

        buttons.put(15, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.DIAMOND_SWORD).name(GameRole.SEEKER.getColoredName()).lore("<gray>尋找者需要在限時之內找到所有的躲藏者", "", gamePlayer.getRole() == GameRole.SEEKER ? "<green>已選擇" : "<yellow>點擊設置身份為" + GameRole.SEEKER.getColoredName()).glow(gamePlayer.getRole() == GameRole.SEEKER).build();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                selectRole(gamePlayer, GameRole.SEEKER);
                openMenu(player);
            }
        });

        return buttons;
    }

    private void selectRole(GamePlayer gamePlayer, GameRole role) {
        Game game = HideAndSeek.INSTANCE.getGame();
        Player player = gamePlayer.getPlayer();
        if (game.isStarted()) {
            Common.sendMessage(player, "<red>你不能在遊戲開始後設置身份");
            return;
        }
        if (!game.getSettings().isAllowPublicRoleSelect()) {
            Common.sendMessage(player, "<red>你不能夠選擇身份, 因為主辦沒有開啟公開選擇隊伍");
            return;
        }
        gamePlayer.setRole(role);
        Common.sendMessage(player, "<yellow>已把你的身份設置為: " + role.getColoredName());
    }
}
