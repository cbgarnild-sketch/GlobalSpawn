package me.globalspawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GlobalSpawn extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("GlobalSpawn enabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("setspawn")) {

            if (!player.isOp()) {
                player.sendMessage("§cYou must be OP to use this command.");
                return true;
            }

            Location loc = player.getLocation();

            getConfig().set("spawn.world", loc.getWorld().getName());
            getConfig().set("spawn.x", loc.getX());
            getConfig().set("spawn.y", loc.getY());
            getConfig().set("spawn.z", loc.getZ());
            getConfig().set("spawn.yaw", loc.getYaw());
            getConfig().set("spawn.pitch", loc.getPitch());
            saveConfig();

            player.sendMessage("§aGlobal spawn set.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("spawn")) {

            if (!getConfig().contains("spawn.world")) {
                player.sendMessage("§cSpawn has not been set.");
                return true;
            }

            Location spawn = new Location(
                    Bukkit.getWorld(getConfig().getString("spawn.world")),
                    getConfig().getDouble("spawn.x"),
                    getConfig().getDouble("spawn.y"),
                    getConfig().getDouble("spawn.z"),
                    (float) getConfig().getDouble("spawn.yaw"),
                    (float) getConfig().getDouble("spawn.pitch")
            );

            player.teleport(spawn);
            player.sendMessage("§aTeleported to spawn.");
            return true;
        }

        return true;
    }
}

