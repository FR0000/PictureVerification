package reconquista.internet.cmd;

import reconquista.internet.PictureVerification;
import reconquista.internet.manager.PermissionManager;
import reconquista.internet.manager.ShiftManager;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IUser;

public abstract class CommandExecutor {

    abstract void execute(String[] args, IUser user);

    protected IDiscordClient discordClient() {
        return PictureVerification.getInstance().getDiscordClient();
    }

    protected PermissionManager permissionManager() {
        return PictureVerification.getInstance().getPermissionManager();
    }

    protected ShiftManager shiftManager() {
        return PictureVerification.getInstance().getShiftManager();
    }

}
