package org.you.game.sango.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UidMappingModel {
	private String platformUid;
	private long userId;
	private int updateTime;
	
	public static UidMappingModel parse(ResultSet rs){
		UidMappingModel model = new UidMappingModel();
		try {
			model.setPlatformUid(rs.getString("platform_uid"));
			model.setUpdateTime(rs.getInt("update_time"));
			model.setUserId(rs.getLong("user_id"));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return model;
	}
	
	public String getPlatformUid() {
		return platformUid;
	}
	public void setPlatformUid(String platformUid) {
		this.platformUid = platformUid;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
