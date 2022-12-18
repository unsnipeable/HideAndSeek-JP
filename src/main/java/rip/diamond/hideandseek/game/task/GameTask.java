package rip.diamond.hideandseek.game.task;

import me.goodestenglish.api.util.TaskTicker;
import rip.diamond.hideandseek.HideAndSeek;
import rip.diamond.hideandseek.game.Game;

public abstract class GameTask extends TaskTicker {

    public final int seconds;

    public GameTask(int seconds) {
        super(0, 20, false);
        this.seconds = seconds;
    }

    public final Game game = HideAndSeek.INSTANCE.getGame();


}
