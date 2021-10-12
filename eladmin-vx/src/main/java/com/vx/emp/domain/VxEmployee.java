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
package com.vx.emp.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author zmh
* @date 2021-10-12
**/
@Entity
@Data
@Table(name="vx_employee")
public class VxEmployee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vx_emp_id")
    @ApiModelProperty(value = "vxEmpId")
    private Long vxEmpId;

    @Column(name = "user_id",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "企业微信中的唯一ID，不区分大小写长度为1~64个字节(由数字、字母和“_-@.”组成，首字符必须是数字或字母)")
    private String userId;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "name")
    private String name;

    @Column(name = "mobile",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "手机号-唯一 不为空")
    private String mobile;

    @Column(name = "znbz_uid",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "智能包装系统中的工号 唯一")
    private String znbzUid;

    @Column(name = "alias")
    @ApiModelProperty(value = "别名")
    private String alias;

    @Column(name = "position")
    @ApiModelProperty(value = "职务")
    private String position;

    @Column(name = "gender")
    @ApiModelProperty(value = "1-表示男性，2-表示女性")
    private String gender;

    @Column(name = "email")
    @ApiModelProperty(value = "长度6~64个字节,企业内必须唯一")
    private String email;

    @Column(name = "telephone")
    @ApiModelProperty(value = "座机--由纯数字、“-”、“+”或“,”组成")
    private String telephone;

    @Column(name = "address")
    @ApiModelProperty(value = "address")
    private String address;

    @Column(name = "main_department")
    @ApiModelProperty(value = "主部门（唯一） 所有部门单独存")
    private String mainDepartment;

    @Column(name = "avatar_mediaId")
    @ApiModelProperty(value = "成员头像的media id，通过素材管理接口上传图片获得的media id")
    private String avatarMediaid;

    @Column(name = "to_invite")
    @ApiModelProperty(value = "是否邀请该成员使用企业微信（将通过微信服务通知或短信或邮件下发邀请，每天自动下发一次，最多持续3个工作日），默认值为true。")
    private Integer toInvite;

    @Column(name = "external_position")
    @ApiModelProperty(value = "对外职位")
    private String externalPosition;

    @Column(name = "enable")
    @ApiModelProperty(value = "启用/禁用成员 1-表示启用成员，0-表示禁用成员")
    private Integer enable;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(VxEmployee source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}