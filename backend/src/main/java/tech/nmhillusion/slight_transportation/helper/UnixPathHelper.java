package tech.nmhillusion.slight_transportation.helper;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
@Component
public class UnixPathHelper {

    public String getUnixPath(String path) {
        return path.replace("\\", "/");
    }

    public String joinPaths(String... paths) {
        return Arrays.stream(paths)
                .map(originPath -> {
                    String p_ = getUnixPath(originPath);

                    while (p_.startsWith("/") && p_.length() > 1) {
                        p_ = p_.substring(1);
                    }

                    while (p_.endsWith("/") && p_.length() > 1) {
                        p_ = p_.substring(0, p_.length() - 1);
                    }

                    return p_;
                })
                .collect(Collectors.joining("/"));
    }

}
