package netherrackdamage;
 
import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;
 
public class NDprops {
    HashMap<String,ArrayList<String>> Properties = new HashMap<String,ArrayList<String>>(); 
    String fileName;
    Logger log = Logger.getLogger("Minecraft");
    Configuration file;
    private final NetherrackDamage plugin;
    int damageDealt;
    int damageDelay;
    String perm;
 
    /**
     * Creates or opens a properties file using specified filename
     * 
     * @param fileName
     */
    public NDprops(NetherrackDamage instance) {
        plugin = instance;
        
    }
    public void doConfig(String permiss) {
        file = new Configuration(new File(plugin.getDataFolder(), "properties.yml"));
        file.load();
        if (new File(plugin.getDataFolder(),"properties.yml").exists()) {
            System.out.println("[Netherrack-Damage] Configuration file loaded!");
        } else {
            file.setProperty("damageDealt", 1);
            file.setProperty("damageDelay", 1);
            file.setProperty("Permissions", permiss);
            file.save();
            System.out.println("[Netherrack-Damage] Configuration file created with default values!");
        }
        
        //Get configs
        damageDealt = file.getInt("damageDealt", damageDealt);
        damageDelay = file.getInt("damageDelay", damageDelay);
        perm = file.getString("Permissions", perm);
    }
}
