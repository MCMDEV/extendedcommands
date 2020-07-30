package net.neruxvace.extendedcommands.listeners;

import net.neruxvace.extendedcommands.ExtendedCommands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private ExtendedCommands plugin;

    public PlayerChatListener(ExtendedCommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)  {
        final Player player = event.getPlayer();
        String command = event.getMessage();

        if(!plugin.getCommandMap().containsKey(player.getUniqueId()))    {
            return;
        }

        if(command.endsWith(">>")) {
            String realCommand = command.substring(0, command.length() - 2);
            final StringBuilder stringBuilder = plugin.getCommandMap().get(player.getUniqueId());
            stringBuilder.append(realCommand);
            plugin.getCommandMap().put(player.getUniqueId(), stringBuilder);

            player.sendMessage("§aBefehl: §e" + stringBuilder.toString());
            player.sendMessage("§aDu kannst deinen Befehl nun vervollständigen oder noch weiter verlängern!");
        }   else    {
            final String finalCommand = (plugin.getCommandMap().get(player.getUniqueId()).toString() + command).substring(1);
            plugin.getCommandMap().remove(player.getUniqueId());
            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                public void run() {
                    Bukkit.getServer().dispatchCommand(player, finalCommand);
                }
            });
            player.sendMessage("§aCommand wurde ausgeführt!");
        }
        event.setCancelled(true);
    }

}
