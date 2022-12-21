package com.funnyboyroks.mapify.command;

import com.funnyboyroks.mapify.Mapify;
import com.funnyboyroks.mapify.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class CommandMapify implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used in-game.");
            return true;
        }
        if (!sender.hasPermission("mapify.command.refreshmaps")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length > 2) {
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

//        player.sendMessage("/mapify " + Arrays.toString(args));


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            return List.of("1x1");
        }
        return Collections.emptyList();
    }
}
