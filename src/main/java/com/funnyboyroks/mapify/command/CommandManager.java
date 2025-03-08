package com.funnyboyroks.mapify.command;


import com.funnyboyroks.mapify.Mapify;
import org.bukkit.command.PluginCommand;

public class CommandManager {

    public CommandManager(Mapify plugin) {

        PluginCommand commandMapify = plugin.getCommand("mapify");
        commandMapify.setExecutor(new CommandMapify());

        PluginCommand refreshMaps = plugin.getCommand("refreshmaps");
        refreshMaps.setExecutor(new CommandRefreshMaps());
    }

}
