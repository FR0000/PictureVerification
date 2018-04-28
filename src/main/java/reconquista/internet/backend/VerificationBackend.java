package reconquista.internet.backend;

import reconquista.internet.impl.PendingVerificationRequest;

import java.util.List;

public interface VerificationBackend {

    void saveModerators();

    List<String> getModerators();

    List<String> getAdmins();

    void savePendingRequests();

    List<PendingVerificationRequest> getPendingVerificationRequests();

    void saveAll();
}
