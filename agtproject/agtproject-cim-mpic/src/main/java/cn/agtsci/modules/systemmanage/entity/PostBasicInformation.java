package cn.agtsci.modules.systemmanage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("_agt_project_post_basic_information")
@ApiModel(value="PostBasicInformation对象", description="")
public class PostBasicInformation implements Serializable {

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

    private String code;

    private String costCenterId;

    private String description;

    private String name;

    private String organizationId;

    private String projectId;

    private Long status;

    private String enableLookupJson;

    private String operPerson;

    private String operPersonName;

    private Date operTime;

    private String updatePerson;

    private String updatePersonName;

    private Date updateTime;


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

    public static final String CODE = "code";

    public static final String COST_CENTER_ID = "cost_center_id";

    public static final String DESCRIPTION = "description";

    public static final String NAME = "name";

    public static final String ORGANIZATION_ID = "organization_id";

    public static final String PROJECT_ID = "project_id";

    public static final String STATUS = "status";

    public static final String ENABLE_LOOKUP_JSON = "enable_lookup_json";

    public static final String OPER_PERSON = "oper_person";

    public static final String OPER_PERSON_NAME = "oper_person_name";

    public static final String OPER_TIME = "oper_time";

    public static final String UPDATE_PERSON = "update_person";

    public static final String UPDATE_PERSON_NAME = "update_person_name";

    public static final String UPDATE_TIME = "update_time";

}
