package org.zxc.service.context;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.zxc.service.cache.TableCache;
import org.zxc.service.service.DBDataService;

public class UpdateTableNamesCache implements Runnable{

	@Inject
	private DBDataService<Map> dbDataService;
	
	@Inject
	private TableNamesCacheContext cacheContext;

	private String dbName;	

	@SuppressWarnings("unchecked")
	public void run() {		
		Map<String, Object> resultMap = null;
		try {			
			resultMap = dbDataService.queryTableNames(dbName, null);
			List<String> resultList = (List<String>) resultMap.get("data");
			cacheContext.setTableNamesCache(dbName, new TableCache(resultList));
		} catch (Exception e) {
			cacheContext.resetUpdate(dbName);
			e.printStackTrace();
		}
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}
