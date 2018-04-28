package reconquista.internet.manager;

import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {

    private List<String> admins = new ArrayList<>();
    private List<String> moderators = new ArrayList<>();

    public List<String> getModerators() {
        return moderators;
    }

    public void setModerators(List<String> moderators) {
        this.moderators = moderators;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public void addModerator(long userID) {
        moderators.add(String.valueOf(userID));
    }

    public void removeModerator(long userID) {
        moderators.remove(String.valueOf(userID));
    }

    public boolean isModerator(long l) {
        return moderators.contains(String.valueOf(l)) || isAdmin(l);
    }

    public boolean isAdmin(long l) {
        return admins.contains(String.valueOf(l));
    }

    public boolean isAdmin(IUser user) {
        return isAdmin(user.getLongID());
    }

    public boolean isModerator(IUser user) {
        return isModerator(user.getLongID());
    }
}
