package net.revo.revoServer.cmd;

import net.revo.revoServer.RevoServer;
import net.revo.revoServer.revoCharacter.RevoChar;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Command implements CommandExecutor, TabCompleter {
    @NotNull
    protected final RevoServer plugin;
    @NotNull
    protected final String commandName;
    @NotNull
    protected final List<String> permissions;
    @NotNull
    protected final Map<String, Action> actions;

    @FunctionalInterface
    public interface Action {
        /**
         * This method is called when a valid subcommand is provided. Thrown exception are logged.
         * @param player the player which executes this command
         * @param args the args of the original command invoke but stripped of the first element
         */
        void command(@NotNull RevoChar player, @NotNull String[] args);
    }

    public Command(RevoServer plugin, String commandName, List<String> permissions)
    {
        this.plugin = plugin;
        this.commandName = commandName;
        this.permissions = permissions == null ? new ArrayList<>() : permissions;
        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(this);
        this.actions = new HashMap<>();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command,
                             @NotNull String s, @NotNull String[] strings)
    {
        if (!this.permissions.isEmpty() && permissions.stream().noneMatch(commandSender::hasPermission))
        {
            commandSender.sendMessage("No Permission");
            return false;
        }
        if (commandSender instanceof Player)
        {
            Action action;
            if (strings.length == 0)
            {

                action = this.actions.get(commandName);
            } else
            {
                action = this.actions.get(strings[0]);
            }

            try
            {
                action.command(RevoChar.getOnlinePlayer((Player) commandSender).orElseThrow(),
                        Arrays.copyOfRange(strings, 1, strings.length));
                return true;
            } catch (Exception e) {
                RevoServer.getInstance().getLogger()
                        .warning("Error while executing command " + commandSender.getName() + ": " + e.getMessage());
                return false;
            }

        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender,
                                                org.bukkit.command.@NotNull Command command,
                                                @NotNull String s, @NotNull String[] strings)
    {
        return actions.keySet().stream()
                .filter(key -> !key.equals(commandName))
                .collect(Collectors.toList());
    }

}
