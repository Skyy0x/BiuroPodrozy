package pl.sky0x.travelAgency.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sky0x.travelAgency.command.defaults.HelpCommand;
import pl.sky0x.travelAgency.command.defaults.UserManageCreateCommand;
import pl.sky0x.travelAgency.service.AuthenticateService;

import java.util.Arrays;
import java.util.Scanner;

@Component
public class CommandListener implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CommandListener.class);

    private final CommandMap commandMap;

    @Autowired
    private final AuthenticateService authenticateService;

    public CommandListener(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
        this.commandMap = new CommandMap();

        Arrays.asList(
                        new UserManageCreateCommand(authenticateService),
                        new HelpCommand(commandMap)
                ).forEach(command -> {
                    commandMap.register(command.getName(), command);
        });
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Command listener started. Type 'help' for a list of commands");
        while (true){
            final Scanner scanner = new Scanner(System.in);

            try {
                System.out.print(">> ");
                final String[] input = scanner.nextLine().split(" ");

                for (String message : commandMap.invoke(input[0], Arrays.copyOfRange(input, 1, input.length))) {
                    logger.info(message);
                }
            } catch (Exception exception) {
                logger.error(exception.getMessage());
            }
        }
    }
}
