/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide.generic;

import com.kamike.db.generic.FieldName;
import com.kamike.db.generic.GenericColumn;
import com.kamike.db.generic.GenericReflect;
import com.kamike.db.generic.GenericType;
import com.kamike.db.generic.TableName;
import com.kamike.divide.KamiDelete;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author THiNk
 * @param <T>
 */
public class KamiGenericDelete<T> extends KamiDelete {

    
    
    protected ArrayList<GenericColumn> ids;

    public KamiGenericDelete(T t) {
        super();

         
        tableName = t.getClass().getAnnotation(TableName.class).value();
        this.init();
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
        buffer.append(TableName());
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

    public int bind() throws SQLException {

        int i = 1;
        for (Iterator<GenericColumn> it = ids.iterator(); it.hasNext();) {
            GenericColumn column = it.next();
            this.setColumn(i, column);
            i++;
        }

        return i;
    }

}
