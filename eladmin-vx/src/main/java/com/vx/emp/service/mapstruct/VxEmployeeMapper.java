package com.vx.emp.service.mapstruct;

import com.vx.emp.domain.VxEmployee;
import com.vx.emp.service.dto.VxEmployeeDto;
import me.zhengjie.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author zmh
 * @date 2021-10-12
 **/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VxEmployeeMapper extends BaseMapper<VxEmployeeDto, VxEmployee> {

}