/*
* Created at 22:42 on 08/11/2016
*/
package net.dobedo.gpio.service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zzhao
 */
@Service
@Slf4j
public class GpioService {

    @Getter
    private GpioController controller;

    @PostConstruct
    private void init() {
        log.info("<init> init GPIO controller");
        this.controller = GpioFactory.getInstance();
    }

    @PreDestroy
    private void done() {
        log.info("<done> shutdown GPIO controller");
        this.controller.shutdown();
    }
}
