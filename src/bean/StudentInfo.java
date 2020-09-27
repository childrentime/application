package bean;

public class StudentInfo {
	private int id;			   //学号
	private String name;       //姓名
	private int age;           //年龄
	private String gender;     //性别
	private String major;      //专业
	public StudentInfo(){
		
	}
	public StudentInfo(int id,String name,int age,String gender,String major){
		this.id=id;
		this.name=name;
		this.age=age;
		this.gender=gender;
		this.major=major;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	
	public int getAge(){
		return age;
	}
	public void setAge(int age){
		this.age=age;
	}
	
	public String getGender(){
		return gender;
	}
	public void setGender(String gender){
		this.gender=gender;
	}
	public String getMajor(){
		return major;
	}
	public void setMajor(String major){
		this.major=major;
	}
}
