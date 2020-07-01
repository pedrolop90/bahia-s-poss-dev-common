package common.util;

import java.util.Locale;

public interface Constants {
    int SCALE_BIGDECIMAL = 2;
    String FORMAT_DATE = "yyyy-MM-dd";
    String FORMAT_DATE_POS_EMV = "yyyyMM";
    String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    String FORMAT_DATE_TIME_ISO = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    String FORMAT_TIME = "HH:mm:ss";
    String FORMAT_LOCAL_TIME_HM = "HH:mm";
    char COMMA_SEPARATOR = ',';
    String PATTERN_EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    String PATTERN_DECIMAL = "#,##0.00";
    Locale LOCALE = new Locale("ES", "co");
    String LIST_SEPARATOR = "@@";
    String FIELD_SEPARATOR = "##";
}
