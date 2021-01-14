package me.mazenz.blockphysics;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.logging.Logger;

public final class BlockPhysics extends JavaPlugin implements Listener {
    public static void main(String[] args) {
        System.out.println((float) -5 + (float) (Math.random() * ((5 - -5) + 1)));
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        saveConfig();        Logger logger = this.getLogger();
        new UpdateChecker(this, 84621).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("The latest version of BlockPhysics is running (0.1)");
            } else {
                logger.info("There is a new version of BlockPhysics. https://www.spigotmc.org/resources/blockphysics.84621/");
            }
        });

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (getConfig().getBoolean("EnableFallingBlocks")) {
            if (event.getBlock().getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR)
                return;

            event.getBlock().getWorld().spawnFallingBlock(event.getBlock().getLocation().add(0.5, 0, 0.5), event.getBlock().getType(), event.getBlock().getData());
            event.getBlock().setType(Material.AIR);
        }
    }
    @EventHandler
    @SuppressWarnings("deprecated")
    public void ExplosiveBlocks(EntityExplodeEvent event) {
        if (getConfig().getBoolean("EnableExplosiveEffect")) {
            for (Block b : event.blockList()) {
                float x = (float) -0.5 + (float) (Math.random() * ((0.5 - -0.5) + 1));
                float y = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
                float z = (float) -0.5 + (float) (Math.random() * ((0.5 - -0.5) + 1));

                FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
                fallingBlock.setDropItem(false);
                fallingBlock.setVelocity(new Vector(x, y, z));

                b.setType(Material.AIR);
            }
        }
    }
}