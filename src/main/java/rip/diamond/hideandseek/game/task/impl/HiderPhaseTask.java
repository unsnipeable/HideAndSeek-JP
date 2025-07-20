package rip.diamond.hideandseek.game.task.impl;

import me.goodestenglish.api.util.Common;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Sound;
import rip.diamond.hideandseek.Config;
import rip.diamond.hideandseek.game.task.GameTask;
import rip.diamond.hideandseek.util.Util;

public class HiderPhaseTask extends GameTask {
    public HiderPhaseTask(int seconds) {
        super(seconds);
    }

    @Override
    public void onRun() {
        if (tick == 0) {
            cancel();
            game.startSeekerPhase(Config.GAME_TIME_SECOND);
        }
        if (Util.ANNOUNCE.contains(tick)) {
            Common.broadcastSound(Sound.UI_BUTTON_CLICK);
        }

        game.getBossBar().name("<yellow>シーカーは<aqua>" + (tick >= 60 ? (tick/60) + 1 + "分" : tick + "秒") + "<yellow>後にハイダーを探し始めます!").color(BossBar.Color.YELLOW).progress((float) tick / seconds);
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
