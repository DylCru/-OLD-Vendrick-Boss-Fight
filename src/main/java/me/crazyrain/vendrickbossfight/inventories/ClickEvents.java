package me.crazyrain.vendrickbossfight.inventories;

import me.crazyrain.vendrickbossfight.functionality.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class ClickEvents implements Listener {

    @EventHandler
    public void AddItem(InventoryClickEvent e){
        if (e.getClickedInventory() == null ||!(e.getClickedInventory().getHolder() instanceof VenInventory)){
            return;
        }

        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();
        if (item != null  && !item.getType().equals(Material.BLACK_STAINED_GLASS_PANE) && !item.getType().equals(Material.ARROW)){
            player.getInventory().addItem(e.getCurrentItem());
        }
    }

    @EventHandler
    public void movePage(InventoryClickEvent e){
        if (e.getClickedInventory() == null ||!(e.getClickedInventory().getHolder() instanceof VenInventory)){
            return;
        }

        e.setCancelled(true);
        VenInventory inv = null;
        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();
        if (item != null  && item.getType().equals(Material.ARROW)){
            int pageNum = Integer.parseInt(String.valueOf(item.getItemMeta().getLore().get(0).toCharArray()[7]));
            switch (pageNum){
                case 1:
                    inv = new VenInventory("Vendrick Items: All items", ItemManager.allItems, 1, false);
                    break;
                case 2:
                    inv = new VenInventory("Vendrick Items: Vendrick", ItemManager.vendrick, 2, false);
                    break;
                case 3:
                    inv = new VenInventory("Vendrick Items: Weapons", ItemManager.weapons, 3, false);
                    break;
                case 4:
                    inv = new VenInventory("Vendrick Items: Materials", ItemManager.materials, 4, true);
                    break;
            }
            player.openInventory(inv.getInventory());
        }
    }

    @EventHandler
    public void searchForItem(InventoryClickEvent e){
        if (e.getClickedInventory() == null ||!(e.getClickedInventory().getHolder() instanceof VenInventory)){
            return;
        }

        e.setCancelled(true);
        VenInventory inv = null;
        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();
        if (item != null  && item.getType().equals(Material.OAK_SIGN)){
            /*
            TODO:
                - add this
             */
        }
    }
}
