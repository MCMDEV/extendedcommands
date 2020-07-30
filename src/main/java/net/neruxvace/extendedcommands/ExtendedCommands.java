package net.neruxvace.extendedcommands;

import net.neruxvace.extendedcommands.listeners.PlayerChatListener;
import net.neruxvace.extendedcommands.listeners.PlayerCommandPreprocessListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ExtendedCommands extends JavaPlugin implements Listener {

    private final Map<UUID, StringBuilder> commandMap = new HashMap<UUID, StringBuilder>();
    private int maxLength;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);

        saveDefaultConfig();

        if (getConfig().contains("maxLength")) {
            maxLength = getConfig().getInt("maxLength");
        }   else    {
            maxLength = 6000;
        }
    }

    public Map<UUID, StringBuilder> getCommandMap() {
        return commandMap;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
