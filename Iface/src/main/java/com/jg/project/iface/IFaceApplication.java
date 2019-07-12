package com.jg.project.iface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Administrator
 */
@SpringBootApplication
@ComponentScan({"com.jg"})
public class IFaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IFaceApplication.class, args);
    }

}
