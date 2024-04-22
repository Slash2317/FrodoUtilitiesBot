package com.slash.frodoutilitiesbot;

import com.slash.frodoutilitiesbot.handler.*;
import com.slash.frodoutilitiesbot.request.RequestContext;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class FrodoUtilitiesBotListener extends ListenerAdapter {

    private InfoRequestHandler infoRequestHandler;
    private ModerationRequestHandler moderationRequestHandler;
    private MiscRequestHandler miscRequestHandler;

    public FrodoUtilitiesBotListener() {
        this.infoRequestHandler = new InfoRequestHandler();
        this.moderationRequestHandler = new ModerationRequestHandler();
        this.miscRequestHandler = new MiscRequestHandler();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        RequestContext requestContext = RequestContext.from(event);

        if (requestContext.command() == null) {
            return;
        }

        switch (requestContext.command()) {
            case MUTE -> moderationRequestHandler.handleMuteCommand(requestContext);
            case UN_MUTE -> moderationRequestHandler.handleUnMuteCommand(requestContext);
            case KICK -> moderationRequestHandler.handleKickCommand(requestContext);
            case BAN -> moderationRequestHandler.handleBanCommand(requestContext);
            case UNBAN -> moderationRequestHandler.handleUnbanCommand(requestContext);
            case ROLE_GIVE -> moderationRequestHandler.handleRoleGiveCommand(requestContext);
            case ROLE_REMOVE -> moderationRequestHandler.handleRoleRemoveCommand(requestContext);
            case MEMBER_INFO -> infoRequestHandler.handleMemberInfoCommand(requestContext);
            case SERVER_INFO -> infoRequestHandler.handleServerInfoCommand(requestContext);
            case ECHO -> miscRequestHandler.handleEchoCommand(requestContext);
            case ROCK_PAPER_SCISSORS -> miscRequestHandler.handleRPSCommand(requestContext);
            case ROCK_PAPER_SCISSORS_LIZARD_SPOCK -> miscRequestHandler.handleRPSLSCommand(requestContext);
            case COIN_FLIP -> miscRequestHandler.handleCoinFlipCommand(requestContext);
            case ROLL_DICE -> miscRequestHandler.handleRollDiceCommand(requestContext);
            case HELP -> miscRequestHandler.handleHelpCommand(requestContext);
            case INVITE -> miscRequestHandler.handleInviteCommand(requestContext);
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();

        if (command.equals("help")) {
            event.reply("This bot doesn't support slash commands. Please use the f! prefix e.g. f!help").queue();
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("help", "Gives info about how to use this bot"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
