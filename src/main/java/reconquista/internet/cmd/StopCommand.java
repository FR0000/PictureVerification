package reconquista.internet.cmd;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

public class StopCommand extends CommandExecutor {
    @Override
    void execute(String[] args, IUser user) {
        if (permissionManager().isModerator(user)) {
            IChannel channel = discordClient().getOrCreatePMChannel(user);
            channel.sendMessage("Deine Schicht wurde beendet.");
            shiftManager().stopShift(user.getLongID());
        }
    }
}
