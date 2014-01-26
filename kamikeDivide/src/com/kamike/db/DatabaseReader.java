/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamike.db;

public abstract class DatabaseReader extends GenericReader<Database> {

    
 
    public abstract Database get(String id);
    
    

}
