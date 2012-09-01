package org.you.game.sango.dao;

import org.you.game.sango.model.UidMappingModel;

public interface IUidMappingDao {

	public UidMappingModel getUserMapping(long userId);
	
	public UidMappingModel getUserMapping(String platformUid);
	
	public long addUserMapping(String platformUid);
	
}
