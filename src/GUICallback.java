package GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface GUICallback {

	public abstract void run(Player p, ClickType type);

}
