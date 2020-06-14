package cuft.TeamBot.Commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OLDGuildMessageReactionAdd extends ListenerAdapter{
	
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event)
	{
		//permissions check
		
		
		//event.getChannel().getHistory().getRetrievedHistory().get(1);
		//if(event.getUserId() == 
		//red
		if(event.getReactionEmote().getName().equals("🟥") &&
				!event.getMember().getUser().equals(event.getJDA().getSelfUser()))
		{
			
			List<Role> roles = event.getMember().getRoles();
			String roleName = null;
			for(Role role : roles)
			{
				if(role.getName().contains("Team"))
				{
					role.getManager().setColor(Color.red).queue();
					if(roleName == null)
					{
						roleName = role.getName();
					}
				}
			}
			event.getChannel().deleteMessageById(event.getMessageId()).queue();
			event.getChannel().sendMessage("```" + roleName + " Successfully Created By " + event.getUser().getName() + "!```").queue();
		}
		//blue
		else if(event.getReactionEmote().getName().equals("🟦") &&
				!event.getMember().getUser().equals(event.getJDA().getSelfUser()))
		{
			List<Role> roles = event.getMember().getRoles();
			String roleName = null;
			for(Role role : roles)
			{
				if(role.getName().contains("Team"))
				{
					role.getManager().setColor(Color.blue).queue();
					if(roleName == null)
					{
						roleName = role.getName();
					}
				}
			}
			event.getChannel().deleteMessageById(event.getMessageId()).queue();
			event.getChannel().sendMessage("```" + roleName + " Successfully Created By " + event.getUser().getName() + "!```").queue();

		}
        //green
		else if(event.getReactionEmote().getName().equals("🟩") &&
				!event.getMember().getUser().equals(event.getJDA().getSelfUser()))
		{
			
			List<Role> roles = event.getMember().getRoles();
			String roleName = null;
			for(Role role : roles)
			{
				if(role.getName().contains("Team"))
				{
					role.getManager().setColor(Color.green).queue();
					if(roleName == null)
					{
						roleName = role.getName();
					}
				}
			}
			event.getChannel().deleteMessageById(event.getMessageId()).queue();
			event.getChannel().sendMessage("```" + roleName + " Successfully Created By " + event.getUser().getName() + "!```").queue();

		}
        //yellow
		else if(event.getReactionEmote().getName().equals("🟨") &&
				!event.getMember().getUser().equals(event.getJDA().getSelfUser()))
		{
			List<Role> roles = event.getMember().getRoles();
			String roleName = null;
			for(Role role : roles)
			{
				if(role.getName().contains("Team"))
				{
					role.getManager().setColor(Color.yellow).queue();
					if(roleName == null)
					{
						roleName = role.getName();
					}
				}
			}
			event.getChannel().deleteMessageById(event.getMessageId()).queue();
			event.getChannel().sendMessage("```" + roleName + " Successfully Created By " + event.getUser().getName() + "!```").queue();

		}
        //purple
		else if(event.getReactionEmote().getName().equals("🟪") &&
				!event.getMember().getUser().equals(event.getJDA().getSelfUser()))
		{
			List<Role> roles = event.getMember().getRoles();
			String roleName = null;
			for(Role role : roles)
			{
				if(role.getName().contains("Team"))
				{
					role.getManager().setColor(0x800080).queue();
					if(roleName == null)
					{
						roleName = role.getName();
					}
				}
			}
			event.getChannel().deleteMessageById(event.getMessageId()).queue();
			event.getChannel().sendMessage("```" + roleName + " Successfully Created By " + event.getUser().getName() + "!```").queue();
		}
	}
}
