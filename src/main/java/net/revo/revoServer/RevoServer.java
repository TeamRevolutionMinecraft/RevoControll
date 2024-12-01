package net.revo.revoServer;

import net.revo.revoServer.revoCharacter.RevoChar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class RevoServer extends JavaPlugin {

    private static RevoServer instance;
    private HashMap<UUID, RevoChar> connectedPlayers;

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
        getInstance().connectedPlayers.forEach((x, revoChar) -> revoChar.storePlayerData());
    }

    public HashMap<UUID, RevoChar> getConnectedPlayers()
    {
        return getInstance().connectedPlayers;
    }

    public void add_player(RevoChar player)
    {
        getInstance().connectedPlayers.put(player.getPlayer().getUniqueId(), player);
    }

    public static RevoServer getInstance()
    {
        return instance;
    }
}
