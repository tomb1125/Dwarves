package attacks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public String statusOnHit;
	
	public LinkedHashSet<String> conditions; //TO BE CONTINUED FROM HERE
	

	public final static double STUN_VALUE = 10;
	
	public Attack() {
		statusOnHit = "init Name";
		type = AttackType.MELEE;
		action = AttackAction.ACTION;
		statusOnHit = "";
		conditions = new LinkedHashSet();
	}
	
	@Override
	public Attack clone() throws CloneNotSupportedException {
		Attack clone = (Attack)super.clone();
		clone.findConditions();
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
				d.stats = new HashMap<String, Integer>();
				for(String key : returnedStats.keySet()) {
					d.stats.put(key, (Integer) returnedStats.get(key));
				}
				
				//System.out.println("Returned stats: "+d.stats);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				System.out.println("Error when parsing attack function: "+this.name);
				e.printStackTrace();
				
				return -1;
			}
		}
		double power = 0;
		if(d.stats.containsKey("dmg")) {
			power = d.stats.get("dmg");
		}
		//if(power == null) power = 0;
		
		if(d.stats.containsKey("stun")) {
			power+= STUN_VALUE;
		}
		
		if(action == AttackAction.ACTION) {
			d.IncreaseStat("attacks_made");
		}
		
		if(d.stats.containsKey("bonus_damage")) {
			power+= Double.valueOf(d.stats.get("bonus_damage"));
		}
		
		if(d.clanTalent == ClanTalent.DARK_DWARF_COMBO && d.stats.get("attacks_made")+1 % 3 == 0) {
			power = power*2;
		}
		
		d.nextTurn();
		
		return power;
	}
	
	//fill conditions
	public void findConditions() {
		 Pattern p = Pattern.compile("(?!map\\[')[^;'\\[\\]]+?(?='\\])");
		 Matcher m = p.matcher(statusOnHit);
		 
		 int i = 0;
		 while (m.find()) {
		    for (int j = 0; j <= m.groupCount(); j++) {
		       String group = m.group(j);
		       conditions.add(group);
		       i++;
		    }
		 }
	}
	
	//return binary code of which conditions are set.
	/*public Integer condtionFulfilled(LiveDwarf d) {
		Integer code = 0;
		for(String cond : conditions) {
			code*=2;
			String localCond = cond;
			for(String stat : d.stats.keySet()) {
				if(localCond.contains(stat)) {
					localCond = localCond.replace("map['"+stat+"]'", d.stats.get(stat));
				}
			}
			 Pattern p = Pattern.compile("[\\(\\s](.+?)\\s*?==\\s*?\1"); //ENDED HERE
			 Matcher m = p.matcher(statusOnHit);
			if(m.matches()) {
				code+=1;
			}
		}
		return code;
	}*/
	
	
	
	
}


