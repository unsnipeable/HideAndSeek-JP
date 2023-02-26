package rip.diamond.hideandseek.disguise.impl;

import lombok.Getter;
import org.bukkit.entity.Player;
import rip.diamond.hideandseek.disguise.Disguise;

@Getter
public class MobDisguise extends Disguise {

    private final String mob;

    public MobDisguise(Player player, String mob) {
        super(player);
        this.mob = mob;
    }

    @Override
    public void startDisguise() {

    }

    @Override
    public void stopDisguise() {

    }
}
