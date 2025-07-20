package rip.diamond.hideandseek.game.task.impl;

import me.goodestenglish.api.util.Common;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Sound;
import rip.diamond.hideandseek.game.task.GameTask;
import rip.diamond.hideandseek.util.Util;

public class CountdownPhaseTask extends GameTask {
    public CountdownPhaseTask(int seconds) {
        super(seconds);
    }

    @Override
    public void onRun() {
        if (tick == 0) {
            cancel();
            game.generateWorld();
            return;
        }
        if (Util.ANNOUNCE.contains(tick)) {
            Common.broadcastSound(Sound.UI_BUTTON_CLICK);
        }

        game.getBossBar().name("<yellow>この試合は<aqua>" + (tick >= 60 ? (tick/60) + 1 + "分" : tick + "秒") + "<yellow>後に開始します").color(BossBar.Color.YELLOW).progress((float) tick / seconds);
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
