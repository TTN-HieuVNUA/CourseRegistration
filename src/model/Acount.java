package model;

public class Acount {
	private String username;
	private String password;
	private String classcode;

	public Acount(String username, String password, String classcode) {
		this.username = username;
		this.password = password;
		this.classcode = classcode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClasscode() {
		return classcode;
	}
	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}
	
	
}
