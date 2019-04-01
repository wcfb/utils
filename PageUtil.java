package wcfb.utils;

import java.util.List;

public class PageUtil<E> {
	

	private int totalRecord;

	private int totalPage;
	

	private int pageSize =5;

	private int currentPage =1;
	
	
	public PageUtil(int totalRecord, int pageSize, int currentPage, List<E> data) {
		super();
		this.totalRecord = totalRecord;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalPage=totalRecord%pageSize!=0?totalRecord/pageSize+1:totalRecord/pageSize;
		this.data = data;
	}


	private List<E>   data;//�洢��ҳ��ѯ���


	public int getTotalRecord() {
		return totalRecord;
	}


	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public List<E> getData() {
		return data;
	}


	public void setData(List<E> data) {
		this.data = data;
	}


	@Override
	public String toString() {
		return "PageUtil [totalRecord=" + totalRecord + ", totalPage="
				+ totalPage + ", pageSize=" + pageSize + ", currentPage="
				+ currentPage + ", data=" + data + "]";
	}
	
	

}
