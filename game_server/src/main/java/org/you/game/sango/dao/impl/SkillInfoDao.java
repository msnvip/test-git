/**
 * $Id$
 * Copyright(C) 2012-2016 msnvip@msn.cn. All rights reserved.
 */
package org.you.game.sango.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.you.core.dao.DataAccessManager;
import org.you.core.dao.OpList;
import org.you.core.dao.OpUniq;
import org.you.core.dao.OpUpdate;
import org.you.core.util.CollectionUtil;
import org.you.game.sango.dao.ISkillInfoDao;
import org.you.game.sango.model.SkillInfo;
import org.you.game.sango.model.SkillSimpleInfo;

/**
 *
 * @author <a href="mailto:msnvip@msn.cn">msnvip</a>
 * @version 1.0
 * @since 1.0
 */
public class SkillInfoDao implements ISkillInfoDao{
	static Logger logger = Logger.getLogger(SkillInfoDao.class);
	
	private static int pageSize = 10;
	
	@Override
	public List<SkillSimpleInfo> getSimpleList(final int category, final int page) {
		
		String listSql = "select * from skill_info where category = ? limit ?,? ";
		
		OpList opList = new OpList(listSql, "skill"){
			@Override
			public void setParam(PreparedStatement ps) throws SQLException {
				ps.setInt(1, category);
				ps.setInt(2, page*pageSize);
				ps.setInt(3, pageSize);
			}
			@Override
			public Object parse(ResultSet rs) throws SQLException {
				return SkillInfo.getInstance(rs);
			}		
		};
		
		List<SkillInfo> skillInfos = null;
		try {
			skillInfos = DataAccessManager.getInstance().queryList(opList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<SkillSimpleInfo> simpleList = CollectionUtil.newArrayList();
		if(!CollectionUtil.isEmpty(skillInfos)){
			for(SkillInfo info : skillInfos){
				simpleList.add(SkillSimpleInfo.getSimpleInfo(info));
			}
		}
		return simpleList;
	}

	@Override
	public SkillInfo getSkillInfo(long userId, int infoId) {
		String sql = " select * from skill_info where id = ? ";
		OpUniq op = new OpUniq(sql, "skill"){
			@Override
			public void setParam(PreparedStatement ps) throws SQLException {
				super.setParam(ps);
			}
			@Override
			public Object parse(ResultSet rs) throws SQLException {
				return super.parse(rs);
			}			
		};
		SkillInfo info = null;
		try {
			info = (SkillInfo) DataAccessManager.getInstance().queryUnique(op);
		} catch (SQLException e) {
			logger.error("error",e);
		}
		return info;
	}

	
	
	@Override
	public void addSkillInfo(final SkillInfo skillInfo) {
		String sql = "insert into skill_info( user_id, category, title, tag_str, content, create_time,"+ 
					  	"have_skill, hope_skill, nick_name, self_sex, province_id, city_id,"+ 
					  	"area_id, age, hope_sex, email, qq, phone, tel) values(" +
					  	"?,?,?,?,?,?," +
					  	"?,?,?,?,?,?," +
					  	"?,?,?,?,?,?,?)";
		OpUpdate op = new OpUpdate(sql, "skill"){
			@Override
			public void setParam(PreparedStatement ps) throws SQLException {
				ps.setLong(1, skillInfo.getUserId());
				ps.setInt(2, skillInfo.getCategory());
				ps.setString(3, skillInfo.getTitle());
				ps.setString(4, skillInfo.getTagStr());
				ps.setString(5, skillInfo.getContent());
				ps.setDate(6, new java.sql.Date(skillInfo.getCreateTime().getTime()));
				ps.setString(7, skillInfo.getHaveSkill());
				ps.setString(8, skillInfo.getHopeSkill());
				ps.setString(9, skillInfo.getNickName());
				ps.setInt(10, skillInfo.getSelfSex());
				ps.setInt(11, skillInfo.getProvinceId());
				ps.setInt(12, skillInfo.getCityId());
				ps.setInt(13, skillInfo.getAreaId());
				ps.setInt(14, skillInfo.getAge());
				ps.setString(15, skillInfo.getHopeSex());
				ps.setString(16, skillInfo.getEmail());
				ps.setString(17, skillInfo.getQq());
				ps.setString(18, skillInfo.getPhone());
				ps.setString(19, skillInfo.getTel());
			}
		};
		
		try {
			int newId = DataAccessManager.getInstance().insertReturnId(op);
		} catch (SQLException e) {
			logger.error("error new",e);
		}
	}

	@Override
	public void updateSkillInfo(final SkillInfo skillInfo) {
		String sql = "update skill_info  set user_id=?, category=?, title=?, tag_str=?, content=?, create_time=?,"+ 
			  	"have_skill=?, hope_skill=?, nick_name=?, self_sex=?, province_id=?, city_id=?,"+ 
			  	"area_id=?, age=?, hope_sex=?, email=?, qq=?, phone=?, tel=? " +
			  	" where id = ?)";
		OpUpdate op = new OpUpdate(sql, "skill"){
			@Override
			public void setParam(PreparedStatement ps) throws SQLException {
				ps.setLong(1, skillInfo.getUserId());
				ps.setInt(2, skillInfo.getCategory());
				ps.setString(3, skillInfo.getTitle());
				ps.setString(4, skillInfo.getTagStr());
				ps.setString(5, skillInfo.getContent());
				ps.setDate(6, new java.sql.Date(skillInfo.getCreateTime().getTime()));
				ps.setString(7, skillInfo.getHaveSkill());
				ps.setString(8, skillInfo.getHopeSkill());
				ps.setString(9, skillInfo.getNickName());
				ps.setInt(10, skillInfo.getSelfSex());
				ps.setInt(11, skillInfo.getProvinceId());
				ps.setInt(12, skillInfo.getCityId());
				ps.setInt(13, skillInfo.getAreaId());
				ps.setInt(14, skillInfo.getAge());
				ps.setString(15, skillInfo.getHopeSex());
				ps.setString(16, skillInfo.getEmail());
				ps.setString(17, skillInfo.getQq());
				ps.setString(18, skillInfo.getPhone());
				ps.setString(19, skillInfo.getTel());
				
				ps.setInt(20, skillInfo.getId());
			}
		};
		
		try {
			int newId = DataAccessManager.getInstance().update(op);
		} catch (SQLException e) {
			logger.error("error new",e);
		}
	}

}
