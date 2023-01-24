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
//        if (dateTimeFromUser.toString().length() == 16) {
//            StringBuilder builder = new StringBuilder().append(dateTimeFromUser).append(":00");
//            dateTimeFromUser = LocalDateTime.of(dateTimeFromUser.getYear(), dateTimeFromUser.getMonth(),
//                    dateTimeFromUser.getDayOfMonth(), dateTimeFromUser.getHour(), dateTimeFromUser.getMinute(), 00);
//        }
        LocalDateTime dateTimeLocal = calculateLocalTime(dateTimeFromUser);
        System.out.println("Czas lokalny: " + dateTimeLocal.toString().replace('T', ' '));
        LocalDateTime dateTimeUTC = calculateTime(dateTimeFromUser, "UTC");
        System.out.println("UTC: " + dateTimeUTC.toString().replace('T', ' '));
        LocalDateTime dateTimeLondon = calculateTime(dateTimeLocal, "Europe/London");
        System.out.println("Londyn: " + dateTimeLondon.toString().replace('T', ' '));
        LocalDateTime dateTimeLosAngeles = calculateTime(dateTimeLocal, "America/Los_Angeles");
        System.out.println("Los Angeles: " + dateTimeLosAngeles.toString().replace('T', ' '));
        LocalDateTime dateTimeSydney = calculateTime(dateTimeLocal, "Australia/Sydney");
        System.out.println("Sydney: " + dateTimeSydney.toString().replace('T', ' '));
    }

    private LocalDateTime calculateTime(LocalDateTime dateTimeLocal, String zoneName) {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(dateTimeLocal.atZone(ZoneId.of("Europe/Berlin")).toInstant(), ZoneId.of(zoneName));
        return zonedDateTime.toLocalDateTime();
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
