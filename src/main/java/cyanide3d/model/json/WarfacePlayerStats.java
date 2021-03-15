package cyanide3d.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WarfacePlayerStats{

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

	@JsonProperty("full_response")
	private String fullResponse;

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

	public void setKills(int kills){
		this.kills = kills;
	}

	public int getKills(){
		return kills;
	}

	public void setClanName(String clanName){
		this.clanName = clanName;
	}

	public String getClanName(){
		return clanName;
	}

	public void setRankId(int rankId){
		this.rankId = rankId;
	}

	public int getRankId(){
		return rankId;
	}

	public void setDeath(int death){
		this.death = death;
	}

	public int getDeath(){
		return death;
	}

	public void setPveDeath(int pveDeath){
		this.pveDeath = pveDeath;
	}

	public int getPveDeath(){
		return pveDeath;
	}

	public void setFullResponse(String fullResponse){
		this.fullResponse = fullResponse;
	}

	public String getFullResponse(){
		return fullResponse;
	}

	public void setPlaytime(int playtime){
		this.playtime = playtime;
	}

	public int getPlaytime(){
		return playtime;
	}

	public void setExperience(int experience){
		this.experience = experience;
	}

	public int getExperience(){
		return experience;
	}

	public void setPveAll(int pveAll){
		this.pveAll = pveAll;
	}

	public int getPveAll(){
		return pveAll;
	}

	public void setPveFriendlyKills(int pveFriendlyKills){
		this.pveFriendlyKills = pveFriendlyKills;
	}

	public int getPveFriendlyKills(){
		return pveFriendlyKills;
	}

	public void setFavoritPVP(String favoritPVP){
		this.favoritPVP = favoritPVP;
	}

	public String getFavoritPVP(){
		return favoritPVP;
	}

	public void setPveKill(int pveKill){
		this.pveKill = pveKill;
	}

	public int getPveKill(){
		return pveKill;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return nickname;
	}

	public void setPveWins(int pveWins){
		this.pveWins = pveWins;
	}

	public int getPveWins(){
		return pveWins;
	}

	public void setFriendlyKills(int friendlyKills){
		this.friendlyKills = friendlyKills;
	}

	public int getFriendlyKills(){
		return friendlyKills;
	}

	public void setPve(double pve){
		this.pve = pve;
	}

	public double getPve(){
		return pve;
	}

	public void setPvpwl(double pvpwl){
		this.pvpwl = pvpwl;
	}

	public double getPvpwl(){
		return pvpwl;
	}

	public void setPvpAll(int pvpAll){
		this.pvpAll = pvpAll;
	}

	public int getPvpAll(){
		return pvpAll;
	}

	public void setIsTransparent(boolean isTransparent){
		this.isTransparent = isTransparent;
	}

	public boolean isIsTransparent(){
		return isTransparent;
	}

	public void setFavoritPVE(String favoritPVE){
		this.favoritPVE = favoritPVE;
	}

	public String getFavoritPVE(){
		return favoritPVE;
	}

	public void setPvp(double pvp){
		this.pvp = pvp;
	}

	public double getPvp(){
		return pvp;
	}

	public void setKill(int kill){
		this.kill = kill;
	}

	public int getKill(){
		return kill;
	}

	public void setPveLost(int pveLost){
		this.pveLost = pveLost;
	}

	public int getPveLost(){
		return pveLost;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setPvpLost(int pvpLost){
		this.pvpLost = pvpLost;
	}

	public int getPvpLost(){
		return pvpLost;
	}

	public void setPveKills(int pveKills){
		this.pveKills = pveKills;
	}

	public int getPveKills(){
		return pveKills;
	}

	public void setPvpWins(int pvpWins){
		this.pvpWins = pvpWins;
	}

	public int getPvpWins(){
		return pvpWins;
	}

	public void setClanId(int clanId){
		this.clanId = clanId;
	}

	public int getClanId(){
		return clanId;
	}

	public void setPlaytimeH(int playtimeH){
		this.playtimeH = playtimeH;
	}

	public int getPlaytimeH(){
		return playtimeH;
	}

	public void setPlaytimeM(int playtimeM){
		this.playtimeM = playtimeM;
	}

	public int getPlaytimeM(){
		return playtimeM;
	}
}