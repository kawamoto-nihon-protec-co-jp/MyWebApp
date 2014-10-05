package org.zxc.service.context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import org.zxc.service.cache.TableCache;
import org.zxc.service.listener.GuiceModuleListener;
import org.zxc.service.module.ServiceModule;

import com.google.inject.Guice;

@Singleton
public class TableNamesCacheContext {

	private ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(10);

	private volatile Map<String, TableCache> cacheMap = new Hashtable<String, TableCache>();
	
	private volatile Map<String, Boolean> isUpdateMap = new HashMap<String, Boolean>();

	public TableCache getTableNamesCache(String dbName) {
		TableCache tableCache = cacheMap.get(dbName);		
		
		if (isUpdateMap.get(dbName) == null) {						
			synchronized (isUpdateMap) {
				if(isUpdateMap.get(dbName) == null){
					
					UpdateTableNamesCache cache = GuiceModuleListener.getInjectorInstance().getInstance(UpdateTableNamesCache.class);
					cache.setDbName(dbName);
					scheduler.scheduleAtFixedRate(cache, 0, 30, TimeUnit.SECONDS);
					isUpdateMap.put(dbName, true);
				}
			}			
		}
		return tableCache == null ? new TableCache(new ArrayList<String>())
				: tableCache;
	}

	public void setTableNamesCache(String dbName, TableCache cache) {	
		cacheMap.put(dbName, cache);		
	}
	
	public void resetUpdate(String dbName){
		isUpdateMap.remove(dbName);
	}
}
