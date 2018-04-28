package reconquista.internet.backend;

import reconquista.internet.PictureVerification;
import reconquista.internet.impl.PendingVerificationRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FileBackend implements VerificationBackend {

    private File admins = new File("admins.txt");
    private File moderators = new File("moderators.txt");
    private File requests = new File("requests.txt");


    public FileBackend() {
        if (!admins.exists()) {
            try {
                admins.createNewFile();
            } catch (IOException ignored) {
            }
        }
        if (!moderators.exists()) {
            try {
                moderators.createNewFile();
            } catch (IOException ignored) {
            }
        }
        if (!requests.exists()) {
            try {
                requests.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void saveModerators() {
        try {
            FileWriter writer = new FileWriter(moderators);
            Iterator<String> mods = PictureVerification.getInstance().getPermissionManager().getModerators().iterator();
            while (mods.hasNext()) {
                writer.write(mods.next() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public List<String> getModerators() {
        try {
            return Files.readAllLines(moderators.toPath());
        } catch (IOException ignored) {
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getAdmins() {
        try {
            return Files.readAllLines(admins.toPath());
        } catch (IOException ignored) {
        }
        return Collections.emptyList();
    }

    @Override
    public void savePendingRequests() {
        try {
            FileWriter writer = new FileWriter(requests);
            Iterator<PendingVerificationRequest> requestIterator = PictureVerification.getInstance().getVerificationManager().getPendingRequests().iterator();
            while (requestIterator.hasNext()) {
                PendingVerificationRequest s = requestIterator.next();
                writer.write(s.getUserId() + ";" + s.getUrl() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public List<PendingVerificationRequest> getPendingVerificationRequests() {
        List<PendingVerificationRequest> pendingVerificationRequests = new ArrayList<>();
        try {
            Files.readAllLines(requests.toPath()).forEach(c -> {
                String[] s = c.split(";");
                pendingVerificationRequests.add(new PendingVerificationRequest(Long.parseLong(s[0]), s[1]));
            });
        } catch (IOException ignored) {
        }
        return pendingVerificationRequests;
    }

    @Override
    public void saveAll() {
        saveModerators();
        savePendingRequests();
    }
}
