package com.gxy.auth.service;

public interface TokenRedisService {
    void addTokenIdToRedis(String mapKey, String key, String value);
    void removeTTokenIdToRedis(String mapKey, String valueKey);
    Boolean isExist(String mapKey, String valueKey);


}
