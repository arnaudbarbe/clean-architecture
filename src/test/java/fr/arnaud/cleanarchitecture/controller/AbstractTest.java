package fr.arnaud.cleanarchitecture.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public abstract class AbstractTest {
    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final LocalDateTime FIXED_DATE = LocalDateTime.now();
    public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));

}
