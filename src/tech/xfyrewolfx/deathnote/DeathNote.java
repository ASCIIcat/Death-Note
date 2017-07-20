package tech.xfyrewolfx.deathnote;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathNote extends JavaPlugin{
	private List<String> DNOwners;
	private Messages msg;
	private Pages p;
	
	public void onEnable(){
		DNOwners = new ArrayList<String>();
		msg = new Messages(this);
		p = new Pages(this);
		this.getCommand("dn").setExecutor(new Commands(this));
		this.getServer().getPluginManager().registerEvents(new NoteListener(this), this);
		this.getLogger().log(Level.INFO, "The Death Note has been enabled");
	}
	
	public Messages getMessages(){
		return msg;
	}
	
	public List<String> getOwners(){
		return DNOwners;
	}
	
	public ItemStack getNewDeathNote(){
		ItemStack note = new ItemStack(Material.BOOK_AND_QUILL);
		
		BookMeta im = (BookMeta)note.getItemMeta();
		im.setTitle("DeathNote");
		im.setDisplayName("§8§lDEATH§oN§8§l0TE");
		im.setAuthor("Unknown");
		im.setGeneration(Generation.TATTERED);
		List<String> list = new ArrayList<String>();
		list.add("");
		
		for(String page : p.getPages())
			list.add(page);
		
		im.setPages(list);
		
		List<String> lore = new ArrayList<String>();
		lore.add("§7The player whose name is written");
		lore.add("§7in this note shall die."); 
		im.setLore(lore);
		
		note.setItemMeta(im);
		return note;
	}
}
