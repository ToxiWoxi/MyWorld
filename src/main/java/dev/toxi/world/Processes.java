package dev.toxi.world;

import dev.toxi.world.config.MessageConfig;
import dev.toxi.world.config.WorldConfig;
import dev.toxi.world.wrapper.SystemWorld;
import dev.toxi.world.wrapper.WorldTemplate;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Processes {
    public void create(Player p, WorldTemplate template) {
        Bukkit.getScheduler().runTask(MyWorld.getInstance(), () -> {
            if (SystemWorld.create(p, template)) p.sendMessage(MessageConfig.getSettingUpWorld());
        });
    }
}
