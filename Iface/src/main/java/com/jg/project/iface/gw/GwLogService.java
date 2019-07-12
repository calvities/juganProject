package com.jg.project.iface.gw;

import com.alibaba.fastjson.JSON;
import com.jg.project.core.mapper.LoraLogMapper;
import com.jg.project.core.po.LoraLog;
import com.jg.project.service.gw.GwLogServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 网关日志
 * @ClassName GwLogService
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-27 9:54
 * @Version 1.0
 **/
@Service
public class GwLogService implements GwLogServiceI {

    @Autowired
    private LoraLogMapper loraLogMapper;

    /**
     * 插入
     * @param json
     * @return
     */
    @Override
    public int insert(String json) {

        LoraLog loraLog = JSON.parseObject(json,LoraLog.class);
        loraLog.setJson(json);
        return loraLogMapper.insert(loraLog);
    }
}
