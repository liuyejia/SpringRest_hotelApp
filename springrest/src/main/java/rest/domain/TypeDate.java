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
public class TypeDate {

    private @Id @GeneratedValue Long id;
	
	private int number;
	
	private Long indate;
	
	@ManyToOne(targetEntity = RoomType.class)
	@JoinColumn(name = "type_id",referencedColumnName = "id", nullable = false)
	private RoomType roomtype;
	
	private double total;
}
