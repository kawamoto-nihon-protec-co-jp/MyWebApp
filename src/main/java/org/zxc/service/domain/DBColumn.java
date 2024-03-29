package org.zxc.service.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DBColumn {

	private String colName;
	
	private String colTypeName;
	
	private int precision;
	
	private int scale;

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColTypeName() {
		return colTypeName;
	}

	public void setColTypeName(String colTypeName) {
		this.colTypeName = colTypeName;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public String descColumn(){
		return this.getColName() + "	" + this.getColTypeName() + "(" + this.getPrecision() + ")";
	}
}
