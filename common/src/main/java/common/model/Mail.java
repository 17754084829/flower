package common.model;

public class Mail {
    private String rec_user;
    private String subject;
    private String content;

    public Mail() {
    }

    public Mail(String rec_user, String subject, String content) {
        this.rec_user = rec_user;
        this.subject = subject;
        this.content = content;
    }

    public String getRec_user() {
        return rec_user;
    }

    public void setRec_user(String rec_user) {
        this.rec_user = rec_user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
