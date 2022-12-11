package com.funnyboyroks.mapify.util;

import com.funnyboyroks.mapify.Mapify;
import com.funnyboyroks.mapify.PluginData;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Util {


    public static CompletableFuture<Boolean> isLatestVersion() {

        int serverVersion = Integer.parseInt(
            Mapify.INSTANCE
                .getDescription()
                .getVersion()
                .replaceAll("\\.|-SNAPSHOT|v", "")
        );

        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("https://api.modrinth.com/v2/project/wsr1TOgJ");
                InputStreamReader reader = new InputStreamReader(url.openStream());
                JsonArray versions = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonArray("versions");
                String version = versions.get(versions.size() - 1).getAsString();

                url = new URL("https://api.modrinth.com/v2/version/" + version);
                reader = new InputStreamReader(url.openStream());
                int latestVersion = Integer.parseInt(
                    JsonParser.parseReader(reader)
                        .getAsJsonObject()
                        .get("version_number")
                        .getAsString()
                        .replaceAll("\\.|-SNAPSHOT|v", "")
                );
                Mapify.INSTANCE.getLogger().info("Latest Version: " + latestVersion);

                return latestVersion <= serverVersion;
            } catch (IOException e) {
                Mapify.INSTANCE.getLogger().severe("Unable to contact Modrinth API to check version!");
                return true;
            }
        });


    }

    public static URL getUrl(String arg) {
        try {
            return new URL(arg);
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    public static Image getImage(URL url) {
        Mapify.INSTANCE.getLogger().info("Fetching image from " + url);
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack createMap(String url, int x, int y, int w, int h) {
        ItemStack stack = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) stack.getItemMeta();

        MapView view = Bukkit.getServer().createMap(Bukkit.getServer().getWorlds().get(0));
        Mapify.INSTANCE.dataHandler.data.mapData.put(view.getId(), new PluginData.MapData(url, x, y, w, h));

        view.getRenderers().forEach(view::removeRenderer);
        view.addRenderer(Util.getRenderer(view));

        meta.setMapView(view);


        List<String> lore = meta.getLore();
        lore = lore == null ? new ArrayList<>() : lore;
        lore.add(0, "Position: (" + x + ", " + y + ")");
        meta.setLore(lore);

        stack.setItemMeta(meta);
        return stack;
    }

    public static List<ItemStack> getMaps(String url, int width, int height) {
        List<ItemStack> out = new ArrayList<>();

        getImage(getUrl(url));

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                out.add(createMap(url, x, y, width, height));
            }
        }

        return out;
    }

    public static Point getDimensions(String str) {
        try {
            String[] parts = str.split("x");
            int w = Integer.parseInt(parts[0]);
            int h = Integer.parseInt(parts[1]);
            return new Point(w, h);
        } catch (Exception ex) {
            return null;
        }
    }

    public static void giveItems(Player player, ItemStack... stacks) {
        HashMap<Integer, ItemStack> overflow = player.getInventory().addItem(stacks);

        if (overflow.isEmpty()) return;

        player.sendMessage(
            ChatColor.RED +
            String.format(
                "You were given %d item%s, but only %d fit. The others have been dropped on the ground.",
                stacks.length,
                stacks.length == 1 ? "" : "s",
                stacks.length - overflow.size()
            ));
        overflow.forEach((k, stack) -> player.getWorld().dropItem(player.getLocation(), stack));
    }

    public static MapRenderer getRenderer(MapView view) {

        PluginData.MapData data = Mapify.INSTANCE.dataHandler.data.mapData.get(view.getId());

        if (data == null) return null;

        return new CustomMapRenderer(Mapify.INSTANCE.imageCache.get(getUrl(data.url)), data.x, data.y, data.scaleX, data.scaleY);
    }

    public static boolean isAllowed(URL url) {
        String host = url.getHost();

        List<String> whitelist = Mapify.INSTANCE.config.whitelist;

        for (String s : whitelist) {
            if (host.toLowerCase().startsWith("regexp:")) {
                if (s.matches(s.substring("regexp:".length()))) {
                    return !Mapify.INSTANCE.config.whitelistIsBlacklist;
                }
            } else {
                return s.equalsIgnoreCase(host);
            }
        }
        return Mapify.INSTANCE.config.whitelistIsBlacklist;

    }
}
