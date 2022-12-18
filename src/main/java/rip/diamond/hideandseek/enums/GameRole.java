package rip.diamond.hideandseek.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public enum GameRole {

    NONE("尚未設置", "<gray>", null, new ItemStack[]{}),
    HIDER("躲藏者", "<aqua>", "在限時之內沒被尋找者發現", new ItemStack[]{

    }),
    SEEKER("尋找者", "<red>", "在限時之內找到所有的躲藏者", new ItemStack[]{
            new ItemStack(Material.NETHERITE_SWORD)
    });

    private final String name;
    private final String color;
    private final String goal;
    private final ItemStack[] tools;

    @Override
    public String toString() {
        return name;
    }

    public String getColoredName() {
        return color + name;
    }
}
