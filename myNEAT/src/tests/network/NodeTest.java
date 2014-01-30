package tests.network;

import static org.junit.Assert.*;

import java.util.ArrayList;

import myNEAT.network.Connection;
import myNEAT.network.Node;
import myNEAT.network.activationFunctions.AFSame;
import myNEAT.network.activationFunctions.ActivationFunction;

import org.junit.Before;
import org.junit.Test;

public class NodeTest {
	Node n;
	ArrayList<Connection> outgoing;
	
	@Before
	public void setUp() throws Exception {
		ActivationFunction af = new AFSame();
		outgoing = new ArrayList<>();
		n = new Node(0, 1, af);		
	}

	@Test
	/**
	 * Tests the setup of a node
	 */
	public void testNode() {
		assertTrue("Node set to be activated", n.activated() == false);
		assertTrue("Nodetype is wrong", n.getType() == 1);
		assertTrue("NodeID is wrong", n.getNodeID() == 0);
	}

	@Test
	/**
	 * Test if activated() is set to true and input set to 0
	 */
	public void testActivate() {
		double e = 0.00001;
		n.addInput(5);	
		
		n.activate();		
		assertTrue("Node is not set to be activated", n.activated() == true);
		
		double o = n.getOutput();
		assertTrue("Node has not computed output", o > 5 - e && o < 5 + e);
		
		n.activate();
		o = n.getOutput();
		assertTrue("Node has not reset input", o > 0 - e && o < 0 + e);
	}

	@Test
	/**
	 * Test if activated() is set to false and input set to 0
	 */
	public void testFlush() {
		double e = 0.00001;
				
		n.activate();		
		n.addInput(5);		
		n.flush();		
		double o = n.getOutput();
		assertTrue("Activation not set to false", n.activated() == false);
		assertTrue("Node has not reset input", o > 0 - e && o < 0 + e);
	}
	
	

}
