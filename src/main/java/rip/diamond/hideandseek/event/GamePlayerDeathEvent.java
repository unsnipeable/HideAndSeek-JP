package rip.diamond.hideandseek.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.goodestenglish.api.util.BaseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@RequiredArgsConstructor
public class GamePlayerDeathEvent extends BaseEvent implements Cancellable {

    @Getter private final Player player;
    private boolean cancel;

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
