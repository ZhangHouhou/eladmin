package me.zhengjie.modules.quartz.task;

import com.vx.base.config.WxCpConfiguration;
import com.vx.emp.domain.VxEmployee;
import com.vx.emp.service.VxEmployeeService;
import com.vx.emp.service.mapstruct.WxCpUserConvertMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.zhengjie.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务获取 企业微信的员工信息
 *
 * @author zmh
 * @date 2021-10-12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SyncVxEmpTask {

    private final VxEmployeeService vxEmpService;

    public void task() {
        log.info("run SyncVxEmpTask");
        final WxCpService wxCpService = WxCpConfiguration.getCpService(1000002);
        if (wxCpService == null) {
            throw new IllegalArgumentException(String.format("未找到对应agentId=[%d]的配置，请核实！", 1000002));
        }

        try {
//            List<WxCpDepart> departList = wxCpService.getDepartmentService().list(1L);
//            log.info("sync department success");
            List<WxCpUser> wxCpUserList = wxCpService.getUserService().listByDepartment(1L, true, 0);
            log.info(JsonUtils.toJson(wxCpUserList));


            List<VxEmployee> employeeList = WxCpUserConvertMapper.MAPPER.toList(wxCpUserList);

            for (WxCpUser wxCpUser : wxCpUserList) {
                for (VxEmployee employee : employeeList) {
                    if (StringUtils.equals(employee.getUserId(), wxCpUser.getUserId())) {
                        List<WxCpUser.Attr> attrList = wxCpUser.getExtAttrs();
                        if (attrList != null && attrList.size() > 0) {
                            WxCpUser.Attr attr = attrList.get(0);
                            employee.setZnbzUid(attr.getTextValue());
                        } else {
                            log.info("没有工号 emp:{}", wxCpUser.getUserId());
                        }

                        if (StringUtils.isBlank(wxCpUser.getMobile())) {
                            log.info("没有手机号 emp:{}", wxCpUser.getUserId());
                        }

                    }
                }
            }
            List<VxEmployee> collect = employeeList.stream().filter(vxEmployee -> StringUtils.isNotBlank(vxEmployee.getMobile())).collect(Collectors.toList());
            List<VxEmployee> collect2 = collect.stream().filter(vxEmployee -> StringUtils.isNotBlank(vxEmployee.getZnbzUid())).collect(Collectors.toList());
            vxEmpService.saveAll(collect2);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        log.info("end SyncVxEmpTask");
    }

}
