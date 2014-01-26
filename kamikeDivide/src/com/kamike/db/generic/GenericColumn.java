 
package com.kamike.db.generic;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * 
 * 
 * @author brin
 *  hubinix@gmail.com
 */
public class GenericColumn {

    private String name;
    private int length;
    private boolean nullable;
    private GenericType type;
    private Field field;
    private Object value;
    private String strValue;
    private int intValue;
    private double doubleValue;
    private Timestamp timestampValue;
    private long longValue;
    private boolean booleanValue;

    public GenericColumn() {
        this.length = 255;
        this.nullable=true;
    }

    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public GenericType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(GenericType type) {
        this.type = type;
    }

    /**
     * @return the field
     */
    public Field getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the strValue
     */
    public String getStrValue() {
        return strValue;
    }

    /**
     * @param strValue the strValue to set
     */
    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    /**
     * @return the intValue
     */
    public int getIntValue() {
        return intValue;
    }

    /**
     * @param intValue the intValue to set
     */
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    /**
     * @return the doubleValue
     */
    public double getDoubleValue() {
        return doubleValue;
    }

    /**
     * @param doubleValue the doubleValue to set
     */
    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    /**
     * @return the timestampValue
     */
    public Timestamp getTimestampValue() {
        return timestampValue;
    }

    /**
     * @param timestampValue the timestampValue to set
     */
    public void setTimestampValue(Timestamp timestampValue) {
        this.timestampValue = timestampValue;
    }

    /**
     * @return the longValue
     */
    public long getLongValue() {
        return longValue;
    }

    /**
     * @param longValue the longValue to set
     */
    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    /**
     * @return the booleanValue
     */
    public boolean getBooleanValue() {
        return booleanValue;
    }

    /**
     * @param booleanValue the booleanValue to set
     */
    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
    
     public String column() {

        StringBuffer buffer = new StringBuffer();
        buffer.append(" `");
        buffer.append(this.getName());
        buffer.append("` ");
        switch (this.getType()) {
            case Int:
                buffer.append(" INT ");
                break;
            case Long:
                buffer.append(" BIGINT ");
                break;
            case Double:
                buffer.append(" DOUBLE ");
                break;
            case Boolean:
                buffer.append(" INT ");
                break;
            case Timestamp:
                buffer.append(" TIMESTAMP ");
                buffer.append(" NULL ");
                break;
            case String:
                if (this.getLength() < 256) {
                    buffer.append("VARCHAR");
                    buffer.append("(");
                    buffer.append(this.getLength());
                    buffer.append(") ");

                } else if (this.getLength() < 65535) {
                    buffer.append(" TEXT ");
                } else if (this.getLength() < 16777215) {
                    buffer.append(" MEDIUMTEXT ");
                } else {
                    buffer.append(" LONGTEXT ");
                }
                break;
        }

        if (this.isNullable()) {
            buffer.append(" NOT NULL ");
        } else {
            buffer.append(" DEFAULT NULL");
        }

        buffer.append(", ");
        return buffer.toString();
    }
}
