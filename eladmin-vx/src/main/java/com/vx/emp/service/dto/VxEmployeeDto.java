/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.vx.emp.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author zmh
 * @date 2021-10-12
 **/
@Data
public class VxEmployeeDto implements Serializable {

    private Long vxEmpId;

    /**
     * 企业微信中的唯一ID，不区分大小写长度为1~64个字节(由数字、字母和“_-@.”组成，首字符必须是数字或字母)
     */
    private String userId;

    private String name;

    /**
     * 手机号-唯一 不为空
     */
    private String mobile;

    /**
     * 智能包装系统中的工号 唯一
     */
    private String znbzUid;

    /**
     * 别名
     */
    private String alias;

    /**
     * 职务
     */
    private String position;

    /**
     * 性别
     */
    private String gender;

    /**
     * 长度6~64个字节,企业内必须唯一
     */
    private String email;

    /**
     * 座机--由纯数字、“-”、“+”或“,”组成
     */
    private String telephone;

    private String address;

    /**
     * 主部门（唯一） 所有部门单独存
     */
    private String mainDepartment;

    /**
     * 成员头像的media id，通过素材管理接口上传图片获得的media id
     */
    private String avatarMediaId;

    /**
     * 是否邀请该成员使用企业微信（将通过微信服务通知或短信或邮件下发邀请，每天自动下发一次，最多持续3个工作日），默认值为true。
     */
    private Boolean toInvite;

    /**
     * 对外职位
     */
    private String externalPosition;

    /**
     * 启用/禁用成员 1-表示启用成员，0-表示禁用成员
     */
    private Integer enable;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 创建日期
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}