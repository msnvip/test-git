package org.you.core.dao;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public abstract class Op
{
  protected String sql;
  protected int multiPurposeParam = -1;
  private int photoDatabaseNumber;
  protected String bizName;
  protected String dbTableName;
  protected int tableSuffix = -1;

  public final String getSql()
  {
    return this.sql;
  }

  public int getMultiPurposeParam()
  {
    return this.multiPurposeParam;
  }

  public int getPhotoDatabaseNumber()
  {
    return this.photoDatabaseNumber;
  }

  public void setPhotoDatabaseNumber(int photoDatabaseNumber) {
    this.photoDatabaseNumber = photoDatabaseNumber;
  }

  public int getTableSuffix()
  {
    return this.tableSuffix;
  }
  public String getBizName() {
    return this.bizName;
  }

  public final void log(Logger logger) {
    if (logger.isDebugEnabled())
      logger.debug(getSql());
  }

  public abstract void setParam(PreparedStatement paramPreparedStatement)
    throws SQLException;
}