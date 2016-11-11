/*
* Created at 21:03 on 11/11/2016
*/
package net.dobedo.gpio.device;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zzhao
 */
public final class Util {

    private Util() {
        throw new AssertionError("not for instantiation or inheritance");
    }

    public static final Path PATH_DEVICES_W1 = Paths.get("/sys/bus/w1/devices");
}
