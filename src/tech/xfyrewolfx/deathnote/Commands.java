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
				sender.sendMessage("§7========= §8DEATH§oN§80TE §7==========");
				sender.sendMessage("§c/dn get §f- Get a new Death Note");
				sender.sendMessage("§7=============================");
			}else if(args.length==1){
				if(args[0].equalsIgnoreCase("get")){
					if(sender.hasPermission("deathnote.get")){
						if(sender instanceof Player){
							((Player)sender).getInventory().addItem(plugin.getNewDeathNote());
							((Player)sender).getInventory().addItem(new ItemStack(Material.APPLE,3));
							sender.sendMessage("§8§oA mysterious notebook has entered your inventory");
						}else{
							sender.sendMessage("§cYou must be mortal to use the Death Note");
						}
					}else{
						sender.sendMessage("§cYou are not permitted to acquire a Death Note");
					}
				}else{
					sender.sendMessage("§cThe §oShinigami §cdid not recognize your command");
				}
			}
			return true;
		}
		return false;
	}
}
