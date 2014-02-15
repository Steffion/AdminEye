package nl.Steffion.AdminEye;

import java.util.ArrayList;

public class VoteHandler {
	public static VoteType type = VoteType.NO_VOTE;
	public static ArrayList<String> voted_players = new ArrayList<String>();
	public static ArrayList<String> vote_yes_players = new ArrayList<String>();
	public static ArrayList<String> vote_no_players = new ArrayList<String>();

	public enum VoteType {
		NO_VOTE, VOTE_KICK, VOTE_BAN;
	}
}
