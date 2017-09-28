package personal.utilities.format;

import org.apache.commons.lang3.time.DateUtils;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

/**
 *
 * NOTE:
 * <p>
 *     <b>Long Date LT / Time</b> - <code>YYYY MMMM DD d. / HH:mm:ss </code> <br />
 *     <b>Short Date / Time LT </b> - <code>YYYY MM DD / HH val. mm min. </code>
 * </p>
 * @author Ernestas
 */
public final class DateFormatter {

    /**
     * <p>
     *      Format date: <b>yyyy 'm.' MMMM 'm\u0117n.' d 'd.'</b>
     * </p>
     * @param defaultValue
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatLongDateMonthGenitiveLT(final Date date, String defaultValue) {
        if ( date == null ) {
            return defaultValue;
        }
        ZonedDateTime zonedDateTime = getTimeZonedDateTime(date);
        final int years = zonedDateTime.getYear();
        final String month = DateFormattingParams.LITHUANIAN_MONTH_OTHER[zonedDateTime.getMonthValue() - 1].toLowerCase();
        final int days = zonedDateTime.getDayOfMonth();

        return String.format(DateFormattingParams.FORMAT_LONG_DATE_LT, years, month, days);
    }

    /**
     * <p>
     *      Format date: <b>yyyy 'm.' MMMM 'm\u0117n.' d 'd.'</b>
     * </p>
     * @param defaultValue
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatLongDateLT(final Date date, String defaultValue) {
        if ( date == null ) {
            return defaultValue;
        }
        ZonedDateTime zonedDateTime = getTimeZonedDateTime(date);
        final int years = zonedDateTime.getYear();
        final String month = DateFormattingParams.LITHUANIAN_MONTH[zonedDateTime.getMonthValue() - 1].toLowerCase();
        final int days = zonedDateTime.getDayOfMonth();

        return String.format(DateFormattingParams.FORMAT_LONG_DATE_LT, years, month, days);
    }

    /**<p>
     * <b>NOTE:</b> <br/>
     * Default value is empty string
     * </p>
     * @see #formatLongDateMonthGenitiveLT(Date, String)
     */
    public static String formatLongDateMonthGenitiveLT(final Date date) {
        return formatLongDateMonthGenitiveLT(date, "");
    }


    /**
     * Format date: <b>yyyy-MM-dd</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatBirthDate(Date date) {
        return formatShortDate(date);
    }

    /**
     * Format date: <b>yyyy 'm.' MMMM 'm\u0117n.' d 'd.' H 'val.' m 'min.'</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatLongDateAndShortTimeLT(Date date) {
        return formatLongDateAndShortTimeLT(date, "");
    }

    /**
     * Format date: <b>yyyy 'm.' MMMM 'm\u0117n.' d 'd.' H 'val.' m 'min.'</b>
     *
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatLongDateAndShortTimeLT(Date date, final String defaultValue) {
        if (date == null) {
            return defaultValue;
        }
        String dateWihoutTime = formatLongDateMonthGenitiveLT(date, defaultValue);
        if ( !isTimeApplicable(date) ) {
            return dateWihoutTime;
        }
        String time = formatShortTimeLT(date);
        return dateWihoutTime + " " + time;
    }

    /**
     * Format date: <b>H 'val.' m 'min.'</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatShortTimeLT(final Date date) {
      return formatShortTimeLT(date, "");
    }

    /**
     * Format date: <b>H 'val.' m 'min.'</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatShortTimeLT(final Date date, String defaultValue) {
        if (date == null) {
            return defaultValue;
        }
        return format(date, DateFormattingParams.DATE_TIME_ONLY_FORMAT_LT);
    }

    /**
     * Format date: <b>yyyy-MM-dd</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatShortDate(Date date) {
        return formatShortDate(date, "");
    }

    /**
     * Format date: <b>yyyy-MM-dd</b>
     *
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatShortDate(Date date, String defaultValue) {
        if ( date == null ) {
            return defaultValue;
        }
        return format(date, DateFormattingParams.STANDARD_DATE_FORMATTER);
    }

    /**
     * Format date: <b>MMMM</b>
     *
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatLongMonthGenitiveLT(Date date, String defaultValue) {
        if ( date == null ) {
            return defaultValue;
        }
        return format(date, DateFormattingParams.MONTH_FORMAT_LT);
    }

    /**
     * Format date: <b>MMMM</b>
     *
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatLongMonthGenitiveLT(Date date) {
        return formatLongMonthGenitiveLT(date, "");
    }


    /**
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatLongMonthLT(Date date, String defaultValue) {
        if (date == null) {
            return defaultValue;
        }
        ZonedDateTime zonedDateTime = getTimeZonedDateTime(date);
        return DateFormattingParams.LITHUANIAN_MONTH[zonedDateTime.getMonthValue() - 1];
    }

    /**
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatLongMonthLT(Date date) {
        return formatLongMonthLT(date, "");
    }

    /**
     * Format date: <b>yyyy-MM-dd HH:mm:ss</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatShortDateAndLongTime(Date date) {
        if ( date == null ) {
            return null;
        }
        return format(date, DateFormattingParams.SIMPLE_TIMESTAMP);
    }

    /**
     * Format date: <b>yyyy</b>
     *
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatYear(Date date, String defaultValue) {
        if ( date == null ) {
            return defaultValue;
        }
        return String.valueOf(getTimeZonedDateTime(date).getYear());
    }

    /**
     * Format date: <b>yyyy-MM</b>
     *
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatYearShortMonth(Date date, String defaultValue) {
        if ( date == null ) {
            return defaultValue;
        }
        return format(date, DateFormattingParams.BRIEF_MONTH_FORMAT);
    }

    public static String formatQuarter(Date date, String defaultValue) {
        if (date == null) {
            return defaultValue;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int quarter = c.get(Calendar.MONTH) / 3;
        return String.format("%s m. %s %s", c.get(Calendar.YEAR), DateFormattingParams.QUARTER_NUM[quarter], DateFormattingParams.QUARTER_LT);
    }

    public static String formatQuarter(Date date) {
        return formatQuarter(date, "");
    }

    /**
     * Format date: <b>yyyy-MM-dd HH:mm</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatShortDateAndShortTime(Date date) {
        return formatShortDateAndShortTime(date, null);
    }

    /**
     * Format date: <b>yyyy-MM-dd HH:mm</b>
     *
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatShortDateAndShortTime(Date date, String defaultValue) {
        return formatShortDateAndShortTime(date, defaultValue, DateFormattingParams.BRIEF_DATETIME_FORMAT);
    }

    private static String formatShortDateAndShortTime(Date date, String defaultValue, DateTimeFormatter dateTimeFormat) {
        if ( date == null ) {
            return defaultValue;
        }
        if ( !isTimeApplicable(date) ) {
            return formatShortDate(date, defaultValue);
        }
        return format(date, dateTimeFormat);
    }

    /**
     * Format date: <b>yyyy-MM-dd H 'val.' m 'min.'</b>
     *
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatShortDateAndShortTimeLT(Date date) {
        return formatShortDateAndShortTimeLT(date, null);
    }

    /**
     * Format date: <b>yyyy-MM-dd H 'val.' m 'min.'</b>
     *
     * @return <code>defaultValue</code> if date parameter is <code>null</code>
     */
    public static String formatShortDateAndShortTimeLT(Date date, String defaultValue) {
        return formatShortDateAndShortTime(date, defaultValue, DateFormattingParams.BRIEF_DATETIME_LT_FORMAT);
    }

    /**
     * Format date: <b>HHmm</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatTightTime(Date date) {
        if ( date == null ) {
            return null;
        }
        return format(date, DateFormattingParams.TIGHT_TIME_FORMAT);
    }

    /**
     * Format date: <b>yyyyMMdd</b>
     *
     * @param date
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static final String formatTightDate(Date date) {
        if ( date == null ) {
            return null;
        }
        return format(date, DateFormattingParams.TIGHT_DATE_FORMAT);
    }

    /**
     * Format date: <b>HH mm</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatBriefTime(Date date) {
        if ( date == null ) {
            return null;
        }
        return format(date, DateFormattingParams.BRIEF_TIME_FORMAT);
    }

    /**
     * Format date: <b>HH:mm</b>
     *
     * @return <code>null</code> if date parameter is <code>null</code>
     */
    public static String formatTimeEU(Date date) {
        if ( date == null ) {
            date = Calendar.getInstance().getTime();
        }
        return format(date, DateFormattingParams.TIME_FORMAT_EU);
    }

    public static String formatPeriod(Date start, Date end, boolean withTime) {
        if (start == null && end == null) {
            return "";
        }

        String endFormatted = withTime ? formatLongDateAndShortTimeLT(end) : formatLongDateMonthGenitiveLT(end);
        if (start == null) {
            return String.format(DateFormattingParams.FORMAT_DATE_TO_LT, endFormatted);
        }

        String startFormatted = withTime ? formatLongDateAndShortTimeLT(start) : formatLongDateMonthGenitiveLT(start);
        if (end == null) {
            return startFormatted;
        }
        return String.format(DateFormattingParams.FORMAT_DATE_FROM_TO_LT, startFormatted, endFormatted);
    }

    /**
     * Check whether time part of the date should be displayed to the user for
     * given date.
     *
     * <p>
     * This is determined by presence of milliseconds in day of the year,
     * provided in date argument. In other words, if date contains non-zero
     * hours, minutes, seconds or milliseconds, time should be displayed.
     * Otherwise only date part should be displayed.
     * </p>
     *
     * @return true if time should be displayed along the date
     */
    public static boolean isTimeApplicable(final Date date) {
        if (date == null) {
            return false;
        }
        long ms = DateUtils.getFragmentInMilliseconds(date, Calendar.DAY_OF_YEAR);
        return ms != 0L;
    }

    private static String format(Date date, DateTimeFormatter dateFormat) {
        ZonedDateTime zonedDateTime = getTimeZonedDateTime(date);
        return dateFormat.format(zonedDateTime);
    }

    private static ZonedDateTime getTimeZonedDateTime(final Date date) {
        Optional<TimeZone> userTimeZone = null; // TODO: set up TIME ZONE
        return Instant.ofEpochMilli(date.getTime()).atZone(userTimeZone.orElse(TimeZone.getDefault()).toZoneId());
    }
}
