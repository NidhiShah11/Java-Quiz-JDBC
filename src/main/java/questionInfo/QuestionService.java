package questionInfo;

import java.sql.SQLException;
import java.util.List;

public interface QuestionService {
    int addQuestions(Question newQuestion);

    int removeQuestions(int questionId);

    List<Question> getAllQuestions();

    int modifyOption(Question q, int questionId);

    int modifyQuestion(Question q, int questionId);
}