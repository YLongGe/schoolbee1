package entity;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean sex;
	private String nick;
	private String idCard;
	private String realName;
	private String teamFlag;
	private String imgHead;
	


	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	
	
	public String getTeamFlag() {
		return teamFlag;
	}

	public void setTeamFlag(String teamFlag) {
		this.teamFlag = teamFlag;
	}

	public String getImgHead() {
		return imgHead;
	}

	public void setImgHead(String imgHead) {
		this.imgHead = imgHead;
	}

	
	
}
