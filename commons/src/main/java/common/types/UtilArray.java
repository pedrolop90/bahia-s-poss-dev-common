package common.types;

import lombok.experimental.UtilityClass;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public final class UtilArray {

    private static final int CANTIDAD = 100000;

    public static List<ByteBuffer> partirArray(byte[] data) {
        List<ByteBuffer> byteBuffers = new ArrayList<>();
        int cantidadBytes = CANTIDAD;
        int cantidadInicial = 0;
        while (cantidadInicial < data.length) {
            if (cantidadInicial + CANTIDAD >= data.length) {
                Integer diferencia = data.length - cantidadInicial;
                cantidadBytes = diferencia;
            }
            byte[] responseData = Arrays
                    .copyOfRange(data,
                            cantidadInicial,
                            cantidadInicial + cantidadBytes
                    );
            byteBuffers.add(ByteBuffer.wrap(responseData));
            cantidadInicial += CANTIDAD;
        }
        return byteBuffers;
    }

    public static List<ByteBuffer> partirArray(byte[] data, int cantidad) {
        List<ByteBuffer> byteBuffers = new ArrayList<>();
        int cantidadBytes = cantidad;
        int cantidadInicial = 0;
        while (cantidadInicial < data.length) {
            if (cantidadInicial + cantidad >= data.length) {
                Integer diferencia = data.length - cantidadInicial;
                cantidadBytes = diferencia;
            }
            byte[] responseData = Arrays
                    .copyOfRange(data,
                            cantidadInicial,
                            cantidadInicial + cantidadBytes
                    );
            byteBuffers.add(ByteBuffer.wrap(responseData));
            cantidadInicial += cantidad;
        }
        return byteBuffers;
    }

}
