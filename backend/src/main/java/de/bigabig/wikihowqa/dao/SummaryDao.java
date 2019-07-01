package de.bigabig.wikihowqa.dao;

import de.bigabig.wikihowqa.model.BetterSummary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SummaryDao extends CrudRepository<BetterSummary, Long> {

    List<BetterSummary> findAllByMethod(String method);
    List<BetterSummary> findAllByTitle(String method);

}
