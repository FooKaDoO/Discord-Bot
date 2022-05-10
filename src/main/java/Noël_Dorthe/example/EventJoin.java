package Noël_Dorthe.example;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


/**
 * Welcomes the new person to the server
 *
 * id Channel does not excite then stops
 */

public class EventJoin extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if(!event.getGuild().getId().equals("959763557854101535"))return;

        event.getGuild().getTextChannelById("959763557854101535").sendMessage("Welcome to the server" + event.getMember().getAsMention() +"!").queue();
    }
}
