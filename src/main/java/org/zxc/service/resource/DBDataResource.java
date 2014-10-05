package org.zxc.service.resource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.zxc.service.service.DBDataService;


@Path("data")
public class DBDataResource {

	@Inject
	private DBDataService<Map> dbDataService;

	@POST
	@Path("query/{dbName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map querySql(@PathParam("dbName") String dbName,
			@FormParam(value="sql") String sql) throws SQLException{		
		return dbDataService.query(dbName, sql);
	}
	
	@POST
	@Path("exec/{dbName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map execSql(@PathParam("dbName") String dbName,
			@FormParam(value="sql") String sql) throws SQLException {
		return dbDataService.update(dbName, sql);
	}
}
