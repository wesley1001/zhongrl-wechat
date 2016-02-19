package cn.xn.wechat.activity.util;

import java.util.ArrayList;
import java.util.List;

public class DataPage<T> {

	private int pageIndex = 1;
	private int pageSize = 7;
	private int totalCount = -1;
	private List<T> list = new ArrayList<T>();
	private int userId;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public DataPage() {
	}

	public DataPage(Integer pageIndex, Integer pageSize) {
		if (pageIndex != null) {
			setPageIndex(pageIndex);
		}
		if (pageSize != null) {
			setPageSize(pageSize);
		}
	}

	public long getPageCount() {
		if (totalCount < 0) {
			return -1;
		}
		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;

		if (pageIndex <= 0) {
			this.pageIndex = 1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize <= 0) {
			this.pageSize = 20;
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
