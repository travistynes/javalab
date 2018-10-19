package p.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
 * See: https://docs.spring.io/spring-data/rest/docs/current/reference/html
 * HAL browser: https://localhost:8080/javalab/browser
 */
@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends CrudRepository<Person, Long> {
	List<Person> findByName(@Param("name") String name);
	boolean existsById(@Param("id") long id);
	boolean existsByName(@Param("name") String name);
	List<Person> findByAge(@Param("age") int age);
	long countByName(@Param("name") String name);
	Person save(Person person);
}
