package entity;

public class DeliveTeamData {
	private int teamIcon;
	private String teamName;
	private String time;
	private String heartCount;
	
	
	
	public DeliveTeamData(){
		
	}
	
	public DeliveTeamData(int teamIcon, String time, String heartCount, String teamName) {
		super();
		this.teamIcon = teamIcon;
		this.time = time;
		this.heartCount = heartCount;
		this.teamName = teamName;
	}
	
	public int getTeamIcon() {
		return teamIcon;
	}
	public void setTeamIcon(int teamIcon) {
		this.teamIcon = teamIcon;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getHeartCount() {
		return heartCount;
	}
	public void setHeartCount(String heartCount) {
		this.heartCount = heartCount;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
}
