package dev.toxi.world.commands.common;

import dev.toxi.world.MyWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandHelp {
//    TODO: Add translation strings
    public boolean send(CommandSender sender, String executedCommand, String commandDescription, List<String> usageExamples) {
        sender.sendMessage("§aCommand help:§f " + executedCommand);
        sender.sendMessage("§aDescription:§f " + commandDescription);
        sender.sendMessage("§aUsage(s):");
        for (String s : usageExamples) sender.sendMessage(s);
        return false;
    }
}
