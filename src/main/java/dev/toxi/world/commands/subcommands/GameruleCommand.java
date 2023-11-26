package dev.toxi.world.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameruleCommand implements TabExecutor {
//    TODO: Write this command!
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

//      Add gamerules to their categories
        List<String> allowedIntGamerules = new ArrayList<>();     // As of 1.20.2
        for (String s : IntGamerules()) if (sender.hasPermission("myworld.gamerule." + s)) allowedIntGamerules.add((s));
        List<String> allowedBooleanGamerules = new ArrayList<>(); // As of 1.20.2
        for (String s : BooleanGamerules()) if (sender.hasPermission("myworld.gamerule." + s)) allowedBooleanGamerules.add((s));

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

    public static Boolean hasPermission (CommandSender sender) {
        if (sender.hasPermission("myworld.gamerule")) return true;
        for (String s : IntGamerules()) if (sender.hasPermission("myworld.gamerule." + s)) return true;
        for (String s : BooleanGamerules()) if (sender.hasPermission("myworld.gamerule." + s)) return true;
        return false;
    }

    private static List<String> IntGamerules () {
        return new ArrayList<String>(Arrays.asList(
                "commandModificationBlockLimit",
                "maxCommandChainLength",
                "maxEntityCramming",
                "playersSleepingPercentage",
                "randomTickSpeed",
                "tntExplosionDropDecay"
        ));
    }

    private static List<String> BooleanGamerules () {
        return new ArrayList<String>(Arrays.asList(
                "announceAdvancements",
                "blockExplosionDropDecay",
                "commandBlockOutput",
                "disableElytraMovementCheck",
                "disableRaids",
                "doDaylightCycle",
                "doEntityDrops",
                "doFireTick",
                "doInsomnia",
                "doImmediateRespawn",
                "doLimitedCrafting",
                "doMobLoot",
                "doMobSpawning",
                "doPatrolSpawning",
                "doTileDrops",
                "doTraderSpawning",
                "doVinesSpread",
                "doWeatherCycle",
                "drowningDamage",
                "doWardenSpawning",
                "drowningDamage",
                "enderPearlsVanishOnDeath",
                "fallDamage",
                "fireDamage",
                "forgiveDeadPlayers",
                "freezeDamage",
                "globalSoundEvents",
                "keepInventory",
                "lavaSourceConversion",
                "logAdminCommands",
                "mobExplosionDropDecay",
                "mobGriefing",
                "naturalRegeneration",
                "reducedDebugInfo",
                "sendCommandFeedback",
                "showDeathMessages",
                "snowAccumulationHeight",
                "spawnRadius",
                "spectatorsGenerateChunks",
                "universalAnger",
                "waterSourceConversion",
                "showTags"
        ));
    }
}