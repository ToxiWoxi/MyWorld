package dev.toxi.world.gui;

import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You are not a player");
            return true;
        }
        WorldPlayer wp = new WorldPlayer((Player) sender);
        if (!wp.isOnSystemWorld()) {
            sender.sendMessage(MessageConfig.getNotOnWorld());
            return true;
        }
        if (!wp.isOwnerofWorld()) {
            sender.sendMessage(MessageConfig.getNoPermission());
            return true;
        }
        ((Player) sender).openInventory(new MyWorldGUI().getInventory((Player) sender));
        return true;
    }

}
