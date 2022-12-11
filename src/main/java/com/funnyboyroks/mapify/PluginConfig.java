package com.funnyboyroks.mapify;

import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PluginConfig {

    private final File configFile;

    public boolean      whitelistIsBlacklist;
    public List<String> whitelist;
    public int          cacheDuration;
    public boolean      httpsOnly;

    public PluginConfig(Mapify plugin) throws IOException {
        plugin.saveDefaultConfig();
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.update();

        FileConfiguration config = plugin.getConfig();

        this.whitelistIsBlacklist = config.getBoolean("whitelist-is-blacklist", true);
        this.whitelist = config.getStringList("whitelist");
        this.cacheDuration = config.getInt("cache-duration", 60);
        this.httpsOnly = config.getBoolean("https-only", true);


    }

    public void update() throws IOException {
        ConfigUpdater.update(Mapify.INSTANCE, "config.yml", this.configFile);
    }

}
