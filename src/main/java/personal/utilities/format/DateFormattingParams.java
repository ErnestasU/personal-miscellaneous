package personal.utilities.format;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Ernestas
 */
public class DateFormattingParams {

    public static final Locale LOCALE_LT_LT = new Locale("LT");

    public static final String FORMAT_DATE_TO_LT = "iki %s";

    public static final String FORMAT_DATE_FROM_LT = "nuo %s";

    public static final String FORMAT_DATE_FROM_TO_LT = "nuo %s " + FORMAT_DATE_TO_LT;

    public static final String FORMAT_LONG_DATE_LT = "%s m. %s %s d.";

    public static final String FORMAT_LONG_DATE_TIME_LT = "%s m. %s %s d. %s val. %s min.";

    public static final DateTimeFormatter TIGHT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static final DateTimeFormatter TIGHT_TIME_FORMAT = DateTimeFormatter.ofPattern("HHmm");

    public static final DateTimeFormatter DATE_TIME_FORMAT_LT = DateTimeFormatter.ofPattern("yyyy 'm.' MMMM d 'd.' H 'val.' m 'min.'", LOCALE_LT_LT);

    public static final DateTimeFormatter DATE_TIME_ONLY_FORMAT_LT = DateTimeFormatter.ofPattern("H 'val.' m 'min.'", LOCALE_LT_LT);

    public static final DateTimeFormatter STANDARD_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", LOCALE_LT_LT);

    public static final DateTimeFormatter YEAR_MONTH_FORMAT_LT = DateTimeFormatter.ofPattern("yyyy 'm.' MMMM 'm\u0117n.'", LOCALE_LT_LT);

    public static final DateTimeFormatter MONTH_FORMAT_LT = DateTimeFormatter.ofPattern("MMMM", LOCALE_LT_LT);

    public static final DateTimeFormatter SIMPLE_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", LOCALE_LT_LT);

    public static final String[] LITHUANIAN_MONTH = {"SAUSIS", "VASARIS", "KOVAS", "BALANDIS", "GEGU\u017d\u0116", "BIR\u017dELIS",
                                                     "LIEPA", "RUGPJ\u016aTIS", "RUGS\u0116JIS", "SPALIS", "LAPKRITIS", "GRUODIS"};

    public static final String[] LITHUANIAN_MONTH_OTHER = {"SAUSIO", "VASARIO", "KOVO", "BALANDŽIO", "GEGUŽĖS", "BIRŽELIO",
                                                           "LIEPOS", "RUGPJŪČIO", "RUGSĖJO", "SPALIO", "LAPKRIČIO", "GRUODŽIO"};

    public static final DateTimeFormatter BRIEF_MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

    public static final DateTimeFormatter BRIEF_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final DateTimeFormatter BRIEF_DATETIME_LT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd H 'val.' m 'min.'", LOCALE_LT_LT);

    public static final DateTimeFormatter BRIEF_TIME_FORMAT = DateTimeFormatter.ofPattern("HH mm");

    public static final DateTimeFormatter TIME_FORMAT_EU  = DateTimeFormatter.ofPattern("HH:mm");

    public static final String QUARTER_LT = "ketvirtis";
    public static final String[] QUARTER_NUM = {"pirmas", "antras", "tre\u010Dias", "ketvirtas"};
}
