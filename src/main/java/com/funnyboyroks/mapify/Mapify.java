package com.funnyboyroks.mapify;

import com.funnyboyroks.mapify.command.CommandManager;
import com.funnyboyroks.mapify.util.Cache;
import com.funnyboyroks.mapify.util.Util;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public final class Mapify extends JavaPlugin {

    public static Mapify INSTANCE;

    public Cache<URL, Image> imageCache;
    public PluginConfig      config;
    public CommandManager    commandManager;
    public DataHandler       dataHandler;

    public Mapify() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        try {
            Util.isLatestVersion().thenAccept((latest) -> {
                if (!latest) {
                    this.getLogger().warning("Mapify has an update!");
                    this.getLogger().warning("Get it from https://modrinth.com/plugin/mapify");
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        Metrics metrics = new Metrics(this, 17096);

        metrics.addCustomChart(new SingleLineChart("maps", () -> Mapify.INSTANCE.dataHandler.data.mapData.size()));
        metrics.addCustomChart(new SimplePie("blacklist", () -> Mapify.INSTANCE.config.whitelistIsBlacklist + ""));
        metrics.addCustomChart(new AdvancedPie("whitelist", () -> Mapify.INSTANCE.config.whitelist.stream().collect(Collectors.toMap(k -> k, v -> 1))));

        this.getLogger().info("Metrics loaded.");

        this.dataHandler = new DataHandler();
        int maps = dataHandler.data.mapData.size();
        this.getLogger().info("Loaded " + maps + " map" + (maps == 1 ? "" : "s") + ".");
        this.commandManager = new CommandManager(this);
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);


        try {
            config = new PluginConfig(INSTANCE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.imageCache = new Cache<>((long) this.config.cacheDuration * 60 * 1000, Util::getImage);

    }

    @Override
    public void onDisable() {
        try {
            this.dataHandler.saveData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
