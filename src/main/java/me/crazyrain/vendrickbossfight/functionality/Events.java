package me.crazyrain.vendrickbossfight.functionality;

import me.crazyrain.vendrickbossfight.CustomEvents.VendrickFightStopEvent;
import me.crazyrain.vendrickbossfight.CustomEvents.VendrickStartAttackEvent;
import me.crazyrain.vendrickbossfight.VendrickBossFight;
import me.crazyrain.vendrickbossfight.attacks.*;
import me.crazyrain.vendrickbossfight.distortions.flaming.FlamingVendrick;
import me.crazyrain.vendrickbossfight.distortions.tidal.TidalVendrick;
import me.crazyrain.vendrickbossfight.distortions.stormy.Hurricane;
import me.crazyrain.vendrickbossfight.distortions.stormy.StormyVendrick;
import me.crazyrain.vendrickbossfight.npcs.Vendrick;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.List;

public class Events implements Listener {

    VendrickBossFight plugin;

    public Events(VendrickBossFight plugin){
        this.plugin = plugin;
    }

    public boolean bossDead = false;
    public boolean spawnedHoard = false;
    public boolean pigsSpawned = false;
    public boolean enraged = false;
    public boolean lost = false;
    public boolean starDropped = false;

    double percent;

    public boolean attacking = false;


    public static List<UUID> plost = new ArrayList<>();


    @EventHandler
    public void spawnBoss(ItemSpawnEvent e) {
        if (e.getEntity().getItemStack().getItemMeta() != null) {
            if (e.getEntity().getThrower() == null) {
                return;
            }
            if (Objects.requireNonNull(e.getEntity().getItemStack().getItemMeta()).getDisplayName().contains(ChatColor.BOLD + "Eternal Star")) {
                Player player = Bukkit.getPlayer(e.getEntity().getThrower());
                starDropped = true;
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (starDropped){
                            if (plugin.venSpawned){
                                player.sendMessage(Lang.FORCE.toString());
                                player.sendMessage(Lang.ENOUGH.toString());
                                return;
                            }

                            if (plugin.getConfig().getBoolean("disable-boss")){
                                return;
                            }

                            Location spawnLoc = e.getEntity().getLocation();

                            if (plugin.getConfig().getBoolean("use-locations")){
                                int foundCount = 0;
                                int x = spawnLoc.getBlockX();
                                int y = spawnLoc.getBlockY();
                                int z = spawnLoc.getBlockZ();
                                for (Location loc : plugin.configSpawnLocs){
                                    foundCount = 0;
                                    if (x == loc.getBlockX()){
                                        foundCount++;
                                    }
                                    if (y == loc.getBlockY()){
                                        foundCount++;
                                    }
                                    if (z == loc.getBlockZ()){
                                        foundCount++;
                                    }
                                }
                                if (foundCount != 3) {
                                    return;
                                }
                            }

                            player.sendMessage(Lang.AWAKE.toString());
                            player.sendMessage(Lang.CIRCLE.toString());
                            player.sendMessage(Lang.STAR.toString());
                            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 0.2f);


                            if (e.getEntity().getItemStack().getItemMeta().getDisplayName().contains("(FLAMING)")){
                                makeCircle(e.getEntity().getLocation(), 6f, Color.ORANGE);
                            } else if (e.getEntity().getItemStack().getItemMeta().getDisplayName().contains("(TIDAL)")) {
                                makeCircle(e.getEntity().getLocation(), 6f, Color.BLUE);
                            } else if (e.getEntity().getItemStack().getItemMeta().getDisplayName().contains("(STORMY)")){
                                makeCircle(e.getEntity().getLocation(), 6f, Color.YELLOW);
                            } else {
                                makeCircle(e.getEntity().getLocation(), 6f, Color.RED);
                            }

                            new BukkitRunnable(){
                                int playerCount = 0;
                                @Override
                                public void run() {
                                    for (Entity e : spawnLoc.getWorld().getNearbyEntities(spawnLoc, 5.5,6,5.5)){
                                        if (e instanceof Player){
                                            playerCount++;
                                            plugin.fighting.add(e.getUniqueId());
                                        }
                                    }

                                    if (playerCount > 0){
                                        bossDead = false;
                                        lost = false;
                                        for (UUID player : plugin.fighting){
                                            assert Bukkit.getPlayer(player) != null;
                                            Bukkit.getPlayer(player).sendTitle(ChatColor.DARK_RED + "Vendrick", ChatColor.RED + "The eternal guardian", 10, 70, 20);
                                            Bukkit.getPlayer(player).sendMessage(Lang.CURSE.toString());
                                            Bukkit.getPlayer(player).playSound(Bukkit.getPlayer(player).getLocation(), Sound.ENTITY_WITHER_SPAWN, 10, 0.8f);
                                            Bukkit.getPlayer(player).playSound(Bukkit.getPlayer(player).getLocation(), Sound.ENTITY_WITHER_SPAWN, 10, 0.8f);

                                            Bar bar = new Bar(Bukkit.getPlayer(player));
                                            bar.add();
                                            plugin.bars.add(bar);

                                        }

                                        if (e.getEntity().getItemStack().getItemMeta().getDisplayName().contains("(FLAMING)")){
                                            plugin.vendrick = new FlamingVendrick(plugin.fighting, spawnLoc, plugin);
                                        } else if (e.getEntity().getItemStack().getItemMeta().getDisplayName().contains("(TIDAL)")){
                                            plugin.vendrick = new TidalVendrick(plugin.fighting, spawnLoc, plugin);
                                            plugin.squids = 4;
                                        } else if (e.getEntity().getItemStack().getItemMeta().getDisplayName().contains("(STORMY)")){
                                            plugin.vendrick = new StormyVendrick(plugin.fighting, spawnLoc, plugin);
                                        }
                                        else {
                                            plugin.vendrick = new Vendrick(plugin.fighting, spawnLoc, plugin);
                                        }

                                        if (e.getEntity().getItemStack().getItemMeta().getDisplayName().contains("(STORMY)")){
                                            Hurricane hurricane = new Hurricane(plugin.vendrick);
                                            plugin.hurricane = hurricane;
                                        }

                                        plugin.vendrick.spawnBoss();

                                    } else {
                                        player.sendMessage(Lang.NOSTART.toString());
                                        plugin.venSpawned = false;
                                        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 0.3f, 0.1f);
                                    }
                                }
                            }.runTaskLater(plugin, 20 * 5);

                            e.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, e.getLocation(), 1);
                            e.getEntity().remove();

                            spawnedHoard = false;
                            pigsSpawned = false;
                            enraged = false;

                            plugin.venSpawned = true;
                        }
                    }
                }.runTaskLater(plugin, 20 * 5);
            }
        }
    }

    public void makeCircle(Location loc, Float radius, Color color){
        new BukkitRunnable(){
            Integer t = 0;
            @Override
            public void run() {
                if (t >= 100){
                    cancel();
                }


                for (int d = 0; d <= 90; d += 1) {
                    Location particleLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
                    particleLoc.setX(loc.getX() + Math.cos(d) * radius);
                    particleLoc.setZ(loc.getZ() + Math.sin(d) * radius);
                    particleLoc.add(0, (d / 90.0), 0);
                    loc.getWorld().spawnParticle(Particle.REDSTONE,  particleLoc, 1, new Particle.DustOptions(color, 1));
                }
                t += 3;
            }
        }.runTaskTimer(plugin, 0, 3);
    }

    @EventHandler
    public void stopSpawning(EntityPickupItemEvent e){
        if (e.getEntity() instanceof Player){
            if (e.getItem().getItemStack().hasItemMeta() && Objects.requireNonNull(e.getItem().getItemStack().getItemMeta()).hasDisplayName()){
                if (e.getItem().getItemStack().getItemMeta().getDisplayName().contains("Eternal Star")){
                    starDropped = false;
                }
            }

        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void showHealth(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Vindicator){
            if (e.getEntity().hasMetadata("Vendrick")){
                if (!(e.getDamager() instanceof Player) && !(e.getDamager() instanceof Arrow)){
                    return;
                }

                if (e.getDamager() instanceof Arrow){
                    if (((Arrow) e.getDamager()).getShooter() instanceof Player){
                        Player player = (Player) ((Arrow) e.getDamager()).getShooter();
                        UUID pId = player.getUniqueId();
                        if (!plugin.fighting.contains(pId) && ! e.getDamager().isOp()){
                            player.sendMessage(Lang.PURE.toString());
                            plugin.vendrick.getVendrick().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, plugin.vendrick.getVendrick().getLocation(), 3);
                            e.setCancelled(true);
                            return;
                        }
                    }
                }

                if (e.getDamager() instanceof Player){
                    if (!plugin.fighting.contains(e.getDamager().getUniqueId()) && ! e.getDamager().isOp()){
                        Player player = (Player) e.getDamager();
                        player.sendMessage(Lang.PURE.toString());
                        e.setCancelled(true);
                        return;
                    }
                }


                if (e.getEntity().getScoreboardTags().contains("venTide")){
                    if (!(plugin.squids == 4)){
                        double damage = e.getDamage() * (plugin.squids / 4.0);
                        e.setDamage(damage);
                    }
                }

                double rawPercent = ((Vindicator) e.getEntity()).getHealth() / plugin.getConfig().getInt("vendrick-health");
                percent = Math.round(rawPercent * 100.0) / 100.0;

                for (Bar bar : plugin.bars){
                    bar.fill(percent);
                }

                attacking = false;

                if (percent <= 0.75){
                    if (!(spawnedHoard)){
                        attacking = true;
                        spawnedHoard = true;
                        ((Vindicator) e.getEntity()).setHealth(plugin.getConfig().getInt("vendrick-health") * 0.75);
                        for (Bar bar : plugin.bars){
                            bar.fill(0.75);
                        }

                        PortalWraiths wraiths = new PortalWraiths(plugin);
                        wraiths.init(plugin.vendrick, plugin.fighting, true);
                        plugin.vendrick.startAttack(1);
                        for (UUID id : plugin.fighting){
                            Bukkit.getPlayer(id).sendMessage(Lang.PORTAL.toString());
                            AttackCharge charge = new AttackCharge(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Eternal Wraiths", Bukkit.getPlayer(id));
                        }
                        plugin.getServer().getPluginManager().callEvent(new VendrickStartAttackEvent(1));
                    }
                }

                if (percent <= 0.50){
                    if (!(pigsSpawned)){
                        attacking = true;
                        pigsSpawned = true;
                        ((Vindicator) e.getEntity()).setHealth(plugin.getConfig().getInt("vendrick-health") * 0.50);
                        for (Bar bar : plugin.bars){
                            bar.fill(0.50);
                        }
                        PigBombs pigBombs = new PigBombs(plugin);
                        pigBombs.init(plugin.vendrick, plugin.fighting);
                        plugin.vendrick.startAttack(2);
                        for (UUID id : plugin.fighting){
                            AttackCharge charge = new AttackCharge(ChatColor.GOLD + "" + ChatColor.BOLD + "Pig Bombs", Bukkit.getPlayer(id));
                            Bukkit.getPlayer(id).sendMessage(Lang.BOMBS.toString());
                        }
                        plugin.getServer().getPluginManager().callEvent(new VendrickStartAttackEvent(2));
                    }
                }

                if (percent <= 0.25){
                    if (!(enraged)){
                        attacking = true;
                        enraged = true;
                        ((Vindicator) e.getEntity()).setHealth(plugin.getConfig().getInt("vendrick-health") * 0.25);
                        for (Bar bar : plugin.bars){
                            bar.fill(0.25);
                        }
                        Enrage enrage = new Enrage(plugin);
                        enrage.init(plugin.vendrick);
                        plugin.vendrick.startAttack(0);

                        for (UUID id : plugin.fighting){
                            AttackCharge charge = new AttackCharge(ChatColor.BLACK + "" + ChatColor.BOLD + "???", Bukkit.getPlayer(id));
                        }

                        new BukkitRunnable(){

                            @Override
                            public void run() {
                                   Shatter shatter = new Shatter(plugin);
                                    new BukkitRunnable(){

                                        @Override
                                        public void run() {
                                            if (!bossDead){
                                               for (UUID id : plugin.fighting){
                                                   shatter.startShatter(Bukkit.getPlayer(id));
                                               }
                                            } else {
                                                cancel();
                                            }
                                        }
                                    }.runTaskTimer(plugin, 0, 20 * 2);
                            }
                        }.runTaskLater(plugin, 20 * 11);
                    }
                }

            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST) // Runs event after showHealth to ensure health is set for interval attacks before this one is rolled
    public void rollForAttack(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Vindicator){
            if (e.getEntity().hasMetadata("Vendrick")){
                if (percent < 0.75 && percent > 0.15){
                    int rand = (int) (Math.random() * 8);
                    if (rand == 7){
                        int attack = (int) (Math.random() * 3);
                        switch (attack) {
                            case 0:
                                if (!(attacking) && percent == 0.75) {
                                    PortalWraiths wraiths = new PortalWraiths(plugin);
                                    wraiths.init(plugin.vendrick, plugin.fighting, true);
                                    plugin.vendrick.startAttack(1);
                                    attacking = true;
                                    for (UUID id : plugin.fighting) {
                                        AttackCharge charge = new AttackCharge(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Eternal Wraiths", Bukkit.getPlayer(id));
                                        Bukkit.getPlayer(id).sendMessage(Lang.PORTAL.toString());
                                    }
                                    plugin.getServer().getPluginManager().callEvent(new VendrickStartAttackEvent(1));
                                }
                                break;
                            case 1:
                                if (!(attacking) && percent == 0.50) {
                                    PigBombs pigBombs = new PigBombs(plugin);
                                    pigBombs.init(plugin.vendrick, plugin.fighting);
                                    plugin.vendrick.startAttack(2);
                                    attacking = true;
                                    for (UUID id : plugin.fighting){
                                        AttackCharge charge = new AttackCharge(ChatColor.GOLD + "" + ChatColor.BOLD + "Pig Bombs", Bukkit.getPlayer(id));
                                        Bukkit.getPlayer(id).sendMessage(Lang.BOMBS.toString());
                                    }
                                    plugin.getServer().getPluginManager().callEvent(new VendrickStartAttackEvent(2));
                                }
                                break;
                            case 2:
                                if (plugin.getConfig().getBoolean("DoGrowths") && percent != 0.75 && percent != 0.50) {
                                    ZombieHoard hoard = new ZombieHoard(plugin);
                                    hoard.init(plugin.vendrick);
                                    attacking = true;
                                    for (UUID id : plugin.fighting){
                                        AttackCharge charge = new AttackCharge(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "The Horde", Bukkit.getPlayer(id));
                                        Bukkit.getPlayer(id).sendMessage(Lang.GROWTHS.toString());
                                    }
                                    plugin.vendrick.startAttack(3);
                                    plugin.getServer().getPluginManager().callEvent(new VendrickStartAttackEvent(3));
                                }
                            }
                         }
                    }
                 }
            }
    }

    public Integer text = 0;
    public boolean eventCalled;
    @EventHandler
    public void onBossDeath (EntityDeathEvent e){
            if (e.getEntity() instanceof Vindicator){
                if (e.getEntity().hasMetadata("Vendrick")){
                    if (e.getEntity().getKiller() != null){

                        for (Entity en : e.getEntity().getNearbyEntities(50,50,50)){
                            if (en.hasMetadata("Wraith") || en.hasMetadata("PigBomb") || en.hasMetadata("SquidShield") || en.hasMetadata("Growth")){
                                en.remove();
                            }
                        }


                        e.getDrops().clear();

                        eventCalled = false;

                        for (UUID id : plugin.fighting){
                            if (!plugin.getConfig().getBoolean("disable-effects")){
                                Bukkit.getPlayer(id).addPotionEffect(PotionEffectType.BLINDNESS.createEffect(10000, 2));
                                Bukkit.getPlayer(id).addPotionEffect(PotionEffectType.SLOW.createEffect(10000, 5));
                            }
                            bossDead = true;

                            Bukkit.getPlayer(id).sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "A loud, echoing voice fills the land");

                            new BukkitRunnable(){
                                int text = 0;

                                @Override
                                public void run() {
                                    text += 1;
                                    switch (text){
                                        case 2:
                                            Bukkit.getPlayer(id).sendMessage(Lang.END1.toString());
                                            Bukkit.getPlayer(id).playSound(Bukkit.getPlayer(id).getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 20f, 0.6f);
                                            break;
                                        case 3:
                                            Bukkit.getPlayer(id).sendMessage(Lang.END2.toString());
                                            Bukkit.getPlayer(id).playSound(Bukkit.getPlayer(id).getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 20f, 0.6f);
                                            break;
                                        case 4:
                                            Bukkit.getPlayer(id).sendMessage(Lang.END3.toString());
                                            Bukkit.getPlayer(id).playSound(Bukkit.getPlayer(id).getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 20f, 0.6f);
                                            break;
                                        case 5:
                                            Bukkit.getPlayer(id).sendMessage(Lang.END4.toString());
                                        case 6:
                                            if (!eventCalled){
                                                plugin.getServer().getPluginManager().callEvent(new VendrickFightStopEvent(plugin.fighting, plugin.fighting, plost
                                                        ,plugin.vendrick.getDistortion(), plugin.vendrick.getDifficulty()));
                                                eventCalled = true;
                                            }
                                            victory(Bukkit.getPlayer(id));
                                            calcLoot(Bukkit.getPlayer(id));
                                            lost = false;
                                            plugin.venSpawned = false;
                                            plugin.fighting.clear();
                                            cancel();
                                    }
                                }
                            }.runTaskTimer(plugin, 0, 20 * 4);
                        }
                    }
                }
            }
    }

    public void victory(Player player) {
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.removePotionEffect(PotionEffectType.SLOW);
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 0.7f);
        player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "VICTORY", ChatColor.BLUE + "The eternal guardian has fallen", 10, 160, 20);
        plugin.pInv.clear();

        for (Entity e : player.getNearbyEntities(100, 100, 100)) {
            if (e.hasMetadata("Wraith")) {
                e.remove();
            }
        }

        for (Bar bar : plugin.bars) {
            bar.remove();
        }
        plugin.bars.clear();

        if (plugin.getConfig().get("WinMessage") == null || Objects.requireNonNull(plugin.getConfig().getString("WinMessage")).equalsIgnoreCase("")) {
            if (plugin.getConfig().getBoolean("BroadcastMessage") || plugin.getConfig().get("BroadcastMessage") == null) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + ChatColor.ITALIC + player.getDisplayName() + ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + " has defeated Vendrick!");
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + ChatColor.ITALIC + player.getDisplayName() + ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + " has defeated Vendrick!");
            }
        } else {
            String message = plugin.getConfig().getString("WinMessage");
            assert message != null;
            message = message.replace("[player]", player.getDisplayName());
            if (plugin.getConfig().getBoolean("BroadcastMessage") || plugin.getConfig().get("BroadcastMessage") == null) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                if (plugin.getConfig().getBoolean("do-drops")){
                    player.sendMessage(ChatColor.GRAY + "As the guardian falls, some loot was left behind");
                }
            }
        }.runTaskLater(plugin, 20);
    }


    public void lose(){
        bossDead = true;
        lost = true;

        Location deathLoc = plugin.vendrick.getVendrick().getLocation();
        for (Entity en : deathLoc.getWorld().getNearbyEntities(deathLoc, 50,50,50)){
            if (en.hasMetadata("Wraith") || en.hasMetadata("Portal")){
                en.remove();
            }
        }

        for (UUID player : plost){
            try{
                Bukkit.getPlayer(player).sendMessage(Lang.LOSE.toString());
            } catch (NullPointerException ignored){}
        }

        new BukkitRunnable(){

            @Override
            public void run() {
                plost.clear();
            }
        }.runTaskLater(plugin, 20 * 5);


        PortalWraiths wraiths = new PortalWraiths(plugin);
        wraiths.init(plugin.vendrick, plugin.fighting, false);
        wraiths.stopSpawning();
        wraiths.stopParticles();
        plugin.vendrick.getVendrick().remove();

        plugin.venSpawned = false;
        plugin.pInv.clear();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
       if (plugin.fighting.contains(e.getPlayer().getUniqueId())){
           e.getPlayer().sendMessage(Lang.PLAYERDEATH.toString().replace("{player}", e.getPlayer().getDisplayName()));
           plugin.fighting.remove(e.getPlayer().getUniqueId());

           for (Bar bar : plugin.bars){
               if (bar.getPlayer().equals(e.getPlayer().getUniqueId())){
                   bar.remove();
               }
           }

           plost.add(e.getPlayer().getUniqueId());

           if (plugin.getConfig().getBoolean("keep-inventory")){
               e.getPlayer().getInventory().setContents(plugin.pInv.get(e.getPlayer().getUniqueId()));
               plugin.pInv.remove(e.getPlayer().getUniqueId());
           }

           if (plugin.fighting.size() == 0){
               lose();
           }
       }

    }

    @EventHandler
    public void onPlayerLeaveMidFight(PlayerQuitEvent e){
        if (plugin.fighting.contains(e.getPlayer().getUniqueId())){
            plost.add(e.getPlayer().getUniqueId());
            plugin.fighting.remove(e.getPlayer().getUniqueId());

            if (plugin.fighting.size() == 0){
                lose();
            }
        }
    }

    @EventHandler
    public void stopPlayerDrops(PlayerDeathEvent e){
        if (plugin.getConfig().getBoolean("keep-inventory")) {
            if (plugin.fighting.contains(e.getEntity().getUniqueId())) {
                plugin.pInv.put(e.getEntity().getUniqueId(), e.getEntity().getInventory().getContents());
                e.getDrops().clear();
            }
        }
    }

    @EventHandler
    public void stopFallDmg(EntityDamageEvent e){
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            if (e.getEntity().hasMetadata("Vendrick")){
                e.setCancelled(true);
            }
        }
    }

    public void calcLoot(Player player){
        if (!plugin.getConfig().getBoolean("do-drops")){
            return;
        }

        int emAmount;
        int rareChance;
        int specialChance;

        if (plugin.vendrick.wasDistorted()){
            emAmount = (int) (Math.random() * (100 - 5) + 5);
            rareChance = (int) (Math.random() * (plugin.getConfig().getInt("rare-chance") + 1) * 1.5);
            specialChance = (int) (Math.random() * (plugin.getConfig().getInt("special-chance") + 1) * 1.5);
        } else {
            emAmount = (int) (Math.random() * (32 - 5) + 5);
            rareChance = (int) (Math.random() * (plugin.getConfig().getInt("rare-chance") + 1));
            specialChance = (int) (Math.random() * (plugin.getConfig().getInt("special-chance") + 1));
        }

        int insaneChance = (int) (Math.random() * (30 + 1));

        giveLoot(player, 1, emAmount);

        if (rareChance >= (plugin.getConfig().getInt("rare-chance"))){
            int whatRare;
            if (plugin.vendrick.wasDistorted()){
                whatRare = (int) (Math.random() * 4);
            } else {
                whatRare = (int) (Math.random() * 3);
            }
            switch (whatRare){
                case 0:
                    giveLoot(player, 2, 2);
                    break;
                case 1:
                    //give player eternal frag
                    giveLoot(player, 3, 1);
                    break;
                case 2:
                    giveLoot(player, 4, 1);
                    break;
                    //give player essence of eternity
                case 3:
                    int amount =  (int) (Math.random() * 2 + 1);
                    giveLoot(player, 10, amount);
            }
        }
        if (specialChance >= (plugin.getConfig().getInt("special-chance"))){
            int whatEpic = (int) (Math.random() * 4);
            switch (whatEpic){
                case 0:
                    giveLoot(player, 5, 1);
                    break;
                case 1:
                    giveLoot(player,6,1);
                    break;
                case 2:
                    giveLoot(player,7, 2);
                    break;
                case 3:
                    giveLoot(player, 8, 1);
                    break;
            }
        }
        if (plugin.vendrick.wasDistorted()){
            if (insaneChance >= 28){
                if (plugin.vendrick.getDifficulty() > 3) {
                    giveLoot(player, 9, 1);
                } else {
                    int whatInsane = (int) (Math.random() * 2);
                    switch (whatInsane){
                        case 1:
                            giveLoot(player, 9, 1);
                            break;
                        case 2:
                            giveLoot(player, 11, 1);
                    }
                }
            }
        }
    }

    public void giveLoot(Player player, Integer item, Integer amount){
        new BukkitRunnable(){

            @Override
            public void run() {
                switch (item){
                    case 1:
                        player.sendMessage(ChatColor.DARK_GRAY + "Received: Emerald x" + amount + " Diamond x" + amount / 3);
                        if (player.getInventory().firstEmpty() == -1){
                            player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.EMERALD, amount));
                            player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.DIAMOND, amount / 3));
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(new ItemStack(Material.EMERALD, amount));
                            player.getInventory().addItem(new ItemStack(Material.DIAMOND, amount / 3));
                        }
                        break;
                    case 2:
                        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "NICE! " + ChatColor.GOLD + "You found " + ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Emerald Block " + ChatColor.DARK_GRAY + "x2");
                        if (player.getInventory().firstEmpty() == -1){;
                            player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.EMERALD_BLOCK, amount));
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(new ItemStack(Material.EMERALD_BLOCK, amount));
                        }
                        break;
                    case 3:
                        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "NICE! " + ChatColor.GOLD + "You found an " + ItemManager.eternalFragment.getItemMeta().getDisplayName());
                        if (player.getInventory().firstEmpty() == -1){;
                            player.getWorld().dropItem(player.getLocation(), ItemManager.eternalFragment);
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(ItemManager.eternalFragment);
                        }
                        break;
                    case 4:
                        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "NICE! " + ChatColor.GOLD + "You found an " + ItemManager.essenceOfEternity.getItemMeta().getDisplayName());
                        if (player.getInventory().firstEmpty() == -1){;
                            player.getWorld().dropItem(player.getLocation(), ItemManager.essenceOfEternity);
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(ItemManager.essenceOfEternity);
                        }
                        break;
                    case 5:
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "WOW! " + ChatColor.LIGHT_PURPLE + "You found the " + ItemManager.vendrickHatchet.getItemMeta().getDisplayName());
                        if (player.getInventory().firstEmpty() == -1){;
                            player.getWorld().dropItem(player.getLocation(), ItemManager.vendrickHatchet);
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(ItemManager.vendrickHatchet);
                        }
                        break;
                    case 6:
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "WOW! " + ChatColor.LIGHT_PURPLE + "You found a " + ItemManager.shatterSpine.getItemMeta().getDisplayName());
                        if (player.getInventory().firstEmpty() == -1){;
                            player.getWorld().dropItem(player.getLocation(), ItemManager.shatterSpine);
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(ItemManager.shatterSpine);
                        }
                        break;
                    case 7:
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "WOW! " + ChatColor.LIGHT_PURPLE + "You found a " + ChatColor.GOLD + "" + ChatColor.BOLD + "Totem of Undying" + ChatColor.DARK_GRAY + " x2");
                        if (player.getInventory().firstEmpty() == -1){;
                            player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.TOTEM_OF_UNDYING, amount));
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, amount));
                        }
                        break;
                    case 8:
                        player.getInventory().addItem(ItemManager.theCatalyst);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "WOW! " + ChatColor.LIGHT_PURPLE + "You found " + ItemManager.theCatalyst.getItemMeta().getDisplayName());
                        if (player.getInventory().firstEmpty() == -1){;
                            player.getWorld().dropItem(player.getLocation(), ItemManager.theCatalyst);
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(ItemManager.theCatalyst);
                        }
                        break;
                    case 9:
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "INCREDIBLE! " + ChatColor.GOLD + "You found a " + ItemManager.oven.getItemMeta().getDisplayName());
                        if (player.getInventory().firstEmpty() == -1){;
                            player.getWorld().dropItem(player.getLocation(), ItemManager.oven);
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(ItemManager.oven);
                        }
                        break;
                    case 10:
                        ItemStack item = ItemManager.infinium.clone();
                        item.setAmount(amount);
                        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "NICE! " + ChatColor.GOLD + "You found " + ItemManager.infinium.getItemMeta().getDisplayName() + ChatColor.DARK_GRAY + " x" + amount);
                        if (player.getInventory().firstEmpty() == -1){;
                            player.getWorld().dropItem(player.getLocation(), item);
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(item);
                        }
                        break;
                    case 11:
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "INCREDIBLE! " + ChatColor.GOLD + "You found a " + ItemManager.fusionChamber.getItemMeta().getDisplayName());
                        if (player.getInventory().firstEmpty() == -1){
                            player.getWorld().dropItem(player.getLocation(), ItemManager.fusionChamber);
                            player.sendMessage(ChatColor.RED + "An item didn't fit in your inventory so it was dropped on the ground!");
                        } else {
                            player.getInventory().addItem(ItemManager.fusionChamber);
                        }
                        break;

                }
            }
        }.runTaskLater(plugin, 20);
    }

    //used for custom crafts
    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent e){
        if (plugin.getConfig().getBoolean("can-craft")){
            if(e.getInventory().getMatrix().length > 9){
                return;
            }

            checkCraft(ItemManager.trueEternalHatchet, e.getInventory(), new HashMap<Integer, ItemStack>() {{
                put(4, ItemManager.vendrickHatchet);
                put(1, ItemManager.eternalFragment);
                put(3, ItemManager.eternalFragment);
                put(5, ItemManager.eternalFragment);
                put(7, ItemManager.eternalFragment);
            }});
            checkCraft(ItemManager.shatterStick, e.getInventory(), new HashMap<Integer, ItemStack>() {{
                put(4, ItemManager.shatterSpine);
                put(1, ItemManager.essenceOfEternity);
                put(3, ItemManager.essenceOfEternity);
                put(5, ItemManager.essenceOfEternity);
                put(7, ItemManager.essenceOfEternity);
            }});
            checkCraft(ItemManager.nutrimentOfTheInfinite, e.getInventory(), new HashMap<Integer, ItemStack>() {{
                put(4, new ItemStack(Material.GOLDEN_APPLE));
                put(1, ItemManager.essenceOfEternity);
                put(5, ItemManager.essenceOfEternity);
                put(3, ItemManager.eternalFragment);
                put(7, ItemManager.eternalFragment);
            }});
            checkCraft(ItemManager.nutrimentU, e.getInventory(), new HashMap<Integer, ItemStack>() {{
                for (int i = 0; i < 6; i++){
                    if (i == 4 || i == 1){
                        continue;
                    }
                    put(i, ItemManager.eternalFragment);
                }
                put(6, ItemManager.infinium);
                put(8, ItemManager.infinium);
                put(7, ItemManager.eternalFragment);
                put(1, ItemManager.nutrimentOfTheInfinite);
                put(4, ItemManager.oven);
            }});
            checkCraft(ItemManager.flamingStar, e.getInventory(), new HashMap<Integer, ItemStack>(){{
                put(3, ItemManager.flameCore);
                put(5, ItemManager.flameCore);
                put(7, ItemManager.flameCore);
                put(4, ItemManager.eternalStar);
                put(1, ItemManager.theCatalyst);
            }});
            checkCraft(ItemManager.tidalStar, e.getInventory(), new HashMap<Integer, ItemStack>(){{
                put(3, ItemManager.waveCore);
                put(5, ItemManager.waveCore);
                put(7, ItemManager.waveCore);
                put(4, ItemManager.eternalStar);
                put(1, ItemManager.theCatalyst);
            }});
            checkCraft(ItemManager.stormStar, e.getInventory(), new HashMap<Integer, ItemStack>(){{
                put(3, ItemManager.voltaicCore);
                put(5, ItemManager.voltaicCore);
                put(7, ItemManager.voltaicCore);
                put(4, ItemManager.eternalStar);
                put(1, ItemManager.theCatalyst);
            }});
            checkCraft(ItemManager.energyRifle, e.getInventory(), new HashMap<Integer, ItemStack>(){{
                put(0, ItemManager.infinium);
                put(1, ItemManager.infinium);
                put(2, ItemManager.infinium);
                put(5, ItemManager.fusionChamber);
                put(8, ItemManager.voltaicCore);
            }});
            checkCraft(ItemManager.theCatalyst, e.getInventory(), new HashMap<Integer, ItemStack>(){{
                put(0, ItemManager.essenceOfEternity);
                put(3, ItemManager.essenceOfEternity);
                put(6, ItemManager.essenceOfEternity);
                put(2, ItemManager.eternalFragment);
                put(5, ItemManager.eternalFragment);
                put(8, ItemManager.eternalFragment);
                put(1, new ItemStack(Material.END_CRYSTAL));
                put(7, new ItemStack(Material.END_CRYSTAL));
            }});
        }
    }
    public void checkCraft(ItemStack result, CraftingInventory inv, HashMap<Integer, ItemStack> ingredients){
        ItemStack[] matrix = inv.getMatrix();
        for(int i = 0; i < 9; i++){
            if(ingredients.containsKey(i)){
                if(matrix[i] == null || !matrix[i].equals(ingredients.get(i))){
                    return;
                }
            } else {
                if(matrix[i] != null){
                    return;
                }
            }
        }
        inv.setResult(result);
    }


    @EventHandler
    public void stopBlockPlace(BlockPlaceEvent e){
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.eternalFragment.getItemMeta().getDisplayName())){
            e.setCancelled(true);
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.shatterSpine.getItemMeta().getDisplayName())){
            e.setCancelled(true);
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.lusciousApple.getItemMeta().getDisplayName())){
            e.setCancelled(true);
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.theCatalyst.getItemMeta().getDisplayName())){
            e.setCancelled(true);
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ItemManager.oven.getItemMeta().getDisplayName())){
            e.setCancelled(true);
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.fusionChamber.getItemMeta().getDisplayName())){
            e.setCancelled(true);
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.catalystPartA.getItemMeta().getDisplayName())){
            e.setCancelled(true);
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.catalystPartB.getItemMeta().getDisplayName())){
            e.setCancelled(true);
        }


    }

    @EventHandler
    public void coreDrops(EntityDeathEvent e){
        if (e.getEntity().getKiller() == null){
            return;
        }

        int dropChance = (int) (Math.random() * 20);

        if (dropChance > 18){
            if (e.getEntity().getType() == EntityType.valueOf(plugin.getConfig().getString("flame-core-mob"))){
                e.getDrops().add(ItemManager.flameCore);
            }
            if (e.getEntity().getType() == EntityType.valueOf(plugin.getConfig().getString("wave-core-mob"))){
                e.getDrops().add(ItemManager.waveCore);
            }
            if (e.getEntity().getType() == EntityType.valueOf(plugin.getConfig().getString("voltaic-core-mob"))){
                e.getDrops().add(ItemManager.voltaicCore);
            }
        }
    }

    @EventHandler
    public void stopStarPlace(PlayerInteractEvent e){
        if (!e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()){
            return;
        }

        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.volatileStar.getItemMeta().getDisplayName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void stopWavePickup(EntityPickupItemEvent e){
        if (plugin.venSpawned){
            if (e.getItem().getItemStack().getType().equals(Material.BLUE_STAINED_GLASS)){
                e.getItem().remove();
                e.setCancelled(true);
            }
        }
    }



}
