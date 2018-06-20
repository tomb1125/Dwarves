package evo;

import java.util.HashMap;
import java.util.LinkedList;

import attacks.Attack;
import talents.ClanTalent;
import talents.GeneralTalent;
import talents.GuildTalent;


public class LiveDwarf extends Dwarf {
	public HashMap<String, String> stats;

	public LiveDwarf(Dwarf d) {
		//DOTO we do not handle strategy yet.
		this();
		
		this.name = d.name;
		this.clanTalent = d.clanTalent;
		this.guildTalent = d.guildTalent;
		this.generalTalent1 = d.generalTalent1;
		this.generalTalent2 = d.generalTalent2;
		this.generalTalent3 = d.generalTalent3;
		
		this.attacks = new LinkedList<Attack>();
		this.mods = new LinkedList<Attack>();
		this.strategy = new LinkedList<Strategy>();
		
		for(Attack att : d.attacks) {
			this.attacks.add(att);
		}
		
		for(Attack mod : d.mods) {
			this.mods.add(mod);
		}
		
		for(Strategy strat : d.strategy) {
			this.strategy.add(strat);
		}
		
		stats.put("turn", "1");
	}	
	
	public LiveDwarf() {
		super();
		stats = new HashMap<String, String>();
	}
	
	public Attack chooseAttack() throws CloneNotSupportedException {
		Attack chosenAttack = null;
		double maxPower = Integer.MIN_VALUE;
		for(Attack a : attacks) {
			double power = a.getRelativePower(this);
			if(power >= maxPower) {
				maxPower = power;
				chosenAttack = a;
			}
		}
		
		return chosenAttack;
	}
	
	@Override
	public LiveDwarf clone() throws CloneNotSupportedException {
		LiveDwarf clone = (LiveDwarf) super.clone();
		clone.stats = new HashMap<String, String>(this.stats);
		clone.attacks = new LinkedList<Attack>();
		
		for(Attack att : this.attacks) {
			clone.attacks.add(att.clone());
		}
		
		for(Attack mod : this.mods) {
			clone.mods.add(mod.clone());
		}
		
	    return clone;
	}
	
	public String getStatusMapForEngine(String codeToEmbedd, Boolean noFooter) {
		HashMap<String, String> tempStats = stats;
		String jsExperssion;
		String statsHeader = "var map = new Object();\r\n";
		for(String key : tempStats.keySet()) {
			statsHeader += "map['"+key+"'] = "+tempStats.get(key)+";\r\n";
			
		}
		
		String statsFooter = "map;\r\n"; //no "return" statement. This is script engine functionality - it will return last called function
		jsExperssion = statsHeader + codeToEmbedd;
		if(!noFooter) {
			jsExperssion = jsExperssion + statsFooter;
		}
		
		return jsExperssion;
	}
	
	public LiveDwarf nextTurn() throws CloneNotSupportedException {
		LiveDwarf d = this.clone();
		
		if(d.stats.get("turn") == null) {
			d.stats.put("turn", "1");
		} else {
			d.stats.put("turn", String.valueOf(Integer.valueOf(d.stats.get("turn"))+1));
		}
		
		LowerStat("stun");
		stats.remove("bonus_damage");
		
		return d;
	}
	
	public void LowerStat(String stat) {
		if(!stats.containsKey(stat)) return;
		
		if(stats.get(stat) == "1" || stats.get(stat) == "0") {
			stats.remove(stat);
		} else {
			stats.put(stat, String.valueOf(Integer.valueOf(stats.get(stat))-1));
		}
	}
	
	public void IncreaseStat(String stat) {
		if(!stats.containsKey(stat)) stats.put(stat, "1");
		stats.put(stat, String.valueOf(Integer.valueOf(stats.get(stat))+1));
	}
}
