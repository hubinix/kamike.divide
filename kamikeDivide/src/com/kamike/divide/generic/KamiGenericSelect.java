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
import com.kamike.db.generic.Id;
import com.kamike.db.generic.TableName;
import com.kamike.divide.KamiResultSet;
import com.kamike.divide.KamiSelect;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author THiNk
 */
public abstract class KamiGenericSelect<T> extends KamiSelect {

    public KamiGenericSelect(String tableName) {
        super(tableName);
    }
    protected ArrayList<GenericColumn> columns;
    protected ArrayList<GenericColumn> ids;

    protected ArrayList<Field> fields;

    public KamiGenericSelect(T t) {
        super();
        tableName = t.getClass().getAnnotation(TableName.class).value();
        this.init();
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
                        col.setIntValue(GenericReflect.getInt(field, t));
                        ids.add(col);
                    }
                } else if (field.getType() == int.class) {
                    col.setType(GenericType.Double);

                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        col.setDoubleValue(GenericReflect.getDouble(field, t));
                        ids.add(col);
                    }
                } else if (field.getType() == String.class) {
                    col.setType(GenericType.String);

                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        col.setStrValue(GenericReflect.getString(field, t));
                        ids.add(col);
                    }
                } else if (field.getType() == long.class) {
                    col.setType(GenericType.Long);

                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        col.setLongValue(GenericReflect.getLong(field, t));
                        ids.add(col);
                    }
                } else if (field.getType() == Date.class) {
                    col.setType(GenericType.Timestamp);

                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        col.setTimestampValue(new Timestamp(GenericReflect.getDate(field, t).getTime()));
                        ids.add(col);
                    }
                } else if (field.getType() == boolean.class) {
                    col.setType(GenericType.Boolean);

                    columns.add(col);
                    Id id = field.getAnnotation(Id.class);
                    if (id != null) {
                        col.setBooleanValue(GenericReflect.getBoolean(field, t));
                        ids.add(col);
                    }
                }
            }
        }
    }

    public KamiGenericSelect() {
        super();
        T t = create();
        tableName = t.getClass().getAnnotation(TableName.class).value();
        this.init();
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

    public String rawSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select  ");

        for (Iterator<GenericColumn> it = columns.iterator(); it.hasNext();) {
            GenericColumn column = it.next();
            buffer.append("t.");
            buffer.append(column.getName());
            if (it.hasNext()) {
                buffer.append(",");

            }
        }
        buffer.append(" from ");

        buffer.append(this.TableName());
        buffer.append(" t ");

        return buffer.toString();
    }

    public String sql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select  ");

        for (Iterator<GenericColumn> it = columns.iterator(); it.hasNext();) {
            GenericColumn column = it.next();

            buffer.append(column.getName());
            if (it.hasNext()) {
                buffer.append(",");

            }
        }
        buffer.append(" from ");
        buffer.append(this.TableName());

        buffer.append(" where  ");

        for (Iterator<GenericColumn> it = columns.iterator(); it.hasNext();) {
            GenericColumn column = it.next();
            buffer.append(column.getName());
            buffer.append("=?");
            if (it.hasNext()) {
                buffer.append(" and ");
            }
        }

        return buffer.toString();
    }

    public String countSQL(String sql) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select  count(1) ");
        //Sql sql2 = Sqls.fetchLong("select count(1) FROM ("+sql+")");
        buffer.append(" from (");

        buffer.append(sql);

        buffer.append(" ) ___countsql ");

        return buffer.toString();
    }

    public long count(KamiResultSet rs) throws SQLException {
        long sum = 0;
        if (rs == null) {
            return 0;
        }
        if (rs.next()) {
            sum = rs.getLong(1);
        }
        return sum;
    }

    public abstract T create();

    public ArrayList<T> fetch(KamiResultSet rs) throws SQLException {
        ArrayList<T> ret = new ArrayList<>();
        if (rs == null) {
            return ret;
        }
        T entity = null;

        while (rs.next()) {
            entity = create();
            int i = 1;
            for (Iterator<GenericColumn> it = columns.iterator(); it.hasNext();) {
                GenericColumn col = it.next();
                switch (col.getType()) {
                    case Int:
                        GenericReflect.set(entity, col.getField(), rs.getInt(i));
                        break;
                    case Long:
                        GenericReflect.set(entity, col.getField(), rs.getLong(i));
                        break;
                    case Double:
                        GenericReflect.set(entity, col.getField(), rs.getDouble(i));
                        break;
                    case Boolean:
                        GenericReflect.set(entity, col.getField(), rs.getInt(i) > 0);
                        break;
                    case Timestamp:
                        GenericReflect.set(entity, col.getField(), rs.getTimestamp(i));
                        break;
                    case String:
                        GenericReflect.set(entity, col.getField(), rs.getString(i));
                        break;
                }
                i++;
            }

            ret.add(entity);
        }

        return ret;

    }

    public T fetchOnce(KamiResultSet rs) throws SQLException {

        if (rs == null) {
            return null;
        }
        T entity = null;

        if (rs.next()) {
            entity = create();
            int i = 1;
            for (Iterator<GenericColumn> it = columns.iterator(); it.hasNext();) {
                GenericColumn col = it.next();
                switch (col.getType()) {
                    case Int:
                        GenericReflect.set(entity, col.getField(), rs.getInt(i));
                        break;
                    case Long:
                        GenericReflect.set(entity, col.getField(), rs.getLong(i));
                        break;
                    case Double:
                        GenericReflect.set(entity, col.getField(), rs.getDouble(i));
                        break;
                    case Boolean:
                        GenericReflect.set(entity, col.getField(), rs.getInt(i) > 0);
                        break;
                    case Timestamp:
                        GenericReflect.set(entity, col.getField(), rs.getTimestamp(i));
                        break;
                    case String:
                        GenericReflect.set(entity, col.getField(), rs.getString(i));
                        break;
                }
                i++;
            }

        }

        return entity;

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
