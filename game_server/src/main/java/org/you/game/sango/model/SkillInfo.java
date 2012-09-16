/**
 * $Id$
 * Copyright(C) 2012-2016 msnvip@msn.cn. All rights reserved.
 */
package org.you.game.sango.model;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author <a href="mailto:msnvip@msn.cn">msnvip</a>
 * @version 1.0
 * @since 1.0
 */
public class SkillInfo {

	private int id;	
	private long userId;
	
	private int category;
	private String title;
	private String tagStr;
	private String content;
	private Date createTime;
	
	private String haveSkill;
	private String hopeSkill;
	
	private String nickName;
	private int selfSex;
	private int provinceId;
	private int cityId;
	private int areaId;
	private int age;
	
	private String hopeSex;
	
	private String email;
	private String qq;
	private String phone;
	private String tel;
	
	private Set<Integer> hopeSexIds;
	private Set<Integer> tagIds;
	
	public static SkillInfo getInstance(ResultSet rs){
		SkillInfo skillInfo = new SkillInfo();
		try {
			skillInfo.setAge(rs.getInt("age"));
			skillInfo.setAreaId(rs.getInt("areaId"));
			skillInfo.setCategory(rs.getInt("category"));
			skillInfo.setCityId(rs.getInt("cityId"));
			skillInfo.setContent(rs.getString("content"));
			skillInfo.setCreateTime(rs.getDate("create_time"));
			skillInfo.setEmail(rs.getString("email"));
			skillInfo.setHaveSkill(rs.getString("have_skill"));
			skillInfo.setHopeSex(rs.getString("hope_sex"));
			skillInfo.setHopeSkill(rs.getString("hope_skill"));
			skillInfo.setId(rs.getInt("id"));
			skillInfo.setNickName(rs.getString("nick_name"));
			skillInfo.setPhone(rs.getString("phone"));
			skillInfo.setProvinceId(rs.getInt("province_id"));
			skillInfo.setQq(rs.getString("qq"));
			skillInfo.setSelfSex(rs.getInt("self_sex"));
			skillInfo.setTagStr(rs.getString("tag_str"));
			skillInfo.setTel(rs.getString("tel"));
			skillInfo.setTitle(rs.getString("title"));
			skillInfo.setUserId(rs.getLong("user_id"));
			return skillInfo;
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
	public String getTagStr() {
		return tagStr;
	}
	public void setTagStr(String tagStr) {
		this.tagStr = tagStr;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public int getSelfSex() {
		return selfSex;
	}
	public void setSelfSex(int selfSex) {
		this.selfSex = selfSex;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getHopeSex() {
		return hopeSex;
	}
	public void setHopeSex(String hopeSex) {
		this.hopeSex = hopeSex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Set<Integer> getHopeSexIds() {
		return hopeSexIds;
	}
	public void setHopeSexIds(Set<Integer> hopeSexIds) {
		this.hopeSexIds = hopeSexIds;
	}
	public Set<Integer> getTagIds() {
		return tagIds;
	}
	public void setTagIds(Set<Integer> tagIds) {
		this.tagIds = tagIds;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
}
