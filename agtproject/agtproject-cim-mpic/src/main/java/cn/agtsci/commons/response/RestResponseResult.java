package cn.agtsci.commons.response;

import cn.agtsci.commons.enums.StatusCodeEnum;
import cn.agtsci.response.ResponseResult;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * 结果返回对象
 * Created by ShenYiDong on 2019/7/23
 */
@Component
@Data
public class RestResponseResult extends ResponseResult implements Serializable {


}
