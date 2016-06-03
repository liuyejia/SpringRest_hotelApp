package rest.common;

public enum OrderStatus {

	booked(1,"预定成功"),
	
	pay(2,"已支付"),
	
	underway(3,"进行中"),
	
	outdate(4,"已过期"),
	
	complete(5,"已完成"),
	
	deleted(6,"已取消");
	
    private final int value ;
	
	private final String desc;
	
	private OrderStatus(int value,String desc){
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
	
	public static OrderStatus valueOf(int statusCode) {
		for (OrderStatus status : values()) {
			if (status.value == statusCode) {
				return status;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
	}
}
