package common.util;

import com.google.common.io.Files;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

@UtilityClass
public final class UtilFile {

    public static String readStringFile(String filename) {
        String data = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                data += line + "\n";
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static ByteBuffer readBytesFile(String ruta) {
        FileInputStream fileInputStream = null;
        try {
            File archivo = new File(ruta);
            fileInputStream = new FileInputStream(ruta);

            byte[] fileArray = new byte[(int) archivo.length()];
            int res = fileInputStream.read(fileArray);

            while (res != -1) {
                res = fileInputStream.read(fileArray);
            }
            return ByteBuffer.wrap(fileArray);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void deleteFile(String ruta) {
        try {
            File archivo = new File(ruta);
            FileInputStream fileInput = new FileInputStream(archivo);

            fileInput.close();
            archivo.delete();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String createFileTemp(String nombre, String extension, byte[] data) {
        File fileTemp = createFileTempReturnFile(nombre, extension, data);
        if (fileTemp == null) {
            return null;
        }
        return fileTemp.getAbsolutePath();
    }

    public static String createFileTemp(String nombre, String extension) {
        File fileTemp = createFileTempReturnFile(nombre, extension, null);
        if (fileTemp == null) {
            return null;
        }
        return fileTemp.getAbsolutePath();
    }

    public static File createFileTempReturnFile(String nombre, String extension) {
        return createFileTempReturnFile(nombre, extension, null);
    }

    public static File createFileTempReturnFile(String nombre, String extension, byte[] data) {
        File fileTemp = null;
        try {
            fileTemp = File.createTempFile(nombre, extension);
            fileTemp.deleteOnExit();
            if (data != null) {
                Files.write(data, fileTemp);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileTemp;
    }

    public static String createFile(String nombre, String extension, String data) {
        if (nombre != null && !nombre.isEmpty() && extension != null && !extension.isEmpty() && data != null && !data.isEmpty()) {
            BufferedWriter out = null;
            try {
                File tempFile = createFileTempReturnFile(nombre, extension, null);
                out = new BufferedWriter(new FileWriter(tempFile));
                out.write(data);
                out.close();
                return tempFile.getAbsolutePath();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
