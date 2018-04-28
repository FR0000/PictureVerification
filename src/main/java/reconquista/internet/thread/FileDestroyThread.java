package reconquista.internet.thread;

import java.io.File;

public class FileDestroyThread extends Thread {
    private File file;

    public FileDestroyThread(File file) {
        this.file = file;
        setName("FileDestroyThread");
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2500L);
        } catch (InterruptedException ignored) {
        }
        file.delete();
    }
}
