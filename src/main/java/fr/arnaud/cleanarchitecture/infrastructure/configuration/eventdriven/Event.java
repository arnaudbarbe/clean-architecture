package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"status", "content"})
@ToString(of= {"status", "content"})
public class Event<C> implements Serializable {

    private static final long serialVersionUID = 1L;

    public final Date created = new Date();
    
	public enum StandardStatus {
		CREATED,
		UPDATED, 
		DELETED;
	}
	
	String status;
	
	C content;
}
