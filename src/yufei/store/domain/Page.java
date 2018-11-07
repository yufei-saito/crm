package yufei.store.domain;

import java.util.List;

public class Page {
	private int pageNum;
	private int everyPage;
	private int maxCount;
	private int maxPage;
	private List list;
	private int startPage;
	private int endPage;
	private String url;
	public Page() {
		super();
		
	}
	public Page(int pageNum,  int maxPage) {
		super();
		this.pageNum = pageNum;
		this.maxPage = maxPage;
		this.startPage = pageNum - 4; //5
		this.endPage = pageNum + 4;  //13
		if(pageNum>9){
			//超过了9页
			if(startPage < 1){
				startPage = 1;
				endPage = startPage+8;
			}
			if(endPage>pageNum){
				endPage = pageNum;
				startPage = endPage-8;
			}
		}else{
			//不够9页
			startPage = 1;
			endPage = maxPage;
		}
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getEveryPage() {
		return everyPage;
	}
	public void setEveryPage(int everyPage) {
		this.everyPage = everyPage;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Page [pageNum=" + pageNum + ", everyPage=" + everyPage + ", maxCount=" + maxCount + ", maxPage="
				+ maxPage + ", list=" + list + "]";
	}
	
	
}
