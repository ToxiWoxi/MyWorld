package dev.toxi.world.util;

import dev.toxi.world.config.SettingsConfig;
import dev.toxi.world.wrapper.SystemWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class Worldutils {

    public static void reloadWorldSettings(){
        for (World w : Bukkit.getWorlds()) {
            SystemWorld sw = SystemWorld.getSystemWorld(w.getName());
            if (sw != null && sw.isLoaded())
                SettingsConfig.editWorld(w);

        }
    }
}
