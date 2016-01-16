package GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
	
	private GUIManager manager;
	
	public EventListener(GUIManager manager) {
		this.manager = manager;
	}
	
	
	// Wenn ein Spieler ein Inventar schließst, wird der GUIManager
	// benachrichtigt, dass es geschlossen wurde
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if( !(e.getPlayer() instanceof Player) ) return;	
		manager.closeGUI( (Player) e.getPlayer() );
	}
	
	
	// Jeder Klick auf ein Inventar wird an den GUIManager weitergeleitet. 
	// Befindet sich der Spieler in einem GUI wird "true" zurückgegeben und das Event
	// wird gecancelt
	@EventHandler 
	public void onClick(InventoryClickEvent e) {
		e.setCancelled( manager.click((Player) e.getWhoClicked(), e.getRawSlot(), e.getClick()) );
	}
	
	// Da das Inventar und dit Hotbar des Spielers auch als GUI gelten kann, muss bei jedem 
	// Rechts- oder Linksklick überprüft werden, ob sich der Spieler gerade in einer GUI befindet
	// und gegebenfalls gecancelt werden
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		switch( e.getAction() ) {
			case LEFT_CLICK_AIR:
			case LEFT_CLICK_BLOCK:
				e.setCancelled( manager.click(e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot(), ClickType.LEFT) );
				break;
				
			case RIGHT_CLICK_AIR:
			case RIGHT_CLICK_BLOCK:
				e.setCancelled( manager.click(e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot(), ClickType.RIGHT) );
				break;
				
			default:
		}
		
		e.getPlayer().updateInventory();
		
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		e.setCancelled( manager.click(e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot(), ClickType.DROP) );
	}
	
	// Wenn der Spieler den Server verlässt soll das GUI geschlossen werden.
	// Da der Spieler sich auch in einer SubGUI befinden kann wird sicherheitshalber
	// 2mal versucht das GUI zu schließen
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		manager.forceClose( (Player) e.getPlayer() );
		manager.forceClose( (Player) e.getPlayer() );
	}
}
