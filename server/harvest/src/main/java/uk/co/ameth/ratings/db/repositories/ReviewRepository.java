package uk.co.ameth.ratings.db.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import uk.co.ameth.ratings.db.model.ReviewEntry;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface ReviewRepository extends Repository<ReviewEntry, String> {

    Optional<ReviewEntry> findById(String s);

    <S extends ReviewEntry> S save(S s);

    Iterable<ReviewEntry> findAll();

    List<ReviewEntry> findByTimestamp(long timestamp);
    List<ReviewEntry> findByText(String text);
    List<ReviewEntry> findByPlatformAndTimestamp(String platform, long timestamp);
}
