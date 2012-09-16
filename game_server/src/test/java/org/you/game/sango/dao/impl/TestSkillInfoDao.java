/**
 * $Id$
 * Copyright(C) 2012-2016 msnvip@msn.cn. All rights reserved.
 */
package org.you.game.sango.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.you.core.config.ConfigUtil;
import org.you.game.sango.dao.ISkillInfoDao;

/**
 *
 * @author <a href="mailto:msnvip@msn.cn">msnvip</a>
 * @version 1.0
 * @since 1.0
 */
public class TestSkillInfoDao {

	private ISkillInfoDao skillInfoDao = null;
	@Before
	public void setUp(){
		ConfigUtil.init();		
		skillInfoDao = GuiceFactory.getInstance(ISkillInfoDao.class);
	}
	
	@Test
	public void testQuery(){
		System.out.println(skillInfoDao.getSimpleList(1, 0));
	}
}
