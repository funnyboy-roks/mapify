package com.funnyboyroks.mapify;

import com.funnyboyroks.mapify.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;

public class Listeners implements Listener {

    @EventHandler
    public void onMapInit(MapInitializeEvent event) {
        var pluginRenderer = Util.getRenderer(event.getMap());
        if (pluginRenderer == null) return;
        var renderers = event.getMap().getRenderers();
        renderers.forEach(event.getMap()::removeRenderer);
        event.getMap().addRenderer(pluginRenderer);
    }
}
