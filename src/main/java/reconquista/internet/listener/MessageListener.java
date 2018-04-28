package reconquista.internet.listener;

import reconquista.internet.PictureVerification;
import reconquista.internet.cmd.Commands;
import reconquista.internet.impl.PendingVerificationRequest;
import reconquista.internet.manager.VerificationManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class MessageListener {

    private VerificationManager verificationManager;

    public MessageListener() {
        this.verificationManager = PictureVerification.getInstance().getVerificationManager();
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {
        new Thread(() -> {
            IUser author = event.getAuthor();
            long ID = author.getLongID();
            String message = event.getMessage().getContent();
            IChannel channel = event.getChannel();
            if (channel.isPrivate()) {
                if (message.startsWith("/")) {
                    Commands.executeCommand(message, author);
                }
                //User Handling
                List<IMessage.Attachment> attachmentList = event.getMessage().getAttachments();
                if (attachmentList.size() > 0) {
                    IMessage.Attachment picture = event.getMessage().getAttachments().get(0);
                    String url = picture.getUrl().toLowerCase();
                    if (url.endsWith("jpg") || url.endsWith("png") || url.endsWith("tff") || url.endsWith("jpeg") || url.endsWith("bmp")) {
                        if (!verificationManager.isAlreadyPending(ID)) {
                            verificationManager.addPendingVerificationRequest(new PendingVerificationRequest(ID, picture.getUrl()));
                            channel.sendMessage("Vielen Dank. Sobald ein Moderator deine Verifizierung geprüft hat, wirst du freigeschalten.");
                        } else {
                            channel.sendMessage("Wir haben deine Verifizierungsanfrage bereits erhalten, bitte gedulde dich noch etwas.");
                        }

                    } else {
                        channel.sendMessage("Du musst ein Bild senden!");
                    }
                }

            }
        }).start();


    }


}
