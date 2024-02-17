package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuizCompletionService completionService;

    public void createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public Iterable<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public void deleteById(long id) {
        quizRepository.deleteById(id);
    }

    public Page<Quiz> getPage(int num) {
       final int QUIZZES_PER_PAGE = 10;
        return quizRepository.findAll(PageRequest.of(num, QUIZZES_PER_PAGE));
    }
    public Quiz findQuizById(long id) {
        //automatic method from CRUDREPO
        return quizRepository.findById(id)
                // lub use same provided by JPA EntityNotFoundException()
                .orElseThrow(() -> new QuizNotFoundException("Nie znaleziono quizu id:" + id));
    }

    public Answer solveQuiz(long id, Quiz quiz, String username) {
        Quiz quizWihAnswers = findQuizById(id);
        if (Arrays.equals(quiz.getAnswer(), quizWihAnswers.getAnswer())) {
            QuizCompletion quizCompletion = new QuizCompletion();
            quizCompletion.setQuizId(id);
            quizCompletion.setCompletedAt(LocalDateTime.now());
            quizCompletion.setSolvedByUser(username);
            completionService.createQuizCompletion(quizCompletion);
            return new Answer(true, "Congratulations, you're right!");
        } else {
            return new Answer(false, "Wrong answer! Please, try again.");
        }
    }
}