package rest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class Room {

	private @Id @GeneratedValue Long id;
	
	@ManyToOne(targetEntity = RoomType.class)
	@JoinColumn(name = "type_id",referencedColumnName = "id", nullable = false)
	private RoomType roomtype;
	
	private String roomnumber;
	
	private int floor;
	
	private int status;
	
	@ManyToOne(targetEntity = Horder.class)
	@JoinColumn(name = "order_id",referencedColumnName = "id", nullable = true)
	private Horder orderid;
	
	//private String facility;
	
	//private String introduction;
	
	//private double price;
	
	//private double discount;
	
	//private String picture;
}
