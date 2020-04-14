package com.gxy.auth.web.api;

import com.gxy.auth.client.domain.OauthCliendetailsDO;
import com.gxy.auth.client.query.OauthCliendetailsQueryDO;
import com.gxy.auth.service.OauthCliendetailsService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


import   com.gxy.client.base.CommonResult;

import   com.gxy.service.base.BaseControllerImpl;
import   com.gxy.service.base.BaseServiceAO;


/**
 * 客户端表
 *
 * @author guoxiaoyu
 * @email ggg_xiaoyu@163.com
 * @date 2020-04-12 18:42:47
 */
@Api(description = "客户端表-相关接口")
@Slf4j
@RestController
@RequestMapping("/api/oauthcliendetails")
public class OauthCliendetailsApi extends BaseControllerImpl<OauthCliendetailsDO, OauthCliendetailsQueryDO> {

    @Autowired
    private OauthCliendetailsService baseService;

    @Override
    public BaseServiceAO<OauthCliendetailsDO, OauthCliendetailsQueryDO> getService() {
        return baseService;
    }
    /**
     * 通用查询逻辑
     *
     * @param q        查询对象
     * @return
     */
    @ApiOperation(value = "通用查询逻辑", httpMethod = "GET", notes = "通用查询逻辑")
    @RequestMapping("query")
    public CommonResult<List<OauthCliendetailsDO>> select(@ModelAttribute("pojo") OauthCliendetailsQueryDO q) {
        CommonResult<List<OauthCliendetailsDO>> query = getService().query(q);
        CommonResult<Integer> count = getService().count(q);
        query.setTotal(count.getResult());
        return query;
    }

    /**
     * 获取详情
     *
     * @return
     */
    @ApiOperation(value = "获取详情", httpMethod = "GET", notes = "获取详情")
    @RequestMapping("get")
    public CommonResult<OauthCliendetailsDO> detail(@RequestParam("id") Long id) {
        return getService().get(id);
    }

    /**
     * 通用更新逻辑
     *
     * @param t
     * @return
     */
    @ApiOperation(value = "通用更新逻辑", httpMethod = "GET", notes = "通用更新逻辑")
    @RequestMapping("update")
    public CommonResult<Long> update(@ModelAttribute("pojo") OauthCliendetailsDO t) {
        return getService().modify(t);
    }

    /**
     * 通用删除逻辑
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "通用删除逻辑", httpMethod = "GET", notes = "通用删除逻辑")
    @RequestMapping("delete")
    public CommonResult<Long> delete(@RequestParam("id") Long id) {
        return getService().remove(id);
    }

    /**
     * 通用插入逻辑
     *
     * @param t
     * @return
     */
    @ApiOperation(value = "通用插入逻辑", httpMethod = "POST", notes = "通用插入逻辑")
    @RequestMapping("add")
    public CommonResult<OauthCliendetailsDO> insert(@RequestBody OauthCliendetailsDO t) {
        return getService().save(t);
    }
}
