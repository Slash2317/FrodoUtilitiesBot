package com.slash.frodoutilitiesbot.request;

import java.util.Collections;
import java.util.List;

import static com.slash.frodoutilitiesbot.request.CommandGroup.*;

public enum Command {

    MUTE("f!mute", MODERATION, "Mutes a user (seconds)", 0, List.of("user", "duration")),
    UN_MUTE("f!unmute", MODERATION, "Un-mutes a user", 0, List.of("user")),
    KICK("f!kick", MODERATION, "Kicks a user", 0, List.of("user", "reason")),
    BAN("f!ban", MODERATION, "Bans a user", 0, List.of("user", "reason")),
    UNBAN("f!unban", MODERATION, "Unbans a user", 0, List.of("user")),
    ROLE_GIVE("f!rolegive", MODERATION, "Adds a role to a user", 0, List.of("user", "role")),
    ROLE_REMOVE("f!roleremove", MODERATION, "Removes a role from a user", 0, List.of("user", "role")),
    MEMBER_INFO("f!member info", SERVER, "Provides information about a certain user", 0, List.of("user")),
    SERVER_INFO("f!server info", SERVER, "Provides server information"),
    ECHO("f!echo", FUN, "Says the requested content", 0, List.of("message")),
    COIN_FLIP("f!coinflip", FUN, "Flips a coin", 0),
    ROLL_DICE("f!rolldice", FUN, "Rolls a coin", 0),
    HELP("f!help", MISC, "Shows list of commands");

    private final String commandName;
    private final String description;
    private final List<String> parameters;
    private final int suffixLength;
    private final CommandGroup commandGroup;

    Command(String commandName, CommandGroup commandGroup, String description, int suffixLength, List<String> parameters) {
        this.commandName = commandName;
        this.commandGroup = commandGroup;
        this.description = description;
        this.parameters = parameters;
        this.suffixLength = suffixLength;
    }

    Command(String commandName, CommandGroup commandGroup, String description, int suffixLength) {
        this(commandName, commandGroup, description, suffixLength, Collections.emptyList());
    }

    Command(String commandName, CommandGroup commandGroup, String description) {
        this(commandName, commandGroup, description, 0, Collections.emptyList());
    }

    public String getCommandName() {
        return commandName;
    }

    public CommandGroup getCommandGroup() {
        return commandGroup;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public int getSuffixLength() {
        return suffixLength;
    }

    public boolean isPrefix() {
        return suffixLength > 0;
    }

    public static Command getCommand(String message) {
        for (Command command : Command.values()) {
            if (message.equals(command.commandName) ||
                    message.startsWith(command.commandName + " ") ||
                    (command.isPrefix() && message.startsWith(command.commandName))) {
                return command;
            }
        }
        return null;
    }

    public String getCommandFormat() {
        StringBuilder sb = new StringBuilder(commandName);

        if (suffixLength > 0) {
            sb.append("#".repeat(suffixLength));
        }
        if (!parameters.isEmpty()) {
            sb.append(" [" + String.join("] [", parameters) + "]");
        }
        return sb.toString();
    }

    public String getFullDescription() {
        return getCommandFormat() + " | " + description;
    }
}
