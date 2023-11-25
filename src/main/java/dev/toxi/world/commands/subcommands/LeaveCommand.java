package dev.toxi.world.commands.subcommands;

import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.PluginConfig;
import dev.toxi.world.wrapper.SystemWorld;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {
    //    TODO: Rewrite permissions!
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String worldname = p.getWorld().getName();
            WorldPlayer wp = new WorldPlayer(p, worldname);

            if (wp.isOnSystemWorld()) {
                // Extra safety for #2
                if (PluginConfig.getSpawn(null).getWorld() == null) {
                    Bukkit.getConsoleSender().sendMessage(PluginConfig.getPrefix() + "§cThe spawn is not properly set");
                    p.sendMessage(PluginConfig.getPrefix() + "§cThe spawn is not properly set");
                    return false;
                }

                p.teleport(PluginConfig.getSpawn(p));
                p.setGameMode(GameMode.SURVIVAL); //p.setGameMode(PluginConfig.getSpawnGamemode());
                World w = Bukkit.getWorld(p.getWorld().getName());
                SystemWorld.tryUnloadLater(w);
            } else {
                p.sendMessage(MessageConfig.getNotOnWorld());
            }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }
}
