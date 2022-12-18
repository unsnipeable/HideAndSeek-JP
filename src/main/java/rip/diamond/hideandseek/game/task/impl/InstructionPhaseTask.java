package rip.diamond.hideandseek.game.task.impl;

import me.goodestenglish.api.util.Common;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Sound;
import rip.diamond.hideandseek.game.task.GameTask;
import rip.diamond.hideandseek.util.Util;

public class InstructionPhaseTask extends GameTask {

    public InstructionPhaseTask(int seconds) {
        super(seconds);
    }

    @Override
    public void onRun() {
        if (tick == 0) {
            cancel();
            game.startHiderPhase(30);
        }
        if (Util.ANNOUNCE.contains(tick)) {
            Common.broadcastSound(Sound.UI_BUTTON_CLICK);
        }

        game.getBossBar().name("<yellow>躲藏者將會在<aqua>" + (tick >= 60 ? (tick/60) + 1 + "分鐘" : tick + "秒") + "<yellow>後開始尋找位置躲藏").color(BossBar.Color.YELLOW).progress((float) tick / seconds);
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
