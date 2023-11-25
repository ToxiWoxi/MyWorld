package dev.toxi.world.commands.subcommands;

import dev.toxi.world.MyWorld;
import dev.toxi.world.Processes;
import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.PluginConfig;
import dev.toxi.world.config.WorldConfig;
import dev.toxi.world.event.WorldResetEvent;
import dev.toxi.world.gui.WorldChooseGUI;
import dev.toxi.world.util.PlayerPositions;
import dev.toxi.world.wrapper.SystemWorld;
import dev.toxi.world.wrapper.WorldTemplate;
import dev.toxi.world.wrapper.WorldTemplateProvider;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ResetWorldCommand implements CommandExecutor {
    //    TODO: Rewrite permissions!
    private static final ArrayList<Player> toConfirm = new ArrayList<>();

    private void createWorld(Player p, String worldname, File f, WorldTemplate template, SystemWorld sw) {

        if (f != null) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().equals("worldconfig.yml"))
                        continue;
                    FileUtils.deleteQuietly(file);
                }
            }
        }

        try {
            File templateDirectory = new File(template.getPath());
            if (templateDirectory.isDirectory())
                FileUtils.copyDirectory(templateDirectory, f);
            toConfirm.remove(p);

            FileUtils.moveDirectoryToDirectory(f, Bukkit.getWorldContainer(), false);

            WorldConfig config = WorldConfig.getWorldConfig(worldname);
            config.home = null;
            config.setTemplateKey(template.getName());
            config.save();

            p.sendMessage(MessageConfig.getWorldReseted());

            // For fast worldcreating after reset
            WorldCreator creator = template.generatorSettings.asWorldCreator(worldname);

            sw.setCreating(true);
            // For #16
            MyWorld.getInstance().getAdapter().create(creator, sw, () -> {
                if (p != null && p.isOnline())
                    p.sendMessage(MessageConfig.getWorldCreated());
            });

        } catch (IOException e) {
            e.printStackTrace();
            p.sendMessage(MessageConfig.getUnknownError());
            System.err.println("Couldn't reset world of " + p.getName());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            DependenceConfig dc = new DependenceConfig(p);
            String worldname = dc.getWorldname();
            SystemWorld sw = SystemWorld.getSystemWorld(worldname);
            if (!dc.hasWorld()) {
                p.sendMessage(MessageConfig.getNoWorldOwn());
                return false;
            }
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("confirm")) {
                    if (sw.isLoaded())
                        sw.directUnload(Bukkit.getWorld(worldname));

                    if (!toConfirm.contains(p)) {
                        p.sendMessage(MessageConfig.getNoRequestSend());
                        return false;
                    }
                    WorldResetEvent event = new WorldResetEvent(p, sw);
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled())
                        return false;

                    if (sw.isLoaded()) {
                        p.sendMessage(MessageConfig.getWorldStillLoaded());
                        return false;
                    }

                    WorldConfig config = WorldConfig.getWorldConfig(worldname);
                    // Delete positions to prevent suffocating and such stuff
                    PlayerPositions.instance.deletePositions(config);

                    File f = new File(PluginConfig.getWorlddir() + "/" + worldname);

                    if (!PluginConfig.isMultiChoose()) {
                        WorldTemplate template = WorldTemplateProvider.getInstance()
                                .getTemplate(PluginConfig.getDefaultWorldTemplate());
                        if (template != null)
                            createWorld(p, worldname, f, template, sw);
                        else {
                            p.sendMessage(PluginConfig.getPrefix() + "§cError in config at \"worldtemplates.default\"");
                            p.sendMessage(PluginConfig.getPrefix() + "§cPlease contact an administrator");
                        }
                    } else {
                        WorldChooseGUI.letChoose(p, (template) -> {
                            if (template != null)
                                createWorld(p, worldname, f, template, sw);
                            else {
                                p.sendMessage(PluginConfig.getPrefix() + "§cError in config at \"worldtemplates.default\"");
                                p.sendMessage(PluginConfig.getPrefix() + "§cPlease contact an administrator");
                            }
                        });
                    }

                } else {
                    p.sendMessage(MessageConfig.getInvalidInput().replaceAll("%input", "\"reset " + args[1] + "\""));
                }
            } else {
                if (sw.isLoaded())
                    sw.directUnload(Bukkit.getWorld(worldname));

                if (toConfirm.contains(p)) {
                    p.sendMessage(MessageConfig.getRequestAlreadySent());
                    return false;
                }

                int time = PluginConfig.getRequestExpire();
                p.sendMessage(MessageConfig.getConfirmRequest().replaceAll("%command", "/my reset confirm"));
                p.sendMessage(MessageConfig.getTimeUntilExpires().replaceAll("%time", String.valueOf(time)));
                toConfirm.add(p);
                Bukkit.getScheduler().runTaskLater(MyWorld.getInstance(), () -> {
                    if (toConfirm.contains(p)) {
                        p.sendMessage(MessageConfig.getRequestExpired());
                        toConfirm.remove(p);
                    }
                }, time * 20L);
            }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Get Config
            return false;
        }
    }

}
