package de.bigabig.wikihowqa.dao;

import de.bigabig.wikihowqa.model.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatingDao extends CrudRepository<Rating, Long> {

    List<Rating> findAllByMethod(String method);

}
