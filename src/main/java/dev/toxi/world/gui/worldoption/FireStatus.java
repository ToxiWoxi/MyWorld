package dev.toxi.world.gui.worldoption;

import dev.toxi.inventory.DependListener;
import dev.toxi.inventory.OrcItem;
import dev.toxi.world.config.DependenceConfig;
import dev.toxi.world.config.PluginConfig;
import dev.toxi.world.wrapper.WorldPlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class FireStatus implements DependListener {

    @Override
    public ItemStack getItemStack(Player p, WorldPlayer wp) {
        String worldname = new DependenceConfig(p).getWorldname();
        File file = new File(worldname + "/worldconfig.yml");
        if (!file.exists())
            file = new File(PluginConfig.getWorlddir() + "/worldconfig.yml");
        if (!file.exists())
            return null;
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        boolean b = cfg.getBoolean("Settings.Fire");
        if (b)
            return OrcItem.enabled.getItemStack(p);

        return null;
    }

}
