package org.gop4i0;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class DeathItemsFix extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("DeathItemsFix on");
    }

    @Override
    public void onDisable() {
        getLogger().info("DeathItemsFix off");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location deathLocation = player.getLocation().clone();

        event.setDeathMessage(null);

        try {
            List<ItemStack> allItems = new ArrayList<>();

            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    allItems.add(item.clone());
                }
            }

            event.getDrops().clear();

            for (ItemStack item : allItems) {
                deathLocation.getWorld().dropItem(deathLocation, item);
            }

        } catch (Exception e) {
            getLogger().severe("Ошибка при обработке смерти игрока " + player.getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}