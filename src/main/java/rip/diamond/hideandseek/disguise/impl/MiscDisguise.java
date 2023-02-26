package rip.diamond.hideandseek.disguise.impl;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.disguise.Disguise;

@Getter
public class MiscDisguise extends Disguise {

    private final Material material;
    private ArmorStand armorStand;
    private FallingBlock fallingBlock;

    public MiscDisguise(Player player, Material material) {
        super(player);
        this.material = material;
    }

    @Override
    public void startDisguise() {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false, false));
        player.setCollidable(false);

        if (!material.isBlock()) {
            throw new UnsupportedOperationException("The selected material " + material.name() + " is not a block");
        }

        fallingBlock = player.getLocation().getWorld().spawnFallingBlock(player.getLocation(), material, (byte) 0);
        fallingBlock.setGravity(false);
        fallingBlock.setDropItem(false);
        fallingBlock.setHurtEntities(false);
        fallingBlock.setInvulnerable(true);
        fallingBlock.setPersistent(true);
        fallingBlock.setSilent(true);
        fallingBlock.setVelocity(new Vector(0,0,0));
        fallingBlock.setTicksLived(1);

        armorStand = (ArmorStand) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        armorStand.setMarker(true);
        armorStand.addPassenger(fallingBlock);

        task = new BukkitRunnable() {
            @Override
            public void run() {
                fallingBlock.setTicksLived(1);
                armorStand.eject();
                armorStand.teleport(player.getLocation());
                armorStand.addPassenger(fallingBlock);
            }
        }.runTaskTimer(HideAndSeek.INSTANCE, 0, 1L);
    }

    @Override
    public void stopDisguise() {
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.setCollidable(true);

        task.cancel();
        fallingBlock.remove();
        armorStand.remove();
    }
}
