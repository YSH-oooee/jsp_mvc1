package step2_00_loginEx;

import java.sql.Timestamp;

//데이터 전달 객체(데이터를 담는 역할)
public class MemberDTO {
	
	private String id;
	private String passwd;
	private String name;
	private Timestamp joindate;
	
	public MemberDTO(String id, String passwd, String name) {
		super();
		this.id = id;
		this.passwd = passwd;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getJoindate() {
		return joindate;
	}
	public void setJoindate(Timestamp joindate) {
		this.joindate = joindate;
	}

}
