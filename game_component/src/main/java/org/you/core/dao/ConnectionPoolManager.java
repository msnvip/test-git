package org.you.core.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public abstract interface ConnectionPoolManager
{
  public abstract Connection getReader(String paramString1, String paramString2)
    throws SQLException;

  public abstract Connection getWriter(String paramString1, String paramString2)
    throws SQLException;

  public abstract Connection getReader(String paramString)
    throws SQLException;

  public abstract Connection getWriter(String paramString)
    throws SQLException;

  public abstract DataSource getReaderDataSource(String paramString1, String paramString2);

  public abstract DataSource getWriterDataSource(String paramString1, String paramString2);

  public abstract DataSource getReaderDataSource(String paramString);

  public abstract DataSource getWriterDataSource(String paramString);
}