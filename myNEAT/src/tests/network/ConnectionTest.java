package tests.network;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import myNEAT.network.Connection;
import myNEAT.network.Node;
import myNEAT.network.activationFunctions.AFSame;
import myNEAT.network.activationFunctions.ActivationFunction;

import org.junit.Before;
import org.junit.Test;

public class ConnectionTest {
	Node in;
	Node out;
	Connection c;
	HashMap<Integer, Connection> outgoing;
		
	@Before
	public void setUp() throws Exception {
		ActivationFunction af = new AFSame();
		outgoing = new HashMap<>();
		in = new Node(0, 1, af, outgoing);
		out = new Node(1, 0, af, outgoing);
		
	}

	@Test
	/**
	 * Test to see if signal is correctly multiplied by the weight
	 */
	public void testPropagateSignal() {
		double e = 0.0001;
		for (double i = -2; i <= 2; i+= 0.5){
			c = new Connection(0, in, out, (double) i, true);
			in.addInput(5);
			in.activate();
			c.propagateSignal();
			out.activate();
			double o = out.getOutput();
			double exp = (double) 5 * i;
			assertTrue(o > exp - e && o < exp + e) ;
		}
		
	}
	
	@Test
	/**
	 * Test to see if connection is set to activated after propagation
	 */
	public void testActivationSet(){
		c = new Connection(0, in, out, (double) 5,true );
		c.propagateSignal();
		assertTrue(c.isActivated() == true);
	}
	
	/**
	 * Test to see if connection is set to deactivated after reset
	 */
	@Test
	public void testReset() {
		c = new Connection(0, in, out, (double) 5,true);
		c.propagateSignal();
		assertTrue(c.isActivated() == true);
		c.reset();
		assertTrue(c.isActivated() != true);
	}

	@Test
	public void testAddWeight() {
		double e = 0.0001;
		c = new Connection(0, in, out, (double) 0.5,true);
		c.addWeight(0.02);
		double w = c.getWeight();
		assertTrue(w > 0.52 - e && w < 0.52 + e );
	}

	@Test
	/**
	 * Test to make sure that no signal propagation is done 
	 * when a conenction is disabled
	 */
	public void testIsEnabled() {
		double e = 0.0001;
		c = new Connection(0, in, out, (double) 0.5,true);
		c.setEnabled(false);
		in.addInput(5);
		in.activate();
		c.propagateSignal();
		out.activate();
		double o = out.getOutput();
		double exp = 0;
		assertTrue(o > exp - e && o < exp + e) ;
	}

}
