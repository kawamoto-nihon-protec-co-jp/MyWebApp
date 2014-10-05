package org.zxc.service.resource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.zxc.service.domain.DBTable;
import org.zxc.service.service.DBDataService;
import org.zxc.service.service.DBMetaService;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
@Path("dbMeta")
public class DBMetaResource {

	@Inject
	private DBMetaService dbMetaService;		
	
	@GET
	@Path("getDetail/{dbName}/{tableName}")
	@Produces(MediaType.APPLICATION_JSON)
	public DBTable findTable(@PathParam("dbName") String dbName,
			@PathParam("tableName") String tableName) {		
		return dbMetaService.findTable(dbName,parseTable(tableName));
	}	
	
	@GET
	@Path("{dbName}/{tableName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> findTableNames(@PathParam("dbName") String dbName,
			@PathParam("tableName") String tableName) throws SQLException {
		return dbMetaService.findTableNames(dbName,tableName);		
	}
		
	@GET
	@Path("getDesc/{dbName}/{tableName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String descTable(@PathParam("dbName") String dbName,
			@PathParam("tableName") String tableName){
		DBTable dbTable = dbMetaService.findTable(dbName,parseTable(tableName));
		return dbTable.descTable();
	}
	
	@GET
	@Path("getCreateSql/{dbName}/{tableName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCreateTableSql(@PathParam("dbName") String dbName,
			@PathParam("tableName") String tableName) {
		DBTable dbTable = dbMetaService.findTable(dbName,parseTable(tableName));
		return dbTable.getCreateTableSql();
	}	
	
	private DBTable parseTable(String tableName){
		DBTable dbTable = new DBTable();
		tableName = tableName.toUpperCase();
		if (tableName.indexOf(".") != -1) {
			tableName = tableName.toUpperCase();
			String[] strs = tableName.split("\\.");
			dbTable.setTableScheam(strs[0]);
			if(strs.length == 2){
				dbTable.setTableName(strs[1]);
			}			
		}else{
			dbTable.setTableName(tableName);
		}
		return dbTable;
	}
}
