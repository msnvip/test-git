package org.you.core.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract interface OperateDataSource {
	public abstract int insertReturnId(OpUpdate paramOpUpdate)
			throws SQLException;

	public abstract boolean queryExist(OpUniq paramOpUniq) throws SQLException;

	public abstract int queryId(OpUniq paramOpUniq) throws SQLException;

	public abstract int queryInt(OpUniq paramOpUniq) throws SQLException;

	@SuppressWarnings("rawtypes")
	public abstract List queryList(OpList paramOpList) throws SQLException;

	public abstract Object queryUnique(OpUniq paramOpUniq) throws SQLException;

	public abstract int update(OpUpdate paramOpUpdate) throws SQLException;

	public abstract void insertBatchReturnFirstSqlId(
			List<OpBatchUpdate> paramList) throws SQLException;

	public abstract Map<String, Object> queryMap(OpMap paramOpMap,
			String paramString) throws SQLException;
}