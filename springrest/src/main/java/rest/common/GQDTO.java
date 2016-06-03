package rest.common;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GQDTO {

	private String gender;
	private long quantity;
	
	public GQDTO(String g, long q){
		this.gender = g;
		this.quantity = q;
	}
}
