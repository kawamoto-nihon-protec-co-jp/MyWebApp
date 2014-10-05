package org.zxc.service.dao;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
 
public class MapResultSetHandler implements ResultSetHandler<Map> {

	@Inject
	private MetaDao metaDao;

	@Override
	public Map<String, List> handle(ResultSet rs) throws SQLException {
		Map<String, List> map = new HashMap<String, List>();
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int cols = rsmd.getColumnCount();
		List<Object[]> dataList = new ArrayList<Object[]>();
		while (rs.next()) {
			Object[] result = new Object[cols];
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
							result[i] = builder.toString();
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
					result[i] = rs.getObject(i + 1);
				}				
			}
			dataList.add(result);
		}

		map.put("metadata", metaDao.findColNames(rsmd));
		map.put("data", dataList);
		return map;
	}
}
