package ${package.Controller};

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
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.comment!}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    ${table.serviceName} ${table.serviceName?uncap_first};

    @UserOperation(name = "查询${table.comment!}", module = ModuleTypeEnum.${entity?upper_case}, type = UserOperationTypeEnum.QUERY)
    @ApiOperation("${table.comment!}数据列表")
    @GetMapping("/listJson")
    public ResponseResult listJson() {
        return ResponseResult.success(${table.serviceName?uncap_first}.list(null));
    }

    @UserOperation(name = "查询${table.comment!}", module = ModuleTypeEnum.${entity?upper_case}, type = UserOperationTypeEnum.QUERY)
    @ApiOperation("${table.comment!}分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", paramType = "query", dataTypeClass = Long.class, defaultValue = "1", value = "当前页"),
            @ApiImplicitParam(name = "pageSize", paramType = "query", dataTypeClass = Long.class, defaultValue = "10", value = "分页尺寸") })
    @GetMapping("/pageJson")
    public ResponseResult pageJson(@ApiIgnore Page<${entity}> page) {
         return ResponseResult.success(${table.serviceName?uncap_first}.page(page, null));
    }

    @UserOperation(name = "查询${table.comment!}", module = ModuleTypeEnum.${entity?upper_case}, type = UserOperationTypeEnum.QUERY)
    @GetMapping("/getOne")
    @ApiOperation("根据id获取${table.comment!}")
    @ApiImplicitParam(name = "id", paramType = "query", dataTypeClass = String.class, defaultValue = "1", value = "${table.comment!}id")
    public ResponseResult getOne(String id) {
        return ResponseResult.success(${table.serviceName?uncap_first}.getById(id));
    }

    @UserOperation(name = "新增${table.comment!}", module = ModuleTypeEnum.${entity?upper_case}, type = UserOperationTypeEnum.INSERT)
    @ApiOperation("新增${table.comment!}")
    @PostMapping("/save")
    public ResponseResult save(@ApiParam @RequestBody ${entity} ${entity?uncap_first}) {
        return ResponseResult.success(${table.serviceName?uncap_first}.save(${entity?uncap_first}));
    }

    @UserOperation(name = "更新${table.comment!}", module = ModuleTypeEnum.${entity?upper_case}, type = UserOperationTypeEnum.UPDATE)
    @ApiOperation("更新${table.comment!}")
    @PostMapping("/update")
    public ResponseResult update(@ApiParam @RequestBody ${entity} ${entity?uncap_first}) {
        return ResponseResult.success(${table.serviceName?uncap_first}.updateById(${entity?uncap_first}));
    }

    @UserOperation(name = "删除${table.comment!}", module = ModuleTypeEnum.${entity?upper_case}, type = UserOperationTypeEnum.DELETE)
    @ApiOperation("删除${table.comment!}")
    @PostMapping("/delete")
    public ResponseResult delete(@ApiParam @RequestBody String id) {
        return ResponseResult.success(${table.serviceName?uncap_first}.removeById(id));
    }

    @UserOperation(name = "批量删除${table.comment!}", module = ModuleTypeEnum.${entity?upper_case}, type = UserOperationTypeEnum.DELETE)
    @ApiOperation("批量删除${table.comment!}")
    @PostMapping("/deleteByIds")
    public ResponseResult deleteByIds(@ApiParam @RequestBody List<String> ids) {
        return ResponseResult.success(${table.serviceName?uncap_first}.removeByIds(ids));
    }
}
</#if>
