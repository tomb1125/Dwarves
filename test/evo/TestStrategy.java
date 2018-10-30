package evo;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import attacks.Attack;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class TestStrategy {
	  @Test
	  public void testNoMods() throws CloneNotSupportedException {
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
		  
		  Strategy str = new Strategy();
		  str.usedAttack = 1;
		  
		  Strategy newStr = str.getNextStrategy(d);
		  
		  
		  
		  assertEquals(true, newStr.usedAttack == 2);
		  assertEquals(true, str.usedAttack == 1);
	  }	
	  

	  @Test
	  public void testNoAttack() throws CloneNotSupportedException {
		// mock creation
		  Attack mockAttack1 =  mock(Attack.class);
		  Attack mockAttack2 =  mock(Attack.class);
		  Attack mockAttack3 =  mock(Attack.class);
		  Attack mockAttack4 =  mock(Attack.class);
		  
		  mockAttack1.name = "att1";
		  mockAttack2.name = "att2";
		  mockAttack3.name = "att3";
		  mockAttack4.name = "att3";
		  
		  LiveDwarf d = new LiveDwarf();
		  
		  d.mods.add(mockAttack1);
		  d.mods.add(mockAttack2);
		  d.mods.add(mockAttack3);
		  d.mods.add(mockAttack4);
		  
		  Strategy str = new Strategy();
		  str.usedAttack = 0;
		  str.usedMods = new HashSet<Integer>(Arrays.asList(0, 2));;
		  
		  Strategy newStr = str.getNextStrategy(d);
		  
		  

		  assertEquals(true, newStr.usedAttack == 0);
		  assertEquals(false, newStr.usedMods.contains(0));
		  assertEquals(true, newStr.usedMods.contains(1));
		  assertEquals(true, newStr.usedMods.contains(2));
		  assertEquals(false, newStr.usedMods.contains(3));
		  assertEquals(true, str.usedMods.contains(0));
	  }	
	  
	  @Test
	  public void maxStrategies() throws CloneNotSupportedException {
		// mock creation
		  Attack mockAttack1 =  mock(Attack.class);
		  Attack mockAttack2 =  mock(Attack.class);
		  Attack mockAttack3 =  mock(Attack.class);
		  Attack mockAttack4 =  mock(Attack.class);
		  
		  mockAttack1.name = "att1";
		  mockAttack2.name = "att2";
		  mockAttack3.name = "att3";
		  mockAttack4.name = "att3";
		  
		  LiveDwarf d = new LiveDwarf();

		  d.attacks.add(mockAttack1);
		  d.attacks.add(mockAttack2);
		  d.attacks.add(mockAttack3);
		  d.attacks.add(mockAttack4);  
		  
		  d.mods.add(mockAttack1);
		  d.mods.add(mockAttack2);
		  d.mods.add(mockAttack3);
		  d.mods.add(mockAttack4);
		  
		  Strategy newStr = new Strategy();
		  newStr.usedAttack = 0;
		  newStr.usedMods = new HashSet<Integer>();
		  
		  Integer numberOfStrategies = 0;
		  while(newStr != null) {
			  newStr = newStr.getNextStrategy(d);
			  numberOfStrategies++;
			  
			  if(numberOfStrategies > 100) break;
		  }
		  
		  assertEquals(Integer.valueOf(5*16), numberOfStrategies);
	  }	
	  
	  @Test 
	  public void testNextStrategyList() {
	 	  LiveDwarf d = new LiveDwarf();
	 	  d.name = "Dwarf 1";
	 	  
	 	  Attack at = new Attack();
	 	  at.name = "Attack 1";
	 	  d.attacks.add(at);
	 	  
	 	  Attack at2 = new Attack();
	 	  at.name = "Attack 1";
	 	  d.attacks.add(at2);
	 	  
	 	  Integer depth = 4;
	 	  List<Strategy> strategyField = new LinkedList<Strategy>();
	 		
	 	  for(int i=0; i<depth;i++) {
	 		 strategyField.add(new Strategy());
	 	  }
	 		
	 	  Integer i=0;
	 		while(strategyField.get(0) != null) {
	 			//do something
	 			i++;
	 			
				Strategy.nextStrategyList(d, strategyField);
				
				if(i>100) break;
	 		}

	      System.out.println(i);
		  assertEquals(Integer.valueOf(81), i);
	 	  
	  }	  
}
