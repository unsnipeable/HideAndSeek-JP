package rip.diamond.hideandseek;

import lombok.RequiredArgsConstructor;
import me.goodestenglish.api.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public enum Items {

    TRANSFORM_TOOL(new ItemBuilder(Material.REDSTONE).name("<yellow>返信").lore("<gray>本物のブロックに変身").build()),
    TELEPORT_TOOL(new ItemBuilder(Material.CLOCK).name("<yellow>ピン留め").lore("<gray>現在の場所をピン留めします。", "<gray>注意: 他のプレイヤーは変装を通り抜けることができます").build()),
    KNOCKBACK_STICK(new ItemBuilder(Material.STICK).name("<yellow>鈍器").lore("<gray>近くのプレイヤーをノックバックします。").enchantment(Enchantment.KNOCKBACK, 1).build()),
    NETHERITE_SWORD(new ItemBuilder(Material.NETHERITE_SWORD).unbreakable().build()),
    ;

    private final ItemStack item;

    public ItemStack getItem() {
        return item.clone();
    }
}
