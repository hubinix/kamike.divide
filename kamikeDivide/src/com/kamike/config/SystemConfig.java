/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author brin
 */
public class SystemConfig {
    private static volatile SystemConfig ec = new SystemConfig();
    
    private final  ConcurrentHashMap‎<String, String> systemConstants = new ConcurrentHashMap‎<String, String>();

    public static  String WATCH_PATH="E:/share/kami";
    public static  String DOMAIN="high.xinhuavideo.com";
    public static String FASP_PATH="E:/high/fasp/bin/";
    public static String SYSDBNAME="kamike";
    public static String THIS_DB_ID="kamike";
    
    
    private SystemConfig() {
    
    }
    private Configuration baseConfig;

    public static SystemConfig getInstance() {
        return ec;
    }

    /**
     * @param baseConfig the baseConfig to set
     */
    public void setBaseConfig(Configuration baseConfig) {
        this.baseConfig = baseConfig;
    }

    /**
     * @return the systemConstants
     */
    public  ConcurrentHashMap‎<String, String> getSystemConstants() {
        return systemConstants;
    }
    
      /**
     * @param name
     * @return the systemConstants
     */
    public String getSystemConstant(String name) {
       return SystemConfig.getInstance().getSystemConstants().get(name);
    }

  

    /**
     * @return the baseConfig
     */
    public Configuration getBaseConfig() {
        return baseConfig;
    }
}
