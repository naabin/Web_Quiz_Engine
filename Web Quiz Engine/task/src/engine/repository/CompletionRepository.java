package engine.repository;

import engine.model.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompletionRepository extends JpaRepository<Completion, Integer> {

    @Query(value="select new Completion(c.quiz.id, c.completedAt) " +
            "from Completion c where c.user.id =:user_id order by c.completedAt desc")
    Page<Completion> getAllByUser(@Param("user_id") Integer userId, Pageable pageable);

    List<Completion> findAllByQuizId(Integer id);

}
