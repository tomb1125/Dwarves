package attacks;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

import attacks.Attack;
import evo.LiveDwarf;

public class TestAttack {

  @BeforeClass
  public static void testSetup() {
	  
  }

  @AfterClass
  public static void testCleanup() {
	  
  }

  //@Test(expected = IllegalArgumentException.class)
  //public void testExceptionIsThrown() {        
  //  tester.divide(1000, 0);
  //}

  @Test
  public void makeAttackStatsCheck() throws CloneNotSupportedException {
		LiveDwarf d = new LiveDwarf();
		d.attacks.add(new Attack());
		d.stats.put("test", 1);
		
		d.attacks.get(0).statusOnHit = "map['test'] = 2; \r\n";
		d.attacks.get(0).makeAttack(d);
		

        assertEquals(Integer.valueOf(2), d.stats.get("test"));
  }
  
  @Test
  //test if checking power does not alter the character stats
  public void getRelativePowerStatsCheck() throws CloneNotSupportedException {
		LiveDwarf d = new LiveDwarf();
		d.name = "Bombur";
		d.attacks.add(new Attack());
		d.stats.put("test", 1);
		
		d.attacks.get(0).statusOnHit = "map['test'] = 2; \r\n";
		d.attacks.get(0).getRelativePower(d);
		

        assertEquals(Integer.valueOf(1), d.stats.get("test"));
  }
  
  @Test
  public void getCondition() throws CloneNotSupportedException {
		LiveDwarf d = new LiveDwarf();
		d.attacks.add(new Attack());
		d.stats.put("test", 1);
		
		d.attacks.get(0).statusOnHit = " if(map['result'] == 2) {map['test'] = 2;} else if(map['test'] == 1) {map['result'] = 2;} \r\n ";
		d.attacks.get(0).findConditions();
        assertEquals(2, d.attacks.get(0).conditions.size());
        //assertEquals(Integer.valueOf(1), d.attacks.get(0).condtionFulfilled(d));
        
        
        
		d.attacks.get(0).makeAttack(d);
        assertEquals(Integer.valueOf(2), d.stats.get("result"));

		d.attacks.get(0).makeAttack(d);
        assertEquals(Integer.valueOf(2), d.stats.get("test"));
        
        assertEquals(Integer.valueOf(3),d.fullfillsConditions());
		
  }
} 