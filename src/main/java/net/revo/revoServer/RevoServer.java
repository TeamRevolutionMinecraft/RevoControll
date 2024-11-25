package net.revo.revoServer;

import net.revo.revoServer.revoPlayer.RevoPlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class RevoServer extends JavaPlugin {

    private static RevoServer instance;
    private HashMap<UUID, RevoPlayer> connectedPlayers;

    @Override
    public void onEnable() {
        // Plugin startup logic
        connectedPlayers = new HashMap<>();
        instance = this;


        getLogger().info("RevoServer is enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static HashMap<UUID, RevoPlayer> getConnectedPlayers()
    {
        return getInstance().connectedPlayers;
    }
    public static RevoServer getInstance()
    {
        return instance;
    }
}
