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
	public void onEnable(){
		this.getCommand("dn").setExecutor(new Commands(this));
		this.getServer().getPluginManager().registerEvents(new NoteListener(this), this);
		this.getLogger().log(Level.INFO, "The Death Note has been enabled");
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
		list.add("§lI.\n§0The player whose name is written in this note shall die");
		list.add("§lII.\n§0This note will not take effect unless the writer has the player's skin in their mind when writing his/her name. Therefore, players sharing the same name will not be affected.");
		list.add("§lIII.\n§0This note will go into effect 40 seconds after writing the player's name");
		list.add("§lIV.\n§0The player who uses the notebook can neither go to Heaven nor Hell");
		
		// Commented out because reasons
//		for(int i=0; i<45; i++){
//			list.add("");
//		}
		
		im.setPages(list);
		
		List<String> lore = new ArrayList<String>();
		lore.add("§7The player whose name is written");
		lore.add("§7in this note shall die."); 
		im.setLore(lore);
		
		note.setItemMeta(im);
		return note;
	}
}
