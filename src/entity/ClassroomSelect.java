package entity;

public class ClassroomSelect {
	String time;
	String classroom;
	String teacher;
	String classname;
	public ClassroomSelect(String time, String classroom, String teacher, String classname) {
		super();
		this.time = time;
		this.classroom = classroom;
		this.teacher = teacher;
		this.classname = classname;
	}

	
	public ClassroomSelect(){
		
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getClassroom() {
		return classroom;
	}


	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}


	public String getTeacher() {
		return teacher;
	}


	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}


	public String getClassname() {
		return classname;
	}


	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	
	
}
