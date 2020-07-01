package common.util;


import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@UtilityClass
public final class UtilInputStream {

    public static String getString(InputStream stream) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
        }

        return inputStringBuilder.toString();
    }

    public static String getStringSilent(InputStream stream) {
        try {
            return getString(stream);
        }
        catch (Exception var2) {
            return null;
        }
    }
}
