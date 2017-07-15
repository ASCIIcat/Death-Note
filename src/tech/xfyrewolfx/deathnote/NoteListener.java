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
import org.bukkit.inventory.meta.BookMeta;
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
	
	// TODO handle note ownership, add some form of Shinigami to the game that only this player can see
//	@EventHandler
//	public void holdDeathNote(PlayerItemHeldEvent e){
//		ItemStack main = e.getPlayer().getInventory().getItemInMainHand();
//		ItemStack off = e.getPlayer().getInventory().getItemInOffHand();
//		String uuid = e.getPlayer().getUniqueId().toString();
//		
//		if(main != null){
//			if(main.getType()==Material.BOOK_AND_QUILL && main.hasItemMeta()){
//				if(main.getItemMeta().hasDisplayName()){
//					if(main.getItemMeta().getDisplayName().equals(plugin.getNewDeathNote().getItemMeta().getDisplayName())){
//						if(!plugin.getOwners().contains(uuid))
//							plugin.getOwners().add(uuid);
//					}
//				}
//			}
//		}
//		
//		if(off != null){
//			if(off.getType()==Material.BOOK_AND_QUILL && off.hasItemMeta()){
//				if(off.getItemMeta().hasDisplayName()){
//					if(off.getItemMeta().getDisplayName().equals(plugin.getNewDeathNote().getItemMeta().getDisplayName())){
//						if(!plugin.getOwners().contains(uuid))
//							plugin.getOwners().add(uuid);
//					}
//				}
//			}
//		}
//	}
//	
//	@EventHandler
//	public void onDeath(PlayerDeathEvent e){
//		if(plugin.getOwners().contains(e.getEntity().getUniqueId().toString())){
//			plugin.getOwners().remove(e.getEntity().getUniqueId().toString());
//		}
//	}
	
	// TODO: ignore duplicate entries	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEditBook(PlayerEditBookEvent e){
		if(e.getPlayer().hasMetadata("DeathNote")){
			String firstPage = e.getNewBookMeta().getPages().get(0).replaceAll("§0", "");
			String[] firstPageNames = firstPage.split("\n");
			List<String> successfulKills = new ArrayList<String>();
			
			for(String possibleName : firstPageNames){
				Player player = Bukkit.getPlayerExact(possibleName);
				if(player != null){
					if(player.isOnline()){
						if(!player.hasPermission("deathnote.exempt")){
							new DeathTimer(player).runTaskTimer(plugin, 0, 20);
							successfulKills.add("§m"+player.getName());
						}
					}
				}
			}
			
			List<String> pages = new ArrayList<String>(e.getNewBookMeta().getPages());
			List<String> newPages = new ArrayList<String>(pages);
			
			for(String pk : new ArrayList<String>(successfulKills)){
				for(String page : pages){
					if(page.startsWith("§m")){
						String[] namesOnPage = page.split("\n");
						if(namesOnPage.length<12){
							newPages.set(pages.indexOf(page), page + "\n§m"+pk);
							break;
						}else{
							if(newPages.size()<50)
								newPages.add(pages.indexOf(page), "§m"+pk);
							break;
						}
					}else if(page.startsWith("§8")){
						if(newPages.size()<50)
							newPages.add(pages.indexOf(page), "§m"+pk);
						break;
					}
				}
			}
			
			newPages.set(0, "");
			
			ItemStack book = e.getPlayer().getInventory().getItemInMainHand();
			BookMeta nbm = (BookMeta)book.getItemMeta();
			nbm.setPages(newPages);
			book.setItemMeta(nbm);
			
			e.setCancelled(true);
			e.getPlayer().removeMetadata("DeathNote", plugin);
		}
	}
}

