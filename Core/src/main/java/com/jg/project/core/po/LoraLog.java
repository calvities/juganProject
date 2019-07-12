package com.jg.project.core.po;

import lombok.Data;

/** 这个是 数据库实体
 * @ClassName LoraLog
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-26 18:21
 * @Version 1.0
 **/
@Data
public class LoraLog {

    private Integer id;

    /**
     * 网关id
     */
    private String gwId;

    /**
     * 时间
     */
    private String time;

    /**
     * 移动电话卡id
     */
    private String iccid;

    /**
     * 消息体
     */
    private String json;
}
