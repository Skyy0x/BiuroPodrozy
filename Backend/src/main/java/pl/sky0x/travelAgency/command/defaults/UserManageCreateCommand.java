package pl.sky0x.travelAgency.command.defaults;

import pl.sky0x.travelAgency.command.Command;
import pl.sky0x.travelAgency.model.user.Role;
import pl.sky0x.travelAgency.service.AuthenticateService;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UserManageCreateCommand implements Command {

    private final AuthenticateService authenticateService;

    public UserManageCreateCommand(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @Override
    public String[] execute(String[] commandArguments) {
        if(commandArguments.length != 2) {
            return new String[]{"Usage: user <username> <password>"};
        }

        try {
            authenticateService.register(commandArguments[0], commandArguments[1]);
        } catch (Exception exception) {
            return new String[]{exception.getMessage()};
        }

        return new String[]{"User " + commandArguments[0] + " created."};
    }

    @Override
    public String getName() {
        return "user";
    }

    @Override
    public String getDescription() {
        return "Creating user using command line.";
    }
}
