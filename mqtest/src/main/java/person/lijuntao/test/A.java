package person.lijuntao.test;

import java.io.Serializable;

public class A implements Serializable{
	int i;
	String name;
	public String getName() {
		return name;
	}
	public <T> T get(Object hhha){
		return (T)hhha;
	}
	/*public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}*/
}
