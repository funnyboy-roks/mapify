package com.funnyboyroks.mapify.command;

import com.funnyboyroks.mapify.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.Collections;
import java.util.List;

public class CommandRefreshMaps implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run in-game.");
            return true;
        }
        if (!sender.hasPermission("mapify.command.mapify")) {
            sender.sendMessage(net.md_5.bungee.api.ChatColor.RED + "You do not have permission to run this command.");
            return true;
        }

        Player player = (Player) sender;
        int count = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() != Material.FILLED_MAP) continue;
            MapMeta meta = (MapMeta) item.getItemMeta();

            MapView view = meta.getMapView();
            MapRenderer renderer = Util.getRenderer(view);
            if (renderer == null) continue;
            view.getRenderers().forEach(view::removeRenderer);
            view.addRenderer(renderer);
            ++count;
        }
        if (count == 0) {
            sender.sendMessage(ChatColor.RED + "No maps found to be refreshed.");
            return true;
        }
        sender.sendMessage(ChatColor.GREEN + "Refreshed " + count + " map" + (count == 1 ? "" : "s") + ".");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
