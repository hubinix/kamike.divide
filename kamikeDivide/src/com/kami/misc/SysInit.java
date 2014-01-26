/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kami.misc;

import com.kamike.config.Configuration;
import com.kamike.config.SystemConfig;
import com.kamike.config.SystemConstants;
 
import com.kamike.db.SysDbInst;

/**
 * 
 * not well code
 * @author brin
 *  hubinix@gmail.com
 */
public class SysInit {

    public void startup() {
        String fileName = SysInit.class.getResource("").toString()+SystemConstants.INITIAL_CONFIG_FILE  ;
        fileName=fileName.replaceAll("file:/", "");
        Configuration conf = new Configuration(fileName);
        //SystemConfig.getInstance().setBaseConfig(conf);

        //将当前实际磁盘路径存入系统中
        SystemConfig.getInstance().getSystemConstants().put(SystemConstants.CONTEXT_PATH,
                fileName);
        SystemConfig.getInstance().getSystemConstants().put(SystemConstants.INITIAL_REALPATH,
                fileName);

        SystemConfig.getInstance().getSystemConstants().put("website.db_ip",
                conf.getValue("website.db_ip"));
        SystemConfig.getInstance().getSystemConstants().put("website.db_name",
                conf.getValue("website.db_name"));
        SystemConfig.getInstance().getSystemConstants().put("website.db_password",
                conf.getValue("website.db_password"));
        SystemConfig.getInstance().getSystemConstants().put("website.db_port",
                conf.getValue("website.db_port"));
        SystemConfig.getInstance().getSystemConstants().put("website.db_user",
                conf.getValue("website.db_user"));
      
        
    }

    public void close() {
        SysDbInst.getInstance().destroy();
       
    }
}
