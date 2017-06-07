package enums;

public enum Type {

	NATIVE(0,"����"),
	IOU (1,"�û������ʲ�"),
	UNIQUE(2,"Ψһ�ʲ�");
	
	private Integer code;
	private String desc;
	
	private Type (int code , String desc) {
		this.code = code;
		this.desc = desc;
	}
}
