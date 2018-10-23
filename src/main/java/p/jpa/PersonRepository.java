package p.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.jpa.repository.Query;

/*
 * See: https://docs.spring.io/spring-data/rest/docs/current/reference/html
 * HAL browser: https://localhost:8080/javalab/api/browser
 */
@RepositoryRestResource(path = "people", collectionResourceRel = "people", itemResourceRel = "person")
public interface PersonRepository extends CrudRepository<Person, Long> {
	/*
	 * Spring will provide an implementation for methods following the
	 * naming convention discussed here:
	 *
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
	 */
	boolean existsById(@Param("id") long id);
	boolean existsByName(@Param("name") String name);
	List<Person> findByAge(@Param("age") int age);
	Person findByName(@Param("name") String name);
	long countByName(@Param("name") String name);
	long countByAge(@Param("age") int age);

	// Method names don't have to match a model field name when the query is provided.
	@Query(value = "select * from public.person where name = :name", nativeQuery = true)
	List<Person> lookupQuery(@Param("name") String name);

	// Prevent POST requests by not exporting the save method.
	@RestResource(exported = false)
	Person save(Person person);

	// Prevent DELETE requests by not exporting the delete method.
	@RestResource(exported = false)
	void delete(Person person);
}
