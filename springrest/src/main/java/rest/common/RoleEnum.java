package rest.common;


public enum RoleEnum {
	STAFF(1,"接待人员"), ADMIN(2,"管理员");
	
	private final int value ;
	
	private final String desc;
	
	private RoleEnum(int value,String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String getDesc(){
		return this.desc;
	}
	
	/**
	 * 通过Value 获取枚举
	 * @param value
	 * @return
	 */
	public static RoleEnum getByValue(Integer value){
		for(RoleEnum RoleEnum : values()){
			if(RoleEnum.value == value){
				return RoleEnum;
			}
		}
		return STAFF;
	}
	
	@Override
	public String toString(){
		return this.desc;
		
	}
	
}
