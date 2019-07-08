package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * 展示数据的pojo
 * @author pc
 *
 */
public class EasyUIDataGridResult implements Serializable{

 
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	private static final long serialVersionUID = 1L;
	//总记录数
	private Integer total;
	//记录的具体信息
	private List<?> rows;
}
