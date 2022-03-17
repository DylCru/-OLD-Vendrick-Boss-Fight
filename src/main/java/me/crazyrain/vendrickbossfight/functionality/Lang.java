package me.crazyrain.vendrickbossfight.functionality;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
    AWAKE("ven-awake", "&4&lVendrick has awoken."),
    CIRCLE("ven-circle","&cStand in the circle before he arrives to be entered into the fight"),
    STAR("ven-star", "&8&oThe eternal star shatters into dust"),
    NOSTART("ven-nostart", "&cIt appears that no one was up for the challenge. You hear a laugh echo from afar"),
    FORCE("ven-force", "&cAn otherworldly force is preventing a second guardian from entering your realm"),
    PURE("ven-pure", "&8&oThe purity of your soul makes your attack phase right through Vendrick!"),
    ENOUGH("ven-enough", "&c&oOne vendrick is probably enough."),
    CURSE("ven-curse", "&8&oA terrible curse has been infused in your soul"),
    LOSE("ven-lose", "&9FOOLS. Eternity WILL continue and your pitiful attempt at defeating me will NOT slow my reign down."),
    PLAYERDEATH("ven-player-death", "&4YES. Keep fueling my eternal reign! Thank you &l{player}"),
    PORTAL("ven-portal", "&c&l[Vendrick] &cI call upon those banished from the plains of mortality"),
    BOMBS("ven-bombs", "&c&l[Vendrick] &cThese bombs are eternal military standard. Good luck cracking their defusal algorithm"),
    GROWTHS("ven-growths", "&c&l[Vendrick] &cI grew these creatures myself. They're of top quality"),
    ENRAGE1("ven-enrage1" ,"&c&l[Vendrick] &cI didn't want it to end this way for you"),
    ENRAGE2("ven-enrage2", "&c&l[Vendrick] &cThis world is nice. But you're in it. Which is a problem"),
    ENRAGE3("ven-enrage3", "&c&l[Vendrick] &cSo I leave you with my parting gift"),
    ENRAGE4("ven-enrage4", "&c&l[Vendrick] &cWatch your step"),
    SHATTER("ven-shatter", "&cThe ground below you is starting to shatter!"),
    END1("ven-end1", "&1This is IMPOSSIBLE"),
    END2("ven-end2", "&1How DARE you disturb the light of the all powerful"),
    END3("ven-end3", "&1DON'T YOU KNOW WHAT ETERNITY MEANS?"),
    END4("ven-end4", "&8&oThe voice fades away into nothingness..."),
    NOPERMS("ven-noperms", "&cOnly OPs can use this command!"),
    NOFIRE("ven-nofire", "&c&l[Vendrick] &cYou can't block my pyres of eternity THAT easily!"),
    NOSPEED("ven-nospeed", "&cVendrick's flooded the battleground! It's hard to move!"),
    STARNAME("star-name", "&lEternal Star"),
    STARTAGLINE("star-tagline", "&8A fallen star from the eternal realm"),
    STAR1("star1","&7Drop on the ground to spawn &4&lVendrick"),
    STAR2("star2","&7Vendrick offers a challenging fight. He uses summons and his hatchet"),
    STAR3("star3","&7to stop those in his way"),
    STAR4("star4","&7It's said that those great enough to best him can reap the rewards of gods"),
    DSTARNAME("dstar-name", "&5&lDistorted &r&lEternal Star"),
    DSTARTAGLINE("dstar-tagline","&8No ordinary star from the eternal realm"),
    DSTAR1("dstar1","&7Drop on the ground to spawn a &5Distorted &4&lVendrick"),
    DSTAR2("dstar2","&5Distorted &7Vendricks are stronger and more dangerous"),
    DSTAR3("dstar3","&7It would be foolish to fight this alone"),
    DSTAR4("dstar4", "&7Greater rewards are at stake for victors"),
    VSTARNAME("vstar-name","&lVolatile Star"),
    VSTAR1("vstar1","&8It's shaking so hard that it could explode if you look at it funny"),
    HATCHETNAME("hatchet-name", "&1&lEternal Hatchet"),
    HATCHET1("hatchet1","&8The standard weapon given to all eternal guardians"),
    HATCHET2("hatchet2","&8It urges for its potential to be released"),
    HATCHET3("hatchet3","&8&oThis weapon can be upgraded!"),
    HATCHET4("hatchet4","&8&oSurround it with 4 Eternal Fragments"),
    ESSENCENAME("essence-name","&9&lEssence of Eternity"),
    ESSENCE1("essence1","&8As eternity comes to an end"),
    ESSENCE2("essence2","&8It's spirits rush to keep their powers whole"),
    ESSENCE3("essence3","&8This was their result"),
    ESSENCE4("essence4","&8&oUsed to make the Shatter Stick. You'll need 4"),
    ESSENCE5("essence5","&8&oFind the spine"),
    FRAGNAME("fragment-name","&9&lEternal Fragment"),
    FRAG1("frag1","&8A fragment of powers to strong for this world"),
    FRAG2("frag2","&8Maybe if you had enough of them their"),
    FRAG3("frag3","&8power could be used to create something great"),
    THATCHETNAME("t-hatchet-name","&1&l&nTrue Eternal Hatchet"),
    THATCHET1("t-hatchet1","&8At last. I can show my true self."),
    THATCHET2("t-hatchet2","&8My true power."),
    THATCHET3("t-hatchet3","&8All shall quiver under my wrath. None shall be spared"),
    THATCHET4("t-hatchet4","&8&lFOR I AM THE ETERNAL CHAMPION"),
    SPINENAME("spine-name","&d&lShatter Spine"),
    SPINE1("spine1","&8A solid foundation to the spirits magnum opus"),
    SPINE2("spine2","&8&oUsed to make the shatter stick. You'll only need the one spine"),
    SPINE3("spine3","&8&oFind the essence"),
    STICKNAME("stick-name","&5&l&nShatter Stick"),
    STICKENCH("stick-ench","&7Shatter I"),
    STICK1("stick1","&8They say the one who can manipulate the world to their will is on par with gods"),
    STICK2("stick2","&8Harness the power of the guardian. Embrace it."),
    NUTRIMENTNAME("nutriment-name","&a&l&nNutriment Of The Infinite"),
    NUTRIMENTENCH("nutriment-ench","&7Feast Of The Guardian I"),
    NUTRIMENT1("nutriment1","&8This sweet, fluffy pie was made through care and love"),
    NUTRIMENT2("nurtiment2","&8A rare sight in the realm of eternity."),
    MERCHANTPLACER("merchant-placer","&9&lMerchant Placer"),
    DMERCHANTPLACER("d-merchant-placer","&9&lDistorted Merchant Placer"),
    CRUSTNAME("crust-name","&6&lPie Crust"),
    CRUST1("crust1","&8This flaky, delicious crust will go perfectly with a meal fit for gods"),
    CRUST2("crust2","&8&oCombine with a golden apple to obtain the Nutriment of the Infinite."),
    APPLENAME("apple-name","&c&lLuscious Apple"),
    APPLE1("apple1","&8Picked from the finest trees in the eternal meadows."),
    APPLE2("apple2","&8Not too sure why it's square though."),
    APPLE3("apple3","&8&oCombine with a Nutriment of the Infinite to upgrade it"),
    OVENNAME("oven-name","&7&l&nPre-Heated Oven"),
    OVEN1("oven1","&8Always remember to turn it off after using it"),
    OVEN2("oven2","&8&oUsed in upgrading the Nutriment of The Infinite"),
    UNUTRIMENTNAME("u-nutriment-name","&a&kaaa&r&a&l&nSlightly Burned Nutriment&r&a&kaaa"),
    UNUTRIMENTENCH("u-nutriment-ench","&7Feast of the Guardian II"),
    UNUTRIMENT1("u-nutriment1","&8Seconds anyone?"),
    CATALYST1("catalyst1","&8A strange relic, passed down through generations of eternal kings"),
    CATALYST2("catalyst2","&8It's shaking. Be careful with what you do with it."),
    BALLLIGHTNINGNAME("ball-lightning-name", "&b&lBall Lightning"),
    BALLLIGHTNING1("ball-lightning1", "&8Right click to throw the ball lightning. Hit Vendrick to reduce the power of his storm!"),
    ENERGYNAME("energy-name","&5&kaaa&5&l&nEnergy Rifle&r&5&kaaa"),
    ENERGY1("energy1","&8Lock and Load."),
    ENERGY2("energy2","&6Ability: Pulse Shot"),
    ENERGY3("energy3","&7Right click to shoot a pulse of energy. Costs 1 hunger per shot"),
    CHAMBERNAME("chamber-name","&5&n&lFusion Chamber"),
    CHAMBER1("chamber1","&8\"For scientific purposes only\""),
    CHAMBER2("chamber2","&8Yeah right."),
    CHAMBER3("chamber3","&8&oUsed to create the Energy Rifle"),
    WEDGENAME("wedge-name","&4&lCatalyst Wedge"),
    WEDGE1("wedge1","&8Combine with the other wedge to obtain the catalyst."),
    UNCHARGEDNAME("uncharged-name","&8&lUncharged Rifle"),
    INFINIUMNAME("infinium-name","&6&lInfinium"),
    INFINIUM1("infinium1", "&8This ore is the foundation of the eternal realms towering cities"),
    INFINIUM2("infinium2","&8Let your creativity flow through"),
    ;


    private String path;
    private String def;
    private static YamlConfiguration LANG;

    Lang(String path, String start){
        this.path = path;
        this.def = start;
    }

    /**
     * Set the {@code YamlConfiguration} to use.
     * @param config The config to set.
     */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    /**
     * Get the default value of the path.
     * @return The default value of the path.
     */
    public String getDefault() {
        return this.def;
    }

    /**
     * Get the path to the string.
     * @return The path to the string.
     */
    public String getPath() {
        return this.path;
    }


}
