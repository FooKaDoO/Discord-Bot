package Noël_Dorthe.example;

import Noël_Dorthe.example.GuessMeRadioFiles.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class GuessMeRadio extends ListenerAdapter {

    String prefix = "-";
    private JDA jda;

    public GuessMeRadio(List<User> mangijad, JDA jda) {
        this.jda = jda;
    } // TODO: Pane 'jda.removeEventListener(this);' sinna kus tahad mängu lõpetada.

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(prefix+"GuessMeRadio")){
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Hi, GuessMeRadio is alive!").queue();
            event.getGuildChannel();
            Guild guild = event.getGuild();
            MessageChannel channel=event.getChannel();
            Member self = guild.getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if(selfVoiceState.inAudioChannel()){
                channel.sendMessage("I'm already in a voice channel!").queue();
            }
            else if (!memberVoiceState.inAudioChannel()){
                channel.sendMessage("You are not in a voice channel!").queue();
            }
            else{
                AudioManager audioManager = guild.getAudioManager();
                AudioChannel memberChannel = memberVoiceState.getChannel();
                audioManager.openAudioConnection(memberChannel);
                channel.sendMessage("Connecting to: " + memberChannel.getName()).queue();
                musicGuessing(event);

            }
        }
    }

    public void musicGuessing(MessageReceivedEvent event){
        event.getChannel().sendMessage("Here we go!").queue();
        PlayerManager.getInstance().loadAndPlay(event.getTextChannel(), "https://www.youtube.com/watch?v=mMfxI3r_LyA");
        jda.removeEventListener(this);
    }

    //Meetod, et oleks lihtsam ja kiirem rakendada "sleep" funktiooni sekundites.
    public static void sleepSek(int n){
        try {
            TimeUnit.SECONDS.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
