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
    ROCK_PAPER_SCISSORS("f!rps", FUN, "Play RPS with the bot", 0, List.of("rock/paper/scissors")),
    ROCK_PAPER_SCISSORS_LIZARD_SPOCK("f!rpsls", FUN, "Play RPSLS with the bot", 0, List.of("rock/paper/scissors/lizard/spock"), true),
    COIN_FLIP("f!flipcoin", FUN, "Flips a coin"),
    ROLL_DICE("f!rolldice", FUN, "Rolls a dice (default 6 sides)", 0, List.of("sides:6")),
    HELP("f!help", MISC, "Shows list of commands"),
    SLASH_HELP("/help", MISC, "Alternative Help command which explains we only support f! prefix."),
    INVITE("f!invite", MISC, "Shows link to invite the bot");

    private final String commandName;
    private final String description;
    private final List<String> parameters;
    private final int suffixLength;
    private final CommandGroup commandGroup;
    private final boolean hidden;

    Command(String commandName, CommandGroup commandGroup, String description, int suffixLength, List<String> parameters, boolean hidden) {
        this.commandName = commandName;
        this.commandGroup = commandGroup;
        this.description = description;
        this.parameters = parameters;
        this.suffixLength = suffixLength;
        this.hidden = hidden;
    }

    Command(String commandName, CommandGroup commandGroup, String description, int suffixLength, List<String> parameters) {
        this(commandName, commandGroup, description, suffixLength, parameters, false);
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

    public boolean isHidden() {
        return hidden;
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
