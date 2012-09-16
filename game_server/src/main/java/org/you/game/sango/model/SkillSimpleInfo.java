/**
 * $Id$
 * Copyright(C) 2012-2016 msnvip@msn.cn. All rights reserved.
 */
package org.you.game.sango.model;

import java.sql.ResultSet;

/**
 *
 * @author <a href="mailto:msnvip@msn.cn">msnvip</a>
 * @version 1.0
 * @since 1.0
 */
public class SkillSimpleInfo {

	private int id;	
	private long userId;	
	private String title;	
	private String haveSkill;
	private String hopeSkill;
	private String nickName;
	private int createTime;
	
	public static SkillSimpleInfo getSimpleInfo(SkillInfo skillInfo){
		SkillSimpleInfo simpleInfo = new SkillSimpleInfo();
		
		simpleInfo.setCreateTime( (int) (skillInfo.getCreateTime().getTime()/1000) );
		simpleInfo.setHaveSkill(skillInfo.getHaveSkill());
		simpleInfo.setHopeSkill(skillInfo.getHopeSkill());
		simpleInfo.setId(skillInfo.getId());
		simpleInfo.setNickName(skillInfo.getNickName());
		simpleInfo.setTitle(skillInfo.getTitle());
		simpleInfo.setUserId(skillInfo.getUserId());
		return simpleInfo;	
	}
	
	public static SkillSimpleInfo getInstance(ResultSet rs){
		SkillSimpleInfo simpleInfo = new SkillSimpleInfo();
		try {
			simpleInfo.setCreateTime((int) (rs.getDate("create_time").getTime()/1000));
			simpleInfo.setHaveSkill(rs.getString("have_skill"));
			simpleInfo.setHopeSkill(rs.getString("hope_skill"));
			simpleInfo.setId(rs.getInt("id"));
			simpleInfo.setNickName(rs.getString("nick_name"));
			simpleInfo.setTitle(rs.getString("title"));
			simpleInfo.setUserId(rs.getLong("user_id"));
			return simpleInfo;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHaveSkill() {
		return haveSkill;
	}
	public void setHaveSkill(String haveSkill) {
		this.haveSkill = haveSkill;
	}
	public String getHopeSkill() {
		return hopeSkill;
	}
	public void setHopeSkill(String hopeSkill) {
		this.hopeSkill = hopeSkill;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	
	
	
}
