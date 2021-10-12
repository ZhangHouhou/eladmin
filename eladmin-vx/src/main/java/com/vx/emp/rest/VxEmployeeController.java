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
package com.vx.emp.rest;

import com.vx.emp.domain.VxEmployee;
import com.vx.emp.service.dto.VxEmployeeQueryCriteria;
import me.zhengjie.annotation.Log;
import com.vx.emp.service.VxEmployeeService;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author zmh
* @date 2021-10-12
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "vxemp管理")
@RequestMapping("/api/vxEmployee")
public class VxEmployeeController {

    private final VxEmployeeService vxEmployeeService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('vxEmployee:list')")
    public void download(HttpServletResponse response, VxEmployeeQueryCriteria criteria) throws IOException {
        vxEmployeeService.download(vxEmployeeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询vxemp")
    @ApiOperation("查询vxemp")
    @PreAuthorize("@el.check('vxEmployee:list')")
    public ResponseEntity<Object> query(VxEmployeeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(vxEmployeeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增vxemp")
    @ApiOperation("新增vxemp")
    @PreAuthorize("@el.check('vxEmployee:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody VxEmployee resources){
        return new ResponseEntity<>(vxEmployeeService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改vxemp")
    @ApiOperation("修改vxemp")
    @PreAuthorize("@el.check('vxEmployee:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody VxEmployee resources){
        vxEmployeeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除vxemp")
    @ApiOperation("删除vxemp")
    @PreAuthorize("@el.check('vxEmployee:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        vxEmployeeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}