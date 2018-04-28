package reconquista.internet.manager;

import reconquista.internet.impl.PendingVerificationRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VerificationManager {
    private List<PendingVerificationRequest> pendingRequests = new ArrayList<>();

    public List<PendingVerificationRequest> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(List<PendingVerificationRequest> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public void addPendingVerificationRequest(PendingVerificationRequest pendingVerificationRequest) {
        pendingRequests.add(pendingVerificationRequest);
    }

    public void removePendingVerificationRequest(PendingVerificationRequest user) {
        pendingRequests.remove(user);
    }

    public boolean isAlreadyPending(long id) {
        return pendingRequests.stream().filter(c -> c.getUserId() == id).count() > 0;
    }

    public PendingVerificationRequest getNextRequest() {
        Iterator<PendingVerificationRequest> it = pendingRequests.iterator();
        if (it.hasNext()) {
            PendingVerificationRequest user = it.next();
            it.remove();
            return user;
        }
        return null;
    }

}
