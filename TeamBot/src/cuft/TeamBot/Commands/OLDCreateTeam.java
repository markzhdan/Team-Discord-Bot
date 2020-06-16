/*need to create methods for:

-checking if invited

need to make sure player can choose what team to accept

make sure only people who are allowed to can execute certain commands - permissions if statements

check if player that you invited is already on a team

//make delay on invite

//ask about java Map class

//make sure when reacting, it checks if the person who reacted is the same who made the *create command

make sure *delete command deletes role from every player with getmemberswithroles
*/
package cuft.TeamBot.Commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import cuft.TeamBot.TeamBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class OLDCreateTeam extends ListenerAdapter{
	
	public boolean isAlreadyRole(List<Role> allRoles, String name)
	{
		for(int i = 0; i < allRoles.size(); i++)
		{
			if(allRoles.get(i).getName().equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public boolean alreadyOnTeam(List<Role> allUserRoles)
	{
		for(int i = 0; i < allUserRoles.size(); i++)
		{
			String name = allUserRoles.get(i).getName();
			if(name.startsWith("Team"))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		final Guild guild = event.getGuild();
		String[] args = event.getMessage().getContentRaw().split(" ");
		String teamNameTemp = "";
		
		for(int i = 1; i < args.length; i++)
		{
			teamNameTemp += args[i] + " ";
		}
		teamNameTemp = teamNameTemp.trim();
		final String teamName = "Team " + teamNameTemp;
		
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "create"))
		{
			if(alreadyOnTeam(event.getMember().getRoles()))
			{
				event.getChannel().sendMessage("You Are Already On A Team!").queue();
			}
			else if(args.length <= 1)
			{
				event.getChannel().sendMessage("You Must Enter In A Team Name!").queue();
			}
			else if(isAlreadyRole(event.getMember().getRoles(), teamName))
			{
				event.getChannel().sendMessage("This Team Already Exists!").queue();
			}
			else
			{
				guild.createRole().setName(teamName).queue(role ->
					guild.addRoleToMember(event.getMember(), role).queue());
				
				guild.createRole().setName(teamName + " Captain").setColor(Color.BLACK).queue(role ->
					guild.addRoleToMember(event.getMember(), role).queue());
							/*Void ->
							event.getChannel().sendMessage("Team Successfully Created!").queue()));*/
				
				//old role system
				/*Role role = event.getGuild().createRole().setName(teamName).complete();
				event.getGuild().addRoleToMember(event.getMember(), role).queue();
				event.getChannel().sendMessage("Team Successfully Created!").queue();*/
				
				EmbedBuilder info  = new EmbedBuilder();
				info.setTitle("Choose A Team Color");
				info.setColor(0x9b42f5);

				event.getChannel().sendMessage(info.build()).queue(new Consumer<Message>() {
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
				info.clear();
			}
		}
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "delete"))
		{
			List<Role> roles = event.getMember().getRoles();
			
			List<Role> teamRoles = new ArrayList<Role>();
			String roleName = null;
			boolean isCaptain = false;
			for(Role role : roles)
			{
				if(role.getName().contains("Team"))
				{
					if(roleName == null)
					{
						roleName = role.getName();
					}
					teamRoles.add(role);
				}
				if(role.getName().contains("Captain") && role.getName().contains("Team"))
				{
					isCaptain = true;
				}
			}
			if(roleName == null)
			{
				event.getChannel().sendMessage("You Are Not Part Of A Team!").queue();
			}
			else if(!isCaptain)
			{
				event.getChannel().sendMessage("You Are Not The Captain of " + roleName + "!").queue();
			}
			else
			{
				for(int i = 0; i < teamRoles.size(); i++)
				{
					event.getGuild().getRolesByName(teamRoles.get(i).getName(), false).get(0).delete().queue();
				}
				
				event.getChannel().sendMessage("```" + roleName + " Successfully Deleted By " + event.getMember().getUser().getName() + "!```").queue();
			}
		}
		
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "invite"))
		{
			List<Role> roles = event.getMember().getRoles();
			String roleName = null;
			for(Role role : roles)
			{
				if(role.getName().contains("Team"))
				{
					if(roleName == null)
					{
						roleName = role.getName();
					}
				}
			}
			if(roleName != null)
			{
				guild.createRole().setName("Invited To " + roleName).setColor(Color.green).queue(role ->
					guild.addRoleToMember(event.getMessage().getMentionedMembers().get(0), role).queue());
			}
			
		}
		
		if(args[0].equalsIgnoreCase(TeamBot.prefix + "join"))
		{
			List<Role> roles = event.getMember().getRoles();
			
			List<Role> teamRoles = new ArrayList<Role>();
			String roleName = null;

			for(Role role : roles)
			{
				if(role.getName().contains("Team") && role.getName().contains("Invited To"))
				{
					if(roleName == null)
					{
						roleName = role.getName();
					}
					teamRoles.add(role);
				}
			}
			//make it so it joins and checks for a team args[1]
			
			if(roleName != null)
			{
				event.getGuild().getRolesByName(teamRoles.get(0).getName(), false).get(0).delete().queue();
				String[] names = roleName.split(" ");
				String temp = "";
				for(int i = 2; i < names.length; i++)
				{
					temp += names[i] + " ";
				}
				final String teamNameThatInvited = temp.trim();
				Role teamThatInvited = event.getGuild().getRolesByName(teamNameThatInvited, true).get(0);
				
				
				guild.addRoleToMember(event.getMember(), teamThatInvited).queue();
				event.getChannel().sendMessage("```You Successfully Joined " + teamThatInvited.getName() + "!```").queue();
				
			}
			else
			{
				event.getChannel().sendMessage("You are not invited to any teams because you have no friends to invite you!").queue();
			}
		}
	}
}
