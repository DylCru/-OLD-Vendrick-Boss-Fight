package me.crazyrain.vendrickbossfight.functionality;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ItemManager {

    public static ItemStack eternalStar;
    public static ItemStack vendrickHatchet;
    public static ItemStack growthSword;
    public static ItemStack eternalFragment;
    public static ItemStack essenceOfEternity;
    public static ItemStack trueEternalHatchet;
    public static ItemStack shatterSpine;
    public static ItemStack nutrimentOfTheInfinite;
    public static ItemStack shatterStick;
    public static ItemStack tradeLoc;
    public static ItemStack DtradeLoc;
    public static ItemStack MtradeLoc;
    public static ItemStack pieCrust;
    public static ItemStack flamingStar;
    public static ItemStack tidalStar;
    public static ItemStack lusciousApple;
    public static ItemStack oven;
    public static ItemStack nutrimentU;
    public static ItemStack theCatalyst;
    public static ItemStack flameCore;
    public static ItemStack waveCore;
    public static ItemStack voltaicCore;
    public static ItemStack volatileStar;
    public static ItemStack stormStar;
    public static ItemStack ballLightning;
    public static ItemStack energyRifle;
    public static ItemStack fusionChamber;
    public static ItemStack catalystPartA;
    public static ItemStack catalystPartB;
    public static ItemStack unchargedRifle;
    public static ItemStack infinium;

    public static void Init(){
        createStar();
        createHatchet();
        createGrowthSword();
        createFrag();
        createEssence();
        createTrueHatchet();
        createSpine();
        makeShatterStick();
        createNutriment();
        createTradeLoc();
        createDLoc();
        createMLoc();
        createCrust();
        createFlamingStar();
        createTidalStar();
        createApple();
        createOven();
        createNutrimentU();
        createCatalyst();
        flameCore = createCore("Flame", ChatColor.RED, "It's hot.", Material.ORANGE_DYE, Rarity.RARE.toString());
        waveCore = createCore("Wave", ChatColor.BLUE, "It's wet.", Material.LIGHT_BLUE_DYE, Rarity.RARE.toString());
        voltaicCore = createCore("Voltaic", ChatColor.YELLOW, "It's electric.", Material.YELLOW_DYE, Rarity.EPIC.toString());
        createVolatile();
        createEarthStar();
        createBall();
        createRifle();
        createChamber();
        catalystPartA = createCatalystPart("A", Material.CHORUS_PLANT);
        catalystPartB = createCatalystPart("B", Material.CHORUS_FLOWER);
        createUncharged();
        createInfinium();
    }

    private static void createStar(){
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Lang.STARNAME.toString());
        List <String> lore = new ArrayList<>();
        lore.add(Lang.STARTAGLINE.toString());
        lore.add("");
        lore.add(Rarity.RARE.toString());
        lore.add("");
        lore.add(Lang.STAR1.toString());
        lore.add(Lang.STAR2.toString());
        lore.add(Lang.STAR3.toString());
        lore.add(Lang.STAR4.toString());
        lore.add("");
        lore.add(ChatColor.YELLOW + "Difficulty: ★☆☆☆☆");
        meta.setLore(lore);

        item.setItemMeta(meta);
        eternalStar = item;

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("eternalstar"), item);
        sr.shape("eee","ede","eee");
        sr.setIngredient('e', Material.EMERALD);
        sr.setIngredient('d', Material.DIAMOND_BLOCK);
        Bukkit.getServer().addRecipe(sr);
    }

    private static void createVolatile(){
        ItemStack item = new ItemStack(Material.FIRE_CHARGE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Lang.VSTARNAME.toString());

        List<String> lore = new ArrayList<>();
        lore.add(Rarity.EPIC.toString());
        lore.add("");
        lore.add(Lang.VSTAR1.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);
        volatileStar = item;
    }

    private static void createFlamingStar(){
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Lang.DSTARNAME.toString() + ChatColor.RED + "" + ChatColor.BOLD + " (FLAMING)");
        List <String> lore = new ArrayList<>();
        lore.add(Lang.DSTARTAGLINE.toString());
        lore.add("");
        lore.add(Rarity.EPIC.toString());
        lore.add("");
        lore.add(Lang.DSTAR1.toString());
        lore.add(Lang.DSTAR2.toString());
        lore.add(Lang.DSTAR3.toString());
        lore.add(Lang.DSTAR4.toString());
        lore.add("");
        lore.add(ChatColor.YELLOW + "Difficulty: ★★☆☆☆");
        lore.add("");
        lore.add(ChatColor.RED + "" + ChatColor.BOLD + "FLAMING " + ChatColor.LIGHT_PURPLE + "Distortion");
        lore.add(ChatColor.GRAY + "Vendrick uses the power of flames and pyre to really");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "light" + ChatColor.GRAY + " up the battlefield");

        meta.setLore(lore);

        item.setItemMeta(meta);
        flamingStar = item;
    }

    private static void createTidalStar(){
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Lang.DSTARNAME.toString() + ChatColor.BLUE + "" + ChatColor.BOLD + " (TIDAL)");
        List <String> lore = new ArrayList<>();
        lore.add(Lang.DSTARTAGLINE.toString());
        lore.add("");
        lore.add(Rarity.EPIC.toString());
        lore.add("");
        lore.add(Lang.DSTAR1.toString());
        lore.add(Lang.DSTAR2.toString());
        lore.add(Lang.DSTAR3.toString());
        lore.add(Lang.DSTAR4.toString());
        lore.add("");
        lore.add(ChatColor.YELLOW + "Difficulty: ★★★☆☆");
        lore.add("");
        lore.add(ChatColor.BLUE + "" + ChatColor.BOLD + "TIDAL " + ChatColor.LIGHT_PURPLE + "Distortion");
        lore.add(ChatColor.GRAY + "Vendrick harnesses the might of the ocean to");
        lore.add(ChatColor.GRAY + "ensure his fight goes " + ChatColor.ITALIC + "swimmingly");


        meta.setLore(lore);

        item.setItemMeta(meta);
        tidalStar = item;
    }

    private static void createEarthStar(){
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Lang.DSTARNAME.toString() + ChatColor.YELLOW + "" + ChatColor.BOLD + " (STORMY)");
        List <String> lore = new ArrayList<>();
        lore.add(Lang.DSTARTAGLINE.toString());
        lore.add("");
        lore.add(Rarity.EPIC.toString());
        lore.add("");
        lore.add(Lang.DSTAR1.toString());
        lore.add(Lang.DSTAR2.toString());
        lore.add(Lang.DSTAR3.toString());
        lore.add(Lang.DSTAR4.toString());
        lore.add("");
        lore.add(ChatColor.YELLOW + "Difficulty: ★★★★☆");
        lore.add("");
        lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "STORMY " + ChatColor.LIGHT_PURPLE + "Distortion");
        lore.add(ChatColor.GRAY + "Vendrick becomes the eye of the storm. Watch where you're going.");
        lore.add(ChatColor.GRAY + "It's about to get " + ChatColor.ITALIC + "electrifying!.");


        meta.setLore(lore);

        item.setItemMeta(meta);
        stormStar = item;
    }

    private static void createHatchet(){
         ItemStack item = new ItemStack(Material.DIAMOND_AXE);
         ItemMeta meta = item.getItemMeta();

         meta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
         meta.setDisplayName(Lang.HATCHETNAME.toString());
         meta.addEnchant(Enchantment.KNOCKBACK, 2, true);

         List <String> lore = new ArrayList<>();
         lore.add("");
         lore.add(Rarity.EPIC.toString());
         lore.add("");
         lore.add(Lang.HATCHET1.toString());
         lore.add(Lang.HATCHET2.toString());
         lore.add("");
         lore.add(Lang.HATCHET3.toString());
         lore.add(Lang.HATCHET4.toString());
         meta.setLore(lore);

         item.setItemMeta(meta);
         vendrickHatchet = item;
    }

    private static void createGrowthSword(){
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Sword of Eternity");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        growthSword = item;
    }

    private static void createFrag(){
         ItemStack item = new ItemStack(Material.LAPIS_BLOCK, 1);
         ItemMeta meta = item.getItemMeta();

         meta.setDisplayName(Lang.FRAGNAME.toString());

         List <String> lore = new ArrayList<>();
         lore.add(Rarity.RARE.toString());
         lore.add("");
         lore.add(Lang.FRAG1.toString());
         lore.add(Lang.FRAG2.toString());
         lore.add(Lang.FRAG3.toString());
         meta.setLore(lore);
         item.setItemMeta(meta);
         eternalFragment = item;
    }

    public static void createEssence(){
         ItemStack item = new ItemStack(Material.BLUE_DYE);
         ItemMeta meta = item.getItemMeta();

         meta.setDisplayName(Lang.ESSENCENAME.toString());

         List <String> lore = new ArrayList<>();
         lore.add(Rarity.RARE.toString());
         lore.add("");
         lore.add(Lang.ESSENCE1.toString());
         lore.add(Lang.ESSENCE2.toString());
         lore.add(Lang.ESSENCE3.toString());
         lore.add("");
         lore.add(Lang.ESSENCE4.toString());
         lore.add(Lang.ESSENCE5.toString());

         meta.setLore(lore);
         item.setItemMeta(meta);
         essenceOfEternity = item;
    }

    private static void createTrueHatchet(){
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Lang.THATCHETNAME.toString());
        meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
        meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        List <String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Rarity.SPECIAL.toString());
        lore.add("");
        lore.add(Lang.THATCHET1.toString());
        lore.add(Lang.THATCHET2.toString());
        lore.add(Lang.THATCHET3.toString());
        lore.add(Lang.THATCHET4.toString());
        meta.setLore(lore);

        item.setItemMeta(meta);
        trueEternalHatchet = item;
    }

    private static void createSpine(){
         ItemStack item = new ItemStack(Material.END_ROD);
         ItemMeta meta = item.getItemMeta();

         meta.addEnchant(Enchantment.LUCK, 1, true);
         meta.setDisplayName(Lang.SPINENAME.toString());
         meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

         List <String> lore = new ArrayList<>();
         lore.add(Rarity.EPIC.toString());
         lore.add("");
         lore.add(Lang.SPINE1.toString());
         lore.add("");
         lore.add(Lang.SPINE2.toString());
         lore.add(Lang.SPINE3.toString());
         meta.setLore(lore);
         item.setItemMeta(meta);
         shatterSpine = item;

    }

    private static void makeShatterStick(){
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        List <String> lore = new ArrayList<>();

        meta.setDisplayName(Lang.STICKNAME.toString());
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        lore.add(Lang.STICKENCH.toString());
        lore.add(ChatColor.DARK_GRAY + "");
        lore.add(Rarity.SPECIAL.toString());
        lore.add("");
        lore.add(Lang.STICK1.toString());
        lore.add(Lang.STICK2.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);

        shatterStick = item;
    }

    private static void createNutriment(){
         ItemStack item = new ItemStack(Material.PUMPKIN_PIE);
         ItemMeta meta = item.getItemMeta();
         List<String> lore = new ArrayList<>();

         meta.setDisplayName(Lang.NUTRIMENTNAME.toString());
         meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
         lore.add(Lang.NUTRIMENTENCH.toString());

         lore.add("");
         lore.add(Rarity.SPECIAL.toString());
         lore.add("");
         lore.add(Lang.NUTRIMENT1.toString());
         lore.add(Lang.NUTRIMENT2.toString());
         meta.setLore(lore);
         item.setItemMeta(meta);

         nutrimentOfTheInfinite = item;
    }

    private static void createTradeLoc(){
        ItemStack item = new ItemStack(Material.BLACK_DYE);
        ItemMeta tradeMeta = item.getItemMeta();
        assert tradeMeta != null;
        tradeMeta.setDisplayName(Lang.MERCHANTPLACER.toString());
        item.setItemMeta(tradeMeta);

        tradeLoc = item;
    }
    private static void createDLoc(){
        ItemStack item = new ItemStack(Material.PURPLE_DYE);
        ItemMeta tradeMeta = item.getItemMeta();
        assert tradeMeta != null;
        tradeMeta.setDisplayName(Lang.DMERCHANTPLACER.toString());
        item.setItemMeta(tradeMeta);

        DtradeLoc = item;
    }
    private static void createMLoc(){
        ItemStack item = new ItemStack(Material.GREEN_DYE);
        ItemMeta tradeMeta = item.getItemMeta();
        assert tradeMeta != null;
        tradeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lMaterial Merchant Placer"));
        item.setItemMeta(tradeMeta);

        MtradeLoc = item;
    }

    private static void createCrust(){
        ItemStack item = new ItemStack(Material.ORANGE_DYE);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(Lang.CRUSTNAME.toString());
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);


        List<String> lore = new ArrayList<>();
        lore.add(Rarity.RARE.toString());
        lore.add("");
        lore.add(Lang.CRUST1.toString());
        lore.add(ChatColor.DARK_GRAY + "");
        lore.add(Lang.CRUST2.toString());

        meta.setLore(lore);
        item.setItemMeta(meta);
        pieCrust = item;
    }

    private static void createApple(){
        ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(Lang.APPLENAME.toString());
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add(Rarity.EPIC.toString());
        lore.add("");
        lore.add(Lang.APPLE1.toString());
        lore.add(Lang.APPLE2.toString());
        lore.add(ChatColor.DARK_GRAY + "");
        lore.add(Lang.APPLE3.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);

        lusciousApple = item;
    }

    private static void createOven(){
        ItemStack item = new ItemStack(Material.FURNACE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Lang.OVENNAME.toString());
        meta.addEnchant(Enchantment.LUCK, 1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add(Rarity.SPECIAL.toString());
        lore.add("");
        lore.add(Lang.OVEN1.toString());
        lore.add(ChatColor.DARK_GRAY + "");
        lore.add(Lang.OVEN2.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);
        oven = item;
    }

    private static void createNutrimentU(){
        ItemStack item = new ItemStack(Material.PUMPKIN_PIE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        meta.setDisplayName(Lang.UNUTRIMENTNAME.toString());
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        lore.add(Lang.UNUTRIMENTENCH.toString());

        lore.add("");
        lore.add(Rarity.INSANE.toString());
        lore.add("");
        lore.add(Lang.UNUTRIMENT1.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);

        nutrimentU = item;
    }

    private static void createCatalyst(){
        ItemStack item = new ItemStack(Material.END_CRYSTAL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lT&k&lh&8&le Ca&k&lt&8&la&k&lly&8&lst"));

        List<String> lore = new ArrayList<>();
        lore.add(Rarity.EPIC.toString());
        lore.add("");
        lore.add(Lang.CATALYST1.toString());
        lore.add(Lang.CATALYST2.toString());

        meta.setLore(lore);
        item.setItemMeta(meta);
        theCatalyst = item;
    }

    private static ItemStack createCore(String type, ChatColor color, String tagLine, Material mat, String rarity){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(color + "" + ChatColor.BOLD + type + " Core");
        List<String> lore = new ArrayList<>();
        lore.add(rarity);
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + tagLine);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static void createBall(){
        ItemStack item = new ItemStack(Material.BLUE_ICE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Lang.BALLLIGHTNINGNAME.toString());
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> lore = new ArrayList<>();
        lore.add(Rarity.EPIC.toString());
        lore.add("");
        lore.add(Lang.BALLLIGHTNING1.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);
        ballLightning = item;
    }

    private static void createRifle(){
        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Lang.ENERGYNAME.toString());
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add(Rarity.INSANE.toString());
        lore.add("");
        lore.add(Lang.ENERGY1.toString());
        lore.add("");
        lore.add(Lang.ENERGY2.toString());
        lore.add(Lang.ENERGY3.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);

        energyRifle = item;
    }

    private static void createChamber(){
        ItemStack item = new ItemStack(Material.STRUCTURE_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Lang.CHAMBERNAME.toString());
        List<String> lore = new ArrayList<>();
        lore.add(Rarity.SPECIAL.toString());
        lore.add("");
        lore.add(Lang.CHAMBER1.toString());
        lore.add(Lang.CHAMBER2.toString());
        lore.add("");
        lore.add(Lang.CHAMBER3.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);

        fusionChamber = item;
    }

    private static ItemStack createCatalystPart(String part, Material mat){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Lang.WEDGENAME + " " + part);
        List<String> lore = new ArrayList<>();
        lore.add(Rarity.EPIC.toString());
        lore.add("");
        lore.add(Lang.WEDGE1.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static void createUncharged(){
        ItemStack item = new ItemStack(Material.STONE_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Lang.UNCHARGEDNAME.toString());
        meta.setLore(Collections.singletonList(Rarity.EPIC.toString()));
        item.setItemMeta(meta);
        unchargedRifle = item;
    }

    private static void createInfinium(){
        ItemStack item = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Lang.INFINIUMNAME.toString());
        meta.addEnchant(Enchantment.LUCK, 1 ,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> lore = new ArrayList<>();
        lore.add(Rarity.RARE.toString());
        lore.add("");
        lore.add(Lang.INFINIUM1.toString());
        lore.add(Lang.INFINIUM2.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);
        infinium = item;
    }
}
