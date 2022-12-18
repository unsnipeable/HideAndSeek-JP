package rip.diamond.hideandseek.game.task.impl;

import me.goodestenglish.api.util.Common;
import net.kyori.adventure.bossbar.BossBar;
import rip.diamond.hideandseek.enums.GameRole;
import rip.diamond.hideandseek.game.task.GameTask;

import java.util.stream.Collectors;

public class EndTask extends GameTask {

    private final GameRole winner;

    public EndTask() {
        super(10);

        if (game.getPlayers().values().stream().noneMatch(gp -> gp.getRole() == GameRole.HIDER && !gp.isDead())) {
            winner = GameRole.SEEKER;
        } else {
            winner = GameRole.HIDER;
        }
    }

    @Override
    public void preRun() {
        Common.broadcastMessage(
                "",
                "<yellow>遊戲結束!",
                winner.getColoredName() + "<green>獲勝!",
                winner.getColoredName() + "<yellow>成員包括: <aqua>" + game.getPlayers().values().stream().filter(gp -> gp.getRole() == winner).map(gp -> gp.getPlayer().getName()).collect(Collectors.joining("<gray>, <aqua>")),
                ""
        );

        game.getBossBar().name("<green>遊戲結束! " + winner.getColoredName() + "<green>獲勝!").color(BossBar.Color.GREEN).progress(1);
    }

    @Override
    public void onRun() {
        if (tick == 0) {
            cancel();
            game.reset();
        }
    }

    @Override
    public TickType getTickType() {
        return TickType.COUNT_DOWN;
    }

    @Override
    public int getStartTick() {
        return seconds;
    }
}
