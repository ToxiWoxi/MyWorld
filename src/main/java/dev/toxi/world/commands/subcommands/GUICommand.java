package dev.toxi.world.commands.subcommands;

import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.gui.MyWorldGUI;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GUICommand implements CommandExecutor {
    //    TODO: Rewrite permissions!
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            WorldPlayer wp = new WorldPlayer(p);
            if (!wp.isOnSystemWorld()) {
                p.sendMessage(MessageConfig.getNotOnWorld());
                return false;
            }
            if (!wp.isOwnerofWorld()) {
                p.sendMessage(MessageConfig.getNoPermission());
                return false;
            }
            p.openInventory(new MyWorldGUI().getInventory(p));
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }
}
