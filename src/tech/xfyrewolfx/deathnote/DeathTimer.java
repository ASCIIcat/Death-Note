package tech.xfyrewolfx.deathnote;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathTimer extends BukkitRunnable {
	
	Player p;
	DeathNote plugin;
	int secs;
	public DeathTimer(Player pl, DeathNote c){
		p=pl;
		secs=40;
		plugin=c;
	}
	
	public void run(){
		if(p.isOnline()){
			if(secs > 0){
				secs -= 1;
			}else{
				p.setMetadata("DeathNote_Killed", new FixedMetadataValue(plugin, true));
				p.setHealth(0d);
				this.cancel();
			}
		}else{
			this.cancel();
		}
	}
}
