package netherrackdamage;

import java.io.*;
import org.bukkit.util.config.Configuration;
import org.bukkit.command.CommandSender;

public class NDcommands {
    private final NetherrackDamage plugin;
    Configuration file;
    NDprops props;
    
    public NDcommands(NetherrackDamage instance, NDprops props) {
        plugin = instance;
        this.props = props;
    }
    public void chngDmgDealt(CommandSender event, String newDmg) {
        file = new Configuration(new File(plugin.getDataFolder(), "properties.yml"));
        file.load();
        if (new File(plugin.getDataFolder(),"properties.yml").exists()) {
            file.setProperty("damageDealt", newDmg);
            event.sendMessage("[Netherrack-Damage] Damage Dealt option updated to " + newDmg + "!");
            file.save();
            props.relConfig();
        } else {
            System.out.println("[Netherrack-Damage] Error! Properties file could not be found.");
            event.sendMessage("[Netherrack-Damage] Error! Properties file could not be found.");
        }
    }
    public void chngDmgDelay(CommandSender event, String newDmgD) {
        file = new Configuration(new File(plugin.getDataFolder(), "properties.yml"));
        file.load();
        if (new File(plugin.getDataFolder(),"properties.yml").exists()) {
            file.setProperty("damageDelay", newDmgD);
            event.sendMessage("[Netherrack-Damage] Damage Delay option updated to " + newDmgD + "!");
            file.save();
            props.relConfig();
        } else {
            System.out.println("[Netherrack-Damage] Error! Properties file could not be found.");
            event.sendMessage("[Netherrack-Damage] Error! Properties file could not be found.");
        }
    }
    public void chngBootMod(CommandSender event, String newBM) {
        file = new Configuration(new File(plugin.getDataFolder(), "properties.yml"));
        file.load();
        if (new File(plugin.getDataFolder(),"properties.yml").exists()) {
            file.setProperty("bootMod", newBM);
            event.sendMessage("[Netherrack-Damage] Boot Modifies Netherrack Damage option updated to " + newBM + "!");
            file.save();
            props.relConfig();
        } else {
            System.out.println("[Netherrack-Damage] Error! Properties file could not be found.");
            event.sendMessage("[Netherrack-Damage] Error! Properties file could not be found.");
        }
    }
    
    
}