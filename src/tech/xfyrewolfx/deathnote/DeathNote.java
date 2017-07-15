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
	
	public void onEnable(){
		DNOwners = new ArrayList<String>();
		this.getCommand("dn").setExecutor(new Commands(this));
		this.getServer().getPluginManager().registerEvents(new NoteListener(this), this);
		this.getLogger().log(Level.INFO, "The Death Note has been enabled");
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
		list.add("§8§lI.\n§0The player whose name is written in this note shall die");
		list.add("§8§lII.\n§0This note will not take effect unless the writer has the player's skin in their mind when writing his/her name. Therefore, players sharing the same name will not be affected.");
		list.add("§8§lIII.\n§0This note will go into effect 40 seconds after writing the player's name");
		list.add("§8§lIV.\n§0The player who uses the notebook can neither go to Heaven nor Hell");
		list.add("§8§lV.\n§0The player who touches the Death Note can recognize the image and voice of its original owner, a god of death, even if the player is not the owner of the note. ");
		list.add("§8§lVI.\n§0The player in possession of the Death Note is possessed by a god of death, its original owner, until they die. ");
		list.add("§8§lVII.\n§0Gods of death, the original owners of the Death Note, do not do, in principle, anything which will help or prevent the deaths in the note.");
		list.add("§8§lVIII.\n§0A god of death can extend their own life by putting a name on their own note, but players cannot. ");
		list.add("§8§lIX.\n§0A player can shorten his/her own life by using the note. ");
		list.add("§8§lX.\n§0A god of death cannot be killed even if stabbed in his heart with a knife or shot in the head with a gun. However, there are ways to kill a god of death, which are not generally known to the god of death. ");
		
		im.setPages(list);
		
		List<String> lore = new ArrayList<String>();
		lore.add("§7The player whose name is written");
		lore.add("§7in this note shall die."); 
		im.setLore(lore);
		
		note.setItemMeta(im);
		return note;
	}
}
