package com.vx.base.rest;


import com.vx.base.config.WxCpConfiguration;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpMessageServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.zhengjie.annotation.AnonymousAccess;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@RestController
@RequestMapping("/wx/cp/demo")
@Slf4j
public class DemoController {

    @PostMapping(produces = "application/json; charset=UTF-8")
    @AnonymousAccess
    public String post(@RequestBody String requestBody) {
        final WxCpService wxCpService = WxCpConfiguration.getCpService(1000002);
        if (wxCpService == null) {
            throw new IllegalArgumentException(String.format("未找到对应agentId=[%d]的配置，请核实！", 1000002));
        }

        WxCpMessageServiceImpl wxCpMessageService = new WxCpMessageServiceImpl(wxCpService);
        WxCpMessage wxCpMessage = WxCpMessage
                .TEXT()
                .agentId(1000002)
                .toUser("ZhangMingHao|ZhangZhenZhen")
                .content("hello 你有安灯异常需要处理")
                .build();
        try {
            wxCpMessageService.send(wxCpMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }


/*        try {
            List<WxCpDepart> departList = wxCpService.getDepartmentService().list(1L);
            log.info(JsonUtils.toJson(departList));
            List<WxCpUser> wxCpUserList = wxCpService.getUserService().listByDepartment(492L, false, 0);
            log.info(JsonUtils.toJson(wxCpUserList));

        } catch (WxErrorException e) {
            e.printStackTrace();
        }*/
        return "out";
    }

}
