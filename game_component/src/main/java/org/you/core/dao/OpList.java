package org.you.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpList extends Op {
	@SuppressWarnings("rawtypes")
	private List collect;

	@SuppressWarnings("rawtypes")
	public OpList(String sql, String bizName) {
		this.sql = sql;
		this.collect = new ArrayList();
		this.bizName = bizName;
	}

	@SuppressWarnings("rawtypes")
	public OpList(String sql, String bizName, int tableSuffix) {
		this.sql = sql;
		this.collect = new ArrayList();
		this.bizName = bizName;
		this.tableSuffix = tableSuffix;
	}

	@SuppressWarnings("rawtypes")
	public final List getResult() {
		return this.collect;
	}

	public void setParam(PreparedStatement ps) throws SQLException {
	}

	public Object parse(ResultSet rs) throws SQLException {
		return null;
	}

	@SuppressWarnings("unchecked")
	public final void add(Object ob) {
		this.collect.add(ob);
	}
}