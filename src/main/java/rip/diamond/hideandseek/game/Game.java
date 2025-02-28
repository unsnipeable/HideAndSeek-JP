package rip.diamond.hideandseek.game;

import lombok.Getter;
import lombok.Setter;
import me.goodestenglish.api.bossbar.impl.GlobalBossBar;
import me.goodestenglish.api.util.Common;
import me.goodestenglish.api.util.TaskTicker;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rip.diamond.hideandseek.Config;
import rip.diamond.hideandseek.enums.GameRole;
import rip.diamond.hideandseek.enums.GameState;
import rip.diamond.hideandseek.game.disguise.DisguiseData;
import rip.diamond.hideandseek.game.task.impl.*;
import rip.diamond.hideandseek.player.GamePlayer;
import rip.diamond.hideandseek.util.PlayerUtil;
import rip.diamond.hideandseek.util.Util;

import java.util.*;

@Getter
@Setter
public class Game {

    public static final String DISGUISE_KEY = "disguise";

    private final Map<UUID, GamePlayer> players = new HashMap<>();
    private GameMap map = new GameMap();
    private GameSettings settings = new GameSettings();
    private GameState state = GameState.WAITING;
    private GlobalBossBar bossBar;
    private TaskTicker currentTask;

    public void startCountdown(int seconds) {
        state = GameState.STARTING;
        bossBar = new GlobalBossBar(BossBar.bossBar(Common.text("<yellow>準備開始倒數"), 1, BossBar.Color.YELLOW, BossBar.Overlay.PROGRESS), "game");
        currentTask = new CountdownPhaseTask(seconds);
    }

    public void generateWorld() {
        bossBar.name("<yellow>正在生成地圖").progress(1);

        map.generateMap(bool -> {
            if (bool) {
                startInstructionPhase(10);
            } else {
                Common.broadcastMessage("<red>在生成地圖的時候發生錯誤, 請聯繫管理員");
            }
        });
    }

    public void startInstructionPhase(int seconds) {
        state = GameState.INSTRUCTION_PHASE;

        List<GamePlayer> gamePlayers = new ArrayList<>(players.values());
        Collections.shuffle(gamePlayers);
        for (GamePlayer gamePlayer : gamePlayers) {
            Player player = gamePlayer.getPlayer();

            if (gamePlayer.getRole() == GameRole.NONE) {
                if (players.values().stream().filter(gp -> gp.getRole() == GameRole.SEEKER).count() < (settings.getMaxSeekers() == -1 ? (gamePlayers.size() / 5) + 1 : settings.getMaxSeekers())) {
                    gamePlayer.setRole(GameRole.SEEKER);
                } else {
                    gamePlayer.setRole(GameRole.HIDER);
                }
            }

            if (gamePlayer.getDisguises() == null) {
                Common.sendMessage(player, "<yellow>由於你沒有設置偽裝, 所以系統將會隨機幫你設置一個偽裝");
                gamePlayer.setDisguises(new DisguiseData());
            }

            Common.sendMessage(player, "","<yellow>你現在是" + gamePlayer.getRole().getColoredName() + "<yellow>!");
            if (gamePlayer.getRole().getGoal() != null) {
                Common.sendMessage(player, "<yellow>獲勝目標: <dark_aqua>" + gamePlayer.getRole().getGoal());
            }
            Common.sendMessage(player, "");
        }

        currentTask = new InstructionPhaseTask(seconds);
    }

    public void startHiderPhase(int seconds) {
        state = GameState.HIDING_PHASE;

        Common.broadcastMessage("","<yellow>遊戲已<green>開始<yellow>!",GameRole.HIDER.getColoredName() + "<yellow>，你們有<aqua>" + seconds + "秒<yellow>的時間去尋找適合的位置躲藏","");
        
        for (GamePlayer gamePlayer : players.values()) {
            Player player = gamePlayer.getPlayer();
            GameRole role = gamePlayer.getRole();
            
            if (role == GameRole.HIDER) {
                map.teleport(player);
                gamePlayer.getDisguises().setDisguise(Util.disguise(player));
                player.getInventory().setContents(role.getTools());
            } else if (role == GameRole.SEEKER) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, true, false));
            }
        }

        currentTask = new HiderPhaseTask(seconds);
    }

    public void startSeekerPhase(int seconds) {
        state = GameState.SEEKER_PHASE;

        Common.broadcastMessage("",GameRole.SEEKER.getColoredName() + "<yellow>已被釋放!","<yellow>如果" + GameRole.SEEKER.getColoredName() + "<yellow>在<aqua>" + (seconds >= 60 ? (seconds/60) + "分鐘" : seconds + "秒") + "<yellow>後還沒找到所有" + GameRole.HIDER.getColoredName() + "<yellow>," + GameRole.SEEKER.getColoredName() + "<yellow>將會獲勝!","<yellow>祝各位好運!","");

        for (GamePlayer gamePlayer : players.values()) {
            Player player = gamePlayer.getPlayer();
            GameRole role = gamePlayer.getRole();

            if (role == GameRole.SEEKER) {
                map.teleport(player);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.getInventory().setContents(role.getTools());
            }
        }

        currentTask = new SeekerPhaseTask(seconds);
    }

    public void end() {
        state = GameState.ENDING;

        currentTask.cancel(); //這個 function 可以在遊戲運行的時候使用, 所以我們必須要先cancel
        currentTask = new EndTask();
    }

    public void reset() {
        players.values().forEach(gp -> {
            gp.reset();
            PlayerUtil.reset(gp.getPlayer());
            gp.getPlayer().teleport(Config.LOBBY_LOCATION);
        });
        map = new GameMap();
        //GameSettings 的東西可以保留, 所以不需要重置GameSettings
        state = GameState.WAITING;
        if (bossBar != null) {
            bossBar.destroy();
            bossBar = null;
        }
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }
    }

    public boolean isStarted() {
        return state == GameState.INSTRUCTION_PHASE || state == GameState.HIDING_PHASE || state == GameState.SEEKER_PHASE || state == GameState.ENDING;
    }

    public boolean canEnd() {
        return players.values().stream().noneMatch(gp -> gp.getRole() == GameRole.HIDER) || players.values().stream().noneMatch(gp -> gp.getRole() == GameRole.SEEKER);
    }

    public GamePlayer getGamePlayer(Player player) {
        return getGamePlayer(player.getUniqueId());
    }

    public GamePlayer getGamePlayer(UUID uuid) {
        return players.get(uuid);
    }

}
