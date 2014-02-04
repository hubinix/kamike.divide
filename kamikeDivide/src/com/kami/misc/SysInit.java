/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kami.misc;

import com.kamike.config.Configuration;
import com.kamike.config.SystemConfig;
import com.kamike.config.SystemConstants;

import com.kamike.db.DbInst;

/**
 *
 * not well code
 *
 * @author brin hubinix@gmail.com
 */
public class SysInit {

    public void startup() {
        String fileName = SysInit.class.getResource("").toString() + SystemConstants.INITIAL_CONFIG_FILE;
        fileName = fileName.replaceAll("file:/", "");
        Configuration conf = new Configuration(fileName);
        //SystemConfig.getInstance().setBaseConfig(conf);

 

        SystemConfig.DB_IP = conf.getValue("kamike.db_ip");
        SystemConfig.DB_NAME = conf.getValue("kamike.db_name");
        SystemConfig.DB_PASSWORD = conf.getValue("kamike.db_password");
        SystemConfig.DB_PORT = conf.getValue("kamike.db_port");
        SystemConfig.DB_USER = conf.getValue("kamike.db_user");

    }

    public void close() {
        DbInst.getInstance().destroy();

    }
}
