package GUI;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public class GUI {

	private Inventory inventory;
	private HashMap<Integer, GUICallback> clickCallbacks = new HashMap<Integer, GUICallback>();	// Callback-Liste
	private GUICallback closeCallback;
	
	
	// Konstruktor
	// Hier wird das Inventar für die GUI erstellt
	public GUI(int size, String title) {
		inventory = Bukkit.createInventory(null, size, title);
	}
	
	// GET INVENTORY
	public Inventory getInventory() {
		return inventory;
	}
	
	// SET INVENTORY
	public void setInventory(Inventory inv) {
		inventory = inv;
	}
	
	// Callback hinzufügen
	// Einem Slot wird ein Callback hinzugefügt, dass aufgerufen wird
	// wenn dieser Slot von einem Spieler geklickt wird
	public void addCallback(int slot, GUICallback callback) {
		clickCallbacks.put(slot, callback);
	}
	
	public void setCloseCallback(GUICallback callback) {
		closeCallback = callback;
	}
	
	// Callback entfernen
	// Callback wird von entsprechenden Slot entfernt
	public void removeCallback(int slot) {
		if( clickCallbacks.containsKey(slot) ) clickCallbacks.remove(slot);
	}
	
	// Klickevent
	// Die GUI wird benachricht auf welchen Slot und von welchem Player
	// geklickt wurde. Ist für den betreffenden Slot ein Callback gesetzt
	// wird dieses aufgerufen
	public void click(int slot, Player p, ClickType type) {
		if( clickCallbacks.containsKey(slot) ) clickCallbacks.get(slot).run(p, type);
	}
	
	public void close(Player p) {
		if( closeCallback != null ) closeCallback.run(p, null);
	}
	
}
