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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class NoteListener implements Listener{
	private DeathNote plugin;
	
	public NoteListener(DeathNote c){
		plugin = c;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClick(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK || e.getAction()==Action.RIGHT_CLICK_AIR){
			
			ItemStack hand;
			if(plugin.getServer().getBukkitVersion().contains("1.7") || plugin.getServer().getBukkitVersion().contains("1.8"))
				hand = e.getPlayer().getItemInHand();
			else 
				hand = e.getPlayer().getInventory().getItemInMainHand();
			
			if(hand != null){
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
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDeath(PlayerDeathEvent e){
		if(e.getEntity().hasMetadata("DeathNote_Killed")){
			e.getEntity().removeMetadata("DeathNote_Killed", plugin);
			e.setDeathMessage(plugin.getMessages().deathMessage(e.getEntity().getDisplayName()));
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEditBook(PlayerEditBookEvent e){
		if(e.getPlayer().hasMetadata("DeathNote")){
			String firstPage = e.getNewBookMeta().getPages().get(0).replaceAll("§0", "");
			String[] firstPageNames = firstPage.split("\n");
			
			if(e.getPlayer().hasPermission("deathnote.use")){
				List<String> oldPages = e.getPreviousBookMeta().getPages();
				List<String> pages = new ArrayList<String>(e.getNewBookMeta().getPages());
				List<String> newPages = new ArrayList<String>(pages);
				
				// Reset pages 2-50
				for(int i=1; i<oldPages.size(); i++){
					if(i < newPages.size())
						newPages.set(i, oldPages.get(i));
					else newPages.add(i, oldPages.get(i));
				}
				
				if(newPages.size() > oldPages.size()){
					int dif = newPages.size() - oldPages.size();
					int osize = newPages.size();
					for(int i=1; i<=dif; i++){
						newPages.remove(osize-i);
					}
				}
				
				for(String name : firstPageNames){
					int uses = 0;
					
					for(String page : newPages){
						if(page.startsWith("§m")){
							String[] namesOnPage = page.split("\n");
							uses += namesOnPage.length;
						}
					}
					
					if(uses < plugin.getMaxUses() || e.getPlayer().hasPermission("deathnote.nolimit")){
						Player player = Bukkit.getPlayerExact(name);
						if(player != null){
							if(player.isOnline()){
								if(!player.hasPermission("deathnote.exempt")){
									new DeathTimer(player, plugin).runTaskTimer(plugin, 0, 20);
									
									for(String page : new ArrayList<String>(newPages)){
										
										if(page.startsWith("§m")){
											String[] namesOnPage = page.split("\n");
											if(namesOnPage.length<12){
												newPages.set(newPages.indexOf(page), page + "\n§m"+name);
												break;
											}else{
												if(newPages.size()<50)
													newPages.add(newPages.indexOf(page), "§m"+name);
												break;
											}
										}else if(page.startsWith("§8")){
											if(newPages.size()<50)
												newPages.add(newPages.indexOf(page), "§m"+name);
											break;
										}
									}
								}
							}
						}
					}else break;
				}
				
				// Reset first page
				newPages.set(0, "");
				
				ItemStack book;
				if(plugin.getServer().getBukkitVersion().contains("1.7") || plugin.getServer().getBukkitVersion().contains("1.8"))
					book = e.getPlayer().getItemInHand();
				else 
					book = e.getPlayer().getInventory().getItemInMainHand();
				
				BookMeta nbm = (BookMeta)book.getItemMeta();
				nbm.setPages(newPages);
				book.setItemMeta(nbm);
			}
			
			e.setCancelled(true);
			e.getPlayer().removeMetadata("DeathNote", plugin);
		}
	}
}

