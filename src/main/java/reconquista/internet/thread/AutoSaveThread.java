package reconquista.internet.thread;

import reconquista.internet.backend.VerificationBackend;

public class AutoSaveThread extends Thread {

    private VerificationBackend backend;

    public AutoSaveThread(VerificationBackend backend) {
        this.backend = backend;
        setName("AutoSaveThread");
    }

    @Override
    public void run() {
        while (true) {
            backend.saveAll();
            try {
                Thread.sleep(5 * 60 * 1000L);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
