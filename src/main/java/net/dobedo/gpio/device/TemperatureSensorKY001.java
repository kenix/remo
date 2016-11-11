/*
* Created at 21:00 on 11/11/2016
*/
package net.dobedo.gpio.device;

import lombok.extern.slf4j.Slf4j;
import net.dobedo.gpio.service.TemperatureService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author zzhao
 */
@Component
@Slf4j
public class TemperatureSensorKY001 implements TemperatureService {

    public static final Pattern P_FN = Pattern.compile("28-\\w+");

    private Path path;

    public TemperatureSensorKY001() throws IOException {
        this.path = probeDeviceDir();
    }

    private Path probeDeviceDir() throws IOException {
        try (final DirectoryStream<Path> paths = Files.newDirectoryStream(Util.PATH_DEVICES_W1)) {
            for (final Path path : paths) {
                if (Files.isDirectory(path) && P_FN.matcher(path.getFileName().toString()).matches()) {
                    log.info("<probeDeviceDir> {}", path);
                    return path;
                }
            }
        }

        log.warn("<probeDeviceDir> not found");
        return null;
    }

    @Override
    public Optional<Float> get() {
        try {
            if (this.path == null) {
                this.path = probeDeviceDir();
            }
            if (this.path != null) {
                final List<String> lines = Files.readAllLines(this.path.resolve("w1_slave"));
                return parseTemperature(lines);
            }
        } catch (IOException e) {
            log.error("<get> failed retrieve temperature from {}", this.path, e);
        }
        return Optional.empty();
    }

    private static Optional<Float> parseTemperature(List<String> lines) {
        if (lines.get(0).trim().endsWith("YES")) {
            final String tLine = lines.get(1);
            return Optional.of(Float.parseFloat(tLine.substring(tLine.lastIndexOf("t=") + 2)) / 1000f);
        }
        return Optional.empty();
    }
}
