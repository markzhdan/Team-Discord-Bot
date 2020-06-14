package cuft.TeamBot.Commands;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Role;

public class Utility {
	//Checks if the player is on already on a team
		public boolean isOnTeam(List<Role> roles)
		{
			for(Role role : roles)
			{
				if(role.getName().contains("Team"))
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
}
