package de.bigabig.wikihowqa.dao;

import de.bigabig.wikihowqa.model.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingDao extends CrudRepository<Rating, Long> {

    List<Rating> findAllByMethod(String method);

    @Query("SELECT AVG(r.rating), FUNCTION('DAY', r.timestamp), FUNCTION('MONTH', r.timestamp), FUNCTION('YEAR', r.timestamp) FROM Rating r WHERE r.method = :method GROUP BY FUNCTION('DAY', r.timestamp), FUNCTION('MONTH', r.timestamp), FUNCTION('YEAR', r.timestamp)")
    List<Object[]> findAverage(
            @Param("method") String method
    );


}
