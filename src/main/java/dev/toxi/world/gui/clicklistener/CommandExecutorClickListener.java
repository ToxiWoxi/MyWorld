package dev.toxi.world.gui.clicklistener;

import dev.toxi.inventory.OrcClickListener;
import dev.toxi.inventory.OrcInventory;
import dev.toxi.inventory.OrcItem;
import org.bukkit.entity.Player;

public class CommandExecutorClickListener implements OrcClickListener {

    private final String message;

    public CommandExecutorClickListener(String message) {
        this.message = message;
    }

    @Override
    public void onClick(Player p, OrcInventory inv, OrcItem item) {
        p.closeInventory();
        p.chat(message);
        // Fix for #9
        inv.redraw(p);
    }
}
