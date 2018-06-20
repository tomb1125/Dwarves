package evo;

import attacks.Attack;

public class Main {

	public static void main(String[] args) throws CloneNotSupportedException {
		LiveDwarf d = new LiveDwarf();
		d.attacks.add(new Attack());
		d.stats.put("test", "1");
		
		d.attacks.get(0).statusOnHit = "map['test'] = 2; \r\n";
		d.attacks.get(0).makeAttack(d);
	}

}
