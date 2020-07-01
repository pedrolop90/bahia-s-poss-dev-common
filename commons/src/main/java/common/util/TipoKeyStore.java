package common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoKeyStore {

    JKS("jks","jks"),
    JCEKS("jceks","pfx"),
    PCKS12("pcks12","pcks12");

    private String tipo;
    private String extension;


}
