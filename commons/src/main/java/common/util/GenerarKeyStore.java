package common.util;

import common.types.ByteNombreDto;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

@Log4j2
@UtilityClass
public final class GenerarKeyStore {

    public static final String TYPE = "jceks";
    public static final String SECRET_KEY = "RSA";
    public static final String EXTENSION = ".pfx";

    public static ByteBuffer generarKeyStore(String passwordEntry, List<ByteNombreDto> data) {
        return generarKeyStore(passwordEntry, data, TipoKeyStore.JCEKS);
    }

    public static ByteBuffer generarKeyStore(String passwordEntry, List<ByteNombreDto> data, TipoKeyStore tipo) {
        log.info(String.format("el password es %s", passwordEntry));
        try {
            KeyStore ks = KeyStore.getInstance(tipo.getTipo());

            char[] passArray = passwordEntry.toCharArray();
            try {
                ks.load(null, passArray);

                for (ByteNombreDto bytes : data) {

                    InputStream inputStream = new ByteArrayInputStream(bytes.getData().array());
                    try {
                        CertificateFactory cf = CertificateFactory.getInstance("X.509");
                        X509Certificate certificate = (X509Certificate) cf.generateCertificate(inputStream);
                        ks.setKeyEntry(bytes.getNombre(), new byte[]{}, new Certificate[]{certificate});

                    }
                    catch (Exception e) {

                    }
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ks.store(outputStream, passArray);
                ByteBuffer keyStoreData = ByteBuffer.wrap(outputStream.toByteArray());
                String temp = UtilFile.createFileTemp("test", EXTENSION, outputStream.toByteArray());
                log.info(String.format("la ubicacion del archivo temporal es = %s", temp));
                return keyStoreData;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (CertificateException e) {
                e.printStackTrace();
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ByteBuffer updateArchive(String passwordEntry, List<ByteNombreDto> data, ByteBuffer dataKeyStore) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(dataKeyStore.array());
            KeyStore ks = KeyStore.getInstance(TYPE);
            char[] passArray = passwordEntry.toCharArray();
            try {
                ks.load(inputStream, passArray);
                for (ByteNombreDto byteNombreDto : data) {
                    ks.deleteEntry(byteNombreDto.getNombre());
                }
                for (ByteNombreDto bytes : data) {
                    InputStream inputStreamCertificado = new ByteArrayInputStream(bytes.getData().array());
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    X509Certificate certificate = (X509Certificate) cf.generateCertificate(inputStream);
                    ks.setKeyEntry(bytes.getNombre(), new byte[]{}, new Certificate[]{certificate});
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ks.store(outputStream, passArray);
                ByteBuffer keyStoreData = ByteBuffer.wrap(outputStream.toByteArray());
                return keyStoreData;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (CertificateException e) {
                e.printStackTrace();
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<ByteNombreDto> loadArchivesPublic(String passwordEntry, ByteBuffer dataKeyStore) {
        List<ByteNombreDto> listResponse = new ArrayList<>();
        try {
            String temp = UtilFile.createFileTemp("test", EXTENSION, dataKeyStore.array());
            log.info(String.format("la ubicacion del archivo temporal es = %s", temp));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(dataKeyStore.array());
            KeyStore ks = KeyStore.getInstance(TYPE);
            char[] passArray = passwordEntry.toCharArray();
            try {
                ks.load(inputStream, passArray);
                Enumeration<String> allAlias = ks.aliases();
                while (allAlias.hasMoreElements()) {
                    String alias = allAlias.nextElement();
                    if (Objects.nonNull(alias)) {
                        Certificate key = ks.getCertificate(alias);
                        if (Objects.nonNull(key)) {
                            byte[] data = key.getEncoded();
                            listResponse.add(
                                    ByteNombreDto
                                            .builder()
                                            .nombre(alias)
                                            .data(ByteBuffer.wrap(data))
                                            .build()
                            );
                        }
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (CertificateException e) {
                e.printStackTrace();
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return listResponse;
    }

    public static List<ByteNombreDto> loadArchivesPrivate(String passwordEntry, ByteBuffer dataKeyStore) {
        List<ByteNombreDto> listResponse = new ArrayList<>();
        try {
            String temp = UtilFile.createFileTemp("test", EXTENSION, dataKeyStore.array());
            log.info(String.format("la ubicacion del archivo temporal es = %s", temp));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(dataKeyStore.array());
            KeyStore ks = KeyStore.getInstance(TYPE);
            char[] passArray = passwordEntry.toCharArray();
            try {
                ks.load(inputStream, passArray);
                Enumeration<String> allAlias = ks.aliases();
                while (allAlias.hasMoreElements()) {
                    String alias = allAlias.nextElement();
                    if (Objects.nonNull(alias)) {
                        Key key = ks.getKey(alias, passwordEntry.toCharArray());
                        if (Objects.nonNull(key)) {
                            byte[] data = key.getEncoded();
                            listResponse.add(
                                    ByteNombreDto
                                            .builder()
                                            .nombre(alias)
                                            .data(ByteBuffer.wrap(data))
                                            .build()
                            );
                        }
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (CertificateException e) {
                e.printStackTrace();
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            }
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return listResponse;
    }


}
