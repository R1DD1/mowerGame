package lawnmower.lawnmowergame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


import java.util.ArrayList;
import java.util.List;

public class LawnMowerMechanic implements Listener {
    private final LawnMowerGame plugin;

    public LawnMowerMechanic(LawnMowerGame plugin) {
        this.plugin = plugin;
    }

    Database database = new Database();

    // player join
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().teleport(Bukkit.getWorld("LawnMowerWorld").getSpawnLocation());
    }
    List<Material> Blocks = new ArrayList<Material>();
    int sizeOfBlocks;

    @EventHandler
    public void connectToLawnMower(PlayerChangedWorldEvent e){
        Player p = e.getPlayer();
        if (p.getWorld().equals(Bukkit.getWorld("LawnMowerWorld"))){
            p.sendMessage("Вы попали в Безбашаную безонокосилку");
            database.createPlayer(p.getUniqueId(), p);

            Location playerLoc = p.getLocation();
            int radius  = 5;
            for (int x = playerLoc.getBlockX() - radius ; x <= playerLoc.getBlockX() + radius ; x++) {
                for (int y = playerLoc.getBlockY() - radius ; y <= playerLoc.getBlockY() + radius ; y++) {
                    for (int z = playerLoc.getBlockZ() - radius ; z <= playerLoc.getBlockZ() + radius ; z++) {
                        Block block = playerLoc.getWorld().getBlockAt(x, y, z);
                        if (block.getType().equals(Material.LONG_GRASS)) {
                            Blocks.add(block.getType());
                        }
                    }
                }
            }
            sizeOfBlocks = Blocks.size();
            database.updateArgs(p.getUniqueId(), "GRASS", sizeOfBlocks);
        }
    }

    @EventHandler
    public void destroyGrassEvent(BlockBreakEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if(b.getType().equals(Material.LONG_GRASS)){
            Location locOfGrass = b.getLocation();
            p.getWorld().getBlockAt(locOfGrass.getBlockX(),locOfGrass.getBlockY()-1,locOfGrass.getBlockZ()).setType(Material.DIRT);
            database.updateArgs(p.getUniqueId(), "GRASS"
                    ,(int) (database.getIntArgs(p.getUniqueId(), "GRASS")-1));

            if (database.getIntArgs(p.getUniqueId(), "GRASS")==0){
                Bukkit.getServer().broadcastMessage("Победил "+ ChatColor.GREEN + p.getPlayerListName());
            }
        }else{
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        e.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
        Blocks.clear();
        database.updateArgs(e.getPlayer().getUniqueId(), "GRASS", 0);
    }



}
