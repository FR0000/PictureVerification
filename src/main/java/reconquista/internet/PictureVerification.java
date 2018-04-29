package reconquista.internet;

import reconquista.internet.backend.FileBackend;
import reconquista.internet.backend.VerificationBackend;
import reconquista.internet.cmd.*;
import reconquista.internet.listener.MessageListener;
import reconquista.internet.listener.ReactionListener;
import reconquista.internet.manager.PermissionManager;
import reconquista.internet.manager.ShiftManager;
import reconquista.internet.manager.VerificationManager;
import reconquista.internet.thread.AutoSaveThread;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

public class PictureVerification {

    private static PictureVerification instance;
    private static PictureVerificationSettings settings = new PictureVerificationSettings();
    private IDiscordClient discordClient;
    private VerificationBackend backend;
    private PermissionManager permissionManager;
    private VerificationManager verificationManager;
    private ShiftManager shiftManager;
    private Thread autoSaveThread;

    public static void main(String[] args) throws Exception {
        if (args.length > 2) {
            settings.setBotToken(args[0]);
            settings.setVerifiedRoleName(args[1].replaceAll("_", " "));
            settings.setServerID(Long.parseLong(args[2]));
        } else {
            System.out.println("PictureVerifiaction.jar <BotToken> <Name of Verified Role> <ServerID>");
            System.exit(0);
        }
        instance = new PictureVerification();
        instance.init();
        instance.load();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            instance.autoSaveThread.interrupt();
            instance.backend.saveAll();
            instance.discordClient.logout();
        }));
    }

    public static PictureVerification getInstance() {
        return instance;
    }

    public static PictureVerificationSettings getSettings() {
        return settings;
    }

    private void load() throws Exception {
        permissionManager.setModerators(backend.getModerators());
        permissionManager.setAdmins(backend.getAdmins());
        verificationManager.setPendingRequests(backend.getPendingVerificationRequests());
        discordClient.login();
        Thread.sleep(5000L);
        settings.setGuild(discordClient.getGuildByID(settings.getServerID()));
        settings.setVerifiedRole(settings.getGuild().getRolesByName(settings.getVerifiedRoleName()).get(0));
        autoSaveThread = new AutoSaveThread(backend);
        autoSaveThread.start();
    }

    private void init() {
        backend = new FileBackend();
        permissionManager = new PermissionManager();
        verificationManager = new VerificationManager();
        shiftManager = new ShiftManager();
        discordClient = new ClientBuilder().withToken(settings.getBotToken()).build();
        discordClient.getDispatcher().registerListener(new MessageListener());
        discordClient.getDispatcher().registerListener(new ReactionListener());
        registerCommands();
    }

    private void registerCommands() {
        Commands.registerCommand("addMod", new AddModCommand());
        Commands.registerCommand("removeMod", new RemoveModCommand());
        Commands.registerCommand("start", new StartCommand());
        Commands.registerCommand("stop", new StopCommand());

    }

    public IDiscordClient getDiscordClient() {
        return discordClient;
    }

    public VerificationBackend getBackend() {
        return backend;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public VerificationManager getVerificationManager() {
        return verificationManager;
    }

    public ShiftManager getShiftManager() {
        return shiftManager;
    }
}
