package cyanide3d.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player{

	@JsonProperty("kills")
	private int kills;

	@JsonProperty("clan_name")
	private String clanName;

	@JsonProperty("rank_id")
	private int rankId;

	@JsonProperty("death")
	private int death;

	@JsonProperty("pve_death")
	private int pveDeath;

	@JsonProperty("playtime")
	private int playtime;

	@JsonProperty("experience")
	private int experience;

	@JsonProperty("pve_all")
	private int pveAll;

	@JsonProperty("pve_friendly_kills")
	private int pveFriendlyKills;

	@JsonProperty("favoritPVP")
	private String favoritPVP;

	@JsonProperty("pve_kill")
	private int pveKill;

	@JsonProperty("nickname")
	private String nickname;

	@JsonProperty("pve_wins")
	private int pveWins;

	@JsonProperty("friendly_kills")
	private int friendlyKills;

	@JsonProperty("pve")
	private double pve;

	@JsonProperty("pvpwl")
	private double pvpwl;

	@JsonProperty("pvp_all")
	private int pvpAll;

	@JsonProperty("is_transparent")
	private boolean isTransparent;

	@JsonProperty("favoritPVE")
	private String favoritPVE;

	@JsonProperty("pvp")
	private double pvp;

	@JsonProperty("kill")
	private int kill;

	@JsonProperty("pve_lost")
	private int pveLost;

	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("pvp_lost")
	private int pvpLost;

	@JsonProperty("pve_kills")
	private int pveKills;

	@JsonProperty("pvp_wins")
	private int pvpWins;

	@JsonProperty("clan_id")
	private int clanId;

	@JsonProperty("playtime_h")
	private int playtimeH;

	@JsonProperty("playtime_m")
	private int playtimeM;

	public int getKills(){
		return kills;
	}

	public String getClanName(){
		return clanName;
	}

	public int getRankId(){
		return rankId;
	}

	public int getDeath(){
		return death;
	}

	public int getPveDeath(){
		return pveDeath;
	}

	public int getPlaytime(){
		return playtime;
	}

	public int getExperience(){
		return experience;
	}

	public int getPveAll(){
		return pveAll;
	}

	public int getPveFriendlyKills(){
		return pveFriendlyKills;
	}

	public String getFavoritPVP(){
		return favoritPVP;
	}

	public int getPveKill(){
		return pveKill;
	}

	public String getNickname(){
		return nickname;
	}

	public int getPveWins(){
		return pveWins;
	}

	public int getFriendlyKills(){
		return friendlyKills;
	}

	public double getPve(){
		return pve;
	}

	public double getPvpwl(){
		return pvpwl;
	}

	public int getPvpAll(){
		return pvpAll;
	}

	public boolean isIsTransparent(){
		return isTransparent;
	}

	public String getFavoritPVE(){
		return favoritPVE;
	}

	public double getPvp(){
		return pvp;
	}

	public int getKill(){
		return kill;
	}

	public int getPveLost(){
		return pveLost;
	}

	public String getUserId(){
		return userId;
	}

	public int getPvpLost(){
		return pvpLost;
	}

	public int getPveKills(){
		return pveKills;
	}

	public int getPvpWins(){
		return pvpWins;
	}

	public int getClanId(){
		return clanId;
	}

	public int getPlaytimeH(){
		return playtimeH;
	}

	public int getPlaytimeM(){
		return playtimeM;
	}
}