package org.zxc.service.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.zxc.service.cache.TableCache;
import org.zxc.service.context.TableNamesCacheContext;
import org.zxc.service.dao.MetaDao;
import org.zxc.service.domain.DBTable;

public class DBMetaServiceImpl implements DBMetaService {
	
	@Inject
	private TableNamesCacheContext tabnameCacheContext;

	@Inject
	private MetaDao metaDao;	
	
	@Inject
	private DBDataService<Map> dbDataService;
	
	@Inject
	private ResultSetHandler<Map> rsh;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zxc.service.service.DBMetaService#findTable(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public DBTable findTable(String dbName, DBTable dbTable) {
		return metaDao.findTable(dbName, dbTable.getTableScheam(),
				dbTable.getTableName());
	}

	public DBTable findTable() {
		return new DBTable();
	}

	@Override
	public List<String> findTableNames(String dbName, String tableName) throws SQLException {
		TableCache tableCache = tabnameCacheContext.getTableNamesCache(dbName);
		return tableCache.getTables(tableName);		
	}
}
