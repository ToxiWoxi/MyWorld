package dev.toxi.inventory;

import org.bukkit.entity.Player;

public interface OrcClickListener {

    void onClick(Player p, OrcInventory inv, OrcItem item);

}
