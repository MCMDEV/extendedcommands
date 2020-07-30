package net.neruxvace.extendedcommands.listeners;

import net.neruxvace.extendedcommands.ExtendedCommands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {

    private ExtendedCommands plugin;

    public PlayerCommandPreprocessListener(ExtendedCommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage();

        if(plugin.getCommandMap().containsKey(player.getUniqueId()))    {
            return;
        }

        if(!command.endsWith(">>")) {
            return;
        }

        if(!player.hasPermission("extendedcommands.extendedlength"))    {
            player.sendMessage("§cDir fehlt dazu die benötigte Berechtigung!");
            return;
        }

        String realCommand = command.substring(0, command.length() - 2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(realCommand);
        plugin.getCommandMap().put(player.getUniqueId(), stringBuilder);

        player.sendMessage("§aBefehl: §e" + stringBuilder.toString());
        player.sendMessage("§aDu kannst deinen Befehl nun vervollständigen oder weiter verlängern!");
        event.setCancelled(true);
    }
}
