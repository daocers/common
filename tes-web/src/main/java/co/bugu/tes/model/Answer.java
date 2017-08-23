package co.bugu.tes.model;

/**
 * Created by user on 2017/3/22.
 */
public class Answer {
    private Integer id;
    private Integer paperId;
    private Integer questionId;
    private String answer;
    private Integer seconds;
    private String timeLeft;
    private Integer result;
    private Double score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
