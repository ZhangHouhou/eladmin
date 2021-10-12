package me.zhengjie.modules.quartz.task;

import com.vx.base.config.WxCpConfiguration;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
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
public class SyncVxEmpTask {

    public void task() {
        log.info("run SyncVxEmpTask");
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
        log.info("end SyncVxEmpTask");
    }

}
