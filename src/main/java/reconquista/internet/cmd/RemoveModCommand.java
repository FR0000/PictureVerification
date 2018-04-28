package reconquista.internet.cmd;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

public class RemoveModCommand extends CommandExecutor {
    @Override
    void execute(String[] args, IUser user) {
        if (permissionManager().isAdmin(user)) {
            IChannel channel = discordClient().getOrCreatePMChannel(user);
            if (args.length > 0) {
                String modID = args[0];
                long userID = Long.parseLong(modID);
                IUser targetUser = discordClient().getUserByID(userID);
                permissionManager().removeModerator(userID);
                channel.sendMessage("Sucessfully removed " + targetUser.mention() + " as Moderator!");
                targetUser.getOrCreatePMChannel().sendMessage("You are no longer Moderator.");
            } else {
                channel.sendMessage("/removeMod <USER ID>");
            }
        }

    }
}
