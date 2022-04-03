package me.crazyrain.vendrickbossfight.Commands;

import me.crazyrain.vendrickbossfight.VendrickBossFight;
import me.crazyrain.vendrickbossfight.functionality.ItemManager;
import me.crazyrain.vendrickbossfight.functionality.Lang;
import me.crazyrain.vendrickbossfight.inventories.VenItems;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.logging.Level;

public class Commands implements CommandExecutor {
    VendrickBossFight plugin;

    public Commands(VendrickBossFight plugin){
        this.plugin = plugin;
    }
    String venPrefix = ChatColor.AQUA + "[VEN]";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            plugin.getLogger().log(Level.WARNING, "Only players can use commands!");
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getLabel().equalsIgnoreCase("ven")){
            if (player.isOp()){
                if (args.length > 0){
                    if (args[0].equalsIgnoreCase("help")){
                        player.sendMessage("");
                        player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Vendrick commands");
                        player.sendMessage(ChatColor.AQUA + "/ven help - Shows this message");
                        player.sendMessage("");
                        player.sendMessage(ChatColor.AQUA + "/ven items / i - Shows the item menu");
                        player.sendMessage("");
                        player.sendMessage(ChatColor.AQUA + "/ven reload / rl - Reloads the plugin's config");
                        player.sendMessage("");
                        player.sendMessage(ChatColor.AQUA + "/ven merchant [e/d] - Allows you to spawn an Eternal Merchant");
                        player.sendMessage("");
                        player.sendMessage(ChatColor.AQUA + "/ven mremove - Removes all merchants in a 5 block radius");
                        player.sendMessage("");
                        player.sendMessage(ChatColor.AQUA + "/ven clear - Removes any vendrick related entities in a 10 block radius. Can only be used while not in a fight. Does not remove merchants.");

                    } else if (args[0].equalsIgnoreCase("items") || args[0].equalsIgnoreCase("i")){
                        VenItems inv = new VenItems();
                        player.openInventory(inv.getInventory());

                    } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")){
                        player.sendMessage(venPrefix + ChatColor.GREEN + " The config has been reloaded!");
                        plugin.reloadConfig();
                        plugin.initLocations();
                        player.sendMessage(venPrefix + ChatColor.GRAY + " " + plugin.configSpawnLocs.size() + " Custom spawning locations successfully initialised");

                    } else if(args[0].equalsIgnoreCase("merchant")){
                        if (args.length < 2) {
                            player.sendMessage(venPrefix + ChatColor.RED + " /ven merchant [e/d]");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("e")){
                            player.getInventory().addItem(ItemManager.tradeLoc);
                            player.sendMessage(ChatColor.GREEN + "You have been given the Merchant Placer. Right click any block to place down an Eternal Merchant");
                        } else if (args[1].equalsIgnoreCase("d")){
                            player.getInventory().addItem(ItemManager.DtradeLoc);
                            player.sendMessage(ChatColor.GREEN + "You have been given the Merchant Placer. Right click any block to place down a Distorted Merchant");
                        } else if (args[1].equalsIgnoreCase("m")){
                            player.getInventory().addItem(ItemManager.MtradeLoc);
                            player.sendMessage(ChatColor.GREEN + "You have been given the Merchant Placer. Right click any block to place down a Material Merchant");
                        }
                        else {
                            player.sendMessage(venPrefix + ChatColor.RED + " /ven merchant [e/d/m]");
                            return true;
                        }
                    } else if(args[0].equalsIgnoreCase("mremove")){
                        int removeCount = 0;
                        for (Entity entity : player.getNearbyEntities(5,5,5)){
                            if (entity.getScoreboardTags().contains("VenMerchant")){
                                entity.remove();
                                if (!entity.getType().equals(EntityType.ARMOR_STAND)){
                                    removeCount++;
                                }
                            }
                        }
                        if (removeCount == 0){
                            player.sendMessage(ChatColor.RED + "No Merchants were removed. Stand closer to them and try again.");
                        } else if (removeCount > 1){
                            player.sendMessage(ChatColor.GREEN + "Removed " + removeCount + " merchants!");
                        } else {
                            player.sendMessage(ChatColor.GREEN + "Removed " + removeCount + " merchant!");
                        }
                    } else if (args[0].equalsIgnoreCase("clear")){
                        if (!plugin.venSpawned){
                            int enCount = 0;
                            for (Entity e : player.getNearbyEntities(10,10,10)){
                                if (e.hasMetadata("PigBomb")
                                        || e.hasMetadata("Portal")
                                        || e.hasMetadata("Wraith")
                                        || e.hasMetadata("Growth")
                                        || e.hasMetadata("SquidShield")
                                        || e.hasMetadata("Vendrick")
                                        || e.hasMetadata("venPulse")
                                        || e.hasMetadata("venCollect")
                                        || e.hasMetadata("venBall")){
                                    e.getWorld().spawnParticle(Particle.SPELL_WITCH, e.getLocation(), 10);
                                    e.remove();
                                    enCount++;
                                }
                            }
                            player.sendMessage(venPrefix + ChatColor.GREEN + " " + enCount + " Entities removed!");
                        } else {
                            player.sendMessage(venPrefix + ChatColor.RED + " Entities cannot be cleared while the fight is still in progress.");
                        }

                    } else if (args[0].equalsIgnoreCase("add")){
                        if (args.length == 2){
                            int x = player.getLocation().getBlockX();
                            int y = player.getLocation().getBlockY();
                            int z = player.getLocation().getBlockZ();
                            int[] coords = {x,y,z};
                            String key = args[1];
                            plugin.getConfig().set("spawn-locations." + key, coords);
                            plugin.saveConfig();
                            player.sendMessage(venPrefix + ChatColor.GREEN + " Added new location: " + key + " to the config!");
                        }
                    }
                    else {
                        player.sendMessage(venPrefix + ChatColor.RED + " /ven [help] [items] [reload] [merchant] [mremove]");
                    }
                } else {
                    player.sendMessage(venPrefix + ChatColor.RED + " /ven [help] [items] [reload] [merchant] [mremove]");
                }
            } else {
                player.sendMessage(venPrefix + " " + Lang.NOPERMS.toString());
            }

        }

        return true;
    }
}
