package org.you.core.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OpMap extends Op
{
  private Map<String, Object> collect;

  public OpMap(String sql, String bizName)
  {
    this.sql = sql;
    this.collect = new HashMap<String, Object>();
    this.bizName = bizName;
  }

  public OpMap(String sql, String bizName, int tableSuffix)
  {
    this.sql = sql;
    this.collect = new HashMap<String, Object>();
    this.bizName = bizName;
    this.tableSuffix = tableSuffix;
  }

  public final Map<String, Object> getResult() {
    return this.collect;
  }

  public void setParam(PreparedStatement ps) throws SQLException {
  }

  public Object parse(ResultSet rs) throws SQLException {
    return null;
  }

  public final void add(String keyFieldName, Object ob)
  {
    this.collect.put(keyFieldName, ob);
  }
}