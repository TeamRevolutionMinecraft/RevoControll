package net.revo.revoServer.revoCharacter;

import net.revo.revoServer.RevoServer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

public class RevoChar
{
    private final Player player;
    private final HashMap<CharacterValues, Integer> numericStats;
    private final HashMap<CharacterValues, String> stringStats;

    public RevoChar(Player player)
    {
        this.player = player;
        this.numericStats = new HashMap<>();
        this.stringStats = new HashMap<>();
    }

    public boolean storePlayerData()
    {
        RevoServer.getInstance().getLogger().info("Storing player data " + player.getName());
        Path path = Paths.get(RevoServer.getInstance().getDataFolder().getPath(), "/", "chars", "/",
                this.player.getUniqueId() + ".yml");
        File file = path.toFile();
        file.mkdirs();
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        numericStats.forEach((stat, value) -> yamlConfiguration.set("stats." + stat.getYaml_string(), value));
        stringStats.forEach((stat, value) -> yamlConfiguration.set("stats." + stat.getYaml_string(), value));

        try
        {
            yamlConfiguration.save(file);
            return true;
        } catch (IOException e)
        {
            RevoServer.getInstance().getLogger().severe("Failed to save player data for " + player.getName());
            return false;
        }

    }

    public Player getPlayer()
    {
        return player;
    }

    public void addStat(CharacterValues charValue, String stringValue)
    {
        if (charValue.getType() != CharValueDataType.STRING)
        {
            throw new IllegalArgumentException("characterValue must be of Type string");
        }
        this.stringStats.put(charValue, stringValue);
    }
    public void addStat(CharacterValues charValue, Integer integerValue)
    {
        if (charValue.getType() != CharValueDataType.INTEGER)
        {
            throw new IllegalArgumentException("characterValue must be of Type integer");
        }

        this.numericStats.put(charValue, integerValue);
    }

    public String getStringValue(CharacterValues charValue)
    {
        return this.stringStats.get(charValue);
    }
    public int getIntegerValue(CharacterValues charValue)
    {
        return this.numericStats.get(charValue);
    }

    public static Optional<RevoChar> getOnlinePlayer(@NotNull Player player)
    {
        RevoChar revoChar = RevoServer.getInstance().getConnectedPlayers().get(player.getUniqueId());
        return Optional.ofNullable(revoChar);
    }
}
