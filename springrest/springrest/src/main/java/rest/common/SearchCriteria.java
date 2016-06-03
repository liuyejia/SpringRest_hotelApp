package rest.common;

import rest.domain.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SearchCriteria {

	   private Long pretime;
	   private Long posttime;
	   private Integer status;
	   private RoomType roomtype;
	   
	   public SearchCriteria(){};
	   
	   public SearchCriteria(Long pretime,Long posttime,Integer status,RoomType roomtype){
		   this.pretime=pretime;
		   this.posttime=posttime;
		   this.status=status;
		   this.roomtype=roomtype;
	   }
}
