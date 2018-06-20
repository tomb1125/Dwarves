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
		d.stats.put("test", "1");
		
		d.attacks.get(0).statusOnHit = "map['test'] = 2; \r\n";
		d.attacks.get(0).makeAttack(d);
		

        assertEquals("2", d.stats.get("test"));
  }
  
  @Test
  //test if checking power does not alter the character stats
  public void getRelativePowerStatsCheck() throws CloneNotSupportedException {
		LiveDwarf d = new LiveDwarf();
		d.name = "Bombur";
		d.attacks.add(new Attack());
		d.stats.put("test", "1");
		
		d.attacks.get(0).statusOnHit = "map['test'] = 2; \r\n";
		d.attacks.get(0).getRelativePower(d);
		

        assertEquals("1", d.stats.get("test"));
  }
  
  
  /*@Test
  public void testIsAvailable() {
		LiveDwarf d = new LiveDwarf();
		d.name = "Bombur";
		d.attacks.add(new Attack());
		d.stats.put("test", "1");

		d.attacks.get(0).statusPrerequisite = "(map['test'] == \"1\");";

        assertEquals(true, d.attacks.get(0).isAvailable(d));

		d.stats.put("test", "2");

        assertEquals(false, d.attacks.get(0).isAvailable(d));
  }*/
} 