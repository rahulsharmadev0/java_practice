
import java.time.*;
import java.time.format.DateTimeFormatter;

/*
- LocalDate	Date without time-zone. (e.g., 2025-09-23)
- LocalTime	Time without date or time-zone.
- LocalDateTime	Date and time without time-zone.
- ZonedDateTime	Date and time with time-zone.
- Instant	Machine timestamp (UTC).
- ZoneId	Represents a time-zone.
- Period	Date-based amount of time (e.g., 2 years, 3 months).
- Duration	Time-based amount of time (e.g., 2 hours, 30 minutes).
- DateTimeFormatter	Formatting and parsing dates/times.
 */
public class DateTimeDemo {
    public static void main(String[] args) {
        calculatingPeriodBw();
        calculatingDurationBw();
    }

    static void checkOutLocalDate() {
        // Give current date 'YYYY-MM-DD'
        System.out.println(LocalDate.now());

        System.out.println(LocalDate.of(2025, 9, 23));

        // Give date of 2001-02-25
        System.out.println(LocalDate.ofYearDay(2001, 56));

        // Convert string to LocalDate object
        System.out.println(LocalDate.parse("2001-02-25"));

        // custom datetime format
        System.out.println(LocalDate.parse(" 2011-12-03+01:00", DateTimeFormatter.ISO_DATE));

    }

    static void checkOutLocalTime() {
        // give current time '23:29:42.671828'
        System.out.println(LocalTime.now());

        // convert sec to time 720/60 = 00:12
        System.out.println(LocalTime.ofSecondOfDay(720));

        // convert string to LocalTime java object then format into 12hr format
        System.out.println(LocalTime.parse("18:30")
                .format(DateTimeFormatter.ofPattern("hh:mm:ss a")));

    }

    static void checkOutLocalDateTime() {
        System.out.println(LocalDateTime.now());

        System.out.println(
                LocalDateTime.of(2001, 05, 01, 13, 30)
                        .format(DateTimeFormatter.ofPattern("dd-MM-YYYY, hh:mm:ss")));
    }

    // Instant Machine timestamp (UTC).
    static void checkIMTimeStamp() {

        Instant now = Instant.now();

        // Instant Machine timestamp in (UTC)
        System.out.println(now);

        // Converted into LocalDateTime
        System.out.println(LocalDateTime.ofInstant(now, ZoneId.systemDefault()));

    }

    static void checkOutPeriod() {
        System.out.println(Period.ofMonths(12));
        System.out.println(Period.ofMonths(121));
        System.out.println(Period.of(2000, 12, 7));
        System.out.println(Period.parse("P2000Y12M7D"));
        System.out.println(Period.ofWeeks(52).getDays());
        System.out.println(Period.ofMonths(52));
        System.out.println(Period.ofWeeks(52).plus(Period.ofDays(12)));
    }

    static void checkOutZoneTime() {
        ZoneId parisZone = ZoneId.of("Europe/Paris");
        System.out.println(ZonedDateTime.now(parisZone));
        System.out.println(ZonedDateTime.now(parisZone).format(DateTimeFormatter.ofPattern("YYYY-MM-dd, hh:mm:ss a")));

    }


    static void calculatingPeriodBw(){
        LocalDate start =  LocalDate.of(2001,5,4);

        LocalDate now = LocalDate.now();

        System.out.println(Period.between(start, now));
    }

    static void calculatingDurationBw(){
        LocalTime start =  LocalTime.of(12,30,30);

        LocalTime now = LocalTime.now();

        System.out.println(Duration.between(start, now));
    }
}