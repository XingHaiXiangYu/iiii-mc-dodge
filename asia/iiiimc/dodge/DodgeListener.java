package asia.iiiimc.dodge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.UUID;

public class DodgeListener implements Listener {
    private final Main plugin;
    private double distance;
    private long cooldownTime;
    private String cooldownMessage;

    public DodgeListener(Main plugin) {
        this.plugin = plugin;
        this.distance = plugin.getConfig().getDouble("dodge.distance", 2.0);
        this.cooldownTime = plugin.getConfig().getLong("dodge.cooldown", 3000);
        this.cooldownMessage = plugin.getConfig().getString("messages.cooldown", "&c技能冷却中，还需要 {second} 秒!");
        reloadConfig();
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) return;

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (plugin.getCooldowns().containsKey(playerUUID)) {
            long timeLeft = (plugin.getCooldowns().get(playerUUID) + cooldownTime - System.currentTimeMillis()) / 1000;
            if (timeLeft > 0) {
                String message = cooldownMessage.replace("{second}", String.valueOf(timeLeft))
                        .replace("&", "§");
                player.sendMessage(message);
                return;
            }
        }
        Vector direction = player.getLocation().getDirection();
        if (player.isSprinting()) {
            direction.multiply(distance);
        } else {
            direction.multiply(distance * 0.6);
        }
        direction.setY(0.2);
        player.setVelocity(direction);
        plugin.getCooldowns().put(playerUUID, System.currentTimeMillis());
    }

    public void reloadConfig() {
        this.distance = plugin.getConfig().getDouble("dodge.distance", 2.0);
        this.cooldownTime = plugin.getConfig().getLong("dodge.cooldown", 3000);
        this.cooldownMessage = plugin.getConfig().getString("messages.cooldown", "&c技能冷却中，还需要 {second} 秒!");
    }
}
