package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final int FORMATTER_YYYY_MM_DD = 10;

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        // uzupełnij rozwiązanie. Korzystaj z przekazanego w parametrze scannera
        LocalDateTime dateTimeFromUser = readDateFromUser(scanner);
        printTimes(dateTimeFromUser);
    }

    private void printTimes(LocalDateTime dateTimeFromUser) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTimeLocal = calculateLocalTime(dateTimeFromUser);
        System.out.println("Czas lokalny: " + formatter.format(dateTimeLocal));
        ZonedDateTime dateTimeUTC = calculateTime(dateTimeFromUser, ZoneId.of("UTC"));
        System.out.println("UTC: " + formatter.format(dateTimeUTC));
        ZonedDateTime dateTimeLondon = calculateTime(dateTimeLocal, ZoneId.of("Europe/London"));
        System.out.println("Londyn: " + formatter.format(dateTimeLondon));
        ZonedDateTime dateTimeLosAngeles = calculateTime(dateTimeLocal, ZoneId.of("America/Los_Angeles"));
        System.out.println("Los Angeles: " + formatter.format(dateTimeLosAngeles));
        ZonedDateTime dateTimeSydney = calculateTime(dateTimeLocal, ZoneId.of("Australia/Sydney"));
        System.out.println("Sydney: " + formatter.format(dateTimeSydney));
    }

    private ZonedDateTime calculateTime(LocalDateTime dateTimeLocal, ZoneId zoneId) {
        ZonedDateTime zdf = dateTimeLocal.atZone(ZoneId.of("Europe/Berlin"));
        return zdf.withZoneSameInstant(zoneId);
    }

    private LocalDateTime calculateLocalTime(LocalDateTime dateTimeFromUser) {
        LocalTime localTime = dateTimeFromUser.toLocalTime();
        LocalDate localDate = dateTimeFromUser.toLocalDate();
        return LocalDateTime.of(localDate, localTime);
    }

    private LocalDateTime readDateFromUser(Scanner scanner) {
        System.out.println("Podaj datę:");
        String dateFromUser = scanner.nextLine();
        return parseDate(dateFromUser);
    }

    private LocalDateTime parseDate(String dateFromUser) {
        String date;
        String time;
        if (dateFromUser.length() > FORMATTER_YYYY_MM_DD) {
            String[] split = dateFromUser.split(" ");
            date = split[0];
            time = split[1];
        } else {
            date = dateFromUser;
            time = "00:00:00";
        }
        List<String> datePatterns = Arrays.asList("yyyy-MM-dd", "dd.MM.yyyy");
        String timePattern = "HH:mm:ss";
        LocalDateTime dateTime = null;
        for (String datePattern : datePatterns) {
            try {
                LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern));
                LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(timePattern));
                dateTime = LocalDateTime.of(localDate, localTime);
            } catch (DateTimeException e) {

            }
        }
        return dateTime;
    }
}
