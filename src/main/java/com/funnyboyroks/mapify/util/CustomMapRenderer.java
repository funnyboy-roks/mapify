package com.funnyboyroks.mapify.util;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomMapRenderer extends MapRenderer {

    private final BufferedImage image;

    public CustomMapRenderer(Image image, int x, int y, int scaleX, int scaleY) {
        //BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        BufferedImage img = (BufferedImage) image.getScaledInstance(scaleX * 128, scaleY * 128, Image.SCALE_DEFAULT);
        this.image = img.getSubimage(x * 128, y * 128, 128, 128);
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                var col = new Color(this.image.getRGB(x, y));
                canvas.setPixelColor(x, y, col);

            }
        }
    }
}
