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
package com.vx.emp.service.impl;

import com.vx.emp.service.mapstruct.VxEmployeeMapper;
import com.vx.emp.domain.VxEmployee;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.vx.emp.repository.VxEmployeeRepository;
import com.vx.emp.service.VxEmployeeService;
import com.vx.emp.service.dto.VxEmployeeDto;
import com.vx.emp.service.dto.VxEmployeeQueryCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author zmh
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2021-10-12
 **/
@Service
@RequiredArgsConstructor
public class VxEmployeeServiceImpl implements VxEmployeeService {

    private final VxEmployeeRepository vxEmployeeRepository;
    private final VxEmployeeMapper vxEmployeeMapper;

    @Override
    public Map<String, Object> queryAll(VxEmployeeQueryCriteria criteria, Pageable pageable) {
        Page<VxEmployee> page = vxEmployeeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(vxEmployeeMapper::toDto));
    }

    @Override
    public List<VxEmployeeDto> queryAll(VxEmployeeQueryCriteria criteria) {
        return vxEmployeeMapper.toDto(vxEmployeeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public VxEmployeeDto findById(Long vxEmpId) {
        VxEmployee vxEmployee = vxEmployeeRepository.findById(vxEmpId).orElseGet(VxEmployee::new);
        ValidationUtil.isNull(vxEmployee.getVxEmpId(), "VxEmployee", "vxEmpId", vxEmpId);
        return vxEmployeeMapper.toDto(vxEmployee);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VxEmployeeDto create(VxEmployee resources) {
        return vxEmployeeMapper.toDto(vxEmployeeRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(VxEmployee resources) {
        VxEmployee vxEmployee = vxEmployeeRepository.findById(resources.getVxEmpId()).orElseGet(VxEmployee::new);
        ValidationUtil.isNull(vxEmployee.getVxEmpId(), "VxEmployee", "id", resources.getVxEmpId());
        vxEmployee.copy(resources);
        vxEmployeeRepository.save(vxEmployee);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long vxEmpId : ids) {
            vxEmployeeRepository.deleteById(vxEmpId);
        }
    }

    @Override
    public void download(List<VxEmployeeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (VxEmployeeDto vxEmployee : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("企业微信中的唯一ID，不区分大小写长度为1~64个字节(由数字、字母和“_-@.”组成，首字符必须是数字或字母)", vxEmployee.getUserId());
            map.put(" name", vxEmployee.getName());
            map.put("手机号-唯一 不为空", vxEmployee.getMobile());
            map.put("智能包装系统中的工号 唯一", vxEmployee.getZnbzUid());
            map.put("别名", vxEmployee.getAlias());
            map.put("职务", vxEmployee.getPosition());
            map.put("1-表示男性，2-表示女性", vxEmployee.getGender());
            map.put("长度6~64个字节,企业内必须唯一", vxEmployee.getEmail());
            map.put("座机--由纯数字、“-”、“+”或“,”组成", vxEmployee.getTelephone());
            map.put(" address", vxEmployee.getAddress());
            map.put("主部门（唯一） 所有部门单独存", vxEmployee.getMainDepartment());
            map.put("成员头像的media id，通过素材管理接口上传图片获得的media id", vxEmployee.getAvatarMediaId());
            map.put("是否邀请该成员使用企业微信（将通过微信服务通知或短信或邮件下发邀请，每天自动下发一次，最多持续3个工作日），默认值为true。", vxEmployee.getToInvite());
            map.put("对外职位", vxEmployee.getExternalPosition());
            map.put("启用/禁用成员 1-表示启用成员，0-表示禁用成员", vxEmployee.getEnable());
            map.put("创建者", vxEmployee.getCreateBy());
            map.put("更新者", vxEmployee.getUpdateBy());
            map.put("创建日期", vxEmployee.getCreateTime());
            map.put("更新时间", vxEmployee.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<VxEmployee> resources) {
        vxEmployeeRepository.saveAll(resources);
    }

    @Override
    public VxEmployeeDto findByZnbzUid(String znbzUid) {
        return vxEmployeeMapper.toDto(vxEmployeeRepository.findByZnbzUid(znbzUid));
    }


}