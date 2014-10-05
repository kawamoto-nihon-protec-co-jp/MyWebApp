package org.zxc.service.module;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.zxc.service.context.TableNamesCacheContext;
import org.zxc.service.context.UpdateTableNamesCache;
import org.zxc.service.dao.MapResultSetHandler;
import org.zxc.service.dao.MetaDao;
import org.zxc.service.dao.Pool;
import org.zxc.service.dao.ProxoolPool;
import org.zxc.service.dao.StringMapResultSetHandler;
import org.zxc.service.service.DBDataService;
import org.zxc.service.service.DBDataServiceImpl;
import org.zxc.service.service.DBMetaService;
import org.zxc.service.service.DBMetaServiceImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class ServiceModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(QueryRunner.class);
//		bind(TableNamesCacheContext.class).in();		
		bind(MetaDao.class);		
		bind(Pool.class).to(ProxoolPool.class);
		bind(new TypeLiteral<ResultSetHandler<Map>>(){}).to(new TypeLiteral<MapResultSetHandler>(){});
		bind(new TypeLiteral<ResultSetHandler<Map<String,List<String>>>>(){}).to(new TypeLiteral<StringMapResultSetHandler>(){});
		bind(new TypeLiteral<DBDataService<Map>>(){}).to(new TypeLiteral<DBDataServiceImpl>(){});
		bind(DBMetaService.class).to(DBMetaServiceImpl.class);
		bind(Runnable.class).annotatedWith(Names.named("UpdateTableNamesCache")).to(UpdateTableNamesCache.class);
	}

}
