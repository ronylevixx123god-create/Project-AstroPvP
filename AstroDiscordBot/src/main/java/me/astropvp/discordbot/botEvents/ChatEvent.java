package me.astropvp.discordbot.botEvents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class ChatEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String messageSent = event.getMessage().getContentRaw();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.green);
        embed.setTitle("**Welcome to the AstroPvP Discord**");
        embed.setDescription(
                ":wave: Hello, " + event.getMember().getUser().getName() +
                "AstroPvP is a Client- and Factions Discord\n" +
                "\n" +
                "**Server IP** | play.astropvp.net\n" +
                "**Store** | https://buy.astropvp.net/\n" +
                "**Client** | https://client.astropvp.net/\n" +
                "**Website** | https://astropvp.net/"
        );


        embed.setThumbnail(event.getMember().getUser().getAvatarUrl());
        embed.setFooter( "AstroPvP | Discord Bot", event.getGuild().getEmotesByName("astro", false).get(0).getImageUrl());



        if (messageSent.equalsIgnoreCase("hello")) {
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}
