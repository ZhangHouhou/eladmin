package com.vx.emp.service.mapstruct;

import com.vx.emp.domain.VxEmployee;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 微信用户信息 与 存储对象转换类
 *
 * @author zmh
 */
@Mapper
public interface WxCpUserConvertMapper {
    WxCpUserConvertMapper MAPPER = Mappers.getMapper(WxCpUserConvertMapper.class);

    /**
     * 转换对象
     *
     * @param source 源对象
     * @return 转换后的存储对象
     */
    @Mapping(source = "source.userId", target = "userId")
    VxEmployee toDto(WxCpUser source);

    /**
     * 列表转换
     *
     * @param sourceList 源对象
     * @return 转换后的存储对象
     */
    List<VxEmployee> toList(List<WxCpUser> sourceList);

}
