package lawnmower.lawnmowergame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {



    private List<Player> onlinePlayers = new ArrayList<Player>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int z = -601;
        if (command.getName().equalsIgnoreCase("start")){
            for (Player p : Bukkit.getOnlinePlayers()){
                onlinePlayers.add(p);
            }
            for (int i =0; i<onlinePlayers.size();i++){
                int newZ = z -10;
                onlinePlayers.get(i).teleport(new Location(Bukkit.getWorld("LawnMowerWorld"), 445, 2, newZ));
                z = newZ;
            }
            return true;
        }
        return true;
    }

    public List<Player> getOnlinePlayers() {
        return onlinePlayers;
    }
}
