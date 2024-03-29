package org.zxc.service.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
 
public class StringMapResultSetHandler implements ResultSetHandler<Map<String,List<String>>> {

	@Override
	public Map<String,List<String>> handle(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int cols = 1;
		List<String> dataList = new ArrayList<String>();
		while (rs.next()) {
			for (int i = 0; i < cols; i++) {			
				if(rsmd.getColumnType(i+1) == Types.CLOB){
					Reader reader = null;
					try {
						Clob clob = rs.getClob(i+1);
						if(clob != null){
							reader = clob.getCharacterStream();						
							char[] chars = new char[2048];
							StringBuilder builder = new StringBuilder();
							int a = 0;
							while((a = reader.read(chars)) != -1){
								builder.append(chars);
							}
							dataList.add(builder.toString());
						}						
					} catch (IOException e) {
						e.printStackTrace();
					} finally{
						if(reader != null){
							try {
								reader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}else{
					dataList.add(rs.getObject(i + 1).toString());
				}				
			}		
		}
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		map.put("data", dataList);
		return map;		
	}
}
