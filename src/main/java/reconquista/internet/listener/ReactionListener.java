package reconquista.internet.listener;

import reconquista.internet.PictureVerification;
import reconquista.internet.impl.ShiftUser;
import reconquista.internet.manager.ShiftManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;

public class ReactionListener {

    private ShiftManager shiftManager;

    public ReactionListener() {
        shiftManager = PictureVerification.getInstance().getShiftManager();
    }

    @EventSubscriber
    public void onReactionAdd(ReactionAddEvent reactionAddEvent) {
        new Thread(() -> {
            if (!reactionAddEvent.getUser().isBot()) {
                ShiftUser user = shiftManager.getShiftUser(reactionAddEvent.getUser().getLongID());
                if (user != null) {
                    String name = reactionAddEvent.getReaction().getEmoji().getName();
                    if (name.equals("✅")) {
                        user.acceptCurrentUser();
                    }
                    if (name.equals("❌")) {
                        user.declineCurrentUser();
                    }
                }
            }
        }).start();

    }
}
