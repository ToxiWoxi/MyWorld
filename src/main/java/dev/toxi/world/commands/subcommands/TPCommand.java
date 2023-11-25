package dev.toxi.world.commands.subcommands;

import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.wrapper.SystemWorld;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length < 2) {
                p.sendMessage(MessageConfig.getWrongUsage().replaceAll("%usage", "/my tp <World>"));
                return false;
            }

            if (args[1].equalsIgnoreCase(p.getName()) || args[1].equalsIgnoreCase(p.getUniqueId().toString())) {
                p.chat("/myworld home");
                return false;
            }


            DependenceConfig dc = new DependenceConfig(args[1]);
            String worldname = dc.getWorldNameByOfflinePlayer();
            if (!dc.hasWorld()) {
                p.sendMessage(MessageConfig.getNoWorldOther());
                return false;
            }
            SystemWorld sw = SystemWorld.getSystemWorld(worldname);
            WorldPlayer wp1 = new WorldPlayer(p, p.getWorld().getName());
            WorldPlayer wp = new WorldPlayer(p, worldname);
            if (p.getWorld().getName().equals(worldname)) {
                sw.teleportToWorldSpawn(p);
                return false;
            }
            if (!p.hasPermission("myworld.tp.world")) {
                if (!wp.isMemberofWorld(worldname) && !wp.isOwnerofWorld()) {
                    p.sendMessage(MessageConfig.getNoMemberOther());
                    return false;
                }
            }
            if (wp1.isOnSystemWorld()) {
                World w = p.getWorld();
                SystemWorld.tryUnloadLater(w);
            }
            if (sw != null) {
                if (!sw.isLoaded()) {
                    sw.load(p);
                } else {
                    sw.teleportToWorldSpawn(p);
                }
            }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }
}
