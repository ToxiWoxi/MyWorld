package dev.toxi.world.gui;

import dev.toxi.inventory.OrcClickListener;
import dev.toxi.inventory.OrcInventory;
import dev.toxi.inventory.OrcItem;
import dev.toxi.world.config.GuiConfig;
import dev.toxi.world.config.WorldConfig;
import dev.toxi.world.gui.clicklistener.InventoryOpenClickListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WorldSystemGUI extends OrcInventory {

    private final static String path = "worldsystem.";

    public WorldSystemGUI() {

        super(GuiConfig.getTitle(GuiConfig.getConfig(), "worldsystem"), GuiConfig.getRows("worldsystem"), GuiConfig.isFill("worldsystem"));

        loadItem("playeroptions", (p, inv, item) -> {
            p.closeInventory();
            PlayersPageGUI.openGUI(p);
        });

        loadItem("worldoptions", new InventoryOpenClickListener(new WorldOptionsGUI()));

        if (GuiConfig.isEnabled(path + "back")) {
            OrcItem back = OrcItem.back.clone();
            back.setOnClick((p, inv, item) -> {
                p.closeInventory();
            });
            addItem(GuiConfig.getSlot(path + "back"), back);
        }
    }

    @Override
    public Inventory getInventory(Player player) {
        PlayersPageGUI.preloadPlayers(WorldConfig.getWorldConfig(player.getWorld().getName()));
        return super.getInventory(player);
    }

    public void loadItem(String subpath, OrcClickListener listener) {
        if (!GuiConfig.isEnabled(path + subpath))
            return;
        OrcItem item = GuiConfig.getItem(path + subpath);
        if (item != null) {
            item.setOnClick(listener);
            addItem(GuiConfig.getSlot(path + subpath), item);
        }
    }

    public void loadItem(String subpath) {
        loadItem(subpath, null);
    }

    public boolean canOpen(Player p) {
        return true;
    }
}