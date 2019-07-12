package com.jg.project.core.mapper;

import com.jg.project.core.po.LoraLog;
import org.springframework.stereotype.Repository;

/**
 * lora 设备mapper
 * @ClassName LoraLogMapper
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-26 18:24
 * @Version 1.0
 **/
@Repository
public interface LoraLogMapper {

    /**
     * 插入
     * @param loraLog
     * @return
     */
    int insert(LoraLog loraLog);
}
