package reconquista.internet.impl;

import com.vdurmont.emoji.EmojiManager;
import reconquista.internet.PictureVerification;
import reconquista.internet.util.Utils;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;

import java.io.File;

public class ShiftUser {

    private long id;
    private PendingVerificationRequest currentPendingVerificationRequest;
    private boolean done = false;

    public ShiftUser(long id) {
        this.id = id;
    }

    public void newUser() {
        if (!done) {
            PictureVerification pictureVerification = PictureVerification.getInstance();
            IDiscordClient discordClient = pictureVerification.getDiscordClient();
            IChannel channel = discordClient.getOrCreatePMChannel(discordClient.getUserByID(id));
            this.currentPendingVerificationRequest = pictureVerification.getVerificationManager().getNextRequest();
            if (currentPendingVerificationRequest != null) {
                try {
                    File file = Utils.fromURL(currentPendingVerificationRequest.getUrl());
                    IMessage iMessage = channel.sendFile(file);
                    Utils.destroyFile(file);
                    iMessage.addReaction(EmojiManager.getForAlias("white_check_mark"));
                    Thread.sleep(250L);
                    iMessage.addReaction(EmojiManager.getForAlias("x"));
                } catch (Exception ignored) {
                }
            } else {
                channel.sendMessage("Leider keine Arbeit mehr! Darum endet deine Schicht jetzt");
                pictureVerification.getShiftManager().stopShift(id);
            }
        }
    }

    public PendingVerificationRequest getCurrentPendingVerificationRequest() {
        return currentPendingVerificationRequest;
    }

    public void acceptCurrentUser() {
        PictureVerification pictureVerification = PictureVerification.getInstance();
        IDiscordClient client = pictureVerification.getDiscordClient();
        IGuild guild = PictureVerification.getSettings().getGuild();
        IRole role = PictureVerification.getSettings().getVerifiedRole();
        guild.getUserByID(currentPendingVerificationRequest.getUserId()).addRole(role);
        new Thread(this::newUser).start();
    }

    public void declineCurrentUser() {
        PictureVerification pictureVerification = PictureVerification.getInstance();
        IDiscordClient client = pictureVerification.getDiscordClient();
        client.getOrCreatePMChannel(client.getUserByID(currentPendingVerificationRequest.getUserId())).sendMessage("Deine Anfrage wurde leider abgelehnt.");
        new Thread(this::newUser).start();
    }

    public void stop() {
        done = true;
    }

    public long getId() {
        return id;
    }
}
