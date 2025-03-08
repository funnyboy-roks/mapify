package com.funnyboyroks.mapify.command;

import com.funnyboyroks.mapify.Mapify;
import com.funnyboyroks.mapify.util.Util;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommandMapify implements CommandExecutor, TabCompleter {

    private static final Map<UUID, Long> cooldownMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used in-game.");
            return true;
        }

        if (!sender.hasPermission("mapify.command.mapify")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
            return true;
        }

        Player player = (Player) sender;
        Long cdEnd = cooldownMap.get(player.getUniqueId());
        if (cdEnd != null) {
            long current = System.currentTimeMillis();
            long remaining = (cdEnd - current) / 1000;
            if (remaining > 0) {
                sender.sendMessage(ChatColor.RED + "You can use this command again in %d seconds.".formatted(remaining));
                return true;
            }
        } else {
            int cooldown = player.isOp() ? Mapify.INSTANCE.config.cooldown : Mapify.INSTANCE.config.opCooldown;
            if (cooldown > 0) {
                long end = System.currentTimeMillis() + cooldown * 1000;
                cooldownMap.put(player.getUniqueId(), end);
                Bukkit.getScheduler().runTaskLater(Mapify.INSTANCE, () -> {
                    cooldownMap.remove(player.getUniqueId());
                }, cooldown * 20L);
            }
        }

        if (args.length == 0 || args.length > 2) {
            return false;
        }

        URL url = Util.getUrl(args[0]);

        if (url == null) {
            player.sendMessage(ChatColor.RED + "Please specify a valid URL.");
            return true;
        }

        if (!Util.isAllowed(url)) {
            player.sendMessage(ChatColor.RED + "This is not a valid domain, please contact your server administrators if you believe this is an issue.");
            return true;
        }

        if (!url.getProtocol().equalsIgnoreCase("https") && Mapify.INSTANCE.config.httpsOnly) {
            player.sendMessage(ChatColor.RED + "This URL is not https. You must use a URL with https.");
            return true;
        }

        Point dims = new Point(1, 1);
        if (args.length == 2) {
            dims = Util.getDimensions(args[1]);
            if (dims == null) {
                player.sendMessage(ChatColor.RED + "Invalid dimensions, must be in the form WIDTHxHEIGHT: \"1x1\".");
                return true;
            }
        }

        List<ItemStack> stacks = Util.getMaps(args[0], dims.x, dims.y);

        if (stacks == null) {
            player.sendMessage(ChatColor.RED + "This URL does not have an image.");
            return true;
        }

        Util.giveItems(player, stacks.toArray(ItemStack[]::new));

        player.sendMessage(ChatColor.GREEN + "Given " + stacks.size() + " map" + (stacks.size() == 1 ? "." : "s."));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return args.length == 2 ? List.of("1x1") : Collections.emptyList();
    }
}
