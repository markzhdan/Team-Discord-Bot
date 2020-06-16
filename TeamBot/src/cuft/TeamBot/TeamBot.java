package cuft.TeamBot;

import javax.security.auth.login.LoginException;

import cuft.TeamBot.Commands.ColorChooserEvent;
import cuft.TeamBot.Commands.Info;
import cuft.TeamBot.Commands.TeamCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class TeamBot {
	public static JDA jda;
	public static String prefix = "*";
	
	//Main Method
	public static void main(String args[]) throws LoginException
	{
		Token tkn = new Token();
		jda = JDABuilder.createDefault(tkn.getToken()).enableIntents(GatewayIntent.GUILD_MEMBERS).setActivity(Activity.playing("Acidic League")).build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		
		jda.addEventListener(new Info());
		jda.addEventListener(new TeamCommands());
		jda.addEventListener(new ColorChooserEvent());
		
		/*
		Old event Listeners
		jda.addEventListener(new CreateTeam());
		jda.addEventListener(new OLDGuildMessageReactionAdd());
		*/
	}
}
