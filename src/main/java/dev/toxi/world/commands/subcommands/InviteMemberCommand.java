package dev.toxi.world.commands.subcommands;

import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.PluginConfig;
import dev.toxi.world.config.WorldConfig;
import dev.toxi.world.event.WorldAddmemberEvent;
import dev.toxi.world.util.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class InviteMemberCommand implements CommandExecutor {
    //    TODO: Rewrite permissions!
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
        Player p = (Player) sender;
        if (args.length < 2) {
            p.sendMessage(MessageConfig.getWrongUsage().replaceAll("%usage", "/my invite <Player>"));
            return false;
        }

        if (p.getName().toLowerCase().equals(args[1].toLowerCase())) {
            p.sendMessage(PluginConfig.getPrefix() + "§cYou're already a member!");
            return false;
        }

        DependenceConfig dc = new DependenceConfig(p);
        if (!dc.hasWorld()) {
            p.sendMessage(MessageConfig.getNoWorldOwn());
            return false;
        }
        @SuppressWarnings("deprecation")
        OfflinePlayer a = PlayerWrapper.getOfflinePlayer(args[1]);
        WorldConfig wc = WorldConfig.getWorldConfig(dc.getWorldname());
        if (a == null) {
            p.sendMessage(MessageConfig.getNotRegistered().replaceAll("%player", args[1]));
            return false;
        } else if (wc.isMember(a.getUniqueId())) {
            p.sendMessage(MessageConfig.getAlreadyMember());
            return false;
        }

        WorldAddmemberEvent event = new WorldAddmemberEvent(a.getUniqueId(), dc.getWorldname(), p);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return false;

        wc.addMember(a.getUniqueId());
        try {
            wc.save();
        } catch (IOException e) {
            p.sendMessage(MessageConfig.getUnknownError());
            e.printStackTrace();
        }
        p.sendMessage(MessageConfig.getMemberAdded().replaceAll("%player", a.getName()));
        return true;

    }
}
