package cn.agtsci.modules.systemmanage.controller;

import cn.agtsci.commons.annotation.UserOperation;
import cn.agtsci.commons.enums.ModuleTypeEnum;
import cn.agtsci.commons.enums.UserOperationTypeEnum;
import cn.agtsci.modules.systemmanage.entity.UserAccount;
import cn.agtsci.modules.systemmanage.service.IUserAccountService;
import cn.agtsci.response.ResponseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis_plus_auto
 * @since 2019-11-06
 */
@Api(tags = "系统管理/账号管理")
@RestController
@RequestMapping("/systemmanage/user-account")
public class UserAccountController {

    @Autowired
    IUserAccountService iUserAccountService;

    @UserOperation(name = "查询", module = ModuleTypeEnum.USER_ACCOUNT, type = UserOperationTypeEnum.QUERY)
    @ApiOperation("数据列表")
    @GetMapping("/listJson")
    public ResponseResult listJson() {
        return ResponseResult.success(iUserAccountService.list(null));
    }

    @UserOperation(name = "查询", module = ModuleTypeEnum.USER_ACCOUNT, type = UserOperationTypeEnum.QUERY)
    @ApiOperation("分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", paramType = "query", dataTypeClass = Long.class, defaultValue = "1", value = "当前页"),
            @ApiImplicitParam(name = "pageSize", paramType = "query", dataTypeClass = Long.class, defaultValue = "10", value = "分页尺寸") })
    @GetMapping("/pageJson")
    public ResponseResult pageJson(@ApiIgnore Page<UserAccount> page) {
         return ResponseResult.success(iUserAccountService.page(page, null));
    }

    @UserOperation(name = "查询", module = ModuleTypeEnum.USER_ACCOUNT, type = UserOperationTypeEnum.QUERY)
    @GetMapping("/getOne")
    @ApiOperation("根据id获取")
    @ApiImplicitParam(name = "id", paramType = "query", dataTypeClass = String.class, defaultValue = "1", value = "id")
    public ResponseResult getOne(String id) {
        return ResponseResult.success(iUserAccountService.getById(id));
    }

    @UserOperation(name = "新增", module = ModuleTypeEnum.USER_ACCOUNT, type = UserOperationTypeEnum.INSERT)
    @ApiOperation("新增")
    @PostMapping("/save")
    public ResponseResult save(@ApiParam @RequestBody UserAccount userAccount) {
        return ResponseResult.success(iUserAccountService.save(userAccount));
    }

    @UserOperation(name = "更新", module = ModuleTypeEnum.USER_ACCOUNT, type = UserOperationTypeEnum.UPDATE)
    @ApiOperation("更新")
    @PostMapping("/update")
    public ResponseResult update(@ApiParam @RequestBody UserAccount userAccount) {
        return ResponseResult.success(iUserAccountService.updateById(userAccount));
    }

    @UserOperation(name = "删除", module = ModuleTypeEnum.USER_ACCOUNT, type = UserOperationTypeEnum.DELETE)
    @ApiOperation("删除")
    @PostMapping("/delete")
    public ResponseResult delete(@ApiParam @RequestBody String id) {
        return ResponseResult.success(iUserAccountService.removeById(id));
    }

    @UserOperation(name = "批量删除", module = ModuleTypeEnum.USER_ACCOUNT, type = UserOperationTypeEnum.DELETE)
    @ApiOperation("批量删除")
    @PostMapping("/deleteByIds")
    public ResponseResult deleteByIds(@ApiParam @RequestBody List<String> ids) {
        return ResponseResult.success(iUserAccountService.removeByIds(ids));
    }
}
