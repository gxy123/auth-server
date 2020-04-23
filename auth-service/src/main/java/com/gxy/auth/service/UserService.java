package com.gxy.auth.service;

import com.gxy.client.base.CommonResult;
import com.gxy.user.client.domain.SyUserDO;
import com.gxy.user.client.vo.SyUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Service
@FeignClient(name = "user-api")
public interface UserService {
    @RequestMapping(method = RequestMethod.GET, value = "user/getUser/{username}")
    CommonResult<SyUserDO> getUserByUserName(@PathVariable("username") String username);
    @RequestMapping(method = RequestMethod.GET, value = "user/getUserVO/{username}")
    CommonResult<SyUserVO> getUserVOByUserName(@PathVariable("username") String username);
}
