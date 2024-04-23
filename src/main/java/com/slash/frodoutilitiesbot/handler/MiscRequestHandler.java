package com.slash.frodoutilitiesbot.handler;

import com.slash.frodoutilitiesbot.domain.RPSOption;
import com.slash.frodoutilitiesbot.request.Command;
import com.slash.frodoutilitiesbot.request.CommandGroup;
import com.slash.frodoutilitiesbot.request.RequestContext;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MiscRequestHandler {

    public void handleHelpCommand(RequestContext requestContext) {
        Map<CommandGroup, List<Command>> groupToCommands = Arrays.stream(Command.values()).filter(c -> !c.isHidden()).collect(Collectors.groupingBy(Command::getCommandGroup));

        StringBuilder sb = new StringBuilder();
        sb.append("""
                **FRODO UTILITIES | COMMANDS**
                ------------------------------------------------""");

        for (CommandGroup group : CommandGroup.values()) {
            sb.append("\n\n" + group.getTitle() + "\n");
            sb.append(groupToCommands.get(group).stream().map(c -> c.getFullDescription()).collect(Collectors.joining("\n")));
        }

        requestContext.event().getChannel().sendMessage(sb.toString()).queue();
    }

    public void handleRPSCommand(RequestContext requestContext) {
        try {
            if (requestContext.arguments() == null || requestContext.arguments().isBlank()) {
                throw new IllegalArgumentException("No arguments");
            }

            RPSOption userOption = RPSOption.find(requestContext.arguments());

            if (userOption == null) {
                throw new IllegalArgumentException("Invalid argument");
            }

            if (userOption == RPSOption.SPOCK || userOption == RPSOption.LIZARD) {
                requestContext.event().getChannel().sendMessage("...you're not like the rest. Try f!rpsls next time").queue();
                return;
            }

            int option = (int) Math.floor(Math.random() * 3);
            RPSOption botOption = RPSOption.STANDARD_OPTIONS[option];

            if (userOption == botOption) {
                requestContext.event().getChannel().sendMessage(botOption.getMessage() + "\nDraw!").queue();
            }
            else if (userOption.getBeatsOptions().contains(botOption.name())) {
                requestContext.event().getChannel().sendMessage(botOption.getMessage() + "\nYou win!").queue();
            }
            else if (botOption.getBeatsOptions().contains(userOption.name())) {
                requestContext.event().getChannel().sendMessage(botOption.getMessage() + "\nI win!").queue();
            }
        }
        catch (IllegalArgumentException e) {
            requestContext.event().getChannel().sendMessage("The command must follow this format `" + requestContext.command().getFullDescription() + "`").queue();
        }
    }

    public void handleRPSLSCommand(RequestContext requestContext) {
        try {
            if (requestContext.arguments() == null || requestContext.arguments().isBlank()) {
                throw new IllegalArgumentException("No arguments");
            }

            RPSOption userOption = RPSOption.find(requestContext.arguments());

            if (userOption == null) {
                throw new IllegalArgumentException("Invalid argument");
            }


            int option = (int) Math.floor(Math.random() * 5);
            RPSOption botOption = RPSOption.values()[option];

            if (userOption == botOption) {
                requestContext.event().getChannel().sendMessage(botOption.getMessage() + "\nDraw!").queue();
            }
            else if (userOption.getBeatsOptions().contains(botOption.name())) {
                requestContext.event().getChannel().sendMessage(botOption.getMessage() + "\nYou win!").queue();
            }
            else if (botOption.getBeatsOptions().contains(userOption.name())) {
                requestContext.event().getChannel().sendMessage(botOption.getMessage() + "\nI win!").queue();
            }
        }
        catch (IllegalArgumentException e) {
            requestContext.event().getChannel().sendMessage("The command must follow this format `" + requestContext.command().getFullDescription() + "`").queue();
        }
    }

    public void handleCoinFlipCommand(RequestContext requestContext) {
        int option = (int) Math.floor(Math.random() * 6000);

        if (option == 0) {
            requestContext.event().getChannel().sendMessage(":new_moon_with_face: It landed on the side").queue();
        }
        else {
            option = (int) Math.floor(Math.random() * 2);

            if (option == 0) {
                requestContext.event().getChannel().sendMessage(":coin: Heads").queue();
            }
            else {
                requestContext.event().getChannel().sendMessage(":coin: Tails").queue();
            }
        }
    }

    public void handleRollDiceCommand(RequestContext requestContext) {
        int sides = 6;
        if (requestContext.arguments() != null && !requestContext.arguments().isBlank()) {
            try {
                sides = Integer.parseInt(requestContext.arguments());
                if (sides < 1) {
                    sides = 6;
                }
            }
            catch (NumberFormatException ignored) {

            }
        }

        int option = (int) Math.floor(Math.random() * sides) + 1;
        requestContext.event().getChannel().sendMessage(":game_die: " + option).queue();
    }

    public void handleEchoCommand(RequestContext requestContext) {
        if (requestContext.arguments() != null && !requestContext.arguments().isBlank()) {
            requestContext.event().getChannel().sendMessage(requestContext.arguments()).queue();
        }
    }

    public void handleInviteCommand(RequestContext requestContext) {
        String inviteUrl = requestContext.event().getJDA().getInviteUrl(
                Permission.MANAGE_ROLES,
                Permission.KICK_MEMBERS,
                Permission.BAN_MEMBERS,
                Permission.CREATE_INSTANT_INVITE,
                Permission.MODERATE_MEMBERS,
                Permission.MESSAGE_SEND,
                Permission.MESSAGE_SEND_IN_THREADS,
                Permission.MESSAGE_MANAGE,
                Permission.MESSAGE_EMBED_LINKS,
                Permission.USE_APPLICATION_COMMANDS
        );

        requestContext.event().getChannel().sendMessage("""
                Here is our bot invite link! :smiley:
                We're glad to hear you want to invite Frodo Utilities in your Discord Server, thank you so much!
                :link: BOT INVITE LINK: http://bit.ly/frodoutilitiesinvite
                :question: Having Trouble? Try this link instead:  [INVITE](""" + inviteUrl + ")").queue();
    }
}
