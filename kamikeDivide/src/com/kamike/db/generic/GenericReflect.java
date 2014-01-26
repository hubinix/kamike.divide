 
package com.kamike.db.generic;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 
 * 
 * @author brin
 *  hubinix@gmail.com
 */
public class GenericReflect {

    public static Object get(Field field, Object obj) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return null == obj ? null : field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getString(Field field, Object obj) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return null == obj ? null : (String) field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static int getInt(Field field, Object obj) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return null == obj ? null : (int) field.get(obj);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getDouble(Field field, Object obj) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return null == obj ? null : (double) field.get(obj);
        } catch (Exception e) {
            return 0d;
        }
    }

    public static long getLong(Field field, Object obj) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return null == obj ? null : (long) field.get(obj);
        } catch (Exception e) {
            return 0l;
        }
    }

    public static Date getDate(Field field, Object obj) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return null == obj ? null : (Date) field.get(obj);
        } catch (Exception e) {
            return new Date(System.currentTimeMillis());
        }
    }
       public static boolean getBoolean(Field field, Object obj) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return null == obj ? null : (boolean) field.get(obj);
        } catch (Exception e) {
            return false;
        }
    }

    public static void set(Object obj, Field field, String value) {

        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            field.set(obj, value);

        } catch (Exception e) {

        }
    }

    public static void set(Object obj, Field field, int value) {

        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            field.set(obj, value);

        } catch (Exception e) {

        }
    }

    public static void set(Object obj, Field field, boolean value) {

        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            field.set(obj, value);

        } catch (Exception e) {

        }
    }

    public static void set(Object obj, Field field, Date value) {

        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            field.set(obj, value);

        } catch (Exception e) {

        }
    }

    public static void set(Object obj, Field field, long value) {

        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            field.set(obj, value);

        } catch (Exception e) {

        }
    }

    public static void set(Object obj, Field field, double value) {

        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            field.set(obj, value);

        } catch (Exception e) {

        }
    }
}
