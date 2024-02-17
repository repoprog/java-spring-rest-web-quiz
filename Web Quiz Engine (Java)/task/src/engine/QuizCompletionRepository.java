package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletionRepository extends CrudRepository<QuizCompletion, Long>,
        PagingAndSortingRepository<QuizCompletion, Long> {
    Page<QuizCompletion> findBySolvedByUser(String username, Pageable pageable);
}
