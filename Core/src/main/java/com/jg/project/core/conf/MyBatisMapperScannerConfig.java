package com.jg.project.core.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

/**
 * @ClassName MyBatisMapperScannerConfig
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-27 9:44
 * @Version 1.0
 **/
@SpringBootConfiguration
@AutoConfigureAfter(MyBatisConfig.class)
@MapperScan(basePackages = {"com.jg.project.core.mapper"},sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisMapperScannerConfig {
}
