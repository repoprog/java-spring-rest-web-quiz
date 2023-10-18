package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    public void createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public Iterable<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public void deleteById(int id) {
        quizRepository.deleteById(id);
    }

    public Quiz findQuizById(int id) {
        //automatic method from CRUDREPO
        return quizRepository.findById(id)
                // lub use same provided by JPA EntityNotFoundException()
                .orElseThrow(() -> new QuizNotFoundException("Nie znaleziono quizu id:" + id));
    }

}