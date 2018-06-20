package talents;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import evo.LiveDwarf;

public class TestTalent {

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
  public void testIsAvailable() {
		LiveDwarf d = new LiveDwarf();
		d.stats.put("turn", "3");
		d.stats.put("stun", "true");
		
		Talent t = new ClanTalent();
		t.prerequisite = "turn == 3 && stun";
		System.out.println(t.isAvailable(d));
        assertEquals(true, t.isAvailable(d));
  }
} 