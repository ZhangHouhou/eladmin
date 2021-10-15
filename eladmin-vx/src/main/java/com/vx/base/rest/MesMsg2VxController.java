package com.vx.base.rest;


import com.vx.base.config.WxCpConfiguration;
import com.vx.emp.service.VxEmployeeService;
import com.vx.emp.service.dto.AndonMsgDto;
import com.vx.emp.service.dto.VxEmployeeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpMessageServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.zhengjie.annotation.AnonymousAccess;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * MES 企业微信提醒接口
 *
 * @author zmh
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/wx/cp/mes")
@Slf4j
public class MesMsg2VxController {

    private final VxEmployeeService empService;

    @PostMapping(value = "/andon", produces = "application/json; charset=UTF-8")
    @AnonymousAccess
    public String andonMsg2Handler(@RequestBody AndonMsgDto requestBody) {
        final WxCpService wxCpService = WxCpConfiguration.getCpService(1000002);
        if (wxCpService == null) {
            throw new IllegalArgumentException(String.format("未找到对应agentId=[%d]的配置，请核实！", 1000002));
        }

        if (requestBody == null) {
            throw new IllegalArgumentException("安灯信息有误!");
        }

        String chargeEmp = requestBody.getChargeEmp();
        if (StringUtils.isBlank(chargeEmp)) {
            throw new IllegalArgumentException("安灯信息有误-无法找到安灯责任人!");
        }

        VxEmployeeDto vxEmployeeDto = empService.findByZnbzUid(chargeEmp);
        if (vxEmployeeDto != null && StringUtils.isNotBlank(vxEmployeeDto.getUserId())) {
            WxCpMessageServiceImpl wxCpMessageService = new WxCpMessageServiceImpl(wxCpService);
            WxCpMessage wxCpMessage = WxCpMessage
                    .TEXT()
                    .agentId(1000002)
                    .toUser(vxEmployeeDto.getUserId())
                    .content(getMsg(requestBody))
                    .build();
            try {
                wxCpMessageService.send(wxCpMessage);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }

        return "success";
    }

    private String getMsg(AndonMsgDto requestBody) {
        return String.format("MES系统安灯异常待处理：批次编码-[%s]_[%s] 类型-[%s] 触发人[%s]- 状态-[%s] 备注-[%s]",
                requestBody.getBatchNum(),
                requestBody.getAndonNum(),
                requestBody.getAndonType(),
                requestBody.getFounder(),
                requestBody.getAndonStatus(),
                StringUtils.isBlank(requestBody.getRemarks()) ? StringUtils.EMPTY : requestBody.getRemarks());
    }

}
