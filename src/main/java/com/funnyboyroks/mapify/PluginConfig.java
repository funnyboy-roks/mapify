package com.funnyboyroks.mapify;

import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PluginConfig {

    private final File configFile;

    public boolean      whitelistIsBlacklist;
    public List<String> whitelist;
    public int          cacheDuration;
    public boolean      httpsOnly;
    public boolean      saveImages;
    public boolean      debug;
    public int          cooldown;
    public int          opCooldown;
    public String       maxSize;

    public PluginConfig(Mapify plugin) throws IOException {
        plugin.saveDefaultConfig();
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.update();

        FileConfiguration config = plugin.getConfig();
        
        this.whitelistIsBlacklist = config.getBoolean("whitelist-is-blacklist", true);
        this.whitelist = config.getStringList("whitelist");
        this.cacheDuration = config.getInt("cache-duration", 60);
        this.httpsOnly = config.getBoolean("https-only", true);
        this.saveImages = config.getBoolean("save-images", false);
        this.debug = config.getBoolean("debug-logging", false);
        this.cooldown = config.getInt("cooldown", 0);
        this.opCooldown = config.getInt("opCooldown", 0);
        this.maxSize = config.getString("max-size", "");
    }

    public void update() throws IOException {
        ConfigUpdater.update(Mapify.INSTANCE, "config.yml", this.configFile);
    }

}
