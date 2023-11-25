package dev.toxi.world.commands.subcommands;

import dev.toxi.world.MyWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
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

        Boolean hasAllGamerules = sender.hasPermission("myworld.gamerule.*") || sender.hasPermission("myworld.*") || sender.hasPermission("*") || sender.isOp();
        List<String> booleanGamerules = new ArrayList<>(); // As of 1.20.2
        List<String> intGamerules = new ArrayList<>();     // As of 1.20.2
//      Add gamerules to their categories
        if (sender.hasPermission("myworld.gamerule.announceAdvancements")           || hasAllGamerules) booleanGamerules.add("announceAdvancements");
        if (sender.hasPermission("myworld.gamerule.blockExplosionDropDecay")        || hasAllGamerules) booleanGamerules.add("blockExplosionDropDecay");
        if (sender.hasPermission("myworld.gamerule.commandBlockOutput")             || hasAllGamerules) booleanGamerules.add("commandBlockOutput");
        if (sender.hasPermission("myworld.gamerule.commandModificationBlockLimit")  || hasAllGamerules) intGamerules    .add("commandModificationBlockLimit");
        if (sender.hasPermission("myworld.gamerule.disableElytraMovementCheck")     || hasAllGamerules) booleanGamerules.add("disableElytraMovementCheck");
        if (sender.hasPermission("myworld.gamerule.disableRaids")                   || hasAllGamerules) booleanGamerules.add("disableRaids");
        if (sender.hasPermission("myworld.gamerule.doDaylightCycle")                || hasAllGamerules) booleanGamerules.add("doDaylightCycle");
        if (sender.hasPermission("myworld.gamerule.doEntityDrops")                  || hasAllGamerules) booleanGamerules.add("doEntityDrops");
        if (sender.hasPermission("myworld.gamerule.doFireTick")                     || hasAllGamerules) booleanGamerules.add("doFireTick");
        if (sender.hasPermission("myworld.gamerule.doInsomnia")                     || hasAllGamerules) booleanGamerules.add("doInsomnia");
        if (sender.hasPermission("myworld.gamerule.doImmediateRespawn")             || hasAllGamerules) booleanGamerules.add("doImmediateRespawn");
        if (sender.hasPermission("myworld.gamerule.doLimitedCrafting")              || hasAllGamerules) booleanGamerules.add("doLimitedCrafting");
        if (sender.hasPermission("myworld.gamerule.doMobLoot")                      || hasAllGamerules) booleanGamerules.add("doMobLoot");
        if (sender.hasPermission("myworld.gamerule.doMobSpawning")                  || hasAllGamerules) booleanGamerules.add("doMobSpawning");
        if (sender.hasPermission("myworld.gamerule.doPatrolSpawning")               || hasAllGamerules) booleanGamerules.add("doPatrolSpawning");
        if (sender.hasPermission("myworld.gamerule.doTileDrops")                    || hasAllGamerules) booleanGamerules.add("doTileDrops");
        if (sender.hasPermission("myworld.gamerule.doTraderSpawning")               || hasAllGamerules) booleanGamerules.add("doTraderSpawning");
        if (sender.hasPermission("myworld.gamerule.doVinesSpread")                  || hasAllGamerules) booleanGamerules.add("doVinesSpread");
        if (sender.hasPermission("myworld.gamerule.doWeatherCycle")                 || hasAllGamerules) booleanGamerules.add("doWeatherCycle");
        if (sender.hasPermission("myworld.gamerule.drowningDamage")                 || hasAllGamerules) booleanGamerules.add("drowningDamage");
        if (sender.hasPermission("myworld.gamerule.doWardenSpawning")               || hasAllGamerules) booleanGamerules.add("doWardenSpawning");
        if (sender.hasPermission("myworld.gamerule.drowningDamage")                 || hasAllGamerules) booleanGamerules.add("drowningDamage");
        if (sender.hasPermission("myworld.gamerule.enderPearlsVanishOnDeath")       || hasAllGamerules) booleanGamerules.add("enderPearlsVanishOnDeath");
        if (sender.hasPermission("myworld.gamerule.fallDamage")                     || hasAllGamerules) booleanGamerules.add("fallDamage");
        if (sender.hasPermission("myworld.gamerule.fireDamage")                     || hasAllGamerules) booleanGamerules.add("fireDamage");
        if (sender.hasPermission("myworld.gamerule.forgiveDeadPlayers")             || hasAllGamerules) booleanGamerules.add("forgiveDeadPlayers");
        if (sender.hasPermission("myworld.gamerule.freezeDamage")                   || hasAllGamerules) booleanGamerules.add("freezeDamage");
        if (sender.hasPermission("myworld.gamerule.globalSoundEvents")              || hasAllGamerules) booleanGamerules.add("globalSoundEvents");
        if (sender.hasPermission("myworld.gamerule.keepInventory")                  || hasAllGamerules) booleanGamerules.add("keepInventory");
        if (sender.hasPermission("myworld.gamerule.lavaSourceConversion")           || hasAllGamerules) booleanGamerules.add("lavaSourceConversion");
        if (sender.hasPermission("myworld.gamerule.logAdminCommands")               || hasAllGamerules) booleanGamerules.add("logAdminCommands");
        if (sender.hasPermission("myworld.gamerule.maxCommandChainLength")          || hasAllGamerules) intGamerules    .add("maxCommandChainLength");
        if (sender.hasPermission("myworld.gamerule.maxEntityCramming")              || hasAllGamerules) intGamerules    .add("maxEntityCramming");
        if (sender.hasPermission("myworld.gamerule.mobExplosionDropDecay")          || hasAllGamerules) booleanGamerules.add("mobExplosionDropDecay");
        if (sender.hasPermission("myworld.gamerule.mobGriefing")                    || hasAllGamerules) booleanGamerules.add("mobGriefing");
        if (sender.hasPermission("myworld.gamerule.naturalRegeneration")            || hasAllGamerules) booleanGamerules.add("naturalRegeneration");
        if (sender.hasPermission("myworld.gamerule.playersSleepingPercentage")      || hasAllGamerules) intGamerules    .add("playersSleepingPercentage");
        if (sender.hasPermission("myworld.gamerule.randomTickSpeed")                || hasAllGamerules) intGamerules    .add("randomTickSpeed");
        if (sender.hasPermission("myworld.gamerule.reducedDebugInfo")               || hasAllGamerules) booleanGamerules.add("reducedDebugInfo");
        if (sender.hasPermission("myworld.gamerule.sendCommandFeedback")            || hasAllGamerules) booleanGamerules.add("sendCommandFeedback");
        if (sender.hasPermission("myworld.gamerule.showDeathMessages")              || hasAllGamerules) booleanGamerules.add("showDeathMessages");
        if (sender.hasPermission("myworld.gamerule.snowAccumulationHeight")         || hasAllGamerules) booleanGamerules.add("snowAccumulationHeight");
        if (sender.hasPermission("myworld.gamerule.spawnRadius")                    || hasAllGamerules) booleanGamerules.add("spawnRadius");
        if (sender.hasPermission("myworld.gamerule.spectatorsGenerateChunks")       || hasAllGamerules) booleanGamerules.add("spectatorsGenerateChunks");
        if (sender.hasPermission("myworld.gamerule.tntExplosionDropDecay")          || hasAllGamerules) intGamerules    .add("tntExplosionDropDecay");
        if (sender.hasPermission("myworld.gamerule.universalAnger")                 || hasAllGamerules) booleanGamerules.add("universalAnger");
        if (sender.hasPermission("myworld.gamerule.waterSourceConversion")          || hasAllGamerules) booleanGamerules.add("waterSourceConversion");
        if (sender.hasPermission("myworld.gamerule.showTags")                       || hasAllGamerules) booleanGamerules.add("showTags");

        int firstArg = 0;
//        Start at the second argument if using the myworld subcommand
        if (command.getName().equals("myworld")) firstArg = 1;

//        1 handled argument
        if (args.length == firstArg + 1) {
            for(String s : intGamerules) if (s.toLowerCase().startsWith(args[firstArg].toLowerCase())) completions.add(s);
            for(String s : booleanGamerules) if (s.toLowerCase().startsWith(args[firstArg].toLowerCase())) completions.add(s);
        }
//        2 handled arguments
        if (args.length == firstArg + 2) {
//            Suggest Boolean if first argument is in booleanGamerules
            if (booleanGamerules.stream().anyMatch(args[firstArg] :: equalsIgnoreCase)) for(String s : Arrays.asList("true", "false")) if (s.toLowerCase().startsWith(args[firstArg + 1].toLowerCase())) completions.add(s);
            if (intGamerules.stream().anyMatch(args[firstArg]::equalsIgnoreCase)) return new ArrayList<>();
        }
        return completions;
    }
}