package cn.clims.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * @author DubLBo
 *
 */
@SuppressWarnings("rawtypes")
public class PageSupport {
	
	private Integer totalCount = 0;//总记录数
	private Integer totalPageCount = 0; //总页数
	private Integer pageSize = Constants.PAGE_SIZE; //每頁顯示的記錄數
	private Integer currentPageNo = 1;//当前页碼
	private Integer number = 3;//当前页之前和之后显示的页数个数
	private List items = new ArrayList();//當前頁記錄內容集合
	
	
	/**
	 * 計算总页数
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {
		if(totalCount > 0){
			this.totalCount = totalCount;
			//设置总页数
			if(this.totalCount % this.pageSize == 0){
				this.totalPageCount = totalCount/pageSize;
			}else if(this.totalCount % this.pageSize > 0)
				this.totalPageCount = totalCount/pageSize + 1;
			else {
				this.totalPageCount = 0;
			}
		}
	}
	
	/**
	 * 獲取總記錄數
	 * @return
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	
	/**
	 * 獲取每頁顯示的記錄數
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 設置每頁顯示的記錄數
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 得到當前頁面編號
	 * @return
	 */
	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	/**
	 * 設置當前頁面編號
	 * @param currentPageNo
	 */
	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	/**
	 * 获取当前页之前和之后显示的页数個數
	 * @return
	 */
	public Integer getNumber() {
		return number;
	}
	
	/**
	 * 設置當前頁之前或之後顯示的頁面個數
	 * @param number
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
	
	/**
	 * 得到總頁數
	 * @return
	 */
	public Integer getTotalPageCount() {
		return totalPageCount;
	}
	
	/**
	 * 获取前一页
	 * @return
	 */
	public Integer getPrev(){
		return currentPageNo-1;
	}
	
	/**
	 * 获取下一页
	 * @return
	 */
	public Integer getNext(){
		return currentPageNo+1;
	}
	
	/**
	 * 获取最后一页
	 * @return
	 */
	public Integer getLast(){
		return totalPageCount;
	}
	
	/**
	 * 判断是否存在前一页
	 * @return
	 */
	public boolean getIsPrev(){
		if(currentPageNo>1)
			return true;
		return false;
	}
	
	/**
	 * 判断是否存在后一页
	 * @return
	 */
	public boolean getIsNext(){
		if(totalPageCount!=null && currentPageNo<totalPageCount)
			return true;
		return false;
	}
	
	/**
	 * 当前页的前number条頁  假設當前頁是 6   共有11頁   如   1 2 3 4 5
	 * @return
	 */
	public List<Integer> getPrevPages(){
		List<Integer> list = new ArrayList<Integer>();
		Integer _frontStart = 1;
		if(currentPageNo > number)
			_frontStart = currentPageNo - number;
		for(Integer i = _frontStart; i<currentPageNo;i++){
			list.add(i);
		}
		return list;
	}
	
	/**
	 * 当前页的后number条  假設當前頁是6  共有11頁  如：7 8 9 10 11
	 * @return
	 */
	public List<Integer> getNextPages(){
		List<Integer> list = new ArrayList<Integer>();
		Integer _endCount = number;
		if(totalPageCount != null){
			if(number<totalPageCount && (currentPageNo+number)<totalPageCount){
				_endCount = currentPageNo+_endCount;
			}else{
				_endCount=totalPageCount;
			}
		}

		for(Integer i = currentPageNo+1; i<=_endCount;i++){
			list.add(i);
		}
		return list;
	}
	
}