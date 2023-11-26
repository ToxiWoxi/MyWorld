package dev.toxi.world.commands;

import dev.toxi.world.commands.subcommands.*;
import dev.toxi.world.util.Worldutils;
import dev.toxi.world.wrapper.WorldTemplateProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyWorldCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {

        if (a.length == 0) return new HelpCommand().onCommand(s, c, l, a);
        switch (a[0].toLowerCase()) {
            //WSCommands
            case "create":
            case "new":
            case "start":
                    return new CreateWorldCommand().onCommand(s, c, l, a);
            case "gui":
                    return new GUICommand().onCommand(s, c, l, a);
            /*case "confirm":
                if (s.hasPermission("ws.confirm")) {
                    return ws.confirmCommand(s, c, l, a);
                } else {
                    return false;
                }*/
            case "home":
                return new HomeCommand().onCommand(s, c, l, a);
            case "tp":
                if (a.length > 1) return new TPCommand().onCommand(s, c, l, a);
                return new HomeCommand().onCommand(s, c, l, a);
            case "info":
                return new WorldInfoCommand().onCommand(s, c, l, a);
            case "credits":
                return new PluginCreditsCommand().onCommand(s, c, l, a);
            case "leave":
                return new LeaveCommand().onCommand(s, c, l, a);
            case "set":
                return new SetCommand().onCommand(s, c, l, a);
            case "gamerule":
                return new GameruleCommand().onCommand(s, c, l, a);
            case "invite":
            case "add":
                return new InviteMemberCommand().onCommand(s, c, l, a);
            case "kick":
            case "remove":
                return new KickMemberCommand().onCommand(s, c, l, a);
            case "delete":
                if (s.hasPermission("myworld.delete")) return new DeleteWorldCommand().onCommand(s, c, l, a);
                return false;
            case "reset":
                return new ResetWorldCommand().onCommand(s, c, l, a);
            case "reload":
                if(!s.isOp()){
                    s.sendMessage("Reloading Settings!");
                    WorldTemplateProvider.getInstance().reload();
                    Worldutils.reloadWorldSettings();
                    return true;
                }
            default:
                //TODO: Change to error
                return new HelpCommand().onCommand(s, c, l, a);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> subCommands = new ArrayList<>(Arrays.asList("create", "reset", "delete", "home", "tp", "leave", "invite", "kick", "set", "allow", "deny", "gui", "info", "credits"));
        if (GameruleCommand.hasPermission(sender)) subCommands.add("gamerule");
        List<String> playerCompletions = Arrays.asList("tp", "invite", "kick", "allow", "deny");
        List<String> worldSettings = Arrays.asList("time", "weather");
        List<String> timeOptions = Arrays.asList("morning", "noon", "day", "evening", "night", "midnight");
        List<String> weatherOptions = Arrays.asList("clear", "raining", "storming");
        List<String> booleanOptions = Arrays.asList("true", "false");
        List<String> completions = new ArrayList<>();
        List<String> playerNames = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            playerNames.add(p.getName());
        }

        if (args[0].equalsIgnoreCase("gamerule")) return new GameruleCommand().onTabComplete(sender, command, alias, args);

        if (args.length == 1) {
            for(String s : subCommands) if (s.startsWith(args[0].toLowerCase())) completions.add(s);
        }

        if (args.length == 2 ) {
            if (playerCompletions.contains(args[0].toLowerCase()) || (args[0].equalsIgnoreCase("delete") && sender.hasPermission("myworld.delete"))) {
                for(String s : playerNames) if (s.toLowerCase().startsWith(args[1].toLowerCase())) completions.add(s);
            }
            if (args[0].equalsIgnoreCase("set")) {
                for(String s : worldSettings) if (s.toLowerCase().startsWith(args[1].toLowerCase())) completions.add(s);
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("set")) {
                if (args[1].equalsIgnoreCase("time")) for(String s : timeOptions) if (s.toLowerCase().startsWith(args[2].toLowerCase())) completions.add(s);
                if (args[1].equalsIgnoreCase("weather")) for(String s : weatherOptions) if (s.toLowerCase().startsWith(args[2].toLowerCase())) completions.add(s);
            }
            if (args[0].equalsIgnoreCase("gamerule")) {
                for(String s : booleanOptions) if (s.toLowerCase().startsWith(args[2].toLowerCase())) completions.add(s);
            }
        }

//        Collections.sort(completions);

        return completions;
    }
}
