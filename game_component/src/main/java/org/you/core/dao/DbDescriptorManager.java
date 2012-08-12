package org.you.core.dao;

public abstract interface DbDescriptorManager
{
  public abstract DbInstanceDescriptor getDbInstance(String paramString);
}