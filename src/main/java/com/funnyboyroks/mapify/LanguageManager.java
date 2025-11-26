package com.funnyboyroks.mapify;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for managing language messages from lang.yml file
 * Allows server administrators to customize message text as desired
 */
public class LanguageManager {
    
    private final Mapify plugin;
    private FileConfiguration langConfig;
    private final Map<String, String> messages;
    
    public LanguageManager(Mapify plugin) {
        this.plugin = plugin;
        this.messages = new HashMap<>();
        loadLanguageFile();
    }
    
    /**
     * Loads the language file from lang.yml
     */
    public void loadLanguageFile() {
        File langFile = new File(plugin.getDataFolder(), "lang.yml");
        
        // If file doesn't exist, copy from resources
        if (!langFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        
        // Load configuration
        langConfig = YamlConfiguration.loadConfiguration(langFile);
        
        // Load all messages to map for fast access
        loadMessages();
    }
    
    /**
     * Loads all messages from configuration to map
     */
    private void loadMessages() {
        messages.clear();
        
        // Load all messages using flat dot notation keys
        for (String key : langConfig.getKeys(false)) {
            messages.put(key, langConfig.getString(key));
        }
    }
    
    /**
     * Gets message based on key
     * @param key Key of the message to retrieve
     * @return Appropriate message, or key if not found
     */
    public String getMessage(String key) {
        return messages.getOrDefault(key, key);
    }
    
    /**
     * Gets message based on key with formatting
     * @param key Key of the message to retrieve
     * @param args Arguments for message formatting
     * @return Formatted message, or key if not found
     */
    public String getMessage(String key, Object... args) {
        String message = getMessage(key);
        try {
            return String.format(message, args);
        } catch (Exception e) {
            plugin.getLogger().warning("Error formatting message '" + key + "': " + e.getMessage());
            return message;
        }
    }
    
    /**
     * Gets message based on key with formatting and placeholders
     * @param key Key of the message to retrieve
     * @param placeholders Map of placeholders to replace
     * @return Formatted message, or key if not found
     */
    public String getMessage(String key, Map<String, String> placeholders) {
        String message = getMessage(key);
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace("%" + entry.getKey() + "%", entry.getValue());
        }
        return message;
    }
    
    /**
     * Reloads the language file
     * Used when /mapify reload command is executed
     */
    public void reload() {
        loadLanguageFile();
    }
}