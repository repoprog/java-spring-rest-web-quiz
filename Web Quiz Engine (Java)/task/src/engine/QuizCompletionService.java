package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuizCompletionService {
    @Autowired
    private QuizCompletionRepository completionRepository;

    public void createQuizCompletion(QuizCompletion quizCompletion) {
        completionRepository.save(quizCompletion);
    }
    public Page<QuizCompletion> getPage(int num, String username) {
        final int COMPLETION_PER_PAGE = 10;
        return completionRepository.findBySolvedByUser(username,
                PageRequest.of(num, COMPLETION_PER_PAGE, Sort.by("completedAt").descending()));
    }

}
