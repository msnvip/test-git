/**
 * $Id$
 * Copyright(C) 2012-2016 msnvip@msn.cn. All rights reserved.
 */
package org.you.game.sango.dao;

import java.util.List;

import org.you.game.sango.dao.impl.SkillInfoDao;
import org.you.game.sango.model.SkillInfo;
import org.you.game.sango.model.SkillSimpleInfo;

import com.google.inject.ImplementedBy;

/**
 *
 * @author <a href="mailto:msnvip@msn.cn">msnvip</a>
 * @version 1.0
 * @since 1.0
 */
@ImplementedBy(SkillInfoDao.class)
public interface ISkillInfoDao {

	public List<SkillSimpleInfo> getSimpleList(int category, int page);
	
	public SkillInfo getSkillInfo(long userId, int infoId);
	
	public void addSkillInfo(SkillInfo skillInfo);
}
