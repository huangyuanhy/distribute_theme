package webService.demo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement  //将对象转化为xml文档
public class User {
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	public User(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
