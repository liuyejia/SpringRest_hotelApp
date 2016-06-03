package rest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class RoomType {
	public static final String PATH="localhost:8080/images/front/";

	private @Id @GeneratedValue Long id;
	
	private String name;
	
	private int total;
	
	private int remain;
	
	private String facility;
	
	private String introduction;
	
	private double price;
	
	private double discount;
	
	private String picture;
	
//	public String getPicture(){
//		return PATH+this.picture;
//	}
}
