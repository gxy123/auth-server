package com.gxy.auth.dao;

import com.gxy.auth.client.domain.OauthCliendetailsDO;
import com.gxy.auth.client.query.OauthCliendetailsQueryDO;
import com.gxy.service.base.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户端表
 *
 * @author guoxiaoyu
 * @email ggg_xiaoyu@163.com
 * @date 2020-04-12 18:42:47
 */
@Mapper
public interface OauthCliendetailsDao extends BaseDAO<OauthCliendetailsDO, OauthCliendetailsQueryDO> {

}
