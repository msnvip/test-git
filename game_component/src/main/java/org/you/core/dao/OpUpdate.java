package org.you.core.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OpUpdate extends Op
{
  public OpUpdate(String sql, String bizName)
  {
    this.sql = sql;
    this.bizName = bizName;
  }

  public OpUpdate(String sql, String bizName, int tableSuffix)
  {
    this.sql = sql;
    this.bizName = bizName;
    this.tableSuffix = tableSuffix;
  }

  public void setParam(PreparedStatement ps)
    throws SQLException
  {
  }
}