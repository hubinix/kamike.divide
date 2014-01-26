 
package com.kamike.db.generic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * 
 * 
 * @author brin
 *  hubinix@gmail.com
 */
public abstract class GenericCreate<T> {
    
    private String tableName;
    protected String schema;
    
    protected ArrayList<GenericColumn> columns = new ArrayList<>();
    protected ArrayList<GenericColumn> ids = new ArrayList<>();
    
    public abstract T create();
    
    public GenericCreate() {
        T t = create();
        tableName = t.getClass().getAnnotation(TableName.class).value();
        Field[] fs = t.getClass().getDeclaredFields();
        columns = new ArrayList<>();
        ids = new ArrayList<>();
        for (Field field : fs) {
            FieldName fieldName = field.getAnnotation(FieldName.class);
            if (fieldName != null) {
                String fname;
                if ("".equals(fieldName.value())) {
                    fname = field.getName();
                } else {
                    fname = fieldName.value();
                }
                
                GenericColumn col = new GenericColumn();
                col.setName(fname);
                col.setField(field);
                if (field.getType() == int.class) {
                    col.setType(GenericType.Int);
                    
                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        ids.add(col);
                    }
                } else if (field.getType() == int.class) {
                    col.setType(GenericType.Double);
                    
                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        ids.add(col);
                    }
                } else if (field.getType() == String.class) {
                    col.setType(GenericType.String);
                    FieldLength length = field.getAnnotation(FieldLength.class);
                    col.setLength(length.value());
                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        ids.add(col);
                    }
                } else if (field.getType() == long.class) {
                    col.setType(GenericType.Long);
                    
                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        ids.add(col);
                    }
                } else if (field.getType() == Date.class) {
                    col.setType(GenericType.Timestamp);
                    
                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        ids.add(col);
                    }
                } else if (field.getType() == boolean.class) {
                    col.setType(GenericType.Boolean);
                    
                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        ids.add(col);
                    }
                }
            }
        }
    }
    
    public String tableExistSQL() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("show tables in ");
        buffer.append(schema);
         buffer.append(" like '");
        buffer.append(tableName);
        buffer.append("';");
        return buffer.toString();
    }

    public String databaseExistSQL() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("show databases like '");
        buffer.append(this.schema);
        buffer.append("';");
        return buffer.toString();
    }
    
    public String createDatebaseSQL() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CREATE DATABASE  IF  NOT EXISTS `");
        buffer.append(this.schema);
        buffer.append("` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;");
        return buffer.toString();
    }
    
    public String begin() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CREATE TABLE `");
        buffer.append(this.schema);
        buffer.append("`.`");
        buffer.append(tableName);
        buffer.append("` (");
        return buffer.toString();
    }
    
    public String end() {
        
        StringBuffer buffer = new StringBuffer();
        buffer.append(" PRIMARY KEY (");
        for (Iterator<GenericColumn> it = getIds().iterator(); it.hasNext();) {
            GenericColumn column = it.next();
            buffer.append("`");
            buffer.append(column.getName());
            buffer.append("`");
            if (it.hasNext()) {
                buffer.append(",");
            }
        }
        buffer.append(" ))  ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        return buffer.toString();
    }
    
    public String createTableSQL() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.begin());
        for (GenericColumn c : columns) {
            buffer.append(c.column());
        }
        buffer.append(this.end());
        return buffer.toString();
    }
public String tableExistSQL(String tableName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("show tables in ");
        buffer.append(schema);
         buffer.append(" like '");
        buffer.append(tableName);
        buffer.append("';");
        return buffer.toString();
    }

  
    public String begin(String tableName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CREATE TABLE `");
        buffer.append(this.schema);
        buffer.append("`.`");
        buffer.append(tableName);
        buffer.append("` (");
        return buffer.toString();
    }
      public String createTableSQL(String tableName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.begin(tableName));
        for (GenericColumn c : columns) {
            buffer.append(c.column());
        }
        buffer.append(this.end());
        return buffer.toString();
    }
    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema the schema to set
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * @return the columns
     */
    public ArrayList<GenericColumn> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(ArrayList<GenericColumn> columns) {
        this.columns = columns;
    }

    /**
     * @return the ids
     */
    public ArrayList<GenericColumn> getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(ArrayList<GenericColumn> ids) {
        this.ids = ids;
    }
}
