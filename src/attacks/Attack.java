package attacks;

import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import evo.Dwarf;
import evo.LiveDwarf;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import talents.ClanTalent;

public class Attack implements Cloneable {
	public String name;
	public AttackType type;
	public AttackAction action;
	
	public double dps;
	public double hitChance;
	public String statusOnHit;
	

	public final static double STUN_VALUE = 10;
	
	public Attack() {
		statusOnHit = "init Name";
		dps = 1;
		hitChance = 1;
		type = AttackType.MELEE;
		action = AttackAction.ACTION;
		statusOnHit = "";
	}
	
	@Override
	public Attack clone() throws CloneNotSupportedException {
		Attack clone = (Attack)super.clone();
		return clone;
	}
	
	public double getRelativePower(LiveDwarf d) throws CloneNotSupportedException {
		LiveDwarf temp = d.clone();
		temp.name = "clone";
		return makeAttack(temp);
	}
	
	@SuppressWarnings("restriction")
	public double makeAttack(LiveDwarf d) throws CloneNotSupportedException {
		if(statusOnHit != null && statusOnHit != "") {
			String jsExperssion = d.getStatusMapForEngine(statusOnHit, false);
			
	        ScriptEngineManager sem = new ScriptEngineManager();
	        ScriptEngine se = sem.getEngineByName("JavaScript");
			try {
	
				jdk.nashorn.api.scripting.ScriptObjectMirror returnedStats =  (ScriptObjectMirror) se.eval(jsExperssion);
				//d.stats = returnedStats;
				d.stats = new HashMap<String, String>();
				for(String key : returnedStats.keySet()) {
					d.stats.put(key, String.valueOf(returnedStats.get(key)));
				}
				
				//System.out.println("Returned stats: "+d.stats);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				System.out.println("Error when parsing attack function: "+this.name);
				e.printStackTrace();
				
				return -1;
			}
		}
		double power = dps;
		if(d.stats.containsKey("stun")) {
			power+= hitChance*STUN_VALUE;
		}
		
		if(action == AttackAction.ACTION) {
			d.IncreaseStat("attacks_made");
		}
		
		if(d.stats.containsKey("bonus_damage")) {
			power+= Double.valueOf(d.stats.get("bonus_damage"));
		}
		
		if(d.clanTalent == ClanTalent.DARK_DWARF_COMBO && Integer.valueOf(d.stats.get("attacks_made"))+1 % 3 == 0) {
			power = power*2;
		}
		
		d.nextTurn();
		
		return power;
	}
	
	/*public Boolean isAvailable(LiveDwarf d) {
		if(statusPrerequisite != null && statusPrerequisite != "") {
			String jsExperssion = d.getStatusMapForEngine(statusPrerequisite, true);
			
	        ScriptEngineManager sem = new ScriptEngineManager();
	        ScriptEngine se = sem.getEngineByName("JavaScript");
	        
	        try {
				return (Boolean) se.eval(jsExperssion);		
	        	
	        	//return (Boolean) se.eval(jsExperssion);
	        } catch(ScriptException e) {
				System.out.println("Error when parsing availability: "+this.name);
	        	return false;
	        }
		}
		
		return true;
	}*/
	
	
}


