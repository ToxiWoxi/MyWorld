package dev.toxi.world.commands.subcommands;

import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.wrapper.SystemWorld;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {
    //    TODO: Rewrite permissions!
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            String worldname = p.getWorld().getName();
            DependenceConfig dc = new DependenceConfig(p);
            if (!dc.hasWorld()) {
                p.sendMessage(MessageConfig.getNoWorldOwn());
                return false;
            }
            WorldPlayer wp = new WorldPlayer(p, worldname);
            if (wp.isOnSystemWorld()) {
                SystemWorld.tryUnloadLater(Bukkit.getWorld(worldname));
            }
            SystemWorld sw = SystemWorld.getSystemWorld(dc.getWorldname());
            if (sw == null) {
                p.sendMessage(MessageConfig.getNoWorldOwn());
                return false;
            }
            if (sw.isLoaded()) {
                sw.teleportToWorldSpawn(p);
            } else {
                sw.load(p);
            }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Add Config
            return false;
        }
    }
}
