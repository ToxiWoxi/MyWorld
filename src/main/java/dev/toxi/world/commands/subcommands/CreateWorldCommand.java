package dev.toxi.world.commands.subcommands;

import dev.toxi.world.Processes;
import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.PluginConfig;
import dev.toxi.world.gui.WorldChooseGUI;
import dev.toxi.world.util.MoneyUtil;
import dev.toxi.world.wrapper.WorldTemplate;
import dev.toxi.world.wrapper.WorldTemplateProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;

            // Check if use can create a world
            if (!p.hasPermission("myworld.get")) {
                p.sendMessage(MessageConfig.getNoPermission());
                return false;
            }

            // create New Entry
            DependenceConfig dc = new DependenceConfig(p);
            if (dc.hasWorld()) {
                p.sendMessage(MessageConfig.getWorldAlreadyExists());
                return false;
            }


            if (PluginConfig.isMultiChoose()) {
                if (args.length > 1) {
                    String key = args[1];
                    WorldTemplate template = WorldTemplateProvider.getInstance().getTemplate(key);
                    if (template != null) {
                        // Permission for this specific template
                        if (template.getPermission() != null && !p.hasPermission(template.getPermission())) {
                            p.sendMessage(MessageConfig.getNoPermission());
                            return false;
                        }

                        // Implementation check for #15
                        if (template.getCost() > 0) {
                            if (!MoneyUtil.hasMoney(p.getUniqueId(), template.getCost())) {
                                p.sendMessage(MessageConfig.getNotEnoughMoney());
                                return false;
                            }
                            MoneyUtil.removeMoney(p.getUniqueId(), template.getCost());
                        }

                        new Processes().create(p, template);
                        return false;
                    }
                }
                WorldChooseGUI.letChoose(p);
            } else {
                WorldTemplate template = WorldTemplateProvider.getInstance()
                        .getTemplate(PluginConfig.getDefaultWorldTemplate());
                if (template != null)
                    new Processes().create(p, template);
                else {
                    p.sendMessage(PluginConfig.getPrefix() + "§cError in config at \"worldtemplates.default\"");
                    p.sendMessage(PluginConfig.getPrefix() + "§cPlease contact an administrator");
                }
            }
            return true;
        } else {
            sender.sendMessage("No Console"); //TODO Add Config
            return false;
        }
    }
}
