package reconquista.internet.manager;

import reconquista.internet.impl.ShiftUser;

import java.util.ArrayList;
import java.util.List;

public class ShiftManager {
    private List<ShiftUser> shiftUsers = new ArrayList<>();

    public void startShift(long id) {
        ShiftUser shiftUser = new ShiftUser(id);
        Thread thread = new Thread(shiftUser::newUser);
        shiftUsers.add(shiftUser);
        thread.start();
    }

    public void stopShift(long id) {
        ShiftUser user = shiftUsers.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        shiftUsers.remove(user);
        user.stop();
    }

    public ShiftUser getShiftUser(long id) {
        return shiftUsers.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

}
