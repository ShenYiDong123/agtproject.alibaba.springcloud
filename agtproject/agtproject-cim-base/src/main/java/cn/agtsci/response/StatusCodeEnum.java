package cn.agtsci.response;

/**
 * 枚举类：Result状态码
 */
public enum StatusCodeEnum {


    SUCCESS("200", "请求接口成功"),
    FAIL("-100", "请求接口出错"),
    PARAMETERISNULL("40001","请求参数为空"),
    LOGINFAIL("-100","登录失败"),
    LOGINSUCCESS("200","登录成功"),
    HYSTRIXFAIL("-200","这为Consumer客户端接口提供的降级信息,此刻服务Provider已经出现异常");

    //值
    private final String value;
    //描述
    private final String desc;


    /**
     * 构造函数
     * @param value
     * @param desc
     */
    StatusCodeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
