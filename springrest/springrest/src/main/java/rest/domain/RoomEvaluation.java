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
public class RoomEvaluation {

	private @Id @GeneratedValue Long id;
	
	private int rank;
	
	private String context;
	
	private Long time;
	
	@ManyToOne(targetEntity = Horder.class)
	@JoinColumn(name = "order_id",referencedColumnName = "id", nullable = false)
	private Horder order;
	
}
