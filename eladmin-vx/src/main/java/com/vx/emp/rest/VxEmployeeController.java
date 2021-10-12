package com.vx.emp.rest;

import com.vx.emp.domain.VxEmployee;
import com.vx.emp.service.VxEmployeeService;
import com.vx.emp.service.dto.VxEmployeeQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zmh
 * @website https://el-admin.vip
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
    public ResponseEntity<Object> query(VxEmployeeQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(vxEmployeeService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增vxemp")
    @ApiOperation("新增vxemp")
    @PreAuthorize("@el.check('vxEmployee:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody VxEmployee resources) {
        return new ResponseEntity<>(vxEmployeeService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改vxemp")
    @ApiOperation("修改vxemp")
    @PreAuthorize("@el.check('vxEmployee:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody VxEmployee resources) {
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