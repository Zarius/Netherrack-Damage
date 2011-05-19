package netherrackdamage;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class NDprops {
    HashMap<String,ArrayList<String>> Properties = new HashMap<String,ArrayList<String>>(); 
    String fileName;
	Logger log = Logger.getLogger("Minecraft");
	File file;
        
        private final NetherrackDamage plugin;

      //  public NDprops(NetherrackDamage instance) {
       //     plugin = instance;
       // }

	/**
     * Creates or opens a properties file using specified filename
     * 
     * @param fileName
     */
    public NDprops(String fileName, NetherrackDamage instance) {
        plugin = instance;
        this.fileName = fileName;
        file = new File(fileName);

        if (file.exists()) {
            try {
                NDprops.load();
            } catch (IOException ex) {
                log.severe("[PropertiesFile] Unable to load " + fileName + "!");
            }
        } else {
            try {
            	file.createNewFile();
            	Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"));
            	Date timestamp = new Date();
                writer.write("#Properties file was generated on " + timestamp.toString());
                writer.write("damageDealt: 1");
                writer.write("#Delay in seconds");
                writer.write("damageDelay: 1");
                writer.close();
            } catch (IOException ex) {
                log.severe("[Netherrack-Damage] Unable to create " + fileName + "!");
            }
        }
    }
    
}