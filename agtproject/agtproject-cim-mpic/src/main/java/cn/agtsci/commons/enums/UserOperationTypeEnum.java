package cn.agtsci.commons.enums;

/**
 * 日志操作类型枚举类
 * 
 * @author caiyt
 * @date 2019-11-05
 *
 */
public enum UserOperationTypeEnum{

    QUERY("查询"),
	INSERT("新增"),
	UPDATE("更新"),
	DELETE("删除"),
	DOWNLOAD("下载"),
	EXCUTE("执行"),
	EXPORT("导出"),
	IMPORT("导入");

	private String value;

	public String getValue() {
		return this.value;
	}

	UserOperationTypeEnum(String value) {
		this.value = value;
	}
}
