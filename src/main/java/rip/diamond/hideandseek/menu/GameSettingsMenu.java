package rip.diamond.hideandseek.menu;

import me.goodestenglish.api.menu.Button;
import me.goodestenglish.api.menu.Menu;
import me.goodestenglish.api.menu.button.IntegerButton;
import me.goodestenglish.api.menu.button.ToggleButton;
import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.game.Game;

import java.util.HashMap;
import java.util.Map;

public class GameSettingsMenu extends Menu {
    @Override
    public Component getTitle(Player player) {
        return Common.text("ゲーム内設定");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();
        final Game game = HideAndSeek.INSTANCE.getGame();

        buttons.put(0, new ToggleButton() {
            @Override
            public String getOptionName() {
                return "<green>隠れるブロックを選択";
            }

            @Override
            public String getDescription() {
                return "<gray>プレイヤーが自分の隠れるブロックを選択できるようにすべきでしょうか?";
            }

            @Override
            public boolean isEnabled(Player player) {
                return game.getSettings().isAllowPublicRoleSelect();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                game.getSettings().setAllowPublicRoleSelect(!game.getSettings().isAllowPublicRoleSelect());
                openMenu(player);
            }
        });

        buttons.put(1, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.MAP).name("<green><bold>マップ").lore("","<gray>現在のマップ: " + (game.getSettings().getMap() == null ? "<red>未設定" : "<green>" + game.getSettings().getMap()),"","<yellow>クリックしてマップを選択").build();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                new MapSelectMenu(GameSettingsMenu.this).openMenu(player);
            }
        });

        buttons.put(2, new IntegerButton() {
            @Override
            public Material getMaterial() {
                return Material.PLAYER_HEAD;
            }

            @Override
            public String getOptionName() {
                return "鬼の人数の最大の";
            }

            @Override
            public String getDescription() {
                return "鬼側の最大数を設定する";
            }

            @Override
            public String getCurrentValue() {
                return game.getSettings().getMaxSeekers() == -1 ? "<red>未設定" : game.getSettings().getMaxSeekers() + "";
            }

            @Override
            public void plus1(Player player) {
                game.getSettings().setMaxSeekers(Math.max(1, game.getSettings().getMaxSeekers() + 1));
            }

            @Override
            public void plus10(Player player) {
                game.getSettings().setMaxSeekers(Math.max(1, game.getSettings().getMaxSeekers() + 10));
            }

            @Override
            public void minus1(Player player) {
                game.getSettings().setMaxSeekers(Math.max(1, game.getSettings().getMaxSeekers() - 1));
            }

            @Override
            public void minus10(Player player) {
                game.getSettings().setMaxSeekers(Math.max(1, game.getSettings().getMaxSeekers() - 10));
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                super.clicked(player, clickType);
                openMenu(player);
            }
        });

        return buttons;
    }
}
