package reconquista.internet;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;

public class PictureVerificationSettings {
    private String botToken;
    private long serverID;
    private String verifiedRoleName;
    private IGuild guild;
    private IRole verifiedRole;

    public IGuild getGuild() {
        return guild;
    }

    public void setGuild(IGuild guild) {
        this.guild = guild;
    }

    public IRole getVerifiedRole() {
        return verifiedRole;
    }

    public void setVerifiedRole(IRole verifiedRole) {
        this.verifiedRole = verifiedRole;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public long getServerID() {
        return serverID;
    }

    public void setServerID(long serverID) {
        this.serverID = serverID;
    }

    public String getVerifiedRoleName() {
        return verifiedRoleName;
    }

    public void setVerifiedRoleName(String verifiedRoleName) {
        this.verifiedRoleName = verifiedRoleName;
    }

}
