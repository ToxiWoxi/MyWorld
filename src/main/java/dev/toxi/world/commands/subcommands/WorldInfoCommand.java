package dev.toxi.world.commands.subcommands;

import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.WorldConfig;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class WorldInfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            // Player p = (Player) sender;
            WorldPlayer wp = new WorldPlayer(p, p.getWorld().getName());
            if (!wp.isOnSystemWorld()) {
                p.sendMessage(MessageConfig.getNotOnWorld());
                return false;
            }

            WorldConfig wc = WorldConfig.getWorldConfig(p.getWorld().getName());
            int id = wc.getId();
            String owner = wc.getOwnerName();
            boolean fire = wc.isFire();
            boolean tnt = wc.isTnt();

            p.sendMessage(MessageConfig.getInfoOwner().replaceAll("%data", owner));
            p.sendMessage(MessageConfig.getInfoId().replaceAll("%data", "" + id));
            p.sendMessage(MessageConfig.getInfoTnt().replaceAll("%data",
                    tnt ? MessageConfig.getInfoEnabled() : MessageConfig.getInfoDisabled()));
            p.sendMessage(MessageConfig.getInfoFire().replaceAll("%data",
                    fire ? MessageConfig.getInfoEnabled() : MessageConfig.getInfoDisabled()));
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = wc.getMembersWithNames().values().iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                if (it.hasNext())
                    sb.append(" ");
            }
            p.sendMessage(MessageConfig.getInfoMember().replaceAll("%data", sb.toString().trim()));
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }
}
