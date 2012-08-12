package org.you.core.dao;

import javax.sql.DataSource;

public abstract interface DataSourceInstance
{
  public static final String CLASSNAME_MYSQL = "com.mysql.jdbc.Driver";
  public static final String CLASSNAME_POSTGRESQL = "org.postgresql.Driver";
  public static final String EMPTY_PATTERN = "";

  public abstract DataSource getWriter(String paramString);

  public abstract DataSource getReader(String paramString);

  public abstract String getTimeStamp();

  public abstract void close();
}