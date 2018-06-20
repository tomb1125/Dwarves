package talents;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import evo.LiveDwarf;

public abstract class Talent {
	public String name;
	public String prerequisite; //Ze stringa zrobiæ prerequisite.
	
	public boolean isAvailable(LiveDwarf d) {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("JavaScript");
        String replacedPrerequiste = prerequisite;
        
        for(String key : d.stats.keySet()) {
        	replacedPrerequiste = replacedPrerequiste.replace(key, d.stats.get(key));
        }
        
        String myExpression = replacedPrerequiste;
		try {
			return (Boolean) se.eval(myExpression);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			System.out.println("Error when parsing prerequisites of talent: "+this.name);
			e.printStackTrace();
			return false;
		}
	}
}
