package entity;
/**
 * 
 * @author LongGe
 * 该类为实体类，存储"更多"页面中的图片和文字
 *
 */
public class InfoItem {
	private int iv_item_util;//图片
	private String tv_item_myinfo;//功能文字
	
	public InfoItem() {}
	public InfoItem(int iv_item_util, String tv_item_myinfo) {
		super();
		this.iv_item_util = iv_item_util;
		this.tv_item_myinfo = tv_item_myinfo;
	}
	
	public int getIv_item_util() {
		return iv_item_util;
	}
	public void setIv_item_util(int iv_item_util) {
		this.iv_item_util = iv_item_util;
	}
	public String getTv_item_myinfo() {
		return tv_item_myinfo;
	}
	public void setTv_item_myinfo(String tv_item_myinfo) {
		this.tv_item_myinfo = tv_item_myinfo;
	}

	
	
}
