package dev.toxi.world.commands.subcommands;

import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.PluginConfig;
import dev.toxi.world.config.WorldConfig;
import dev.toxi.world.event.WorldToggleFireEvent;
import dev.toxi.world.event.WorldToggleTntEvent;
import dev.toxi.world.wrapper.SystemWorld;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class OldWorldSettingsCommands {

    public boolean tntCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
            DependenceConfig dc = new DependenceConfig(p);
            if (!dc.hasWorld()) {
                p.sendMessage(MessageConfig.getNoWorldOwn());
                return false;
            }

            WorldConfig wc = WorldConfig.getWorldConfig(dc.getWorldname());
            boolean tnt = wc.isTnt();
            WorldToggleTntEvent event = new WorldToggleTntEvent(p, SystemWorld.getSystemWorld(dc.getWorldname()), tnt);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return false;

            wc.setTnt(p.getUniqueId(), !tnt);
            try {
                wc.save();
            } catch (IOException e) {
                p.sendMessage(PluginConfig.getPrefix() + "§cSomething went wrong");
                e.printStackTrace();
            }
            tnt = wc.isTnt();
            if (tnt) {
                p.sendMessage(MessageConfig.getToggleTntEnabled());
            } else {
                p.sendMessage(MessageConfig.getToggleTntDisabled());
            }
                return true;
            } else {
                sender.sendMessage("No Console"); //TODO Get Config
                return false;
            }
        }

    public boolean fireCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
        DependenceConfig dc = new DependenceConfig(p);
        if (!dc.hasWorld()) {
            p.sendMessage(MessageConfig.getNoWorldOwn());
            return false;
        }

        WorldConfig wc = WorldConfig.getWorldConfig(dc.getWorldname());
        boolean fire = wc.isFire();
        WorldToggleFireEvent event = new WorldToggleFireEvent(p, SystemWorld.getSystemWorld(dc.getWorldname()), fire);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return false;

        wc.setFire(p.getUniqueId(), !fire);
        try {
            wc.save();
        } catch (IOException e) {
            p.sendMessage(PluginConfig.getPrefix() + "§cSomething went wrong");
            e.printStackTrace();
        }
        fire = wc.isFire();
        if (fire) {
            p.sendMessage(MessageConfig.getToggleFireEnabled());
        } else {
            p.sendMessage(MessageConfig.getToggleFireDisabled());
        }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }
}
