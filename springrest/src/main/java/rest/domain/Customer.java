package rest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class Customer {

	private @Id @GeneratedValue Long id;
	
	private String identity;
	
	private String name;
	
	private String gender;
	
	private int birthyear;
}
