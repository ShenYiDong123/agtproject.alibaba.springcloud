package cn.agtsci.modules.systemmanage.controller;

import cn.agtsci.commons.annotation.UserOperation;
import cn.agtsci.commons.enums.ModuleTypeEnum;
import cn.agtsci.commons.enums.UserOperationTypeEnum;
import cn.agtsci.response.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.agtsci.modules.systemmanage.service.IPostBasicInformationService;
import cn.agtsci.modules.systemmanage.entity.PostBasicInformation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis_plus_auto
 * @since 2019-11-06
 */
@Api(tags = "系统管理/岗位管理")
@RestController
@RequestMapping("/systemmanage/post-basic-information")
public class PostBasicInformationController {

    @Autowired
    IPostBasicInformationService iPostBasicInformationService;

    @UserOperation(name = "查询", module = ModuleTypeEnum.POST_BASIC_INFORMATION, type = UserOperationTypeEnum.QUERY)
    @ApiOperation("数据列表")
    @GetMapping("/listJson")
    public ResponseResult listJson() {
        return ResponseResult.success(iPostBasicInformationService.list(null));
    }

    @UserOperation(name = "查询", module = ModuleTypeEnum.POST_BASIC_INFORMATION, type = UserOperationTypeEnum.QUERY)
    @ApiOperation("分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", paramType = "query", dataTypeClass = Long.class, defaultValue = "1", value = "当前页"),
            @ApiImplicitParam(name = "pageSize", paramType = "query", dataTypeClass = Long.class, defaultValue = "10", value = "分页尺寸") })
    @GetMapping("/pageJson")
    public ResponseResult pageJson(@ApiIgnore Page<PostBasicInformation> page) {
         return ResponseResult.success(iPostBasicInformationService.page(page, null));
    }

    @UserOperation(name = "查询", module = ModuleTypeEnum.POST_BASIC_INFORMATION, type = UserOperationTypeEnum.QUERY)
    @GetMapping("/getOne")
    @ApiOperation("根据id获取")
    @ApiImplicitParam(name = "id", paramType = "query", dataTypeClass = String.class, defaultValue = "1", value = "id")
    public ResponseResult getOne(String id) {
        return ResponseResult.success(iPostBasicInformationService.getById(id));
    }

    @UserOperation(name = "新增", module = ModuleTypeEnum.POST_BASIC_INFORMATION, type = UserOperationTypeEnum.INSERT)
    @ApiOperation("新增")
    @PostMapping("/save")
    public ResponseResult save(@ApiParam @RequestBody PostBasicInformation postBasicInformation) {
        return ResponseResult.success(iPostBasicInformationService.save(postBasicInformation));
    }

    @UserOperation(name = "更新", module = ModuleTypeEnum.POST_BASIC_INFORMATION, type = UserOperationTypeEnum.UPDATE)
    @ApiOperation("更新")
    @PostMapping("/update")
    public ResponseResult update(@ApiParam @RequestBody PostBasicInformation postBasicInformation) {
        return ResponseResult.success(iPostBasicInformationService.updateById(postBasicInformation));
    }

    @UserOperation(name = "删除", module = ModuleTypeEnum.POST_BASIC_INFORMATION, type = UserOperationTypeEnum.DELETE)
    @ApiOperation("删除")
    @PostMapping("/delete")
    public ResponseResult delete(@ApiParam @RequestBody String id) {
        return ResponseResult.success(iPostBasicInformationService.removeById(id));
    }

    @UserOperation(name = "批量删除", module = ModuleTypeEnum.POST_BASIC_INFORMATION, type = UserOperationTypeEnum.DELETE)
    @ApiOperation("批量删除")
    @PostMapping("/deleteByIds")
    public ResponseResult deleteByIds(@ApiParam @RequestBody List<String> ids) {
        return ResponseResult.success(iPostBasicInformationService.removeByIds(ids));
    }
}
