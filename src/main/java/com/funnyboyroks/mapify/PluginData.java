package com.funnyboyroks.mapify;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.map.MapView;

public class PluginData {

    public final Map<Integer, MapData> mapData;

    public PluginData() {
        this.mapData = new HashMap<>();
    }

    public static class MapData {

        public final String  url;
        public final int     x;
        public final int     y;
        public final int     scaleX;
        public final int     scaleY;
        /** whether the center coordinates have been set to MAX_INT (false for existing maps) */
        public       boolean maxCoords = false;

        public MapData(String url, int x, int y, int scaleX, int scaleY) {
            this.url = url;
            this.x = x;
            this.y = y;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.maxCoords = false;
        }

        /**
         * Configure the {@link MapView} to match the data for this map
         */
        public void configureView(MapView view) {
            view.setCenterX(Integer.MAX_VALUE);
            view.setCenterZ(Integer.MAX_VALUE);
            this.maxCoords = true;
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder("PluginData.MapData { ");
            out.append("url: " + url);
            out.append(", x: " + x);
            out.append(", y: " + y);
            out.append(", scaleX: " + scaleX);
            out.append(", scaleY: " + scaleY);
            out.append(", maxCoords: " + maxCoords);
            out.append(" }");
            return out.toString();
        }
    }

}
