package reconquista.internet.cmd;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

public class AddModCommand extends CommandExecutor {

    @Override
    public void execute(String[] args, IUser user) {
        if (permissionManager().isAdmin(user)) {
            IChannel channel = discordClient().getOrCreatePMChannel(user);
            if (args.length > 0) {
                String modID = args[0];
                long userID = Long.parseLong(modID);
                IUser targetUser = discordClient().getUserByID(userID);
                permissionManager().addModerator(userID);
                channel.sendMessage("Sucessfully added " + targetUser.mention() + " as Moderator!");
                targetUser.getOrCreatePMChannel().sendMessage(user.mention() + " promoted you to Moderator! Congratz!");
            } else {
                channel.sendMessage("/addMod <USER ID>");
            }
        }

    }
}
