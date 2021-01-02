package me.sstorey.speedrunhunter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

	public void onEnable() {
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "SpeedrunnerHunterCompass -> Plugin Enabled!");
	}
	
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "SpeedrunnerHunterCompass -> Plugin Disabled!");
	}
	
	public boolean  onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.DARK_RED + "Tracker");
		item.setItemMeta(im);
		
		ItemStack beef = new ItemStack(Material.COOKED_BEEF, 2);
	
		//initial instructions
		if(label.equalsIgnoreCase("srh")) {
			player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
			player.sendMessage(ChatColor.BLUE + "Speedrun Hunter");
			player.sendMessage(ChatColor.BLUE + "/hunter <name>" + ChatColor.WHITE + " to set the player as a hunter");
			player.sendMessage(ChatColor.BLUE + "/speedrunner <name>" + ChatColor.WHITE + " to set the player as a runner");
			player.sendMessage(ChatColor.BLUE + "/compass" + ChatColor.WHITE + " to get a compass");
			player.sendMessage(ChatColor.BLUE + "/follow <name>" + ChatColor.WHITE + " to set the compass target");
			player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
			player.sendMessage("");
			return true;
		}
		
		
		// /hunter sets as hunter and gives compass
		if(label.equalsIgnoreCase("hunter")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.BLUE + "/hunter <name>");
				return true;
			} 
			if(args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				sender.sendMessage(ChatColor.LIGHT_PURPLE + target.getName() + " set as hunter!");
				target.getInventory().addItem(item);
				return true;
			}
		}
		
		
		// /runner sets as runner
		if(label.equalsIgnoreCase("speedrunner")) {			
			if(args.length == 0) {
				sender.sendMessage(ChatColor.BLUE + "/speedrunner <name>");
				return true;
			} 
			Player target = Bukkit.getPlayer(args[0]);
			if(args.length == 1) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + target.getName() + " set as speedrunner!");
				target.getInventory().addItem(beef);
				return true;
			}
		}
		
		
		//give compass
		if(label.equalsIgnoreCase("compass")) {
			player.getInventory().addItem(item);
			return true;
		}
		
		
		//follow player
		if(label.equalsIgnoreCase("follow")) {
			Player target = Bukkit.getPlayer(args[0]);
			if(args.length == 0){
				player.sendMessage(ChatColor.RED + "Available Players: " + Bukkit.getOnlinePlayers());
				player.sendMessage(ChatColor.RED + "Try /follow <player>");
				return true;
			} else if(args.length == 1) {
				player.sendMessage(ChatColor.RED + "Following " + target.getName());
				
			}
			
			new BukkitRunnable() {
				public void run() {
				Location loc = target.getLocation();
				player.setCompassTarget(loc);
				}
			}.runTaskTimerAsynchronously(this, 0, 1);
		}
		
		return true;
	}
	
}

