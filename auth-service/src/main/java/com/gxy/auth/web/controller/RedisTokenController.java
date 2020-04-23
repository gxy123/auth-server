package com.gxy.auth.web.controller;

import com.gxy.auth.client.domain.OauthCodeDO;
import com.gxy.auth.service.TokenRedisService;
import com.gxy.client.base.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/redis")
@Slf4j
public class RedisTokenController {
    @Autowired
    private TokenRedisService tokenRedisService;

    @ApiOperation(value = "获取详情", httpMethod = "GET", notes = "获取详情")
    @RequestMapping("get/{mapKey}/{valueKey}")
    public CommonResult<Boolean> detail(@PathVariable("mapKey") String mapKey, @PathVariable("valueKey") String valueKey) {
        Boolean exist = tokenRedisService.isExist(mapKey, valueKey);
        return CommonResult.successReturn(exist);
    }
}
