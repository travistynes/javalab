package p.repository;

import io.crnk.core.exception.ResourceNotFoundException;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepository;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import org.springframework.stereotype.Component;
import p.dto.PersonDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PersonRepository extends ResourceRepositoryBase<PersonDto, Long> {
    Map<Long, PersonDto> people = new ConcurrentHashMap<>();

    public PersonRepository() {
        super(PersonDto.class);

        for(int a = 0; a < 3; a++) {
            PersonDto p = new PersonDto();
            p.setId(Long.valueOf(a));
            p.setName("Person " + a);

            people.put(p.getId(), p);
        }
    }

    @Override
    public ResourceList<PersonDto> findAll(QuerySpec querySpec) {
        return querySpec.apply(people.values());
    }

    @Override
    public <P extends PersonDto> P create(P person) {
        Long id = Long.valueOf(people.size());
        person.setId(id);
        people.put(id, person);

        return person;
    }

    @Override
    public <P extends PersonDto> P save(P person) {
        people.put(person.getId(), person);
        return person;
    }

    @Override
    public void delete(Long id) {
        people.remove(id);
    }
}
