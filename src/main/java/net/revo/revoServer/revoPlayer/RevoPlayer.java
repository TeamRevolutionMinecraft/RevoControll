package net.revo.revoServer.revoPlayer;

import net.revo.revoServer.RevoServer;
import org.bukkit.entity.Player;

import java.util.Optional;

public class RevoPlayer
{
    private final Player player;

    public RevoPlayer(Player player)
    {
        this.player = player;
    }

    public static Optional<RevoPlayer> getOnlinePlayer(Player player)
    {
        RevoPlayer revoPlayer = RevoServer.getConnectedPlayers().get(player.getUniqueId());
        return Optional.ofNullable(revoPlayer);
    }
}
