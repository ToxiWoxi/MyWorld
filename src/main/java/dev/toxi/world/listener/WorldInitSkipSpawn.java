package dev.toxi.world.listener;

// import dev.toxi.world.config.SettingsConfig;
import dev.toxi.world.wrapper.SystemWorld;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class WorldInitSkipSpawn implements Listener {

    @EventHandler
    public void worldInit(WorldInitEvent e) {
        World world = e.getWorld();
        world.getWorldBorder().setWarningDistance(0);
        SystemWorld sw = SystemWorld.getSystemWorld(world.getName());
        if(sw == null)
            return;

        // SettingsConfig.editWorld(world);
        e.getWorld().setKeepSpawnInMemory(false);
    }

}
