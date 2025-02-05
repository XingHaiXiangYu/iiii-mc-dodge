package asia.iiiimc.dodge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DodgeCommand implements CommandExecutor {
    private final Main plugin;

    public DodgeCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("§c用法: /dodge reload");
            return true;
        }

        if (!sender.hasPermission("dodge.admin")) {
            sender.sendMessage("§c你没有权限执行此命令！");
            return true;
        }

        plugin.reloadConfig();
        plugin.getListener().reloadConfig();
        sender.sendMessage("§a配置文件重载成功！");
        return true;
    }
}