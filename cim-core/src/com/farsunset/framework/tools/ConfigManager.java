 
package com.farsunset.framework.tools;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 *
 * @author farsunset (3979434@qq.com)
 */
public class ConfigManager {

    private static final Log log = LogFactory.getLog(ConfigManager.class);

    private  Properties config;

    private static ConfigManager instance;

    private ConfigManager() {
        loadConfig();
    }

    /**
     * Returns the singleton instance of ConfigManger.
     * 
     * @return the instance
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                instance = new ConfigManager();
            }
        }
        return instance;
    }

   

    /**
     * Loads the specific configuration file.
     * 
     * @param configFileName the file name
     */
    public void loadConfig() {
        try {
        	config = new Properties();
        	config.load(ConfigManager.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            
        }
    }

    /**
     * Returns the loaded configuration object.
     * 
     * @return the configuration
     */
    public String get(String key) {
        return config.getProperty(key);
    }

}
