package com.gxy.auth.service.impl;


import com.gxy.auth.client.domain.OauthCliendetailsDO;
import com.gxy.auth.client.query.OauthCliendetailsQueryDO;
import com.gxy.auth.dao.OauthCliendetailsDao;
import com.gxy.auth.service.OauthCliendetailsService;
import com.gxy.service.base.BaseDAO;
import com.gxy.service.base.BaseServiceAOImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OauthCliendetailsServiceImpl extends BaseServiceAOImpl<OauthCliendetailsDO, OauthCliendetailsQueryDO> implements OauthCliendetailsService  {

    @Resource
    private OauthCliendetailsDao baseDao;


    @Override
    public BaseDAO<OauthCliendetailsDO, OauthCliendetailsQueryDO> getDAO() {
        return baseDao;
    }


}
