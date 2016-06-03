package rest.common;


public enum RoomStatus {

	empty(1,"空房"),
	
	//booked(2,"已预定"),
	
	used(2,"使用中"),
	
	fault(3,"故障"),
	
	disable(4,"不可用");
	
    private final int value ;
	
	private final String desc;
	
	private RoomStatus(int value,String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public int value() {
		return this.value;
	}

	public String getDesc() {
		return this.desc;
	}
	
	@Override
	public String toString(){
		return this.desc;
		
	}
	
	public static RoomStatus valueOf(int statusCode) {
		for (RoomStatus status : values()) {
			if (status.value == statusCode) {
				return status;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
	}
}
