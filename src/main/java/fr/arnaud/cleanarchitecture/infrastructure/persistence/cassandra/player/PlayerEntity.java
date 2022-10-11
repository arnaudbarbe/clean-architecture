package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.player;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import fr.arnaud.cleanarchitecture.core.entity.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of= {"id", "firstName", "lastName"})
@Table("player")
public class PlayerEntity {

	@PrimaryKey
	UUID id;
	
	String firstName;
 
	String lastName;

	public PlayerEntity() {
		super();
	}
	
	public PlayerEntity(final UUID id) {
		this.id = id;
	}

	public PlayerEntity(final Player player) {
		this.id = player.getId();
		fromModel(player);
	}

	public Player toModel() {
		return Player.builder()
				.id(this.id)
				.firstName(this.firstName)
				.lastName(this.lastName)
				.build();
	}

	public void fromModel(final Player player) {
		this.firstName = player.getFirstName();
		this.lastName = player.getLastName();
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
		PlayerEntity other = (PlayerEntity) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}
}