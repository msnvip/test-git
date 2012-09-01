package org.you.game.sango.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.you.core.dao.DataAccessManager;
import org.you.core.dao.DbException;
import org.you.core.dao.OpUniq;
import org.you.game.sango.dao.DbConstant;
import org.you.game.sango.dao.IUidMappingDao;
import org.you.game.sango.model.UidMappingModel;

public class UidMappingDaoImpl implements IUidMappingDao {

	@Override
	public UidMappingModel getUserMapping(final long userId) {
		String sql = " select * from "+DbConstant.TABLE_USER_MAPPING+" where uid = ? ";
		OpUniq op = new OpUniq(sql,DbConstant.DB_TEST){

			@Override
			public Object parse(ResultSet rs) throws SQLException {
				return UidMappingModel.parse(rs);
			}

			@Override
			public void setParam(PreparedStatement ps) throws SQLException {
				ps.setLong(0, userId);
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
