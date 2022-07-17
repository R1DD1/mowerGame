package lawnmower.lawnmowergame;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Database {

    LawnMowerGame plugin = LawnMowerGame.getPlugin(LawnMowerGame.class);

    public boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                plugin.getServer().broadcastMessage(ChatColor.YELLOW + "LOAD DB by R1DD1");
                return true;
            }
            plugin.getServer().broadcastMessage(ChatColor.RED + "DB: Player NOT Found");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            if (playerExists(uuid) != true) {
                PreparedStatement insert = plugin.getConnection()
                        .prepareStatement("INSERT INTO " + plugin.table
                                + " (UUID,GRASS) VALUES (?,?)");
                insert.setString(1, uuid.toString());
                insert.setInt(2, 0);

                insert.executeUpdate();

                plugin.getServer().broadcastMessage(ChatColor.GREEN + "DB: Loaded Successfully");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // STRING arg == WHAT WE NEED UPDATE

    public void updateArgs(UUID uuid, String arg, int intArg) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("UPDATE " + plugin.table + " SET " + arg + "=? WHERE UUID=?");
            statement.setInt(1, intArg);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // STRING arg == WHAT WE NEED GET

    public double getIntArgs(UUID uuid, String arg) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            int argValue = (results.getInt(arg));

            return argValue;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
