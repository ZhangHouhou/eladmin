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
import org.springframework.stereotype.Component;

import java.util.List;

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
            List<WxCpUser> wxCpUserList = wxCpService.getUserService().listByDepartment(492L, false, 0);
            log.info(JsonUtils.toJson(wxCpUserList));

            List<VxEmployee> employeeList = WxCpUserConvertMapper.MAPPER.toList(wxCpUserList);
            vxEmpService.saveAll(employeeList);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        log.info("end SyncVxEmpTask");
    }

}
