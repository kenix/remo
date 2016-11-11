package net.dobedo.gpio.device

import spock.lang.Specification

import static net.dobedo.gpio.device.TemperatureSensorKY001.P_FN

/**
 * @author zzhao
 */
class TemperatureSensorKY001Spec extends Specification {

    def 'device dir pattern should work'() {
        expect:
        P_FN.matcher('28-03163571a3ff').matches()
        !P_FN.matcher('00-03163571a3ff').matches()
    }

    def 'should be able to parse temperature'() {
        when:
        def lines = [
                '3c 01 4b 46 7f ff 0c 10 36 : crc=36 YES',
                '3c 01 4b 46 7f ff 0c 10 36 t=19750'
        ]
        def temperature = TemperatureSensorKY001.parseTemperature(lines)
        then:
        temperature.isPresent()
        temperature.get() == 19.75f
    }
}
