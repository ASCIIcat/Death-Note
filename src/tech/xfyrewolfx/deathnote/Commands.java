package tech.xfyrewolfx.deathnote;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor{
	
	private DeathNote plugin;
	public Commands(DeathNote c){
		plugin=c;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("dn")){
			if(args.length==0 || args.length > 1){
				sender.sendMessage("�7========= �8DEATH�oN�80TE �7==========");
				sender.sendMessage("�c/dn get �f- Get a new Death Note");
				sender.sendMessage("");
				sender.sendMessage("�5Created by FireBreath15");
				sender.sendMessage("�5�ohttp://xfyrewolfx.tech/");
				sender.sendMessage("�7=============================");
			}else if(args.length==1){
				if(args[0].equalsIgnoreCase("get")){
					if(sender.hasPermission("deathnote.get")){
						if(sender instanceof Player){
							((Player)sender).getInventory().addItem(plugin.getNewDeathNote());
							((Player)sender).getInventory().addItem(new ItemStack(Material.APPLE,3));
							sender.sendMessage(plugin.getMessages().gotDeathnote());
						}else{
							sender.sendMessage(plugin.getMessages().mustBePlayer());
						}
					}else{
						sender.sendMessage(plugin.getMessages().noPermission());
					}
				}else{
					sender.sendMessage(plugin.getMessages().wrongCommand());
				}
			}
			return true;
		}
		return false;
	}
}
