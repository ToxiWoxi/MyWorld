package dev.toxi.world.gui.clicklistener;

import dev.toxi.inventory.OrcClickListener;
import dev.toxi.inventory.OrcInventory;
import dev.toxi.inventory.OrcItem;
import dev.toxi.world.config.MessageConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryOpenClickListener implements OrcClickListener {

    private final OrcInventory open;

    public InventoryOpenClickListener(OrcInventory inv) {
        open = inv;
    }

    @Override
    public void onClick(Player p, OrcInventory inv, OrcItem item) {
        p.closeInventory();
        if (open == null) {
            return;
        }
        Inventory to = open.getInventory(p);
        if (to != null) {
            p.openInventory(to);
        } else {
            p.closeInventory();
            p.sendMessage(MessageConfig.getNoPermission());
        }
    }

}
