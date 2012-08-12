package org.you.core.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OpUniq extends Op
{
  public Object result;

  public OpUniq(String sql, String bizName)
  {
    this.sql = sql;
    this.result = null;
    this.bizName = bizName;
  }

  public OpUniq(String sql, String bizName, int tableSuffix)
  {
    this.sql = sql;
    this.result = null;
    this.bizName = bizName;
    this.tableSuffix = tableSuffix;
  }

  public void setParam(PreparedStatement ps) throws SQLException {
  }

  public Object parse(ResultSet rs) throws SQLException {
    return null;
  }

  public final Object getResult() {
    return this.result;
  }

  public final void add(Object ob) {
    this.result = ob;
  }
}