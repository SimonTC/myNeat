package tests.network.activationFunctions;

import static org.junit.Assert.*;
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

}
