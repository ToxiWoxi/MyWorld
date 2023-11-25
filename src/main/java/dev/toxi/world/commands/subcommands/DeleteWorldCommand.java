package dev.toxi.world.commands.subcommands;

import dev.toxi.world.MyWorld;
import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.PluginConfig;
import dev.toxi.world.config.WorldConfig;
import dev.toxi.world.event.WorldDeleteEvent;
import dev.toxi.world.util.PlayerPositions;
import dev.toxi.world.wrapper.SystemWorld;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class DeleteWorldCommand implements CommandExecutor {
    //    TODO: Rewrite permissions!
    //    TODO: Add argument logic!
    //    TODO: Add confirmation check!
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandSender cs = sender;

        if (args.length < 2) {
            cs.sendMessage(MessageConfig.getWrongUsage().replaceAll("%usage", "/my delete <Player>"));
            return false;
        }

        DependenceConfig dc = new DependenceConfig(args[1]);
        if (!dc.hasWorld()) {
            cs.sendMessage(MessageConfig.getNoWorldOther());
            return false;
        }

        String worldname = dc.getWorldname();
        SystemWorld sw = SystemWorld.getSystemWorld(worldname);
        WorldDeleteEvent event = new WorldDeleteEvent(cs, sw);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return false;

        if (sw != null && sw.isLoaded())
            sw.directUnload(Bukkit.getWorld(worldname));

        WorldConfig config = WorldConfig.getWorldConfig(worldname);
        // Delete unnecessary positions
        PlayerPositions.instance.deletePositions(config);

        Bukkit.getScheduler().runTaskLater(MyWorld.getInstance(), () -> {
            OfflinePlayer op = dc.getOwner();

            String uuid = op.getUniqueId().toString();
            File dir = new File(PluginConfig.getWorlddir() + "/" + worldname);
            if (!dir.exists())
                dir = new File(Bukkit.getWorldContainer(), worldname);
            if (dir.exists())
                try {
                    FileUtils.deleteDirectory(dir);
                } catch (Exception e) {
                    cs.sendMessage(MessageConfig.getUnknownError());
                    e.printStackTrace();
                }
            File dconfig = new File("plugins//MyWorld//dependence.yml");
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(dconfig);
            cfg.set("Dependences." + uuid + ".ID", null);
            cfg.set("Dependences." + uuid + ".ActualName", null);
            cfg.set("Dependences." + uuid, null);
            try {
                cfg.save(dconfig);
            } catch (Exception e) {
                cs.sendMessage(MessageConfig.getUnknownError());
                e.printStackTrace();
            }
            cs.sendMessage(MessageConfig.getDeleteWorldOther().replaceAll("%player", op.getName()));
            if (op.isOnline()) {
                Player p1 = Bukkit.getPlayer(op.getUniqueId());
                p1.sendMessage(MessageConfig.getDeleteWorldOwn());
            }
        }, 10);
        return true;
    }
}
