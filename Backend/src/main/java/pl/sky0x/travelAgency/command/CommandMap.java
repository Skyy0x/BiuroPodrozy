package pl.sky0x.travelAgency.command;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandMap {

    private final Map<String, Command> commands = new ConcurrentHashMap<>();

    public CommandMap register(String name, Command command) {
        commands.put(name, command);

        return this;
    }

    public CommandMap unregister(String name) {
        commands.remove(name);

        return this;
    }

    public String[] invoke(String name, String[] arguments) {
        final Command command = commands.get(name);

        if (command != null) {
            return command.execute(arguments);
        }

        return new String[]{"Command " + name + " doesn't found."};
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
