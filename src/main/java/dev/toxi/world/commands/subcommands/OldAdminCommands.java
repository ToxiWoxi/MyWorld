package dev.toxi.world.commands.subcommands;

import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.PluginConfig;
import dev.toxi.world.config.WorldConfig;
import dev.toxi.world.util.PlayerWrapper;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OldAdminCommands {
    public boolean setStorm(CommandSender sender, boolean storm) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command has to be executed as a player!");
            return false;
        }

        Player p = (Player) sender;
        DependenceConfig dc = new DependenceConfig(p);
        if (!dc.hasWorld()) {
            p.sendMessage(MessageConfig.getNotOnWorld());
            return false;
        }

        if (dc.getWorldname().equals(p.getWorld().getName())) {
            p.getWorld().setStorm(storm);
            return true;
        }


        p.sendMessage(MessageConfig.getNotOnWorld());
        return false;
    }

    public boolean setTime(CommandSender sender, long ticks) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command has to be executed as a player!");
            return false;
        }

        Player p = (Player) sender;
        DependenceConfig dc = new DependenceConfig(p);
        if (!dc.hasWorld()) {
            p.sendMessage(MessageConfig.getNotOnWorld());
            return false;
        }

        if (dc.getWorldname().equals(p.getWorld().getName())) {
            p.getWorld().setTime(ticks);
            return true;
        }


        p.sendMessage(MessageConfig.getNotOnWorld());
        return false;
    }

    public boolean toggleTeleportCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length < 2) {
                p.sendMessage(MessageConfig.getWrongUsage().replaceAll("%usage", "/my toggletp <Player>"));
                return false;
            }

            DependenceConfig dc = new DependenceConfig(p);
            if (!dc.hasWorld()) {
                p.sendMessage(MessageConfig.getNoWorldOwn());
                return false;
            }
            OfflinePlayer a = PlayerWrapper.getOfflinePlayer(args[1]);
            WorldConfig wc = WorldConfig.getWorldConfig(dc.getWorldname());
            if (!wc.isMember(a.getUniqueId())) {
                p.sendMessage(MessageConfig.getNoMemberOwn());
                return false;
            }
            WorldPlayer wp = new WorldPlayer(a, dc.getWorldname());
            if (wp.isOwnerofWorld()) {
                p.sendMessage(PluginConfig.getPrefix() + "§cYou cannot disable teleporting for yourself!");
                return false;
            }
            if (wp.toggleTeleport()) {
                p.sendMessage(MessageConfig.getToggleTeleportEnabled().replaceAll("%player", a.getName()));
            } else {
                p.sendMessage(MessageConfig.getToggleTeleportDisabled().replaceAll("%player", a.getName()));
            }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }

    public boolean toggleGamemodeCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length < 2) {
                p.sendMessage(MessageConfig.getWrongUsage().replaceAll("%usage", "/my togglegm <Player>"));
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
            if (!wc.isMember(a.getUniqueId())) {
                p.sendMessage(MessageConfig.getNoMemberOwn());
                return false;
            }
            WorldPlayer wp = new WorldPlayer(a, dc.getWorldname());
            if (wp.isOwnerofWorld()) {
                p.sendMessage(PluginConfig.getPrefix() + "§cYou cannot disable gamemode changing for yourself!");
                return false;
            }
            if (wp.toggleGamemode()) {
                p.sendMessage(MessageConfig.getToggleGameModeEnabled().replaceAll("%player", a.getName()));
            } else {
                p.sendMessage(MessageConfig.getToggleGameModeDisabled().replaceAll("%player", a.getName()));
            }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }

    public boolean toggleWorldeditCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length < 2) {
                p.sendMessage(MessageConfig.getWrongUsage().replaceAll("%usage", "/my togglewe <Player>"));
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
            if (!wc.isMember(a.getUniqueId())) {
                p.sendMessage(MessageConfig.getNoMemberOwn());
                return false;
            }
            WorldPlayer wp = new WorldPlayer(a, dc.getWorldname());
            if (wp.isOwnerofWorld()) {
                p.sendMessage(PluginConfig.getPrefix() + "§cYou cannot disable WorldEdit for yourself!");
                return false;
            }
            if (wp.toggleWorldEdit()) {
                p.sendMessage(MessageConfig.getToggleWorldeditEnabled().replaceAll("%player", a.getName()));
            } else {
                p.sendMessage(MessageConfig.getToggleWorldeditDisabled().replaceAll("%player", a.getName()));
            }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }

    public boolean toggleBuildCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length < 2) {
                p.sendMessage(MessageConfig.getWrongUsage().replaceAll("%usage", "/my togglebuild <Player>"));
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
            if (!wc.isMember(a.getUniqueId())) {
                p.sendMessage(MessageConfig.getNoMemberOwn());
                return false;
            }
            WorldPlayer wp = new WorldPlayer(a, dc.getWorldname());
            if (wp.isOwnerofWorld()) {
                p.sendMessage(PluginConfig.getPrefix() + "§cYou cannot disable building for yourself!");
                return false;
            }
            if (wp.toggleBuild()) {
                p.sendMessage(MessageConfig.getToggleBuildEnabled().replaceAll("%player", a.getName()));
            } else {
                p.sendMessage(MessageConfig.getToggleBuildDisabled().replaceAll("%player", a.getName()));
            }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }
}
