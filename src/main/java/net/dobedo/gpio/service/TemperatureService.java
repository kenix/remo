/*
* Created at 21:29 on 11/11/2016
*/
package net.dobedo.gpio.service;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author zzhao
 */
public interface TemperatureService extends Supplier<Optional<Float>> {
}
