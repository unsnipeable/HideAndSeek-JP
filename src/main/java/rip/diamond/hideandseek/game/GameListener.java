package rip.diamond.hideandseek.game;

import me.goodestenglish.api.util.Common;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import rip.diamond.hideandseek.Config;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.enums.GameState;
import rip.diamond.hideandseek.event.GamePlayerDeathEvent;
import rip.diamond.hideandseek.player.GamePlayer;
import rip.diamond.hideandseek.util.PlayerUtil;

public class GameListener implements Listener {

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        Game game = HideAndSeek.INSTANCE.getGame();

        if (game.isStarted()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Common.text("<red>遊戲已開始, 無法進入"));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Game game = HideAndSeek.INSTANCE.getGame();

        game.getPlayers().putIfAbsent(player.getUniqueId(), new GamePlayer(player.getUniqueId()));

        PlayerUtil.reset(player);
        player.teleport(Config.LOBBY_LOCATION);

        event.joinMessage(Common.text("<gray>[<green>+<gray>] <yellow>" + player.getName()));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Game game = HideAndSeek.INSTANCE.getGame();

        game.getPlayers().remove(player.getUniqueId());

        event.quitMessage(Common.text("<gray>[<red>-<gray>] <yellow>" + player.getName()));

        if (game.canEnd()) {
            game.end();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (HideAndSeek.INSTANCE.getGame().getState() != GameState.SEEKER_PHASE) {
            event.setCancelled(true);
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            event.setCancelled(true);
            return;
        }

        if (event.getEntity() instanceof Player player) {
            double health = player.getHealth();
            if (health - event.getDamage() > 0) {
                return;
            }
            GamePlayerDeathEvent e = new GamePlayerDeathEvent(player);
            e.callEvent();
            if (e.isCancelled()) {
                event.setDamage(0);
            }
            return;
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Game game = HideAndSeek.INSTANCE.getGame();
        if (event.getEntity() instanceof Player entity && event.getDamager() instanceof Player damager) {
            GamePlayer gameEntity = game.getGamePlayer(entity);
            GamePlayer gameDamager = game.getGamePlayer(damager);

            if (gameEntity.getRole() == gameDamager.getRole()) {
                event.setCancelled(true);
                return;
            }

            // TODO: 17/12/2022 Reveal block
        }
    }

    @EventHandler
    public void onDeath(GamePlayerDeathEvent event) {
        event.setCancelled(true);

        Game game = HideAndSeek.INSTANCE.getGame();
        Player player = event.getPlayer();
        GamePlayer gamePlayer = game.getGamePlayer(player);

        gamePlayer.setDead(true);
        gamePlayer.getDisguises().getDisguise().stopDisguise();
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setGameMode(GameMode.SPECTATOR);

        if (game.canEnd()) {
            game.end();
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (event.getEntityType() == EntityType.ARROW) {
            projectile.remove();
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            entity.remove();
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        event.getWorld().setDifficulty(Difficulty.HARD);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.DEFAULT) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(EntityDropItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (player.getGameMode() != GameMode.CREATIVE && action == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);
        }
    }

}
