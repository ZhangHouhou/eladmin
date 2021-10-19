package com.vx.emp.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zmh
 */
@Data
public class AndonMsgDto implements Serializable {

    private static final long serialVersionUID = -2389761972057454147L;
    private String batchNum;
    private String andonNum;
    private String andonStatus;
    private String andonType;
    private String andonLevel;
    private String remarks;
    private String founder;
    /**
     * 安灯 处理人
     */
    private String chargeEmp;
    private String workCellName;
}
