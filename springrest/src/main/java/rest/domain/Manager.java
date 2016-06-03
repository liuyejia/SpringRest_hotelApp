package rest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import rest.common.RoleEnum;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Data
@ToString(exclude = "password")
@Entity
public class Manager {

	public static final PasswordEncoder PASSWORD_ENCODER = new
	 BCryptPasswordEncoder();

	private @Id @GeneratedValue Long id;

	private String account;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private int role;

	public void setPassword(String password) {
		 this.password = PASSWORD_ENCODER.encode(password);
	 }
	public void setRole(int role) {
		this.role = RoleEnum.getByValue(role).getValue();
	}

	public String getRole() {
		return RoleEnum.getByValue(this.role).toString();
	}
	//获取权限的数字表达
	public int getNumRole(){
		return this.role;
	}
	//用于获取用户 权限的英文表达式
	public String[] getEnRole(){
		List<String> roles=new ArrayList<String>();
		if(this.role==1)
			roles.add("ROLE_STAFF");
		else{
			roles.add("ROLE_STAFF");
			roles.add("ROLE_ADMIN");
		}
		String[] array = (String[])roles.toArray(new String[roles.size()]); 
		return  array;
	}
	
	protected Manager() {
	}

	public Manager(String account, String password, int role) {

		this.account = account;
		this.setPassword(password);
		this.role = role;
	}

}
