package org.you.core.dao;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OpBatchUpdate extends Op
{
  public OpBatchUpdate(String sql)
  {
    this.sql = sql;
  }

  public OpBatchUpdate(String sql, String bizName)
  {
    this.sql = sql;
    this.bizName = bizName;
  }

  public OpBatchUpdate(String sql, int tableSuffix)
  {
    this.sql = sql;
    this.tableSuffix = tableSuffix;
  }

  public OpBatchUpdate(String sql, String bizName, int tableSuffix)
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