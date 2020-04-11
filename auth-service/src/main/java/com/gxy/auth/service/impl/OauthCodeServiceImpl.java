package com.gxy.auth.service.impl;

import com.gxy.auth.client.domain.OauthCodeDO;
import com.gxy.auth.client.query.OauthCodeQueryDO;
import com.gxy.auth.dao.OauthCodeDao;
import com.gxy.auth.service.OauthCodeService;
import com.gxy.service.base.BaseDAO;
import com.gxy.service.base.BaseServiceAOImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OauthCodeServiceImpl extends BaseServiceAOImpl<OauthCodeDO, OauthCodeQueryDO> implements OauthCodeService {

    @Resource
    private OauthCodeDao baseDao;


    @Override
    public BaseDAO<OauthCodeDO, OauthCodeQueryDO> getDAO() {
        return baseDao;
    }


}
