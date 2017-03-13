package entity;

public class CourseInfo {
	String course;
	String cname;
	float avg;
	
	public CourseInfo(){
		
	}
	public CourseInfo(String course, String cname, float avg) {
		super();
		this.course = course;
		this.cname = cname;
		this.avg = avg;
	}
	
	
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	
	
	
	
	
	
}
