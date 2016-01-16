package GUI;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GUIManager {
	
	private  HashMap<String, GUI> GUIs = new HashMap<String, GUI>();			// Erstellt GUIs
	private  HashMap<Player, String> openGUIs = new HashMap<Player, String>();	// Spieler in GUIs
	private  HashMap<Player, String> openSubGUIs = new HashMap<Player, String>(); // Spieler in SubGUIs
	private  HashMap<UUID, ItemStack[]> playerInv = new HashMap<UUID, ItemStack[]>();	// Gespeicherte Inventare
	
	// Konstruktor
	public GUIManager(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new EventListener(this), plugin);
	}

	// Neue GUI erstellen
	public void createGUI(String id, int size, String title) {
		GUI gui = new GUI(size * 9, title);
		GUIs.put(id, gui);
	}
	
	// Vorhandene GUI löschen
	public void deleteGUI(String id) {
		if( GUIs.containsKey(id) ) GUIs.remove(id);
	}
	
	// Vorhande GUI öffnen
	// Spieler wird einer Liste hinzugefügt, dass das System weiß, 
	// dass der Spieler sich gerade in einer GUI befindet und in welcher
	// GUI er sich befindet. Soll sich das GUI nicht als extra Inventar öffnen,
	// wird das Inventar des Spielers zuerst gespeichert und dann ersetzt
	public void openGUI(Player p, String id, boolean extraInventory) {
		if( !GUIs.containsKey(id) ) return;
		GUI gui = GUIs.get(id);
		if( extraInventory ) {
			p.openInventory(gui.getInventory());
			openGUIs.put(p, id);
		} else {
			playerInv.put(p.getUniqueId(), p.getInventory().getContents());
			p.getInventory().setContents(gui.getInventory().getContents());
			openGUIs.put(p, id);
			p.updateInventory();
		}
	}
	
	// Wenn das Inventar des Spielers eine GUI ist, kann man ein Extra-Inventar als 
	// SubGUI aufrufen, damit das HauptGUI nicht mitgeschlossen wird, sollte das SubGUI
	// geschlossen werden
	public void openSubGUI(Player p, String id) {
		if( !GUIs.containsKey(id) ) return;
		GUI gui = GUIs.get(id);
		p.openInventory(gui.getInventory());
		openSubGUIs.put(p, id);
	}
	
	// Spieler wird wieder aus der Liste gelöscht
	// Ist seine Hotbar das GUI, wird es nicht geschlossen.
	// Bei dieser Methode wird das CloseCallback des Spielers aufgerufen
	public void closeGUI(Player p) {
		if( openSubGUIs.containsKey(p) ) {
			openSubGUIs.remove(p);
			p.closeInventory();
			GUI gui = GUIs.get(openSubGUIs.get(p));
			if( gui != null) gui.close(p);
			return;
		}
		if( openGUIs.containsKey(p) && !playerInv.containsKey(p.getUniqueId()) ) {
			openGUIs.remove(p);
			p.closeInventory();
			GUIs.get(openGUIs.get(p)).close(p);
			return;
		}
	}
	
	// Das GUI des Spielers wird geschlossen, selbst wenn es die Hotbar ist.
	// Der Spieler wird aus der Liste gelöscht und falls nötig wird ihm sein
	// altes Inventar wiedergegeben. Bei dieser Methode wird nicht das CloseCallback
	// des GUI des Spielers aufgerufen
	public void forceClose(Player p) {
		if( openSubGUIs.containsKey(p) ) {
			openSubGUIs.remove(p);
			return;
		}
		if( openGUIs.containsKey(p) ) openGUIs.remove(p);
		if( playerInv.containsKey(p.getUniqueId()) ) {
			p.getInventory().setContents(playerInv.get(p.getUniqueId()));
			playerInv.remove(p.getUniqueId());
			p.updateInventory();
		}
		p.closeInventory();
	}
	
	// GUI wird zurückgegeben
	public GUI getGUI(String id) {
		if( GUIs.containsKey(id) ) return GUIs.get(id);
		else return null;
	}
	
	// Klickevent wird ausgelöst.
	// Befindet sich der Spieler in einer GUI wird "true" zurückgegben,
	// damit der Klick gecancelt wird. Die geöffnet GUI wird benachrichtigt,
	// welcher Spieler auf welchen Slot geklickt hat
	public boolean click(final Player p, final int slot, final ClickType type) {
		if( openSubGUIs.containsKey(p) ) {
			GUIs.get( openSubGUIs.get(p) ).click(slot, p, type);
			return true;
		}
		if ( openGUIs.containsKey(p) ) {
			GUIs.get( openGUIs.get(p) ).click(slot, p, type);
			return true;
		} 
		return false;		
	}
	
}
