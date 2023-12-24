package questionInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class QuestionImplimentation implements QuestionService {
    static Connection conn;

    static {
        String url = "jdbc:mysql://localhost:3306/quetiondb";
        String username = "root";
        String password = "tiger";
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("CONNECTION UNSUCCESSFUL !! ");
            System.exit(1);
        }
    }

    @Override
    public int addQuestions(Question newQuestion) {
        String insertQuery = "INSERT INTO Question_info(QUESTION , OPTION1 , OPTION2 , OPTION3  , ANSWER)" +
                "VALUES (? , ? , ? , ? , ?) ";
        try {
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, newQuestion.getQuestion());
            pstmt.setString(2, newQuestion.getOption1());
            pstmt.setString(3, newQuestion.getOption2());
            pstmt.setString(4, newQuestion.getOption3());
            pstmt.setString(5, newQuestion.getAnswer());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("INVALID QUESTION DATA !!");
        }
        return 0;
    }

    @Override
    public int removeQuestions(int questionId) {
        String deleteQuery = "DELETE FROM QUESTION_INFO WHERE QUESTION_ID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, questionId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("INVALID QUESTION ID ");
        }
        return 0;
    }

    public List<Question> getAllQuestions() {
        String selectQuery = "select * from question_info";
        List<Question> questionList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt(1));
                q.setQuestion(rs.getString(2));
                q.setOption1(rs.getString(3));
                q.setOption2(rs.getString(4));
                q.setOption3(rs.getString(5));
                q.setAnswer(rs.getString(6));

                questionList.add(q);
            }
        } catch (SQLException e) {

        }

        return questionList;

    }

    @Override
    public int modifyOption(Question q, int questionId) {

        PreparedStatement pstmt = null;
        String query = "update quetion_info set quetion =? where quetion_id = ?";
        int count = 0;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, q.getQuetion());
            pstmt.setInt(2,questionId);

            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int modifyQuestion(Question q, int id) {
        PreparedStatement pstmt = null;
        int count = 0;
        String query = "update quetion_info set option1=?, option2=?, option3=?, answer=? where quetion_id=?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,q.getOption1());
            pstmt.setString(2,q.getOption2());
            pstmt.setString(3,q.getOption3());
            pstmt.setString(4,q.getAnswer());
            pstmt.setInt(5,id);

            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Option not Added");
        }
        return count;
    }
}




