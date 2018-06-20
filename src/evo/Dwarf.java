package evo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import attacks.Attack;
import attacks.AttackAction;
import attacks.AttackType;
import talents.ClanTalent;
import talents.ClanTalent;
import talents.GeneralTier1;
import talents.GeneralTier2;
import talents.GeneralTier3;
import talents.GeneralTier4;
import talents.GuildTalent;

public class Dwarf implements Cloneable {
	public final Integer DEPTH = 4;
	
	public static final Attack BASIC_ATTACK;
	static {
		BASIC_ATTACK = new Attack();
		BASIC_ATTACK.name = "Basic Attack";
		BASIC_ATTACK.dps = 3;
		BASIC_ATTACK.hitChance = 0.5;
	}
	
	public String name;
	public ClanTalent clanTalent;
	public GuildTalent guildTalent;
	public GeneralTier1 generalTalent1;
	public GeneralTier2 generalTalent2;
	public GeneralTier3 generalTalent3;
	public GeneralTier4 generalTalent4;
	public List<Attack> attacks;
	public List<Attack> mods;
	public List<Strategy> strategy;
	
	
	Dwarf() {
		attacks = new LinkedList<Attack>();
		mods = new LinkedList<Attack>();
		strategy = new LinkedList<Strategy>();
		attacks.add(BASIC_ATTACK);
	}
	
	private void loadTalent(ClanTalent x) {
		this.clanTalent = x;
	}
	
	private void loadTalent(GuildTalent x) {
		this.guildTalent = x;
	}
	
	private void loadTalent(GeneralTier1 x) {
		this.generalTalent1 = x;
	}

	private void loadTalent(GeneralTier2 x) {
		this.generalTalent2 = x;
	}
	
	private void loadTalent(GeneralTier3 x) {
		this.generalTalent3 = x;
	}
	
	private void loadTalent(GeneralTier4 x) {
		this.generalTalent4 = x;
	}
	
	@Override
	public Dwarf clone() throws CloneNotSupportedException {
		Dwarf clone = (Dwarf)super.clone();
		clone.attacks = new LinkedList<Attack>(attacks);
	    return clone;
	}
	
	public Strategy getBestStrategyForTurn(int turn, int depth) throws CloneNotSupportedException {
		List<Integer> proposedStrategy = new LinkedList<Integer>();
		
		//make one algorythm for late game build (work from beginning to end)
		//make one algorythm for rotation build (work from end to beginning)
		LiveDwarf d = new LiveDwarf(this);
		if(turn > 0) {
			for(Integer t=0; t<turn; t++) {
				d.strategy.get(t).useStrategy(d);
			}
		}
		
		List<List<Integer>> tempDeepStrategy = new LinkedList<List<Integer>>();
		Double maxPower = Double.MIN_VALUE;
		List<Strategy> bestStratField = new LinkedList<Strategy>();
		List<Strategy> strategyField = new LinkedList<Strategy>();

	 	 for(int i=0; i<depth;i++) {
	 	   strategyField.add(new Strategy());
	 	   bestStratField.add(new Strategy());
	 	 }
		//helper integer to traverse all possibilites 
		
		while(strategyField.get(0) != null) {
			
			Double power = Double.valueOf(0);
			LiveDwarf clone = d.clone();
			
			for(Strategy strat : strategyField) {
				power+=strat.useStrategy(clone);
			}
			
			if(bestStratField == null || power > maxPower) {
				bestStratField = new LinkedList<Strategy>();
				for(Strategy str : strategyField) {
					bestStratField.add(str.clone());
					//System.out.print(str.usedAttack+" ");
				}
				//System.out.println("");
				
				
				maxPower = power;
			}
			
			Strategy.nextStrategyList(d, strategyField);
			
		}
		

		for(Strategy str : bestStratField) {
			System.out.print(str.usedAttack+" ");
		}
		System.out.println("");

		
		
		return bestStratField.get(0);
	}
	
	public double getStrategyPower(List<Strategy> stratList) throws CloneNotSupportedException {
		LiveDwarf d = new LiveDwarf(this);
		double cumulatedPower = 0;
		
		for(Strategy str : stratList) {
			cumulatedPower += str.useStrategy(d);
		}
		
		return cumulatedPower;
	}
	
	public Attack getAttack(Integer i) {
		return attacks.get(i);
	}
	
	public void fillStrategy(Integer maxTurn) throws CloneNotSupportedException {
		for(Integer t=0; t<=maxTurn; t++) {
			this.strategy.add(t,this.getBestStrategyForTurn(t, DEPTH));
		}
	}
	
}
