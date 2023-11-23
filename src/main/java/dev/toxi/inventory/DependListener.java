package dev.toxi.inventory;

import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface DependListener {

    ItemStack getItemStack(Player p, WorldPlayer wp);

}
