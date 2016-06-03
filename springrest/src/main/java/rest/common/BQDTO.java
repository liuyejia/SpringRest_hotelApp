package rest.common;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BQDTO {

	int year;
	long quantity;
	
	public BQDTO(int y,long q){
		this.year = y;
		this.quantity =q;
	}
}
