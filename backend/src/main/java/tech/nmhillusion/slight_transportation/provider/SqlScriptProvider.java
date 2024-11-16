package tech.nmhillusion.slight_transportation.provider;

import org.springframework.stereotype.Component;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.util.IOStreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */

@Component
public class SqlScriptProvider {

    public String getSqlScript(String sqlResourcePath) throws IOException {
        try (final InputStream sqlStream = getClass().getClassLoader().getResourceAsStream(
                Paths.get("sql-scripts", sqlResourcePath).toString()
        )) {
            if (null == sqlStream) {
                throw new IOException("SQL Resource not found: " + sqlResourcePath);
            }

            final String sqlLoadedScript = IOStreamUtil.convertInputStreamToString(sqlStream);

            LogHelper.getLogger(this).info("Loaded SQL script [{}]: {}", sqlResourcePath, sqlLoadedScript);

            return sqlLoadedScript;
        }
    }

}
