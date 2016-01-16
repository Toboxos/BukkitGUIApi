# Author: Toboxos
# Version: 1.0
#
#
## How to Use:


### Create Manager:

``` JAVA
GUIManager manager = new GUIManager(JavaPlugin);
```

### Create GUI:
``` JAVA
manager.createGUI(String id, int rows, String title);
```

### Delete GUI:
``` JAVA
manager.deleteGUI(String id);
```

### Get GUI:
``` JAVA
GUI gui = manager.getGUI(String id);
```

### Edit GUI-Inventory:
``` JAVA
gui.getInventory();
gui.setInventory(Inventory inv);
```

### Add Callback to Slot/CloseCallback:
``` JAVA
gui.addCallback(int slot, new GUICallback() {
    @Override
    public void run(Player p, ClickType type) {
        // Your Code
    }
});

gui.setCloseCallback(new GUICallback() {
    @Override
    public void run(Player p, ClickType type) {
        // Your Code
    }
});
```

### Delete Callback from Slot/CloseCallback:
``` JAVA
gui.removeCallback(int slot);
gui.setCloseCallback(null);
```

### OpenGUI:
``` JAVA
// If extraInventory is false the Players Hotbar is the GUI
manager.openGUI(String id, Player p, boolean extraInventory); 

// If the Players Hotbar is a GUI you can use this to open a SubGUI in an extra Inventory
manager.openSubGUI(String id, Player p);
```