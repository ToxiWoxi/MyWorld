package dev.toxi.world.gui;

import dev.toxi.inventory.DependListener;
import dev.toxi.inventory.OrcInventory;
import dev.toxi.inventory.OrcItem;
import dev.toxi.world.config.GuiConfig;
import dev.toxi.world.gui.clicklistener.ComingSoonClickListener;
import dev.toxi.world.gui.clicklistener.CommandExecutorClickListener;
import dev.toxi.world.gui.playeroption.BuildStatus;
import dev.toxi.world.gui.playeroption.GamemodeStatus;
import dev.toxi.world.gui.playeroption.TeleportStatus;
import dev.toxi.world.gui.playeroption.WorldEditStatus;
import dev.toxi.world.util.PlayerWrapper;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerOptionsGUI extends OrcInventory {

    private final static String path = "options.player.";

    public PlayerOptionsGUI(Player loader, String otherPlayer, UUID other) {
        super(GuiConfig.getTitle(GuiConfig.getConfig(), "options.player").replace("%player", otherPlayer), GuiConfig.getRows("options.player"), GuiConfig.isFill("options.player"));
        WorldPlayer wp = new WorldPlayer(PlayerWrapper.getOfflinePlayer(other), loader.getWorld().getName());
        loadItem("build", "/ws togglebuild " + otherPlayer, new BuildStatus(wp));
        loadItem("gamemode", "/ws togglegm " + otherPlayer, new GamemodeStatus(wp));
        loadItem("teleport", "/ws toggletp " + otherPlayer, new TeleportStatus(wp));
        loadItem("worldedit", "/ws togglewe " + otherPlayer, new WorldEditStatus(wp));
        loadItem("time");
        loadItem("addmember");
        loadItem("delmember");
        loadItem("setpermissions");
        loadItem("administrateworld");

        if (GuiConfig.isEnabled(path + "back")) {
            OrcItem back = OrcItem.back.clone();
            back.setOnClick((p, inv, i) -> {
                p.closeInventory();
                PlayersPageGUI.openGUI(p);
            });
            addItem(GuiConfig.getSlot(path + "back"), back);
        }
    }

    public void loadItem(String subpath, String message, DependListener depend) {
        if (!GuiConfig.isEnabled(path + subpath))
            return;
        OrcItem item = GuiConfig.getItem(path + subpath);
        if (item != null) {
            if (message == null) {
                item.setOnClick(new ComingSoonClickListener());
            } else {
                item.setOnClick(new CommandExecutorClickListener(message));
            }
            addItem(GuiConfig.getSlot(path + subpath), item);
            if (depend == null) {
                addItem(GuiConfig.getState(path + subpath), OrcItem.coming_soon.clone());
            } else {
                addItem(GuiConfig.getState(path + subpath), OrcItem.disabled.clone().setDepend(depend));
            }
        }
    }

    public void loadItem(String subpath, String message) {
        loadItem(subpath, message, null);
    }

    public void loadItem(String subpath) {
        loadItem(subpath, null);
    }
}
