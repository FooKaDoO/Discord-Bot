package Noël_Dorthe.example;

import Noël_Dorthe.example.GuessMeRadioFiles.GuildMusicManager;
import Noël_Dorthe.example.GuessMeRadioFiles.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;


public class GuessMeRadio extends ListenerAdapter {

    String prefix = "-";
    private final JDA jda;
    String previousLink=null;
    int rightAnswers=0;

    public GuessMeRadio(JDA jda) {
        this.jda = jda;
    } // TODO: Pane 'jda.removeEventListener(this);' sinna kus tahad mängu lõpetada.

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String pref=prefix+"GuessMeRadio";
        String[] args = event.getMessage().getContentRaw().split(" ", 3);
        if(args[0].equalsIgnoreCase(pref)) {
            event.getChannel().sendTyping().queue();
            switch (args[1]) {
                case "guess":
                    if (args.length < 3) {
                        event.getChannel().sendMessage("This is not a valid guess command! The command has to be '-GuessMeRadio guess ...'").queue();
                    }
                    else if (guessCommand(event, args[2])) {
                        songInfoAndSkip(event);
                        rightAnswers+=1;
                    }
                    break;
                case "stop":
                    previousLink=stopCommand(event);
                    break;
                case "resume":
                    if(previousLink==null){
                        event.getChannel().sendMessage("The track is playing. To resume, you have to stop it first with '-GuessMeRadio stop'.").queue();
                    }
                    resumeCommand(event,previousLink);
                    previousLink=null;
                    break;
                case "skip":
                    skipCommand(event);
                    break;
                case "play":
                    if (args.length < 3 || args[2].isEmpty()) {
                        event.getChannel().sendMessage("This is not a valid guess command! The command has to be '-GuessMeRadio play <link>'").queue();
                        break;
                    }
                    playCommand(event,args[2]);
                    break;
                case "leave":
                    event.getChannel().sendMessage("Okayy, game over! You answered " +rightAnswers+ " song(s) right. Seeya later!").queue();
                    leaveCommand(event);
                    jda.removeEventListener(this);
                    break;
                default:
                    event.getChannel().sendMessage("This is not a valid command. To get more information on commands, type '-GuessMeRadio'.").queue();
                    break;
                }
        }
    }

    private void songInfoAndSkip(MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        MessageChannel channel=event.getChannel();
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        AudioPlayer audioPlayer = musicManager.audioPlayer;
        AudioTrack playingTrack = audioPlayer.getPlayingTrack();
        AudioTrackInfo info = playingTrack.getInfo();
        channel.sendMessage("This is a track called: '" + info.title + "' by " + info.author).queue();
        musicManager.scheduler.nextTrack();
        channel.sendMessage("Great job on this one! Here is a new one :)").queue();

    }

    public boolean startCommand(@NotNull MessageReceivedEvent event){
        Guild guild = event.getGuild(); //Sama, mis videotes ctx
        MessageChannel channel=event.getChannel();
        Member self = guild.getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()){
            channel.sendMessage("You are not in a voice channel!").queue();
            return false;
        }
        else{
            AudioManager audioManager = guild.getAudioManager();
            AudioChannel memberChannel = memberVoiceState.getChannel();
            audioManager.openAudioConnection(memberChannel);
            channel.sendMessageFormat("Connecting to:  \uD83D\uDD0A %s", memberChannel.getName()).queue();
            return true;
        }
    }

    public boolean musicGuessing(MessageReceivedEvent event, String[] args) throws InterruptedException {
        Guild guild = event.getGuild();
        MessageChannel channel=event.getChannel();
        if(!startCommand(event))
            return false;
        else if (args.length<3 || args[2].isEmpty() ){
            channel.sendMessage("Please provide a link with the message like this: '-GuessMeRadio start <link> ").queue();
            return false;
        }
        String link= args[2];
        if(!isUrl(link)){
            link= "ytsearch:" + link;
        }
        PlayerManager.getInstance().loadAndPlay(event.getTextChannel(), link);
        return true;
    }

    private static boolean isUrl(String url){
        try{
            new URI(url);
            return true;
        } catch(URISyntaxException e){
            return false;
        }
    }
    private static boolean guessCommand(MessageReceivedEvent event, String guess){
        Guild guild = event.getGuild();
        MessageChannel channel=event.getChannel();
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        AudioPlayer audioPlayer = musicManager.audioPlayer;
        AudioTrack playingTrack = audioPlayer.getPlayingTrack();
        AudioTrackInfo info = playingTrack.getInfo();
        String[] title = info.title.split(" ");
        String[] guessing= guess.split(" ");
        for (String s : title) {
            for (String s1 : guessing) {
                if(s.equalsIgnoreCase(s1)){
                    channel.sendMessage("This is the right answer!").queue();
                    return true;
                }
            }
        }
        channel.sendMessage("Bing-Bing-Bong, your answer is wrong!").queue();
        return false;
    }

    private static void skipCommand(MessageReceivedEvent event){
        Guild guild = event.getGuild(); //Sama, mis videotes ctx
        MessageChannel channel=event.getChannel();
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        AudioPlayer audioPlayer = musicManager.audioPlayer;
        if (audioPlayer.getPlayingTrack()==null){
            channel.sendMessage("There is no track playing. If you have stopped the track, use '-GuessMeRadio resume'" +
                    "or play a new track with '-GuessMeRadio play <link>'.").queue();
            return;
        }
        musicManager.scheduler.nextTrack();
        channel.sendMessage("Skipped the track. Here is a new one :)").queue();
    }

    private static String stopCommand(MessageReceivedEvent event){
        Guild guild = event.getGuild(); //Sama, mis videotes ctx
        MessageChannel channel=event.getChannel();
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        AudioTrack playingTrack = musicManager.scheduler.player.getPlayingTrack();
        String previousLink = playingTrack.getInfo().uri;
        musicManager.scheduler.player.stopTrack();
        channel.sendMessage("The audio has been stopped. To resume, use '-GuessMeRadio resume' command.").queue();
        return previousLink;
    }

    public static void resumeCommand(MessageReceivedEvent event, String previousLink){
        Guild guild = event.getGuild();
        MessageChannel channel=event.getChannel();
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        AudioPlayer audioPlayer = musicManager.audioPlayer;
        PlayerManager.getInstance().loadAndPlay(event.getTextChannel(), previousLink);
        channel.sendMessage("The audio has been resumed.").queue();
    }

    public static void playCommand(MessageReceivedEvent event, String args){
        Guild guild = event.getGuild();
        MessageChannel channel=event.getChannel();
        stopCommand(event);
        String link= args;
        if(!isUrl(link)){
            link= "ytsearch:" + link;
        }
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.scheduler.queue.clear();
        PlayerManager.getInstance().loadAndPlay(event.getTextChannel(), link);
        return;
    }
    private static void leaveCommand(MessageReceivedEvent event){
        Guild guild = event.getGuild(); //Sama, mis videotes ctx
        AudioManager audioManager = guild.getAudioManager();
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.scheduler.queue.clear();
        musicManager.audioPlayer.stopTrack();
        audioManager.closeAudioConnection();

    }
}
