package dev.toxi.world.gui.playeroption;

import dev.toxi.inventory.DependListener;
import dev.toxi.inventory.OrcItem;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuildStatus implements DependListener {

    private final WorldPlayer wp;

    public BuildStatus(WorldPlayer wp) {
        this.wp = wp;
    }

    @Override
    public ItemStack getItemStack(Player p, WorldPlayer player) {
        return wp.canBuild() ? OrcItem.enabled.getItemStack(p, wp) : OrcItem.disabled.getItemStack(p, wp);
    }
}
