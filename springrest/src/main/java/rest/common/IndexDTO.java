package rest.common;

import java.util.List;

import lombok.Data;
import lombok.ToString;
import rest.domain.Horder;

@Data
@ToString
public class IndexDTO {

	private Long count;
	
	private int ordernumber;
	
	private double income;
	
	private List<Horder> orderlist;
	
//	public IndexDTO(Long number,double income){
//		this.ordernumber=number;
//		this.income=income;
//	}
}
