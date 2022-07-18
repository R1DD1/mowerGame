package lawnmower.lawnmowergame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class LawnMowerGame extends JavaPlugin {
    @Override
    public void onEnable() {
        mySQLSetup();

        WorldCreator lawnMower = new WorldCreator("LawnMowerWorld");
        lawnMower.type(WorldType.FLAT);
        lawnMower.generatorSettings("2;0;1;");
        lawnMower.createWorld();

        getServer().getPluginManager().registerEvents(new LawnMowerMechanic(this), this);
        getCommand("start").setExecutor(new Commands());
    }

    //DB
    private Connection connection;
    public String host, database, username, password, table;
    public int port;

    public void mySQLSetup(){
        host = "localhost";
        port = 3306;
        database = "lawnmower";
        password="";
        username = "root";
        table = "users";

        try{
            synchronized (this){
                if (getConnection()!=null && !getConnection().isClosed()){
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://"
                        + this.host + ":" + this.port + "/" + this.database, this.username, this.password));

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"DATABASE WORKING");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }




    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
