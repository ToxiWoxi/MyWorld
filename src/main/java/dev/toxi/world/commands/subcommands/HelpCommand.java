package dev.toxi.world.commands.subcommands;

import dev.toxi.world.MyWorld;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.PluginConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandSender cs = sender;

        String prefix = PluginConfig.getPrefix();
        cs.sendMessage(
                prefix + "MyWorld by CrazyCloudCraft v" + MyWorld.getInstance().getDescription().getVersion());
        cs.sendMessage(prefix + "Contributors: Jubeki, montlikadani, jstoeckm2, Butzlabben");
        List<String> cmdHelp = MessageConfig.getCommandHelp();
        cmdHelp.forEach(s -> cs.sendMessage("§6" + s)); //(prefix + s));
        // cs.sendMessage(prefix + "==============");
        if (cs.hasPermission("myworld.delete")) {
            cs.sendMessage("§6" + MessageConfig.getDeleteCommandHelp());
        }
        return true;
    }
}
