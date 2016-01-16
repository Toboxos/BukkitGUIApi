#Author: Toboxos
#Version: 1.0
#
#
## How to Use:
#### *Copy the Source Files into your Project*
####


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

### Open GUI:
``` JAVA
// If extraInventory is false the Players Hotbar is the GUI
manager.openGUI(Player p, String id, boolean extraInventory); 

// If the Players Hotbar is a GUI you can use this to open a SubGUI in an extra Inventory
manager.openSubGUI(Player p, String id);
```

### Close GUI:
``` JAVA
// This will fire the CloseCallback
manager.closeGUI(Player p);

// This will NOT fire the CloseCallback. You need this to close a GUI in Players Hotbar
manager.forceClose(Player p);
```


### Example Code:
``` JAVA
    final GUIManager manager = new GUIManager(JavaPlugin);
    
    manager.creatGUI("lobby", 1, "");
    manager.createGUI("gadgets", 2, "§cGadgets");
    
    GUI gui = manager.getGUI("lobby");
    gui.getInventory().addItem(0, new ItemStack(Material.REDSTONE_BLOCK));
    gui.addCallback(0, new GUICallback() {
        @Override
        public void run(Player p, ClickType type) {
            if( type == ClickType.LEFT ) p.sendMessage("Leftclick");
            if( type == ClickType.RIGHT ) p.sendMessage("Rightclick");
            manager.openSubGUI(p, "gadgets");
        }
    });
    
    gui = manager.getGUI("gadgets");
    gui.getInventory().addItem(0, new ItemStack(Material.GOLD_BLOCK));
    gui.getInventory().addItem(1, new ItemStack(Material.DIAMOND_BLOCK));
    gui.addCallback(0, new GUICallback() {
        @Override
        public void run(Player p, ClickType type) {
            p.sendMessages("You clicked GoldBlock");
        }
    });
    gui.addCallback(1, new GUICallback() {
        @Override
        public void run(Player p, ClickType type) {
            p.sendMessage("You clicked DiamondBlock");
        }
    });
    
    // At PlayerJoinEvent
    manager.openGUI(e.getPlayer(), "gadgets", false);
```