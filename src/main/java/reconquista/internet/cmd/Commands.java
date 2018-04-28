package reconquista.internet.cmd;

import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;

public class Commands {

    private static HashMap<String, CommandExecutor> commands = new HashMap<>();

    public static void registerCommand(String command, CommandExecutor commandExecutor) {
        commands.put(command, commandExecutor);
    }

    public static void executeCommand(String command, IUser user) {
        String[] args = command.contains(" ") ? command.substring(command.indexOf(" ") + 1).split(" ") : new String[]{};
        command = command.contains(" ") ? command.substring(1, command.indexOf(" ")) : command.substring(1);
        CommandExecutor executor = commands.get(command);
        if (executor != null) {
            executor.execute(args, user);
        }
    }

}
