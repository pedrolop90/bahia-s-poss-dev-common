package common.util;

import common.types.ByteNombreDto;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static common.util.UtilFile.createFileTempReturnFile;
import static common.util.UtilFile.deleteFile;
import static common.util.UtilFile.readBytesFile;

@Log4j2
@UtilityClass
public final class UtilZip {

    public static final String EXTENSION = ".zip";

    public static ByteNombreDto comprimirToZip(List<ByteNombreDto> data, String nombre) {
        String nombresConcatenados = data
                .stream()
                .map(byteNombreDto -> byteNombreDto.getNombre() + "." + byteNombreDto.getExtension())
                .collect(Collectors.joining(", "));
        log.info(String.format("creando zip con nombre=%s, los archivos a compromir son=%s.", nombre, nombresConcatenados));

        if (nombre.contains(".")) {
            nombre = nombre.substring(0, nombre.indexOf("."));
        }

        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        File fileTemp = null;
        try {
            fileTemp = createFileTempReturnFile(nombre, EXTENSION);
            fos = new FileOutputStream(fileTemp);
            zipOut = new ZipOutputStream(fos);
            int offset = 0;
            for (ByteNombreDto byteNombreDto : data) {
                byte[] bytesData = byteNombreDto.getData().duplicate().array();

                ZipEntry zipEntry = new ZipEntry(byteNombreDto.getNombre() + "." + byteNombreDto.getExtension());
                zipOut.putNextEntry(zipEntry);
                zipOut.write(bytesData, 0, bytesData.length);
                offset += bytesData.length;
            }
            zipOut.flush();
            zipOut.finish();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (zipOut != null) {
                    zipOut.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        String ruta = fileTemp.getAbsolutePath();

        log.info(String.format("ruta de creacion -> %s.", ruta));

        ByteBuffer buffer = readBytesFile(ruta);
        deleteFile(ruta);
        return ByteNombreDto.builder().nombre(nombre).data(buffer).extension(EXTENSION).build();
    }

}
