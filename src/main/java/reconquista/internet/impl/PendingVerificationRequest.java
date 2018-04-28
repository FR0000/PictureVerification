package reconquista.internet.impl;

public class PendingVerificationRequest {
    private long userId;
    private String url;

    public PendingVerificationRequest(long userId, String url) {
        this.userId = userId;
        this.url = url;
    }

    public long getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }
}
