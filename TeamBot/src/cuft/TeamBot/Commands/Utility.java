package cuft.TeamBot.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.api.entities.Role;

public class Utility {
	//Checks if the player is on already on a team
	final private List<String> availableColors = new ArrayList<String>(Arrays.asList("🟥", "🟦", "🟩", "🟨", "🟪"));
	
	public List<String> getColors()
	{
		return availableColors;
	}
		public boolean isOnTeam(List<Role> roles)
		{
			for(Role role : roles)
			{
				if(role.getName().contains("Team") && !role.getName().contains("Invited To"))
				{
					return true;
				}
			}
			return false;
		}
		
		//Checks if the team already exists
		public boolean teamExists(List<Role> allServerRoles, String name)
		{
			for(Role role : allServerRoles)
			{
				if(role.getName().contains(name))
				{
					return true;
				}
			}
			return false;
		}
		
		public List<Role> allUsersTeamRoles(List<Role> roles)
		{
			List<Role> teamRoles = new ArrayList<Role>();
			for(Role role : roles)
			{
				if(role.getName().contains("Team"))
				{
					teamRoles.add(role);
				}
			}
			return teamRoles;
		}
		
		public Role getUserTeam(List<Role> roles)
		{
			for(Role role : roles)
			{
				if(role.getName().contains("Team") && !role.getName().contains("Captain"))
				{
					return role;
				}
			}
			return null;
		}
		
		public boolean isCaptain(List<Role> roles)
		{
			for(Role role : roles)
			{
				if(role.getName().contains("Team") && role.getName().contains("Captain"))
				{
					return true;
				}
			}
			return false;
		}
		
		public boolean isAlreadyInvited(List<Role> roles, String teamName)
		{
			for(Role role : roles)
			{
				if(role.getName().contains("Invited To " + teamName))
				{
					return true;
				}
			}
			return false;
		}
		
		public List<Role> getAllTeamInvites(List<Role> roles)
		{
			List<Role> invitedTeams = new ArrayList<Role>();
			for(Role role : roles)
			{
				if(role.getName().contains("Invited To"))
				{
					invitedTeams.add(role);
				}
			}
			return invitedTeams;
		}
		
		public boolean isInvitedToTeam(List<Role> roles, String teamName)
		{
			for(Role role : roles)
			{
				if(role.getName().contains(teamName))
				{
					return true;
				}
			}
			return false;
		}
		
		public boolean inviteExists(List<Role> roles, String teamName)
		{
			for(Role role : roles)
			{
				if(role.getName().contains("Invited To " + teamName))
				{
					return true;
				}
			}
			return false;
		}
}
