package com.gxy.auth.service.impl;

import com.gxy.auth.service.TokenRedisService;
import com.gxy.auth.wrapper.RedisWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenRedisServiceImpl implements TokenRedisService {
    @Autowired
    private RedisWrapper redisWrapper;

    @Override
    public void addTokenIdToRedis(String mapKey, String key, String value) {
        redisWrapper.putMapValue(mapKey, key, value);
    }

    @Override
    public void removeTTokenIdToRedis(String mapKey, String valueKey) {
        redisWrapper.delMapValueByKey(mapKey, valueKey);
    }

    @Override
    public Boolean isExist(String mapKey, String valueKey) {
        Object mapValueByKey = redisWrapper.getMapValueByKey(mapKey, valueKey);
        if (mapValueByKey == null) {
            return false;
        }
        return true;
    }
}
