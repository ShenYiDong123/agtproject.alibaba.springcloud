package cn.agtsci.modules.systemmanage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatis_plus_auto
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("_agt_project_user_account")
@ApiModel(value="UserAccount对象", description="")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Date createDate;

    private String createUser;

    private String isLocked;

    private Date lockDate;

    private String lockKey;

    private String lockUser;

    private String md5;

    private String tenantId;

    private Date updateDate;

    private String updateUser;

    private Integer version;

    private String account;

    private Long accountType;

    private String costCenter;

    private String country;

    private String email;

    private String employeeId;

    private Date endTime;

    private String language;

    private String mobilePhone;

    private String name;

    private String organizationId;

    private String passWord;

    private String phone;

    private String projectId;

    private Date startTime;

    private Long status;

    private Long userType;

    private String enableLookupJson;

    private String operPerson;

    private String operPersonName;

    private String operTime;

    private String updatePerson;

    private String updatePersonName;

    private String updateTime;

    private String major;

    private String professionalTitle;

    private String firstLogin;

    private String avatar;

    private String imStatus;

    private String sign;


    public static final String ID = "id";

    public static final String CREATE_DATE = "create_date";

    public static final String CREATE_USER = "create_user";

    public static final String IS_LOCKED = "is_locked";

    public static final String LOCK_DATE = "lock_date";

    public static final String LOCK_KEY = "lock_key";

    public static final String LOCK_USER = "lock_user";

    public static final String MD5 = "md5";

    public static final String TENANT_ID = "tenant_id";

    public static final String UPDATE_DATE = "update_date";

    public static final String UPDATE_USER = "update_user";

    public static final String VERSION = "version";

    public static final String ACCOUNT = "account";

    public static final String ACCOUNT_TYPE = "account_type";

    public static final String COST_CENTER = "cost_center";

    public static final String COUNTRY = "country";

    public static final String EMAIL = "email";

    public static final String EMPLOYEE_ID = "employee_id";

    public static final String END_TIME = "end_time";

    public static final String LANGUAGE = "language";

    public static final String MOBILE_PHONE = "mobile_phone";

    public static final String NAME = "name";

    public static final String ORGANIZATION_ID = "organization_id";

    public static final String PASS_WORD = "pass_word";

    public static final String PHONE = "phone";

    public static final String PROJECT_ID = "project_id";

    public static final String START_TIME = "start_time";

    public static final String STATUS = "status";

    public static final String USER_TYPE = "user_type";

    public static final String ENABLE_LOOKUP_JSON = "enable_lookup_json";

    public static final String OPER_PERSON = "oper_person";

    public static final String OPER_PERSON_NAME = "oper_person_name";

    public static final String OPER_TIME = "oper_time";

    public static final String UPDATE_PERSON = "update_person";

    public static final String UPDATE_PERSON_NAME = "update_person_name";

    public static final String UPDATE_TIME = "update_time";

    public static final String MAJOR = "major";

    public static final String PROFESSIONAL_TITLE = "professional_title";

    public static final String FIRST_LOGIN = "first_login";

    public static final String AVATAR = "avatar";

    public static final String IM_STATUS = "im_status";

    public static final String SIGN = "sign";

}
