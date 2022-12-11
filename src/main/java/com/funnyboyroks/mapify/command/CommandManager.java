package com.funnyboyroks.mapify.command;


import org.bukkit.command.PluginCommand;

public class CommandManager {

//    private Commodore commodore;

    public CommandManager(com.funnyboyroks.mapify.Mapify plugin) {

        // register your command executor as normal.
        PluginCommand commandMapify = plugin.getCommand("mapify");
        commandMapify.setExecutor(new CommandMapify());

        PluginCommand refreshMaps = plugin.getCommand("refreshmaps");
        refreshMaps.setExecutor(new CommandRefreshMaps());

        // check if brigadier is supported
//        if (CommodoreProvider.isSupported()) {
//
//            // get a commodore instance
//            this.commodore = CommodoreProvider.getCommodore(plugin);
//
//            // register your completions.
////            registerCompletions(commodore, command);
//
//            this.commodore.register(
//                commandMapify,
//                LiteralArgumentBuilder.literal("mapify")
//                    .then(
//                        RequiredArgumentBuilder.argument("url", StringArgumentType.string())
//                            .then(RequiredArgumentBuilder.argument("dimensions", StringArgumentType.string()))
//                    )
//            );
//        }
    }

}
