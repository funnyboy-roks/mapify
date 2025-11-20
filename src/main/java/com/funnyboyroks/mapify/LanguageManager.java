package com.funnyboyroks.mapify;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Kelas untuk mengelola pesan-pesan bahasa dari file lang.yml
 * Memungkinkan admin server untuk mengubah teks pesan sesuai keinginan
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
     * Memuat file bahasa dari lang.yml
     */
    public void loadLanguageFile() {
        File langFile = new File(plugin.getDataFolder(), "lang.yml");
        
        // Jika file tidak ada, salin dari resources
        if (!langFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        
        // Load konfigurasi
        langConfig = YamlConfiguration.loadConfiguration(langFile);
        
        // Load semua pesan ke map untuk akses cepat
        loadMessages();
    }
    
    /**
     * Memuat semua pesan dari konfigurasi ke map
     */
    private void loadMessages() {
        messages.clear();
        
        // Load command messages
        if (langConfig.contains("command")) {
            for (String key : langConfig.getConfigurationSection("command").getKeys(false)) {
                String fullPath = "command." + key;
                messages.put(key, langConfig.getString(fullPath));
            }
        }
        
        // Load config messages
        if (langConfig.contains("config")) {
            for (String key : langConfig.getConfigurationSection("config").getKeys(false)) {
                String fullPath = "config." + key;
                messages.put(key, langConfig.getString(fullPath));
            }
        }
        
        // Load system messages
        if (langConfig.contains("system")) {
            for (String key : langConfig.getConfigurationSection("system").getKeys(false)) {
                String fullPath = "system." + key;
                messages.put(key, langConfig.getString(fullPath));
            }
        }
    }
    
    /**
     * Mendapatkan pesan berdasarkan key
     * @param key Key pesan yang ingin diambil
     * @return Pesan yang sesuai, atau key jika tidak ditemukan
     */
    public String getMessage(String key) {
        return messages.getOrDefault(key, key);
    }
    
    /**
     * Mendapatkan pesan berdasarkan key dengan format
     * @param key Key pesan yang ingin diambil
     * @param args Argumen untuk format pesan
     * @return Pesan yang sudah diformat, atau key jika tidak ditemukan
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
     * Mendapatkan pesan berdasarkan key dengan format dan placeholder
     * @param key Key pesan yang ingin diambil
     * @param placeholders Map of placeholders to replace
     * @return Pesan yang sudah diformat, atau key jika tidak ditemukan
     */
    public String getMessage(String key, Map<String, String> placeholders) {
        String message = getMessage(key);
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace("%" + entry.getKey() + "%", entry.getValue());
        }
        return message;
    }
    
    /**
     * Memuat ulang file bahasa
     * Digunakan saat perintah /mapify reload dijalankan
     */
    public void reload() {
        loadLanguageFile();
    }
}