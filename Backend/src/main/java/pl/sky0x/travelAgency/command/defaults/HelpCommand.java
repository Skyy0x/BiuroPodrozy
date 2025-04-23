package pl.sky0x.travelAgency.command.defaults;

import pl.sky0x.travelAgency.command.Command;
import pl.sky0x.travelAgency.command.CommandMap;

public class HelpCommand implements Command {

    private final CommandMap commandMap;

    public HelpCommand(CommandMap commandMap) {
        this.commandMap = commandMap;
    }

    @Override
    public String[] execute(String[] commandArguments) {
        return commandMap.getCommands()
                .values()
                .stream()
                .map(command -> command.getName() + " - " + command.getDescription())
                .toArray(String[]::new);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "type 'help' for more information";
    }
}
