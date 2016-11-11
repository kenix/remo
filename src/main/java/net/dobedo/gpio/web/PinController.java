/*
* Created at 22:41 on 08/11/2016
*/
package net.dobedo.gpio.web;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dobedo.gpio.service.GpioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzhao
 */
@RestController
@RequestMapping("gpio/{id}")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PinController {

    private final GpioService gpioService;

    @GetMapping("state")
    public String state(@PathVariable int id) {
        final String pinName = getPinName(id);
        log.info("<state> {}", pinName);

        return getGpioPin(pinName).getState().name();
    }

    @PutMapping("on")
    public void pinOn(@PathVariable int id) {
        final String pinName = getPinName(id);
        log.info("<pinOn> {}", pinName);

        getGpioPin(pinName).high();
    }

    @PutMapping("off")
    public void pinOff(@PathVariable int id) {
        final String pinName = getPinName(id);
        log.info("<pinOff> {}", pinName);

        getGpioPin(pinName).low();
    }

    @PutMapping("toggle")
    public void toggle(@PathVariable int id) {
        final String pinName = getPinName(id);
        log.info("<toggle> {}", pinName);

        getGpioPin(pinName).toggle();
    }

    private String getPinName(@PathVariable int id) {
        return "GPIO " + id;
    }

    private GpioPinDigitalOutput getGpioPin(String pinName) {
        final GpioController ctrl = this.gpioService.getController();
        return (GpioPinDigitalOutput) ctrl.getProvisionedPins()
                .stream()
                .filter(p -> p.getName().equals(pinName))
                .findFirst()
                .orElseGet(() -> {
                    log.info("<getGpioPin> provisioning {}", pinName);
                    final GpioPinDigitalOutput pin = ctrl.provisionDigitalOutputPin(RaspiPin.getPinByName(pinName));
                    pin.setShutdownOptions(true, PinState.LOW);
                    return pin;
                });
    }
}
