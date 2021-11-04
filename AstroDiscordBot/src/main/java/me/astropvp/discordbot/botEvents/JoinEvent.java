package me.astropvp.discordbot.botEvents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class JoinEvent extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        JDA client = event.getJDA();
        TextChannel channel = client.getTextChannelById("811262959094333512");

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


        assert channel != null;
        channel.sendMessage(embed.build()).queue();

    }
}