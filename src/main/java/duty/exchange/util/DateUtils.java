package duty.exchange.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date and time operations
 */
public class DateUtils {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Check if two time ranges overlap
     */
    public static boolean isTimeOverlapping(LocalTime start1, LocalTime end1, 
                                           LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
    
    /**
     * Check if a date is in the future
     */
    public static boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
    
    /**
     * Check if a date is in the past
     */
    public static boolean isPastDate(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
    
    /**
     * Check if a date is today
     */
    public static boolean isToday(LocalDate date) {
        return date.equals(LocalDate.now());
    }
    
    /**
     * Format date to string
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }
    
    /**
     * Format time to string
     */
    public static String formatTime(LocalTime time) {
        return time != null ? time.format(TIME_FORMATTER) : null;
    }
    
    /**
     * Format datetime to string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMATTER) : null;
    }
    
    /**
     * Check if duty date is at least 24 hours in the future
     */
    public static boolean isDutyDateValidForExchange(LocalDate dutyDate) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return dutyDate.isAfter(tomorrow) || dutyDate.equals(tomorrow);
    }
    
    /**
     * Get days between two dates
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return java.time.temporal.ChronoUnit.DAYS.between(start, end);
    }
}
