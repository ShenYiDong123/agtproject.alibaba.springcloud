package cn.agtsci.commons.enums;

/**
 * 业务模块类型枚举类
 *
 * @author caiyt
 * @date 2019-11-05
 *
 */
public enum ModuleTypeEnum {

    USER_ACCOUNT("用户账号基本信息"),
    POST_BASIC_INFORMATION("角色基本信息"),
    AREADOC("地区档案");

    private String value;

    public String getValue() {
        return this.value;
    }

    ModuleTypeEnum(String value) {
        this.value = value;
    }
}
