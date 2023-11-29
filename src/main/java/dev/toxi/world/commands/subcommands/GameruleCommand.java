package dev.toxi.world.commands.subcommands;

import dev.toxi.world.commands.common.CommandHelp;
import dev.toxi.world.commands.common.ConsoleNotAllowed;
import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.wrapper.SystemWorld;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameruleCommand implements TabExecutor {
//    WIP: Write this command!
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//      Reject console
//      TODO: WRITE A WAY FOR CONSOLE TO CHANGE PLAYERS' GAMERULES
        if (!(sender instanceof Player)) return new ConsoleNotAllowed().onCommand(sender, command, label, args);

        int firstArg = 0;
        String executedCommand = "/" + label;
//      Start at the second argument if using the myworld subcommand
        if (command.getName().equals("myworld")) {
            firstArg = 1;
            executedCommand += " gamerule";
        }

//      Reject if incorrect number of arguments
        if (args.length <= firstArg || args.length > firstArg + 3) {
            return new CommandHelp().send(
                sender,
                executedCommand,
                "Get or set the value of a gamerule on your world.",
                Arrays.asList("§f" + executedCommand + " §2<gamerule>§a - Get the value of a gamerule", "§f" + executedCommand + " §2<gamerule> <value>§a - Set the value of a gamerule")
            );
        }

//      Reject if user does not have a world
        DependenceConfig dc = new DependenceConfig(sender.getName());
        if (!dc.hasWorld()) {
            sender.sendMessage(MessageConfig.getNoWorldOwn());
            return false;
        }

//      Reject if gamerule is invalid
//      Set providedGamerule(s)
        GameRule<?> providedGamerule = null;
        GameRule<Integer> providedGameruleI = null;
        GameRule<Boolean> providedGameruleB = null;
        for (GameRule<Integer> g : IntegerGamerules())
            if (g.getName().equalsIgnoreCase(args[firstArg])) {
                providedGamerule = g;
                providedGameruleI = g;
                break;
            }
        for (GameRule<Boolean> g : BooleanGamerules())
            if (g.getName().equalsIgnoreCase(args[firstArg])) {
                providedGamerule = g;
                providedGameruleB = g;
                break;
            }
        if (providedGamerule == null) {
            sender.sendMessage("Invalid gamerule");
            return false;
        }

//      Get sender's world
        SystemWorld sw = SystemWorld.getSystemWorld(dc.getWorldname());
        World senderWorld = sw.getWorld();
        if (sw.getWorld() == null || !(sw.isLoaded())) {
            sw.backgroundLoad(((Player) sender).getPlayer());
            senderWorld = sw.getWorld();
        }


//      1 handled argument: Get value
        if (args.length == firstArg + 1) {
//            TODO: ADD TRANSLATION STRINGS
            String gameruleValue = "§2" + senderWorld.getGameRuleValue(providedGamerule);
//            if (gameruleValue.equals("§2true")) gameruleValue = "§2true";
            if (gameruleValue.equals("§2false")) gameruleValue = "§4false";
            sender.sendMessage("§fGamerule §a" + providedGamerule.getName() + "§f is currently set to " + gameruleValue + "§f.");
            return true;
        }

//      Get gamerules that sender has permission to modify
        List<String> allowedGamerules = new ArrayList<>();
        for (GameRule<?> g: AllGamerules()) if (sender.hasPermission("myworld.gamerule." + g.getName())) allowedGamerules.add(g.getName());
//      Check if user has permission to change gamerule (args[firstArg])
        boolean hasPermission = allowedGamerules.stream().anyMatch(args[firstArg]::equalsIgnoreCase);
//      Reject if user cannot change gamerule
        if (!hasPermission) {
//            TODO: ADD TRANSLATION STRING
            sender.sendMessage("§4You don't have permission to change the value of this gamerule.");
            return false;
        }

//      2 handled arguments
        if (args.length == firstArg + 2) {
//          For integer gamerules
            if (providedGamerule.getType().equals(Integer.class) && providedGameruleI != null) {
                try {
                    senderWorld.setGameRule(providedGameruleI, Integer.parseInt(args[firstArg + 1]));
                    sender.sendMessage("§fGamerule §a" + providedGamerule.getName() + "§f is now set to §2" + args[firstArg + 1]  + "§f.");
                    return true;
//              Invalid arguments should get rejected as "NumberFormatException"
                } catch (NumberFormatException e) {
                    sender.sendMessage("§4Invalid gamerule value. Expected integer.");
                    return false;
                }
            }
//          For boolean gamerules
            if (providedGamerule.getType().equals(Boolean.class) && providedGameruleB != null) {
                if (Arrays.asList("true", "false").contains(args[firstArg + 1].toLowerCase())) {
                    senderWorld.setGameRule(providedGameruleB, Boolean.parseBoolean(args[firstArg + 1]));
                    String gameruleValue = "§4false";
                    if (Boolean.parseBoolean(args[firstArg + 1])) gameruleValue = "§2true";
                    sender.sendMessage("§fGamerule §a" + providedGamerule.getName() + "§f is now set to " + gameruleValue + "§f.");
                    return true;
                }
//              Invalid arguments are falsy and therefore fail previous if statement.
                sender.sendMessage("§4Invalid gamerule value. Expected \"true\" or \"false\".");
                return false;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

//      Add gamerules to their categories
        List<String> allowedIntGamerules = new ArrayList<>();     // As of 1.20.2
        for (GameRule<Integer> g : IntegerGamerules()) if (sender.hasPermission("myworld.gamerule." + g.getName())) allowedIntGamerules.add((g.getName()));
        List<String> allowedBooleanGamerules = new ArrayList<>(); // As of 1.20.2
        for (GameRule<Boolean> g : BooleanGamerules()) if (sender.hasPermission("myworld.gamerule." + g.getName())) allowedBooleanGamerules.add((g.getName()));

        int firstArg = 0;
//        Start at the second argument if using the myworld subcommand
        if (command.getName().equals("myworld")) firstArg = 1;

//        1 handled argument
        if (args.length == firstArg + 1) {
            for(String s : allowedIntGamerules) if (s.toLowerCase().startsWith(args[firstArg].toLowerCase())) completions.add(s);
            for(String s : allowedBooleanGamerules) if (s.toLowerCase().startsWith(args[firstArg].toLowerCase())) completions.add(s);
        }
//        2 handled arguments
        if (args.length == firstArg + 2) {
//            Suggest Boolean if first argument is in booleanGamerules
            if (allowedBooleanGamerules.stream().anyMatch(args[firstArg]::equalsIgnoreCase)) for(String s : Arrays.asList("true", "false")) if (s.toLowerCase().startsWith(args[firstArg + 1].toLowerCase())) completions.add(s);
            if (allowedIntGamerules.stream().anyMatch(args[firstArg]::equalsIgnoreCase)) return new ArrayList<>();
        }
        return completions;
    }

    public static Boolean hasRootPermission(CommandSender sender) {
        if (sender.hasPermission("myworld.gamerule")) return true;
        for (GameRule<?> g : AllGamerules()) if (sender.hasPermission("myworld.gamerule." + g.getName())) return true;
        return false;
    }

    private static List<GameRule<?>> AllGamerules() {
        return List.of(GameRule.values());
    }

    private static List<GameRule<Integer>> IntegerGamerules() {
        List<GameRule<Integer>> intGamerules = new ArrayList<>();

        for (GameRule g : AllGamerules()) if (g.getType().equals(Integer.class)) intGamerules.add(g);

        return intGamerules;
    }

    private static List<GameRule<Boolean>> BooleanGamerules() {
        List<GameRule<Boolean>> booleanGamerules = new ArrayList<>();

        for (GameRule g : AllGamerules()) if (g.getType().equals(Boolean.class)) booleanGamerules.add(g);

        return booleanGamerules;
    }
}