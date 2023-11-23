package dev.toxi.world.gui.clicklistener;

import dev.toxi.inventory.OrcClickListener;
import dev.toxi.inventory.OrcInventory;
import dev.toxi.inventory.OrcItem;
import org.bukkit.entity.Player;

public class ComingSoonClickListener implements OrcClickListener {

    @Override
    public void onClick(Player p, OrcInventory inv, OrcItem item) {
        p.closeInventory();
        p.sendMessage("§cComing soon...");
    }

}
