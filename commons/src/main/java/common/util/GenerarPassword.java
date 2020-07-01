package common.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public final class GenerarPassword {

    public static String generarPassword() {
        return UUID.randomUUID().toString();
    }

}
