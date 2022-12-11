package com.funnyboyroks.mapify.util;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;

public class CustomMapRenderer extends MapRenderer {

    private final Image image;
    private final int   x;
    private final int   y;

    public CustomMapRenderer(Image image, int x, int y, int scaleX, int scaleY) {

        this.image = image.getScaledInstance(scaleX * 128, scaleY * 128, Image.SCALE_DEFAULT);

        this.x = x * -128;
        this.y = y * -128;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {

        canvas.drawImage(x, y, image);
    }
}
