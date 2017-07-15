package tech.xfyrewolfx.deathnote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class NoteListener implements Listener{
	private DeathNote plugin;
	
	public NoteListener(DeathNote c){
		plugin = c;
		System.out.println("Listener loaded");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClick(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK || e.getAction()==Action.RIGHT_CLICK_AIR){
			if(e.getPlayer().getInventory().getItemInMainHand() != null){
				ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
				
				if(hand.getType()==Material.BOOK_AND_QUILL && hand.hasItemMeta()){
					if(hand.getItemMeta().hasDisplayName()){
						if(hand.getItemMeta().getDisplayName().equals(plugin.getNewDeathNote().getItemMeta().getDisplayName())){
							e.getPlayer().setMetadata("DeathNote", new FixedMetadataValue(plugin, true));
						}
					}
				}
			}
		}
	}
	
	// 1. Strikethrough names that were killed by this specific Death Note
	// 2. Death messages
	// 3. More rules
	// 4. Death causes
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEditBook(PlayerEditBookEvent e){
		if(e.getPlayer().hasMetadata("DeathNote")){
			String page = e.getNewBookMeta().getPages().get(0).replaceAll("§0", "");
			String[] names = page.split("\n");
			List<String> successfulKills = new ArrayList<String>();
			
			for(String pn : names){
				Player p = Bukkit.getPlayerExact(pn);
				if(p != null){
					if(p.isOnline()){
						if(!p.hasPermission("deathnote.exempt")){
							new DeathTimer(p).runTaskTimer(plugin, 0, 20);
							successfulKills.add("§m"+p.getName());
						}
					}
				}
			}
			
			// TODO put successfulKills between page 1 and the start of rules
			
			e.setCancelled(true);
			e.getPlayer().removeMetadata("DeathNote", plugin);
		}
	}
}
