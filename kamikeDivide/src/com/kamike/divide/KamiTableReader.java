/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.SysDbInst;
import com.kamike.db.generic.BaseReader;
import com.kamike.db.generic.GenericSelect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class KamiTableReader extends BaseReader<KamiTable> {

    public KamiTableReader() {
       
    }

    @Override
    public GenericSelect<KamiTable> createSelect() {
        return new KamiTableSelect();
    }

    @Override
    public GenericSelect<KamiTable> createSelect(KamiTable t) {
        return new KamiTableSelect(t);
    }
    
    
    public long count(String sql) {
        GenericSelect<KamiTable> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        long ret = 0;
        try {
            conn = SysDbInst.getInstance().getDatabase().getSingleConnection();
            ps = conn.prepareStatement(select.countSQL(sql), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = ps.executeQuery();
            ret = select.count(rs);

        } catch (Exception e) {
            ret = 0;
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(BaseReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    @Override
    public ArrayList<KamiTable> find(KamiTable t) {
        GenericSelect<KamiTable> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        ArrayList<KamiTable> ret = null;
        try {
            conn = SysDbInst.getInstance().getDatabase().getSingleConnection();
            ps = conn.prepareStatement(select.rawSql() + "where t.name=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, t.getName());
            rs = ps.executeQuery();
            ret = select.fetch(rs);

        } catch (Exception e) {
            ret = new ArrayList<>();
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(KamiTableReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    public ArrayList<KamiTable> find(String name) {
        GenericSelect<KamiTable> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        ArrayList<KamiTable> ret = null;
        try {
            conn = SysDbInst.getInstance().getDatabase().getSingleConnection();
            ps = conn.prepareStatement(select.rawSql() + "where t.name=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, name);
            rs = ps.executeQuery();
            ret = select.fetch(rs);

        } catch (Exception e) {
            ret = new ArrayList<>();
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(KamiTableReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    public ArrayList<KamiTable> findForInsert(String name,int num) {
        // 
        GenericSelect<KamiTable> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        ArrayList<KamiTable> ret = null;
        try {
            conn = SysDbInst.getInstance().getDatabase().getSingleConnection();
            ps = conn.prepareStatement(select.rawSql() + "WHERE  t.closed=0 and t.name = ?  "
                    + "and t.closed=0 and t.alive=1  "
                    + "order by  t.create_date desc limit ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, name);
            ps.setInt(1, num);
            rs = ps.executeQuery();
            ret = select.fetch(rs);

        } catch (Exception e) {
            ret = new ArrayList<>();
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(KamiTableReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    public ArrayList<KamiTable> find(String name, Date beginDate, Date endDate) {
        GenericSelect<KamiTable> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        ArrayList<KamiTable> ret = null;
        try {
            conn = SysDbInst.getInstance().getDatabase().getSingleConnection();
            ps = conn.prepareStatement(select.rawSql() + " where t.name= ? "
                    + "and t.begin_date<? and t.end_date>? "
                    + "order by  t.create_date desc", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, name);
            ps.setTimestamp(2, new Timestamp(endDate.getTime()));
            ps.setTimestamp(3, new Timestamp(beginDate.getTime()));
            rs = ps.executeQuery();
            ret = select.fetch(rs);

        } catch (Exception e) {
            ret = new ArrayList<>();
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(KamiTableReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    
}
