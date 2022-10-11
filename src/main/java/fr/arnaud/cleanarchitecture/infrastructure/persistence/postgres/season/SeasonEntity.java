package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.season;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.arnaud.cleanarchitecture.core.entities.Season;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of= {"id", "name", "startDate", "endDate"})
@Entity
@Table(name = "season")
public class SeasonEntity {

	@Id
	UUID id;
	
	String name;
    
	LocalDateTime startDate;

    LocalDateTime endDate;

	public SeasonEntity() {
		super();
	}

	public SeasonEntity(final UUID id) {
		this.id = id;
	}

	public SeasonEntity(final Season season) {
		this.id = season.getId();
		fromEntity(season);
	}

	public Season toEntity() {
		return Season.builder()
				.id(this.id)
				.name(this.name)
				.startDate(this.startDate)
				.endDate(this.endDate)
				.build();
	}

	public void fromEntity(final Season season) {
		this.name = season.getName();
		this.startDate = season.getStartDate();
		this.endDate = season.getEndDate();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeasonEntity other = (SeasonEntity) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}
}