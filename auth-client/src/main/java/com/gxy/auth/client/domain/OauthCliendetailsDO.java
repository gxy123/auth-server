package com.gxy.auth.client.domain;

import com.gxy.client.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 客户端表
 *
 * @author guoxiaoyu
 * @email ggg_xiaoyu@163.com
 * @date 2020-04-12 18:42:47
 */
@Data
@ApiModel
public class OauthCliendetailsDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("客户端标识 ID")
    private String clientId;
    @ApiModelProperty("客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,")
    private String resourceIds;
    @ApiModelProperty("客户端安全码")
    private String clientSecret;
    @ApiModelProperty("客户端访问范围，默认为空则拥有全部范围")
    private String scope;
    @ApiModelProperty("")
    private String authorizedGrantTypes;
    @ApiModelProperty("客户端的重定向URI,可为空, 当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致.")
    private String webServerRedirectUri;
    @ApiModelProperty("客户端可使用的权限")
    private String authorities;
    @ApiModelProperty("设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时).")
    private Integer accessTokenValidity;
    @ApiModelProperty("设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天).")
    private Integer refreshTokenValidity;
    @ApiModelProperty("")
    private String additionalInformation;
    @ApiModelProperty("设置用户是否自动Approval操作, 默认值为 'false', 可选值包括 'true','false', 'read','write'.")
    private String autoapprove;
}
