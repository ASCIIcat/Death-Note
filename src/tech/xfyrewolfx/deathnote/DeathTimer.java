package tech.xfyrewolfx.deathnote;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathTimer extends BukkitRunnable {
	
	Player p;
	int secs;
	public DeathTimer(Player pl){
		p=pl;
		secs=40;
	}
	
	public void run(){
		if(p.isOnline()){
			if(secs > 0){
				secs -= 1;
			}else{
				p.setHealth(0d);
				this.cancel();
			}
		}else{
			this.cancel();
		}
	}
}
