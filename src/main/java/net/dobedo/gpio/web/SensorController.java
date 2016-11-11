/*
* Created at 21:28 on 11/11/2016
*/
package net.dobedo.gpio.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dobedo.gpio.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author zzhao
 */
@RestController
@RequestMapping("sensor")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SensorController {

    private final TemperatureService temperatureService;

    @GetMapping("temperature")
    public String getTemperature() {
        final Optional<Float> result = this.temperatureService.get();
        return result.isPresent() ? String.valueOf(result.get()) : "Unavailable";
    }
}
