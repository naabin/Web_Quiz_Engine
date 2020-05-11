package engine.repository;

import engine.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Query(value="select new Quiz(q.id, q.completedAt) from Quiz q where q.completed=?1")
    Page<Quiz> findByCompleted(Boolean completed, Pageable pageable);
}
