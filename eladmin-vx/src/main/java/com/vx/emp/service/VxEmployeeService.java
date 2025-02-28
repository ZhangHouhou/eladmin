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
package com.vx.emp.service;

import com.vx.emp.domain.VxEmployee;
import com.vx.emp.service.dto.VxEmployeeDto;
import com.vx.emp.service.dto.VxEmployeeQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zmh
 * @website https://el-admin.vip
 * @description 服务接口
 * @date 2021-10-12
 **/
public interface VxEmployeeService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(VxEmployeeQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<VxEmployeeDto>
     */
    List<VxEmployeeDto> queryAll(VxEmployeeQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param vxEmpId ID
     * @return VxEmployeeDto
     */
    VxEmployeeDto findById(Long vxEmpId);

    /**
     * 创建
     *
     * @param resources /
     * @return VxEmployeeDto
     */
    VxEmployeeDto create(VxEmployee resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(VxEmployee resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<VxEmployeeDto> all, HttpServletResponse response) throws IOException;

    /**
     * 保存list
     *
     * @param resources 员工list
     */
    @Transactional(rollbackFor = Exception.class)
    void saveAll(List<VxEmployee> resources);

    /**
     * 通过智能包装工号 查询企业微信员工信息
     *
     * @param znbzUid 智能包装工号
     * @return 企业微信员工对象
     */
    VxEmployeeDto findByZnbzUid(String znbzUid);
}