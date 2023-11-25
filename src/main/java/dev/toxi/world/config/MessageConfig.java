package dev.toxi.world.config;

import dev.toxi.world.MyWorld;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageConfig {

    private static final List<String> defaultCmdHelp = new ArrayList<>(20);
    private static File file;

    static {
        defaultCmdHelp.add("/my create §8- §7Create a world");
        defaultCmdHelp.add("/my reset §8- §7Reset your World");
        defaultCmdHelp.add("/my home §8- §7Teleport to your world's home");
        defaultCmdHelp.add("/my tp §8- §7Teleport to another player's world");
        defaultCmdHelp.add("/my leave §8- §7Leave a world");
        defaultCmdHelp.add("/my invite §8- §7Add a player to your World");
        defaultCmdHelp.add("/my kick §8 - §7Remove a player from your World");
        defaultCmdHelp.add("/my set §8- §7Change a setting on your world");
        defaultCmdHelp.add("/my toggle §8- §7Change a gamerule on your world");
        defaultCmdHelp.add("/my allow §8- §7Allow a permission for a player on your world");
        defaultCmdHelp.add("/my deny §8- §7Deny a permission for a player on your world");
        defaultCmdHelp.add("/my gui §8- §7Open settings GUI (must be world owner)");
        defaultCmdHelp.add("/my info §8- §7Shows information about the world you are in");
        defaultCmdHelp.add("/my credits §8- §7See information about the plugin");
    }

    private MessageConfig() {
    }

    public static void checkConfig(File f) {
        file = f;
        if (!file.exists()) {
            try {
                InputStream in = JavaPlugin.getPlugin(MyWorld.class).getResource("languages/" + f.getName());

                if (in == null) {
                    in = JavaPlugin.getPlugin(MyWorld.class).getResource("custom.yml");
                }
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                System.err.println("Wasn't able to create Message file");
                e.printStackTrace();
            }
        }
    }

    private static YamlConfiguration getConfig() {
        try {
            return YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getRawMessage(String path, String alt) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path, alt));
    }

    private static String getMessage(String path, String alt) {
        return PluginConfig.getPrefix() + getRawMessage(path, alt);
    }

    public static String getNoPermission() {
        return getMessage("nopermission", "§cYou don't have permissions!");
    }

    public static String getSettingUpWorld() {
        return getMessage("world.setting_up", "§aSetting up world...");
    }

    public static String getPlayerList() {
        return getMessage("world.playerlist", "Player in this world: %player");
    }

    public static String getLagDetection() {
        return getMessage("lagdetection", "Lagdetection in world from: §c%world");
    }

    public static String getWrongUsage() {
        return getMessage("wrong_usage", "§c%usage");
    }

    public static String getNoWorldOwn() {
        return getMessage("world.does_not_exists.own", "§cYou don't have a world!");
    }

    public static String getNoWorldOther() {
        return getMessage("world.does_not_exists.other", "§cThis player doesn't has a world!");
    }

    public static String getNotRegistered() {
        return getMessage("not_registered", "§cThis player hasn't joined yet!");
    }

    public static String getAlreadyMember() {
        return getMessage("member.already_added", "§cThis player is already a member!");
    }

    public static String getMemberAdded() {
        return getMessage("member.added", "You have added &c%player&6 to your World!");
    }

    public static String getUnknownError() {
        return getMessage("unknown_error", "§cSomething went wrong...");
    }

    public static String getDeleteWorldOwn() {
        return getMessage("world.delete.own", "§cYour world was deleted!");
    }

    public static String getDeleteWorldOther() {
        return getMessage("world.delete.other", "You deleted the world of §c%player§6!");
    }

    public static String getNoMemberOwn() {
        return getMessage("member.not_added.own", "§cThis player isn't a member!");
    }

    public static String getMemberRemoved() {
        return getMessage("member.removed", "You removed §c%player§6 from your world!");
    }

    public static String getNoMemberAdded() {
        return getMessage("member.no_one_added", "§cThere are no members added");
    }

    public static String getWorldAlreadyExists() {
        return getMessage("world.already_exists", "§cYou already have a world!");
    }

    public static String getWorldCreated() {
        return getMessage("world.created", "Your world is now ready. Get there with §a/my home");
    }

    public static String getWorldStillCreating() {
        return getMessage("world.still_creating", "§cWorld is still creating");
    }

    public static String getNotOnWorld() {
        return getMessage("world.not_on", "§cYou are not on a world!");
    }

    public static String getWorldStillLoaded() {
        return getMessage("world.still_loaded", "§cYour world is still loaded!");
    }

    public static String getNoRequestSend() {
        return getMessage("request.not_sent", "§cYou didn't send a request!");
    }

    public static String getWorldReseted() {
        return getMessage("world.reseted", "Your world was reseted!");
    }

    public static String getInvalidInput() {
        return getMessage("request.invalid_input", "§c%input is not a valid input!");
    }

    public static String getRequestAlreadySent() {
        return getMessage("request.already_sent", "§cYou already sent a request!");
    }

    public static String getRequestExpired() {
        return getMessage("request.expired", "§cYou request is expired!");
    }

    public static String getTimeUntilExpires() {
        return getMessage("request.until_expire", "§cYour request expires in %time seconds!");
    }

    public static String getConfirmRequest() {
        return getMessage("request.confirm", "§cPlease confirm reset of your world: %command");
    }

    public static String getNoMemberOther() {
        return getMessage("member.not_added.other", "§cYou are not added to this world!");
    }

    public static String getInfoOwner() {
        return getMessage("info.owner", "Owner: %data");
    }

    public static String getInfoId() {
        return getMessage("info.id", "ID: %data");
    }

    public static String getInfoMember() {
        return getMessage("info.member", "Member: %data");
    }

    public static String getInfoTnt() {
        return getMessage("info.tnt", "TNT: %data");
    }

    public static String getInfoFire() {
        return getMessage("info.fire", "Fire: %data");
    }

    public static String getInfoEnabled() {
        return getRawMessage("info.enabled", "§aOn");
    }

    public static String getInfoDisabled() {
        return getRawMessage("info.disabled", "§cOff");
    }

    public static String getToggleGameModeEnabled() {
        return getMessage("toggle.gamemode.enabled", "§a%player§6 can now change his gamemode!");
    }

    public static String getToggleGameModeDisabled() {
        return getMessage("toggle.gamemode.disabled", "§c%player§6 can no longer change his gamemode!");
    }

    public static String getToggleTeleportEnabled() {
        return getMessage("toggle.teleport.enabled", "§a%player§6 can now teleport!");
    }

    public static String getToggleTeleportDisabled() {
        return getMessage("toggle.teleport.disabled", "§c%player§6 can no longer teleport!");
    }

    public static String getToggleBuildEnabled() {
        return getMessage("toggle.build.enabled", "§a%player§6 can now build!");
    }

    public static String getToggleBuildDisabled() {
        return getMessage("toggle.build.disabled", "§c%player§6 can no longer build!");
    }

    public static String getToggleWorldeditEnabled() {
        return getMessage("toggle.worldedit.enabled", "§a%player§6 can now use WorldEdit!");
    }

    public static String getToggleWorldeditDisabled() {
        return getMessage("toggle.worldedit.disabled", "§c%player§6 can no longer use WorldEdit!");
    }


    public static String getToggleFireEnabled() {
        return getMessage("toggle.fire.enabled", "§aYou activated fire!");
    }

    public static String getToggleFireDisabled() {
        return getMessage("toggle.fire.disabled", "§cYou deactivated fire!");
    }

    public static String getToggleTntEnabled() {
        return getMessage("toggle.tnt.enabled", "§aYou activated TNT-Damage!");
    }

    public static String getToggleTntDisabled() {
        return getMessage("toggle.tnt.disabled", "§cYou deactivated TNT-Damage!");
    }

    public static String getDeleteCommandHelp() {
        return getRawMessage("command_help.delete_command", "/my delete §8- §7Delete a World");
    }

    public static List<String> getCommandHelp() {
        List<String> list = getConfig().getStringList("command_help.list");
        if (list == null)
            list = defaultCmdHelp;
        list = list.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
        return list;
    }

    public static String getHomeSet() {
        return getMessage("world.set_home", "You set the home");
    }

    public static String getNotEnoughMoney() {
        return getMessage("not_enough_money", "You do not have enough money");
    }
}
