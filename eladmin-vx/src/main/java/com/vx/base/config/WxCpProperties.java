package com.vx.base.config;


import lombok.Getter;
import lombok.Setter;
import me.zhengjie.utils.JsonUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "wechat.cp")
public class WxCpProperties {
    /**
     * 设置企业微信的corpId
     */
    private String corpId;

    private List<AppConfig> appConfigs;

    @Getter
    @Setter
    public static class AppConfig {
        /**
         * 设置企业微信应用的AgentId
         */
        private Integer agentId;

        /**
         * 设置企业微信应用的Secret
         */
        private String secret;

        /**
         * 设置企业微信应用的token
         */
        private String token;

        /**
         * 设置企业微信应用的EncodingAESKey
         */
        private String aesKey;

    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
