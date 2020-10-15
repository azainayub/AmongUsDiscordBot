package com.azain.amougusbot.command.commands.amongus;

import com.azain.amougusbot.command.CommandContext;
import com.azain.amougusbot.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.io.IOException;
import java.util.List;

public class ShhhCommand implements ICommand
{

    @Override
    public void handle(CommandContext ctx)
    {
        final Member member = ctx.getMember();
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();

        if(!member.hasPermission(Permission.VOICE_MUTE_OTHERS))
        {
            channel.sendMessage(":x: You need to have `Mute Others` permission to use this command").queue();
            return;
        }

        if(!self.hasPermission(Permission.VOICE_MUTE_OTHERS))
        {
            channel.sendMessage(":x: I don't have permission to mute others!").queue();
            return;
        }


        final GuildVoiceState memberVS = member.getVoiceState();
        final VoiceChannel voiceChannel = memberVS.getChannel();

        if(!memberVS.inVoiceChannel())
        {
            channel.sendMessage("You need to be in Voice Channel to use this command").queue();
            return;
        }

        List<Member> members = voiceChannel.getMembers();

        members.forEach(member1 -> {
            if(!member1.getVoiceState().isGuildMuted())
                member1.mute(true).reason("Game Started!").reason("Game Started!").queue();
            else
                channel.sendMessage( member1.getAsMention() + " is already `muted`!" ).queue();
        });

        ctx.getMessage().delete().queue();
    }

    @Override
    public String getName() {
        return "shhh";
    }

    @Override
    public String getHelp() {
        return "Mutes all member in voice channel";
    }

    @Override
    public List<String> getAliases() {
        return List.of("sh", "shh");
    }
}
