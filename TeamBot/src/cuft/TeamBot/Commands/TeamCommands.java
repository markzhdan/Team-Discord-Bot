//make sure if person has invites and wants to make a team, the invites go away

package cuft.TeamBot.Commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cuft.TeamBot.TeamBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class TeamCommands extends ListenerAdapter{
	
	//Creates team name
	public String createTeamName(String[] usersMessage)
	{
		String name = "";
		for(int i = 1; i < usersMessage.length; i++)
		{
			name += usersMessage[i] + " ";
		}
		return name.trim();
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		Utility util = new Utility();
		final Guild guild = event.getGuild();
		//Separates Message into args
		String[] args = event.getMessage().getContentRaw().split(" ");
		final String teamName = createTeamName(args);
		
		//all of user's roles
		List<Role> allUserRoles = event.getMember().getRoles(); 
		
		//Create team command
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "create"))
		{
			if(util.isOnTeam(allUserRoles))
			{
				event.getChannel().sendMessage("You Are Already On A Team!").queue();
			}
			else if(args.length <= 1)
			{
				event.getChannel().sendMessage("You Must Enter In A Team Name!").queue();
			}
			else if(util.teamExists(guild.getRoles(), teamName))
			{
				event.getChannel().sendMessage("This Team Already Exists!").queue();
			}
			else
			{
				//Creates team role and captain role and adds its to the player
				guild.createRole().setName("Team " + teamName).queue(role ->
					guild.addRoleToMember(event.getMember(), role).queue());
			
				guild.createRole().setName("Team " + teamName + " Captain").queue(role ->
					guild.addRoleToMember(event.getMember(), role).queue());
				
				//Color chooser pops up right after
				EmbedBuilder colorChooser  = new EmbedBuilder();
				colorChooser.setTitle("Choose A Team Color");
				colorChooser.setColor(Color.black);

				event.getChannel().sendMessage(colorChooser.build()).queue(new Consumer<Message>() {
		            @Override
		            public void accept(Message message) {
		            	//red
		                message.addReaction("🟥").queue();
		                //blue
		                message.addReaction("🟦").queue();
		                //green
		                message.addReaction("🟩").queue();
		                //yellow
		                message.addReaction("🟨").queue();
		                //purple
		                message.addReaction("🟪").queue();
		            }
		        });
				colorChooser.clear();
			}
		}
		
		//Delete command
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "delete"))
		{
			if(!util.isOnTeam(allUserRoles))
			{
				event.getChannel().sendMessage("You Are Not On A Team!").queue();
			}
			else if(!util.isCaptain(allUserRoles))
			{
				event.getChannel().sendMessage("You Are Not The Captain Of Your Team!").queue();
			}
			else
			{
				String tName = util.getUserTeam(allUserRoles).getName();
				for(Role role : util.allUsersTeamRoles(allUserRoles))
				{
					role.delete().queue();
				}
				//hotfix CHANGE
				if(util.isAlreadyInvited(guild.getRoles(), util.getUserTeam(allUserRoles).getName()))
				{
					guild.getRolesByName("Invited To " + util.getUserTeam(allUserRoles).getName(), true).get(0).delete().queue();
				}
				event.getChannel().sendMessage("```" + tName + " Successfully Deleted By " + event.getMember().getUser().getName() + "!```").queue();
			}
		}
		
		//Invite command
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "invite"))
		{
			List<Member> emptyList = new ArrayList<Member>();
			
			if(!util.isOnTeam(allUserRoles))
			{
				event.getChannel().sendMessage("You Are Not On A Team!").queue();
			}
			else if(!util.isCaptain(allUserRoles))
			{
				event.getChannel().sendMessage("You Are Not The Captain Of Your Team!").queue();
			}
			else if(event.getMessage().getMentionedMembers().equals(emptyList))
			{
				event.getChannel().sendMessage("You Must @ The Player You Want To Invite").queue();
			}
			else if(util.isAlreadyInvited(event.getMessage().getMentionedMembers().get(0).getRoles(), util.getUserTeam(allUserRoles).getName()))
			{
				event.getChannel().sendMessage("You Have Already Invited This Player!").queue();
			}
			else if(util.isOnTeam(event.getMessage().getMentionedMembers().get(0).getRoles()))
			{
				event.getChannel().sendMessage(event.getMessage().getMentionedMembers().get(0).getUser().getName() + " Is Already On A Team!").queue();
			}
			else
			{
				if(util.inviteExists(guild.getRoles(), util.getUserTeam(allUserRoles).getName()))
				{
					guild.addRoleToMember(event.getMessage().getMentionedMembers().get(0), guild.getRolesByName("Invited To " + util.getUserTeam(allUserRoles).getName(), false).get(0)).queue();;
				}
				else
				{
					guild.createRole().setName("Invited To " + util.getUserTeam(allUserRoles).getName()).queue(role ->
					guild.addRoleToMember(event.getMessage().getMentionedMembers().get(0), role).queue());
				}
				
				event.getChannel().sendMessage("```You Successfully invited " + event.getMessage().getMentionedMembers().get(0).getEffectiveName() + " To " + util.getUserTeam(allUserRoles).getName() + "!```").queue();
			}
		}
		
		//Views all current invites
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "invites"))
		{
			List<Role> emptyList = new ArrayList<Role>();
			if(util.getAllTeamInvites(allUserRoles).equals(emptyList))
			{
				event.getChannel().sendMessage("You Have No Invites To Any Teams!").queue();
			}
			else
			{
				EmbedBuilder invites  = new EmbedBuilder();
				invites.setTitle("All Current Invites:");
				invites.setColor(Color.black);
				
				for(Role role : util.getAllTeamInvites(allUserRoles))
				{
					invites.addField("------------", role.getName(), false);
				}
				event.getChannel().sendMessage(invites.build()).queue();
				invites.clear();
			}
		}
		
		//Join Command
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "accept"))
		{
			//if invited to team method
			List<Role> emptyList = new ArrayList<Role>();
			if(util.getAllTeamInvites(allUserRoles).equals(emptyList))
			{
				event.getChannel().sendMessage("You Have No Invites To Any Teams!").queue();
			}
			else if(args.length <= 1)
			{
				event.getChannel().sendMessage("Enter A Team To Accept Their Invite!").queue();
			}
			else if(!util.teamExists(guild.getRoles(), teamName))
			{
				event.getChannel().sendMessage(teamName + " Does Not Exist!").queue();
			}
			else if(!util.isInvitedToTeam(util.getAllTeamInvites(allUserRoles), teamName))
			{
				event.getChannel().sendMessage("You Are Not Invited To This Team!").queue();
			}
			else
			{
				
				guild.addRoleToMember(event.getMember(), guild.getRolesByName("Team " + teamName, true).get(0)).queue();
				guild.removeRoleFromMember(event.getMember(), guild.getRolesByName("Invited To Team " + teamName, true).get(0)).queue();
				
				event.getChannel().sendMessage("You Have Joined Team " + teamName + "!").queue();
			}
		}
		
		//Decline Command
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "decline"))
		{
			List<Role> emptyList = new ArrayList<Role>();
			if(util.getAllTeamInvites(allUserRoles).equals(emptyList))
			{
				event.getChannel().sendMessage("You Have No Invites To Any Teams!").queue();
			}
			else if(args.length <= 1)
			{
				event.getChannel().sendMessage("Enter A Team To Decline Their Invite!").queue();
			}
			else if(!util.teamExists(guild.getRoles(), teamName))
			{
				event.getChannel().sendMessage(teamName + " Does Not Exist!").queue();
			}
			else if(!util.isInvitedToTeam(util.getAllTeamInvites(allUserRoles), teamName))
			{
				event.getChannel().sendMessage("You Are Not Invited To This Team!").queue();
			}
			else
			{
				guild.getRolesByName("Invited To Team " + teamName, true).get(0).delete().queue();
				event.getChannel().sendMessage("You Successfully declined " + teamName + "'s Invitation!").queue();
			}
		}
		
		//Sent Invites - says all players who you invited
	}
}
