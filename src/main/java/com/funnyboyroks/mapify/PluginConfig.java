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
        this.opCooldown = config.getInt("op-cooldown", 0);
        this.maxSize = config.getString("max-size", "");
    }

    public void update() throws IOException {
        ConfigUpdater.update(Mapify.INSTANCE, "config.yml", this.configFile);
    }

    // should be a record, but we're using old java
    public static class Diff {
        public String key;
        public String old;
        public String neu;

        public <T> Diff(String key, T old, T neu) {
            this.key = key;
            this.old = old == null ? null : old.toString();
            this.neu = neu == null ? null : neu.toString();
        }
    };

    public List<Diff> diff(PluginConfig neu) {
        List<Diff> out = new ArrayList<>();
        if (this.whitelistIsBlacklist != neu.whitelistIsBlacklist)
            out.add(new Diff("whitelist-is-blacklist", this.whitelistIsBlacklist, neu.whitelistIsBlacklist));
        if (!this.whitelist.equals(neu.whitelist))
            out.add(new Diff("whitelist", this.whitelist, neu.whitelist));
        if (this.cacheDuration != neu.cacheDuration)
            out.add(new Diff("cache-duration", this.cacheDuration, neu.cacheDuration));
        if (this.httpsOnly != neu.httpsOnly)
            out.add(new Diff("https-only", this.httpsOnly, neu.httpsOnly));
        if (this.saveImages != neu.saveImages)
            out.add(new Diff("save-images", this.saveImages, neu.saveImages));
        if (this.debug != neu.debug)
            out.add(new Diff("debug-logging", this.debug, neu.debug));
        if (this.cooldown != neu.cooldown)
            out.add(new Diff("cooldown", this.cooldown, neu.cooldown));
        if (this.opCooldown != neu.opCooldown)
            out.add(new Diff("op-cooldown", this.opCooldown, neu.opCooldown));
        if (!this.maxSize.equals(neu.maxSize))
            out.add(new Diff("max-size", this.maxSize, neu.maxSize));
        return out;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PluginConfig { ");
        sb.append("whitelistIsBlacklist: "); sb.append(this.whitelistIsBlacklist); sb.append(", ");
        sb.append("whitelist: "); sb.append(this.whitelist); sb.append(", ");
        sb.append("cacheDuration: "); sb.append(this.cacheDuration); sb.append(", ");
        sb.append("httpsOnly: "); sb.append(this.httpsOnly); sb.append(", ");
        sb.append("saveImages: "); sb.append(this.saveImages); sb.append(", ");
        sb.append("debug: "); sb.append(this.debug); sb.append(", ");
        sb.append("cooldown: "); sb.append(this.cooldown); sb.append(", ");
        sb.append("opCooldown: "); sb.append(this.opCooldown); sb.append(", ");
        sb.append("maxSize: "); sb.append(this.maxSize); sb.append(", ");
        sb.append("}");
        return sb.toString();
    }

}
