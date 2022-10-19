package fr.arnaud.cleanarchitecture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import fr.arnaud.cleanarchitecture.infrastructure.client.async.LeaguePublisherService;
import fr.arnaud.cleanarchitecture.infrastructure.client.async.PlayerPublisherService;

public abstract class AbstractTest {
	public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	public static final LocalDateTime FIXED_DATE = LocalDateTime.now();
	public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected PlayerPublisherService playerPublisherService;

    @Autowired
    protected LeaguePublisherService leaguePublisherService;

}
