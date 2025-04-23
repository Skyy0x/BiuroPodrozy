package pl.sky0x.travelAgency.command;

public interface Command {

    String[] execute(String[] commandArguments);

    String getName();

    default String getDescription() {
        return "";
    }
}
