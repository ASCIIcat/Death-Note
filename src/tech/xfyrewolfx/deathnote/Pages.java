package tech.xfyrewolfx.deathnote;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Pages {
	DeathNote plugin;
	private FileConfiguration pagesConf;
	private File pagesFile;
	private List<String> pages;
	
	public Pages(DeathNote c){
		plugin=c;
		this.pagesConf = null;
	    this.pagesFile = null;
	    pages = new ArrayList<String>();
	    
	    this.reloadPages();
	    
	    if(this.getPagesFile().contains("pages"))
	    	this.loadValues();
	    else this.saveDefaultValues();
	}
	
	public List<String> getPages(){
		return pages;
	}
	
	public void saveDefaultValues(){
		pages.add("&8&lI.\n&0The player whose name is written in this note shall die.");
		pages.add("&8&lII.\n&0This note will not take effect unless the writer has the player's skin in their mind when writing his/her name. Therefore, players sharing the same name will not be affected.");
		pages.add("&8&lIII.\n&0This note will go into effect 40 seconds after writing the player's name.");
		pages.add("&8&lIV.\n&0The player who uses the notebook can neither go to Heaven nor Hell.");
		pages.add("&8&lV.\n&0The player who touches the Death Note can recognize the image and voice of its original owner, a god of death, even if the player is not the owner of the note.");
		pages.add("&8&lVI.\n&0The player in possession of the Death Note is possessed by a god of death, its original owner, until they die.");
		pages.add("&8&lVII.\n&0Gods of death, the original owners of the Death Note, do not do, in principle, anything which will help or prevent the deaths in the note.");
		pages.add("&8&lVIII.\n&0A god of death can extend their own life by putting a name on their own note, but players cannot.");
		pages.add("&8&lIX.\n&0A player can shorten his/her own life by using the note.");
		pages.add("&8&lX.\n&0A god of death cannot be killed even if stabbed in his heart with a knife or shot in the head with a gun. However, there are ways to kill a god of death, which are not generally known to the god of death.");
		
		this.getPagesFile().set("pages", pages);
		this.savePages();
		
		for(String page : new ArrayList<String>(pages)){
			pages.remove(pages.indexOf(page));
			pages.add(page.replaceAll("&", "§"));
		}
	}
	
	public void loadValues(){
		pages.clear();
		
		for(String s : this.getPagesFile().getStringList("pages")){
			if(pages.size()<49){
				pages.add(s.replaceAll("&", "§"));
			}
		}
	}
	
	public void reloadPages(){
		if (this.pagesFile == null){
			this.pagesFile = new File(plugin.getDataFolder()+"//pages.yml");
			this.pagesConf = YamlConfiguration.loadConfiguration(this.pagesFile);
		}
	}
	 
	public FileConfiguration getPagesFile(){
		if (this.pagesConf == null) {
			reloadPages();
		}
		return this.pagesConf;
	}
	 
	public void savePages(){
		if ((this.pagesConf == null) || (this.pagesFile == null)) {
			return;
		}
		
		try{
			getPagesFile().save(this.pagesFile);
		} catch (Exception ex){
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.pagesFile, ex);
		}
	}
}
