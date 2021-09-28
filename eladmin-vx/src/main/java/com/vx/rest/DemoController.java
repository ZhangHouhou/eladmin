package com.vx.rest;


import com.vx.config.WxCpConfiguration;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import me.zhengjie.annotation.AnonymousAccess;
import org.springframework.web.bind.annotation.*;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@RestController
@RequestMapping("/wx/cp/demo")
@Slf4j
public class DemoController {

    @GetMapping(produces = "text/plain;charset=utf-8")
    @AnonymousAccess
    public String helloVx() {

        return "非法请求";
    }

    @PostMapping(produces = "application/json; charset=UTF-8")
    @AnonymousAccess
    public String post(@RequestBody String requestBody) {
        final WxCpService wxCpService = WxCpConfiguration.getCpService(1000002);
        if (wxCpService == null) {
            throw new IllegalArgumentException(String.format("未找到对应agentId=[%d]的配置，请核实！", 1000002));
        }

        try {
            String accessToken = wxCpService.getAccessToken();
            System.out.println(accessToken);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "out";
    }

    private WxCpXmlOutMessage route(Integer agentId, WxCpXmlMessage message) {
        try {
            return WxCpConfiguration.getRouters().get(agentId).route(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }


}
