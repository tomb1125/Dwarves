package evo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import attacks.Attack;

public class Strategy implements Cloneable {
	Integer usedAttack;
	Set<Integer> usedMods;
	
	public Strategy() {
		usedAttack = 0;
		usedMods = new HashSet<Integer>();
	}
	
	public Strategy clone() {
		Strategy clone = new Strategy();
		clone.usedAttack = this.usedAttack;
		clone.usedMods.addAll(this.usedMods);
		return clone;
	}
	
	public Strategy(Integer att, Set<Integer> mods) {
		this();
		usedAttack = att;
		usedMods = mods;
	}
	
	public double useStrategy(LiveDwarf d) throws CloneNotSupportedException {
		double cumulatedPower = 0;
		
		for(Integer mod : usedMods) {
			Attack att = d.mods.get(mod);
			cumulatedPower += att.makeAttack(d);
		}
		
		Attack att = d.attacks.get(usedAttack);
		cumulatedPower += att.makeAttack(d);
		
		return cumulatedPower;
	}
	
	public Strategy getNextStrategy(Dwarf d) {
		Strategy newStr = new Strategy();
		Integer lastAttack = d.attacks.size();
		Integer maxMod = (int) d.mods.size();
		
		newStr.usedMods = new HashSet<>(this.usedMods);
		
		if(usedAttack+1 < lastAttack) {
			newStr.usedAttack = usedAttack+1;
		} else {
			newStr.usedAttack = 0;
			putMod(newStr.usedMods, 0);
			
			if(newStr.usedMods.contains(maxMod)) {
				return null;
			}
		}
		
		return newStr;
	}
	
	public void putMod(Set<Integer> mods, Integer mod) {
		
		if(!mods.contains(mod)) {
			mods.add(mod);
		} else {
			mods.remove(mod);
			putMod(mods, mod+1);
		}		
	}

	public static void nextStrategyList(LiveDwarf d, List<Strategy> strategyArray) {
		nextStrategyList(d, strategyArray, strategyArray.size()-1);
	}
	
	
	public static void nextStrategyList(LiveDwarf d, List<Strategy> strategyArray, Integer index) {
		Strategy nextStrategy = strategyArray.get(index).getNextStrategy(d);
		
		if(nextStrategy == null && index>0) {
			strategyArray.set(index, new Strategy());
			nextStrategyList(d, strategyArray, index-1);
		} else {
			strategyArray.set(index, nextStrategy);
		}
	}
}
