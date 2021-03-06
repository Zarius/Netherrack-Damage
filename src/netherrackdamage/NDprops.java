package netherrackdamage;
 
import java.io.*;
import java.util.*;
import org.bukkit.util.config.Configuration;
 
public class NDprops {
    HashMap<String,ArrayList<String>> Properties = new HashMap<String,ArrayList<String>>(); 
    String fileName;
    Configuration file;
    private final NetherrackDamage plugin;
    int damageDealt;
    int damageDelay;
    String blockID;
    String bootMod;
    String temp;
    String armorMod;
    String painMessages;
    String painMessage;
    String debugMessages;

    public NDprops(NetherrackDamage instance) {
        plugin = instance;
        
    }
    public void doConfig() {
        file = new Configuration(new File(plugin.getDataFolder(), "properties.yml"));
        file.load();
        if (new File(plugin.getDataFolder(),"properties.yml").exists()) {
            System.out.println("[Netherrack-Damage] Configuration file loaded!");
        } else {
            file.setProperty("damageDealt", 1);
            file.setProperty("damageDelay", 1);
            file.setProperty("protectedWorlds", "No");
            file.setProperty("blockID", "87");
            file.setProperty("bootMod", "Yes");
            file.setProperty("armorMod", "Yes");
            file.setProperty("painMessages", "No");
            file.setProperty("painMessage", "*Ouch* Radiation damage from Netherrack.");
            file.setProperty("debugMessages", "No");
            file.save();
            System.out.println("[Netherrack-Damage] Configuration file created with default values!");
        }
        
        //Get configs
        damageDealt = file.getInt("damageDealt", damageDealt);
        damageDelay = file.getInt("damageDelay", damageDelay);
        bootMod = file.getString("bootMod", bootMod);
        blockID = file.getString("blockID", blockID);
        armorMod = file.getString("armorMod", armorMod);
        painMessages = file.getString("painMessages", painMessages);
        painMessage = file.getString("painMessage", painMessage);
        temp = file.getString("protectedWorlds", temp);
        debugMessages = file.getString("debugMessages", debugMessages);
        
    }
    public void relConfig() {
        file = new Configuration(new File(plugin.getDataFolder(), "properties.yml"));
        file.load();
        if (new File(plugin.getDataFolder(),"properties.yml").exists()) {
            System.out.println("[Netherrack-Damage] Configuration file reloaded!");
        }
        //Get configs
        damageDealt = file.getInt("damageDealt", damageDealt);
        damageDelay = file.getInt("damageDelay", damageDelay);
        bootMod = file.getString("bootMod", bootMod);
        armorMod = file.getString("armorMod", armorMod);
        painMessages = file.getString("painMessages", painMessages);
        painMessage = file.getString("painMessage", painMessage);
        blockID = file.getString("blockID", blockID);
        temp = file.getString("protectedWorlds", temp);
        debugMessages = file.getString("debugMessages", debugMessages);
    }
}
