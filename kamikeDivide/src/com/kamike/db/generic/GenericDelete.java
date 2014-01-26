 
package com.kamike.db.generic;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * 
 * 
 * @author brin
 *  hubinix@gmail.com
 */
public class GenericDelete<T> {

    protected T t;
    protected ArrayList<GenericColumn> ids;

    private String tableName;

    public GenericDelete(T t) {
        this.t = t;
        tableName = t.getClass().getAnnotation(TableName.class).value();

        Field[] fs = t.getClass().getDeclaredFields();
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
                    col.setIntValue(GenericReflect.getInt(field, t));
                    ids.add(col);
                } else if (field.getType() == int.class) {
                    col.setType(GenericType.Double);
                    col.setDoubleValue(GenericReflect.getDouble(field, t));
                    ids.add(col);
                } else if (field.getType() == String.class) {
                    col.setType(GenericType.String);
                    col.setStrValue(GenericReflect.getString(field, t));
                    ids.add(col);
                } else if (field.getType() == long.class) {
                    col.setType(GenericType.Long);
                    col.setLongValue(GenericReflect.getLong(field, t));
                    ids.add(col);
                } else if (field.getType() == Date.class) {
                    col.setType(GenericType.Timestamp);
                    col.setTimestampValue(new Timestamp(GenericReflect.getDate(field, t).getTime()));
                    ids.add(col);
                } else if (field.getType() == boolean.class) {
                    col.setType(GenericType.Boolean);
                    col.setBooleanValue(GenericReflect.getBoolean(field, t));
                    ids.add(col);
                }
            }
        }
    }
    
    

    public String sql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("delete from ");
        buffer.append(getTableName());
        buffer.append("  where ");
        for (Iterator<GenericColumn> it = ids.iterator(); it.hasNext();) {
            GenericColumn id = it.next();
            buffer.append(id.getName());
            buffer.append("=? ");
            if (it.hasNext()) {
                buffer.append(" and ");
            }
        }
        return buffer.toString();
    }

    public String sql(String dbName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("delete from ");
        buffer.append(dbName);
        buffer.append(".");
        buffer.append(getTableName());
        buffer.append("  where ");
        for (Iterator<GenericColumn> it = ids.iterator(); it.hasNext();) {
            GenericColumn id = it.next();
            buffer.append(id.getName());
            buffer.append("=? ");
            if (it.hasNext()) {
                buffer.append(" and ");
            }
        }
        return buffer.toString();
    }

    public int bind(PreparedStatement ps) throws SQLException {
        if (ps == null) {
            return 0;
        }
        int i = 1;
        for (Iterator<GenericColumn> it = ids.iterator(); it.hasNext();) {
            GenericColumn column = it.next();
            switch (column.getType()) {
                case Int:
                    ps.setInt(i, column.getIntValue());
                    break;
                case Long:
                    ps.setLong(i, column.getLongValue());
                    break;
                case Double:
                    ps.setDouble(i, column.getDoubleValue());
                    break;
                case Boolean:
                    ps.setBoolean(i, column.getBooleanValue());
                    break;
                case Timestamp:
                    ps.setTimestamp(i, column.getTimestampValue());
                    break;
                case String:
                    ps.setString(i, column.getStrValue());
                    break;
            }
            i++;
        }

        return i;
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

}
