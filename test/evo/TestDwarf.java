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
	  attack1.statusOnHit = "map.setup = 1;";
	  
	  attack2.name = "finisher";
	  attack2.statusOnHit = "if(map.setup == '1') map.bonus_damage = 3; delete map.setup;";
	  
	  attack3.name = "strong";
	  attack3.dps = 1.5;
	  
	  Dwarf d = new Dwarf();
	  d.attacks.add(attack1);
	  d.attacks.add(attack2);
	  d.attacks.add(attack3);
	  
	  assertEquals(true, 0 == d.getBestStrategyForTurn(0, 4).usedAttack);
  }
  
  @Test
  public void TestFillStrategy() throws CloneNotSupportedException {
		// mock creation
	  Attack attack1 = new Attack();
	  Attack attack2 = new Attack();
	  Attack attack3 = new Attack();
	  
	  attack1.name = "setup";
	  attack1.statusOnHit = "map.setup = 1;";
	  
	  attack2.name = "finisher";
	  attack2.statusOnHit = "if(map.setup == '1') map.bonus_damage = 3; delete map.setup;";
	  
	  attack3.name = "strong";
	  attack3.dps = 1.5;
	  
	  Dwarf d = new Dwarf();
	  d.attacks.add(attack1);
	  d.attacks.add(attack2);
	  d.attacks.add(attack3);	  
	  
	  d.fillStrategy(4);

	  assertEquals(true, 0 == d.strategy.get(0).usedAttack);
	  assertEquals(true, 1 == d.strategy.get(1).usedAttack);
	  assertEquals(true, 0 == d.strategy.get(2).usedAttack);
	  assertEquals(true, 1 == d.strategy.get(3).usedAttack);
  }
}