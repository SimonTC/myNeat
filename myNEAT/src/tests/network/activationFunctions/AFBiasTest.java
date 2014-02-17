package tests.network.activationFunctions;

import static org.junit.Assert.*;
import myNEAT.network.activationFunctions.AFBias;
import myNEAT.network.activationFunctions.AFSame;
import myNEAT.network.activationFunctions.ActivationFunction;

import org.junit.Before;
import org.junit.Test;

public class AFBiasTest {

	@Test
	public void testActivation() {
		ActivationFunction af = new AFBias(1);
		double e = 0.0001;
		
		double o = af.getActivation(5);
		assertTrue(o > 1 - e && o < 1 + e);
		
	}
	
	@Test 
	public void testEqualNotEqual(){
		ActivationFunction a = new AFBias(0);
		ActivationFunction b = new AFBias(0.001);
		
		assertFalse(a.equals(b));
	}
	
	@Test 
	public void testEqualIsEqual(){
		ActivationFunction a = new AFBias(0.0);
		ActivationFunction b = new AFBias(0.0);
		
		assertTrue(a.equals(b));
	}

}
