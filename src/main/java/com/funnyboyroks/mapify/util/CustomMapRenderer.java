package com.funnyboyroks.mapify.util;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomMapRenderer extends MapRenderer {

    private final BufferedImage image;
    private final int MAP_SIZE = 128;

    public CustomMapRenderer(Image image, int x, int y, int scaleX, int scaleY) {
       BufferedImage img = new BufferedImage(MAP_SIZE, MAP_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        var scaled = image.getScaledInstance(scaleX * MAP_SIZE, scaleY * MAP_SIZE, Image.SCALE_DEFAULT);
        g.drawImage(scaled, -x * MAP_SIZE, -y * MAP_SIZE, null);
        g.dispose();
        this.image = img;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                // _Note: this seems to be more performant than using MapCanvas#drawImage._
                var col = new Color(this.image.getRGB(x, y));
                canvas.setPixelColor(x, y, col);
            }
        }
    }
}
