package rest.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class Horder {
//由于 order是MySQL保留字只能换名字
	private @Id @GeneratedValue Long id;
	//用户只能通过手机号查询订单
	private String phone;
	
	private String email;
	
	private String demand;
	
	private String customername;
	//接待人员可以通过身份证和手机号查询订单
	private String indentity;
	
	private int quantity;
	
	private double cost;
	
	private Long pretime;
	
	private Long posttime;
	
	private Long ordertime;
	
	private String purpose;
	
	private Integer status;//订单状态(为了用户可以取消订单)
	
//	@ManyToOne(targetEntity = Manager.class)
//	@JoinColumn(name = "manager_id",referencedColumnName = "id", nullable = false)
//	private Manager manager;
	
	private String op_account;//用户预定订单时account是null的，所以不适合用外键
	
	@ManyToOne(targetEntity = RoomType.class)
	@JoinColumn(name = "type_id",referencedColumnName = "id", nullable = false)
	private RoomType roomtype;
	
	@ManyToMany(targetEntity = Room.class,cascade = CascadeType.ALL)
	@JoinTable(name = "ORDER_ROOM", 
	joinColumns = { @JoinColumn(name = "ORDER_ID") }, 
	inverseJoinColumns = { @JoinColumn(name = "ROOM_ID") })
	private List<Room> roomlist;
	
	
}
