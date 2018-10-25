package p.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "pet", schema = "public")
public class Pet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "owner_id")
	private long owner_id;

	@Column(name = "name")
	private String name;

	public long getOwnerId() {
		return this.owner_id;
	}

	public String getName() {
		return this.name;
	}
}
