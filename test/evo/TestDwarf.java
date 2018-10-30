package evo;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.io.Console;

import attacks.Attack;
import evo.LiveDwarf;

public class TestDwarf {
  //@Test(expected = IllegalArgumentException.class)
  //public void testExceptionIsThrown() {        
  //  tester.divide(1000, 0);
  //}

  @Test
  public void TestBestStrategy() throws CloneNotSupportedException {
	// mock creation
	  Attack attack1 = new Attack();
	  Attack attack2 = new Attack();
	  Attack attack3 = new Attack();
	  
	  attack1.name = "setup";
	  attack1.statusOnHit = "map.setup = 1; map.dmg = 2;";
	  
	  attack2.name = "finisher";
	  attack2.statusOnHit = "if(map.setup == '1') map.bonus_damage = 6; map.dmg = 2; delete map.setup;";
	  
	  attack3.name = "strong" ;
	  attack3.statusOnHit = "map.dmg = 3;";

	  
	  Dwarf d = new Dwarf();
	  d.attacks.add(attack1);
	  d.attacks.add(attack2);
	  d.attacks.add(attack3);
	  
	  assertEquals("setup", d.attacks.get(d.getBestStrategyForTurn(0, 4).usedAttack).name);
  }
  
  @Test
  public void TestFillStrategy() throws CloneNotSupportedException {
		// mock creation
	  Attack attack1 = new Attack();
	  Attack attack2 = new Attack();
	  Attack attack3 = new Attack();
	  
	  attack1.name = "setup";
	  attack1.statusOnHit = "map.setup = 1; map.dmg = 2;";
	  
	  attack2.name = "finisher";
	  attack2.statusOnHit = "if(map.setup == '1') map.bonus_damage = 6; map.dmg = 2; delete map.setup;";
	  
	  attack3.name = "strong" ;
	  attack3.statusOnHit = "map.dmg = 3;";
	  
	  Dwarf d = new Dwarf();
	  d.attacks.add(attack1);
	  d.attacks.add(attack2);
	  d.attacks.add(attack3);	  
	  
	  d.fillStrategy(4);

	  assertEquals("setup", d.attacks.get(d.strategy.get(0).usedAttack).name);
	  assertEquals("finisher", d.attacks.get(d.strategy.get(1).usedAttack).name);
	  assertEquals("setup", d.attacks.get(d.strategy.get(2).usedAttack).name);
	  assertEquals("finisher", d.attacks.get(d.strategy.get(3).usedAttack).name);
  }
}