package rest.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.ToString;
import rest.domain.RoomType;


@Data
@ToString
public class RNDTO {

	//@JsonProperty(access = Access.WRITE_ONLY)
	RoomType roomtype;
	long number;
	
	public RNDTO(RoomType r,long n){
		this.roomtype = r;
		this.number = n;
	}
}
