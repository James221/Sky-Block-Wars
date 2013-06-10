package me.kyle.burnett.SkyBlockWarriors.Listeners;

import me.kyle.burnett.SkyBlockWarriors.Main;
import net.minecraft.server.v1_5_R3.Packet205ClientCommand;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;

public class PlayerDeath implements Listener{
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Entity ent = e.getEntity();
		
		if(ent instanceof Player){
			Player p = (Player) ent;
			
			if(Main.gameAPI.isInGame(p)){
				Packet205ClientCommand packet = new Packet205ClientCommand();
				packet.a = 1;
				((CraftPlayer) e.getEntity()).getHandle().playerConnection.sendPacket(packet);
				p.teleport(Main.getLobby());
				
				Inventory main = Main.invent.fromBase64(Main.Inv.getString(p.getName() + ".Main"));
				Inventory armor = Main.invent.fromBase64(Main.Inv.getString(p.getName() + ".Armor"));
				
				if(main != null){
					p.getInventory().setContents(main.getContents());

				}
				
				if(armor != null){
					p.getInventory().setArmorContents(armor.getContents());
				}
				
				p.sendMessage(ChatColor.RED + "You were killed by " + ChatColor.GOLD + e.getEntity().getLastDamage() + ChatColor.RED + ".");
				
				Main.playerAPI.sendDeathMessage(p, ChatColor.BLUE + "Player " + ChatColor.GOLD + p.getName() + ChatColor.BLUE + "has been eliminated.");
			}
		}
	}

}