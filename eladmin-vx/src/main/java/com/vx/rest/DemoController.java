package com.vx.rest;


import com.vx.config.WxCpConfiguration;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpTpDepart;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.tp.service.WxCpTpDepartmentService;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.utils.JsonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

        try {
            List<WxCpDepart> departList = wxCpService.getDepartmentService().list(1L);
            log.info(JsonUtils.toJson(departList));
            List<WxCpUser> wxCpUserList = wxCpService.getUserService().listByDepartment(492L, false, 0);
            log.info(JsonUtils.toJson(wxCpUserList));

        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "out";
    }

}
