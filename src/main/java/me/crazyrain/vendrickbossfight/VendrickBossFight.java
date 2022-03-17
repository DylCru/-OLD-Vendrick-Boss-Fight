package me.crazyrain.vendrickbossfight;

import me.crazyrain.vendrickbossfight.Commands.Commands;
import me.crazyrain.vendrickbossfight.Commands.FightCommands;
import me.crazyrain.vendrickbossfight.Commands.TimerTest;
import me.crazyrain.vendrickbossfight.attacks.*;
import me.crazyrain.vendrickbossfight.distortions.flaming.FlameEvents;
import me.crazyrain.vendrickbossfight.distortions.stormy.Hurricane;
import me.crazyrain.vendrickbossfight.distortions.stormy.StormyEvents;
import me.crazyrain.vendrickbossfight.distortions.tidal.TideEvents;
import me.crazyrain.vendrickbossfight.functionality.*;
import me.crazyrain.vendrickbossfight.functionality.GiveItem;
import me.crazyrain.vendrickbossfight.npcs.Vendrick;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class VendrickBossFight extends JavaPlugin {

    public boolean venSpawned = false;
    public List<UUID> fighting = new ArrayList<>();
    public List<Bar> bars = new ArrayList<>();

    public HashMap<UUID, ItemStack[]> pInv = new HashMap<>();

    public static YamlConfiguration LANG;
    public static File LANG_FILE;

    public int squids = 4;

    public Vendrick vendrick;
    public Hurricane hurricane;

    public List<Location> configSpawnLocs = new ArrayList<>();

    Logger log;

    public static VendrickBossFight plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        log = VendrickBossFight.getPlugin(VendrickBossFight.class).getLogger();
        log.log(Level.INFO, "Plugin is online. Vendrick awaits.");

        LANG = loadLang();
        ItemManager.Init();
        initLocations();

        getServer().getPluginManager().registerEvents(new Events(this),this);
        getServer().getPluginManager().registerEvents(new ZombieHoard(this), this);
        getServer().getPluginManager().registerEvents(new PigBombs(this), this);
        getServer().getPluginManager().registerEvents(new PlayerShatter(this), this);
        getServer().getPluginManager().registerEvents(new GiveItem(), this);
        getServer().getPluginManager().registerEvents(new NutrimentFunc(), this);
        getServer().getPluginManager().registerEvents(new PortalWraiths(this), this);
        getServer().getPluginManager().registerEvents(new MerchantFunc(this), this);
        getServer().getPluginManager().registerEvents(new DMerchantFunc(this), this);
        getServer().getPluginManager().registerEvents(new MaterialMerchFunc(this), this);
        getServer().getPluginManager().registerEvents(new FlameEvents(this),this);
        getServer().getPluginManager().registerEvents(new TideEvents(this), this);
        getServer().getPluginManager().registerEvents(new StormyEvents(this), this );
        getServer().getPluginManager().registerEvents(new EnergyRifle(this), this);
        getServer().getPluginManager().registerEvents(new StatTrackingEvents(), this);
        getCommand("ven").setExecutor(new Commands(this));
        getCommand("venfight").setExecutor(new FightCommands(this));
        getCommand("ventimer").setExecutor(new TimerTest());

    }

    @Override
    public void onDisable(){
        for (Bar bar : bars){
            bar.remove();
        }
        if (venSpawned){
            vendrick.getVendrick().remove();
            try {
                hurricane.removeBar();
            } catch (Exception ignored){
            }
        }
    }

    public void initLocations(){
        configSpawnLocs.clear();
        log.log(Level.INFO, "Setting up custom spawning locations...");
        for (String key : getConfig().getConfigurationSection("spawn-locations").getKeys(false)){
            List<Integer> coords = getConfig().getIntegerList("spawn-locations." + key);
            try {
                Location location = new Location(getServer().getWorld("world"), coords.get(0), coords.get(1), coords.get(2));
                configSpawnLocs.add(location);
            } catch (Exception e){
                log.log(Level.WARNING, "Couldn't initialise custom location: " + key + ". Please ensure the coordinates you entered are integers.");
            }
        }

        log.log(Level.INFO, "Done! You have " + configSpawnLocs.size() + " Custom location(s) for Vendrick to spawn at.");
    }

    public YamlConfiguration loadLang() {
        File lang = new File(getDataFolder(), "lang.yml");
        if (!lang.exists()) {
            try {
                getDataFolder().mkdir();
                lang.createNewFile();
                InputStream defConfigStream = this.getResource("lang.yml");
                if (defConfigStream != null) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(lang);
                    defConfig.save(lang);
                    Lang.setFile(defConfig);
                    return defConfig;
                }
            } catch(IOException e) {
                e.printStackTrace(); // So they notice
                log.severe("[Vendrick] Couldn't create language file.");
                log.severe("[Vendrick] This is a fatal error. Now disabling");
                this.setEnabled(false); // Without it loaded, we can't send them messages
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        for(Lang item:Lang.values()) {
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }
        Lang.setFile(conf);
        VendrickBossFight.LANG = conf;
        VendrickBossFight.LANG_FILE = lang;
        try {
            conf.save(getLangFile());
        } catch(IOException e) {
            log.log(Level.WARNING, "Vendrick: Failed to save lang.yml.");
            log.log(Level.WARNING, "Vendrick: Report this stack trace to CrazyRain.");
            e.printStackTrace();
        }
        return conf;
    }

    /**
     * Gets the lang.yml config.
     * @return The lang.yml config.
     */
    public YamlConfiguration getLang() {
        return LANG;
    }

    /**
     * Get the lang.yml file.
     * @return The lang.yml file.
     */
    public File getLangFile() {
        return LANG_FILE;
    }
}
