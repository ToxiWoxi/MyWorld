package dev.toxi.world.gui.playeroption;

import dev.toxi.inventory.DependListener;
import dev.toxi.inventory.OrcItem;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WorldEditStatus
        implements DependListener {
    private final WorldPlayer wp;

    public WorldEditStatus(WorldPlayer wp) {
        this.wp = wp;
    }

    public ItemStack getItemStack(Player p, WorldPlayer player) {
        return this.wp.canWorldedit() ? OrcItem.enabled.getItemStack(p, this.wp) : OrcItem.disabled.getItemStack(p, this.wp);
    }
}
