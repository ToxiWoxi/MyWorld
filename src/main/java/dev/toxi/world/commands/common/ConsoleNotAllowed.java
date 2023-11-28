package dev.toxi.world.commands.common;

import dev.toxi.world.MyWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConsoleNotAllowed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MyWorld.getInstance().getServer().getConsoleSender().sendMessage("§f[§aMyWorld§f] §4ERROR:§f This command cannot be used by the console!");
        return false;
    }

}
