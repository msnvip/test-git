package org.you.game.sango.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.you.core.dao.DataAccessManager;
import org.you.core.dao.DbException;
import org.you.core.dao.OpUniq;
import org.you.game.sango.dao.DbConstant;
import org.you.game.sango.dao.IUidMappingMgr;
import org.you.game.sango.model.UidMappingModel;

public class UidMappingMgr implements IUidMappingMgr {

	@Override
	public UidMappingModel getUserMapping(long userId) {
		String sql = " select * from user_mapping where uid = ? ";
		OpUniq op = new OpUniq(sql,DbConstant.TABLE_USER_MAPPING){

			@Override
			public void setParam(PreparedStatement ps) throws SQLException {
				super.setParam(ps);
			}			
		};
		
		UidMappingModel mapping = null;
		try {
			 mapping = (UidMappingModel) DataAccessManager.getInstance().queryUnique(op);
		} catch (SQLException e) {
			throw new DbException("getUserMapping error["+userId+"]",e);
		}
		
		return mapping;
	}

	@Override
	public UidMappingModel getUserMapping(String platformUid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long addUserMapping(String platformUid) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
