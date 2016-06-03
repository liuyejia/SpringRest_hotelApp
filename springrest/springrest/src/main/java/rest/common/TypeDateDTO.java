package rest.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.ToString;
import rest.domain.RoomType;

@Data
@ToString
public class TypeDateDTO {

	private int number;

	@JsonProperty(access = Access.WRITE_ONLY)
	private RoomType roomtype;

	public TypeDateDTO(int number, RoomType roomtype) {
		this.number = number;
		this.roomtype = roomtype;
	}

	public int getMaxNumber() {
		int diff = this.roomtype.getRemain() - this.number;
		return (diff > 0) ? diff : 0;
	}
	
	public Long getTypeid(){
		return this.roomtype.getId();
	}
}
