package org.you.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class DataAccessManager implements OperateDataSource {
	private static DataAccessManager instance = new DataAccessManager();

	protected Logger logger = Logger.getLogger(getClass());

	public static DataAccessManager getInstance() {
		if (instance == null)
			synchronized (DataAccessManager.class) {
				if (instance == null)
					instance = new DataAccessManager();
			}
		return instance;
	}

	public void closeConnection(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeResultSet(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeRSC(ResultSet rs, Statement st, Connection conn) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				this.logger.error("res close", e);
			}
		if (st != null)
			try {
				st.close();
			} catch (SQLException e) {
				this.logger.error("prestatement  close", e);
			}
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeStatement(Statement st) {
		try {
			if (st != null)
				st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Connection getConn(char o, Op op) throws SQLException {
		if ((op.getBizName() == null) || (op.getBizName().length() == 0))
			throw new SQLException("Op in BizName can't null");
		Connection conn = null;
		if (o == 'r')
			if (op.getTableSuffix() < 0)
				conn = getConnection(op.getBizName(), true);
			else
				conn = getConnection(op.getBizName(), op.getTableSuffix(), true);
		else if (o == 'w') {
			if (op.getTableSuffix() < 0)
				conn = getConnection(op.getBizName(), false);
			else
				conn = getConnection(op.getBizName(), op.getTableSuffix(),
						false);
		} else
			throw new SQLException("unsetting r or w");
		return conn;
	}

	public Connection getConnection(String bizName, boolean isReadConnection)
			throws SQLException {
		if ((bizName == null) || (bizName.length() == 0))
			throw new SQLException("BizName can't be null");
		Connection conn = null;
		if (isReadConnection)
			try {
				conn = DbAdapter.getInstance().getReadConnection(bizName);
			} catch (Exception e) {
				e.printStackTrace(System.err);
				throw new SQLException();
			}
		else
			try {
				conn = DbAdapter.getInstance().getWriteConnection(bizName);
			} catch (Exception e) {
				e.printStackTrace(System.err);
				throw new SQLException();
			}
		return conn;
	}

	public Connection getConnection(String bizName, int tableSuffix,
			boolean isReadConnection) throws SQLException {
		if ((bizName == null) || (bizName.length() == 0))
			throw new SQLException("BizName 不能为null");
		Connection conn = null;
		String tablePattern = getTablePattern(bizName, tableSuffix);
		if (isReadConnection) {
			conn = DbAdapter.getInstance().getReadConnection(bizName,
					tablePattern);
		} else {
			conn = DbAdapter.getInstance().getWriteConnection(bizName,
					tablePattern);
		}
		return conn;
	}

	public long getQueryId(OpUniq op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long result = 0L;
		long begin = 0L;
		try {
			begin = sqlBegin();
			conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());
			op.setParam(ps);

			rs = ps.executeQuery();
			if (rs.next())
				result = rs.getLong(1);
			if (rs.next()) {
				this.logger
						.error("Non Unique Result Error: wrong sql syntax or database not consistence!");
			}
			long l1 = result;

			return l1;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	private String getTablePattern(String bizName, int tableSuffix) {
		return bizName + "_" + tableSuffix;
	}

	public boolean insert(OpUpdate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();
			conn = getConn('w', op);
			ps = conn.prepareStatement(op.getSql());
			op.setParam(ps);
			int count = ps.executeUpdate();
			if (count > 0) {
				if (ps != null)
					ps.close();
				return true;
			}
			return false;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public int[] batchUpdate(OpUpdate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();
			conn = getConn('w', op);
			ps = conn.prepareStatement(op.getSql());
			op.setParam(ps);
			int[] arrayOfInt = ps.executeBatch();

			return arrayOfInt;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public void insertBatchReturnFirstSqlId(List<OpBatchUpdate> ops)
			throws SQLException {
		if ((ops == null) || (ops.size() == 0)) {
			throw new SQLException(
					" ----- the opbatchupdate list can't null -------------");
		}
		OpBatchUpdate firstOp = (OpBatchUpdate) ops.get(0);
		if ((firstOp.bizName == null) || (firstOp.bizName.trim().length() == 0)) {
			throw new SQLException(
					" ----- the bizName of the first opbatchupdate object can't null -------------");
		}

		PreparedStatement ps = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();
			conn = getConn('w', firstOp);
			for (OpBatchUpdate opb : ops) {
				ps = conn.prepareStatement(opb.getSql());
				opb.setParam(ps);

				ps.executeUpdate();
				if (ps != null)
					ps.close();
			}
		} finally {
			closeRSC(null, ps, conn);
			sqlEnd("excutebatch sql,", begin);
		}
	}

	public int insertReturnId(OpUpdate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();
			conn = getConn('w', op);
			ps = conn.prepareStatement(op.getSql());
			op.setParam(ps);

			int count = ps.executeUpdate();
			if (count > 0) {
				if (ps != null)
					ps.close();
				ps = conn.prepareStatement("select last_insert_id();");
				rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
			return 0;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public boolean queryExist(OpUniq op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();

			conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());
			op.setParam(ps);

			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public int queryId(OpUniq op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int result = 0;
		long begin = 0L;
		try {
			begin = sqlBegin();
			conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());
			op.setParam(ps);

			rs = ps.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
			if (rs.next()) {
				this.logger
						.error("Non Unique Result Error: wrong sql syntax or database not consistence!");
			}
			int i = result;

			return i;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public int queryInt(OpUniq op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int result = 0;
		long begin = 0L;
		try {
			begin = sqlBegin();

			conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());

			op.setParam(ps);

			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			} else {
				return 0;
			}
			if (rs.next()) {
				this.logger
						.error("Non Unique Result Error: wrong sql syntax or database not consistence!");
			}
			return result;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public int queryInt(OpUniq op, boolean master) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int result = 0;
		long begin = 0L;
		try {
			begin = sqlBegin();

			if (master)
				conn = getConn('w', op);
			else
				conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());

			op.setParam(ps);

			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			} else {
				return 0;
			}
			if (rs.next()) {
				this.logger
						.error("Non Unique Result Error: wrong sql syntax or database not consistence!");
			}
			return result;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	@SuppressWarnings("rawtypes")
	public List queryList(OpList op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();

			conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());

			op.setParam(ps);

			rs = ps.executeQuery();
			while (rs.next())
				op.add(op.parse(rs));
			List localList = op.getResult();

			return localList;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	@SuppressWarnings("rawtypes")
	public List queryList(OpList op, boolean master) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();

			if (master)
				conn = getConn('w', op);
			else
				conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());

			op.setParam(ps);

			rs = ps.executeQuery();
			while (rs.next())
				op.add(op.parse(rs));
			List localList = op.getResult();

			return localList;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public long queryLong(OpUniq op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long result = 0L;
		long begin = 0L;
		try {
			begin = sqlBegin();

			conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());

			op.setParam(ps);

			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			} else {
				return 0;
			}
			if (rs.next()) {
				this.logger
						.error("Non Unique Result Error: wrong sql syntax or database not consistence!");
			}
			return result;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public Map<String, Object> queryMap(OpMap op, String keyFieldName)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();

			conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());

			op.setParam(ps);

			rs = ps.executeQuery();
			while (rs.next())
				op.add(rs.getString(keyFieldName), op.parse(rs));
			return op.getResult();
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public Map<String, Object> queryMap(OpMap op, String[] keyFieldNames)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();

			conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());

			op.setParam(ps);

			rs = ps.executeQuery();
			int len = keyFieldNames.length;
			String key = "";
			while (rs.next()) {
				for (int i = 0; i < len - 1; ++i)
					if (i == len - 1)
						key = key + rs.getString(keyFieldNames[i]);
					else
						key = key + rs.getString(keyFieldNames[i]) + ",";
				op.add(key, op.parse(rs));
			}
			return op.getResult();
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public Object queryUnique(OpUniq op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();

			conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());
			op.setParam(ps);

			rs = ps.executeQuery();
			if (rs.next())
				op.add(op.parse(rs));
			if (rs.next()) {
				this.logger.error("----------【error sql】----------------");
				this.logger
						.error("Non Unique Result Error: wrong sql syntax or database not consistence!");

				this.logger.error("wrong sql is:" + op.getSql());
				this.logger.error("wrong ps is:" + ps);
			}
			Object localObject1 = op.getResult();

			return localObject1;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public Object queryUnique(OpUniq op, boolean master) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();

			if (master)
				conn = getConn('w', op);
			else
				conn = getConn('r', op);
			ps = conn.prepareStatement(op.getSql());
			op.setParam(ps);

			rs = ps.executeQuery();
			if (rs.next())
				op.add(op.parse(rs));
			if (rs.next()) {
				this.logger.error("----------【error sql】----------------");
				this.logger
						.error("Non Unique Result Error: wrong sql syntax or database not consistence!");

				this.logger.error("wrong sql is:" + op.getSql());
				this.logger.error("wrong ps is:" + ps);
			}
			Object localObject1 = op.getResult();

			return localObject1;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	private long sqlBegin() {
		if (this.logger.isDebugEnabled())
			return System.currentTimeMillis();
		return 0L;
	}

	private void sqlEnd(String sql, long begin) {
		if ((begin > 0L) && (this.logger.isDebugEnabled())) {
			long end = System.currentTimeMillis();
			this.logger.debug("|" + sql + "|" + (end - begin));
		}
	}

	public int update(OpUpdate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long begin = 0L;
		try {
			begin = sqlBegin();

			conn = getConn('w', op);
			ps = conn.prepareStatement(op.getSql());
			op.setParam(ps);

			int i = ps.executeUpdate();

			return i;
		} finally {
			closeRSC(rs, ps, conn);
			sqlEnd(op.getSql(), begin);
		}
	}

	public static void main(String[] args) {
		String sqlStr = "select count(*)  from ss where id < ? ";
		OpUniq op = new OpUniq(sqlStr, "self_test") {
			public void setParam(PreparedStatement ps) throws SQLException {
				ps.setInt(1,3);
			}

			public Object parse(ResultSet rs) throws SQLException {
				return new Integer(rs.getInt(1));
			}
		};
		try {
			int count = ((Integer) getInstance().queryUnique(op)).intValue();
			System.out.print(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}