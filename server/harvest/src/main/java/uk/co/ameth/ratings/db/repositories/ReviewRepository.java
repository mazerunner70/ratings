package uk.co.ameth.ratings.db.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import uk.co.ameth.ratings.db.model.ReviewEntry;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface ReviewRepository extends CrudRepository<ReviewEntry, String> {

    @Override
    Optional<ReviewEntry> findById(String s);

    @Override
    <S extends ReviewEntry> S save(S s);
}
