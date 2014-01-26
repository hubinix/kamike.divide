 
package com.kamike.db.generic;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public abstract class GenericSelect<T> {

    protected ArrayList<GenericColumn> columns;
    protected ArrayList<GenericColumn> ids;

    protected ArrayList<Field> fields;

    private String tableName;

    public GenericSelect(T t) {

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

    public GenericSelect() {

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
    
    public GenericSelect(String tableName) {

        T t = create();

        this.tableName = tableName;
        
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

        buffer.append(getTableName());
        buffer.append(" t ");

        return buffer.toString();
    }

    public String rawSql(String dbName) {
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

        buffer.append(dbName);
        buffer.append(".");
        buffer.append(getTableName());
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
        buffer.append(getTableName());

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

    public String sql(String dbName) {
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
        buffer.append(dbName);
        buffer.append(".");
        buffer.append(getTableName());
        buffer.append(" t ");

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

    public long count(ResultSet rs) throws SQLException {
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

    public ArrayList<T> fetch(ResultSet rs) throws SQLException {
        ArrayList<T> ret = new ArrayList<>();
        if (rs == null ) {
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

    public T fetchOnce(ResultSet rs) throws SQLException {

        if (rs == null ) {
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
