package org.zxc.service.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.zxc.service.domain.DBColumn;

@Path("/hello")
public class HelloResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello(){
		return "hello rest";
	}

	@GET
	@Path("world")
	@Produces(MediaType.APPLICATION_JSON)
	public DBColumn world(){
		DBColumn dbColumn = new DBColumn();
		dbColumn.setColName("fas杏色");
		return dbColumn;
	}
}
