package p.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import java.util.List;

@Entity
@Table(name = "person", schema = "public")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private int age;

	@OneToMany
	@JoinColumn(name = "owner_id")
	private List<Pet> pets;

	public List<Pet> getPets() {
		return this.pets;
	}

	public String getName() {
		return this.name;
	}

	public int getAge() {
		return this.age;
	}
}
