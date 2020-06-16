package cuft.TeamBot.Commands;

import cuft.TeamBot.TeamBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "info"))
		{
			EmbedBuilder info  = new EmbedBuilder();
			info.setTitle("**Acidic League Team Bot**");
			info.setDescription("Commands:\n"
					+ "`*create [teamname]` - creates a new team\n"
					+ "`*delete` - deletes your team\n"
					+ "`*invite [@player]` - invites player to your team\n"
					+ "`*invites` - lists all invites you have to teams\n"
					+ "`*accept [teamname]` - joins a team\n"
					+ "`*decline [teamname]` - declines a team");
			info.setColor(0xd15f26);
			info.setFooter("Created by Cuft#0633", "https://thumbs.dreamstime.com/b/zarz%C4%85dzania-i-pracy-zespo%C5%82owej-ikona-dru%C5%BCyna-i-grupa-praca-zespo%C5%82owa-ludzie-sojusz-zarz%C4%85dzanie-symbol-ui-sie%C4%87-logo-znak-80268734.jpg");
			
			event.getChannel().sendMessage(info.build()).queue();
			info.clear();
		}
	}
}
