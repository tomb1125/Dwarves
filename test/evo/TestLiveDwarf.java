package evo;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

import attacks.Attack;
import evo.LiveDwarf;

public class TestLiveDwarf {

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
  public void testChooseAttack() throws CloneNotSupportedException {
	// mock creation
	  Attack mockAttack1 =  mock(Attack.class);
	  Attack mockAttack2 =  mock(Attack.class);
	  Attack mockAttack3 =  mock(Attack.class);
	  
	  mockAttack1.name = "att1";
	  mockAttack2.name = "att2";
	  mockAttack3.name = "att3";
	  
	  LiveDwarf d = new LiveDwarf();
	  
	  d.attacks.add(mockAttack1);
	  d.attacks.add(mockAttack2);
	  d.attacks.add(mockAttack3);
	  
	  when(mockAttack1.getRelativePower(d)).thenReturn((double) 2);
	  when(mockAttack2.getRelativePower(d)).thenReturn((double) 4);
	  when(mockAttack3.getRelativePower(d)).thenReturn((double) 1);
	  
	  assertEquals(mockAttack2.name,d.chooseAttack().name);
  }
  
  @Test
  public void testClone() throws CloneNotSupportedException {
	  LiveDwarf d = new LiveDwarf();
	  d.name = "Dwarf 1";
	  
	  Attack at = new Attack();
	  at.name = "Attack 1";
	  d.attacks.add(at);
	  
	  d.stats.put("test", "1");
	  
	  LiveDwarf clone = d.clone();
	  clone.name = "Clone";
	  at.name = "Changed";
	  d.stats.put("test", "2");
	  
	  assertEquals(false, clone.name == d.name);
	  assertEquals(false, clone.attacks.get(0).name == d.attacks.get(0).name);
	  assertEquals(false, clone.stats.get("test") == d.stats.get("test"));
  }
  

} 