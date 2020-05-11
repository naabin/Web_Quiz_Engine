package engine.responsemapper;

public class QuizFeedback {
    private boolean success;
    private String feedback;

    public QuizFeedback(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public QuizFeedback() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
