package tech.xfyrewolfx.deathnote;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {
	DeathNote plugin;
	private FileConfiguration messages;
	private File messagesFile;
	private HashMap<String, String> vals;
	public Messages(DeathNote c){
		plugin=c;
		this.messages = null;
	    this.messagesFile = null;
	    
	    vals = new HashMap<String, String>();
	    this.reloadMessages();
	    
	    if(this.getMessages().contains("plugin-title")){
	    	this.loadValues();
	    }else{
	    	this.saveDefaultValues();
	    }
	    
	    plugin.getServer().getLogger().log(Level.INFO, "Loaded Custom Messages");
	}
	
	public void saveDefaultValues(){
		this.getMessages().set("plugin-title", "[&8&lDEATH&oN&8&l0TE&f]");
		this.getMessages().set("wrong-command", "&cThe &oShinigami &cdid not recognize your command");
		this.getMessages().set("no-permission", "&cYou are not permitted to acquire a Death Note!");
		this.getMessages().set("got-deathnote", "&8&oA mysterious notebook has entered your inventory");
		this.getMessages().set("must-be-player", "&cYou must be mortal to use the Death Note");
		this.getMessages().set("player-death", "%PLAYER% &fdied from a heart attack");
	
		this.saveMessages();
		this.loadValues();
	}
	
	public void loadValues(){
		vals.clear();
		
		vals.put("plugin-title", getMessages().getString("plugin-title").replaceAll("&", "§")+" ");
		vals.put("wrong-command", getMessages().getString("wrong-command").replaceAll("&", "§"));
		vals.put("no-permission", getMessages().getString("no-permission").replaceAll("&", "§"));
		vals.put("got-deathnote", getMessages().getString("got-deathnote").replaceAll("&", "§"));
		vals.put("must-be-player", getMessages().getString("must-be-player").replaceAll("&", "§"));
		vals.put("player-death", getMessages().getString("player-death").replaceAll("&", "§"));
	}
	
	public String wrongCommand(){
		return vals.get("plugin-title")+vals.get("wrong-command");
	}
	public String noPermission(){
		return vals.get("plugin-title")+vals.get("no-permission");
	}
	public String gotDeathnote(){
		return vals.get("plugin-title")+vals.get("got-deathnote");
	}
	public String mustBePlayer(){
		return vals.get("plugin-title")+vals.get("must-be-player");
	}
	public String deathMessage(String name){
		return vals.get("player-death").replaceAll("%PLAYER%", name);
	}
	
	public void reloadMessages(){
		if (this.messagesFile == null){
			this.messagesFile = new File(plugin.getDataFolder()+"/messages.yml");
			this.messages = YamlConfiguration.loadConfiguration(this.messagesFile);
		}
	}
	 
	public FileConfiguration getMessages(){
		if (this.messages == null) {
			reloadMessages();
		}
		return this.messages;
	}
	 
	public void saveMessages(){
		if ((this.messages == null) || (this.messagesFile == null)) {
			return;
		}
		
		try{
			getMessages().save(this.messagesFile);
		} catch (Exception ex){
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.messagesFile, ex);
		}
	}
}
