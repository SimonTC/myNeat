package tests.network.activationFunctions;

import static org.junit.Assert.*;
import myNEAT.network.activationFunctions.AFBias;
import myNEAT.network.activationFunctions.AFSame;
import myNEAT.network.activationFunctions.ActivationFunction;

import org.junit.Before;
import org.junit.Test;

public class AFSameTest {


	@Test
	public void testActivation() {
		ActivationFunction af = new AFSame();
		double e = 0.0001;
		for (double i = -5; i <= 5; i= i+= 0.5){
			double o = af.getActivation(i);
			assertTrue(o > i - e && o < i + e);
		}
	}
	
	@Test 
	public void testEqualNotEqual(){
		ActivationFunction a = new AFSame();
		ActivationFunction b = new AFBias(0);
		
		assertFalse(a.equals(b));
	}
	
	@Test 
	public void testEqualIsEqual(){
		ActivationFunction a = new AFSame();
		ActivationFunction b = new AFSame();
		
		assertTrue(a.equals(b));
	}

}
