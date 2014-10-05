package org.zxc.service.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.zxc.service.dao.Pool;
import org.zxc.service.util.DataBaseUtil;
import org.zxc.service.util.TemplateUtil;

public class DBDataServiceImpl implements DBDataService<Map> {

	@Inject
	private QueryRunner runner;
	
	@Inject
	private Pool pool;
	
	@Inject
	private ResultSetHandler<Map> rsh;
	
	@Inject
	private ResultSetHandler<Map<String,List<String>>> strListRsh;	
	
	@Override
	public Map update(String dbName, String sql) throws SQLException {
		return getResultMap(runner.update(pool.getConnection(dbName), sql));
	}

	@Override
	public Map update(String dbName,String sql, Object... params) throws SQLException {
		return getResultMap(runner.update(pool.getConnection(dbName), sql, params));
	}

	@Override
	public Map query(String dbName,String sql) throws SQLException {
		Connection conn = pool.getConnection(dbName);
		return query(conn, sql);
	}

	@Override
	public Map query(String dbName, String sql, Object... params) throws SQLException {
		return runner.query(pool.getConnection(dbName), sql, rsh, params);
	}	
	
	public Map queryTableNames(String dbName,Map<String,Object> dataMap) throws SQLException {
		Connection conn = pool.getConnection(dbName);
		String dbProduc = DataBaseUtil.getDBProduct(conn);
		String sql = TemplateUtil.getSql(dataMap, dbProduc + "-all-tables");
		return runner.query(conn, sql,strListRsh);
	}

	private Map query(Connection conn, String sql) throws SQLException {
		return runner.query(conn, sql, rsh);		
	}
	
	private Map getResultMap(int num){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", num);
		return result;
	}
}
