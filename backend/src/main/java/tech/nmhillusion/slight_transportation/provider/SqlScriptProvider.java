package tech.nmhillusion.slight_transportation.provider;

import org.springframework.stereotype.Component;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.util.IOStreamUtil;
import tech.nmhillusion.slight_transportation.helper.UnixPathHelper;

import java.io.IOException;
import java.io.InputStream;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */

@Component
public class SqlScriptProvider {
    private final UnixPathHelper unixPathHelper;

    public SqlScriptProvider(UnixPathHelper unixPathHelper) {
        this.unixPathHelper = unixPathHelper;
    }


    public String getSqlScript(String sqlResourcePath) throws IOException {
        try (final InputStream sqlStream = getClass().getClassLoader().getResourceAsStream(
                unixPathHelper.joinPaths("sql-scripts", sqlResourcePath)
        )) {
            if (null == sqlStream) {
                throw new IOException("SQL Resource not found: " + sqlResourcePath);
            }

            final String sqlLoadedScript = IOStreamUtil.convertInputStreamToString(sqlStream);

            LogHelper.getLogger(this).info("Loaded SQL script [{}]: {}",
                    sqlResourcePath
                    , sqlLoadedScript
            );

            return sqlLoadedScript;
        }
    }

}
