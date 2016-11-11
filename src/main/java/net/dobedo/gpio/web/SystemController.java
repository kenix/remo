/*
* Created at 18:33 on 11/11/2016
*/
package net.dobedo.gpio.web;

import com.google.common.collect.Maps;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformManager;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * @author zzhao
 */
@RestController
@RequestMapping("sys")
@Slf4j
public class SystemController {

    @GetMapping("info")
    public Map<String, String> getInfo() throws Exception {
        final Map<String, String> info = Maps.newTreeMap();

        final Platform platform = PlatformManager.getPlatform();
        info.put("platform-name", platform.getLabel());
        info.put("platform-id", platform.getId());

        addInfo(info, SystemInfo.class);
        addInfo(info, NetworkInfo.class);

        return info;
    }

    private void addInfo(Map<String, String> info, Class clazz) throws Exception {
        Arrays.stream(clazz.getMethods())
                .filter(SystemController::isGetter)
                .forEach(m -> {
                    try {
                        info.put(getPropertyName(m), String.valueOf(m.invoke(null)));
                    } catch (Exception e) {
                        info.put(getPropertyName(m), e.getMessage());
                    }
                });
    }

    public static boolean isGetter(Method method) {
        final String name = method.getName();
        return method.getParameterCount() == 0 && (name.startsWith("get") || name.startsWith("is"));
    }

    private String getPropertyName(Method method) {
        final String name = method.getName();
        return name.startsWith("get") ? name.substring(3) : name.substring(2);
    }
}
