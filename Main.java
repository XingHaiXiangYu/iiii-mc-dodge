package asia.iiiimc.dodge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin {
    private FileConfiguration config;
    private Map<UUID, Long> cooldowns = new HashMap<>();
    private DodgeListener listener;


    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        listener = new DodgeListener(this);
        getServer().getPluginManager().registerEvents(listener, this);

        getCommand("iiii-mc-dodge").setExecutor(new DodgeCommand(this));
    }

    @Override
    public void onDisable() {
        cooldowns.clear();
    }

    public Map<UUID, Long> getCooldowns() {
        return cooldowns;
    }

    public DodgeListener getListener() {
        return listener;
    }

}
