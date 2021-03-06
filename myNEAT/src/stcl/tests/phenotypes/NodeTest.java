package stcl.tests.phenotypes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import stcl.myNEAT.phenotypes.Connection;
import stcl.myNEAT.phenotypes.Node;
import stcl.myNEAT.phenotypes.NeuralNetwork.NodeType;
import stcl.myNEAT.phenotypes.activationFunctions.AFBias;
import stcl.myNEAT.phenotypes.activationFunctions.AFSame;
import stcl.myNEAT.phenotypes.activationFunctions.ActivationFunction;

public class NodeTest {
	Node n;
	HashMap<Integer, Connection> outgoing;
	
	@Before
	public void setUp() throws Exception {
		ActivationFunction af = new AFSame();
		outgoing = new HashMap<Integer, Connection>();
		n = new Node(0, NodeType.INPUT, af, outgoing);		
	}

	@Test
	/**
	 * Tests the setup of a node
	 */
	public void testNode() {
		assertTrue("Node set to be activated", n.activated() == false);
		assertTrue("Nodetype is wrong", n.getType() == NodeType.INPUT);
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
	
	@Test
	/**
	 * Test equality
	 */
	public void testEqualNotEqual(){
		Node m;
		ActivationFunction af = new AFSame();
		
		n = new Node(0, NodeType.INPUT, af, outgoing);			
		
		//Different ID
		m = new Node(1, NodeType.INPUT, af, outgoing);
		assertFalse(n.equals(m));
		
		//Different type
		m = new Node(0, NodeType.BIAS, af, outgoing);
		assertFalse(n.equals(m));
		
		//Different activation function
		ActivationFunction bf = new AFBias(0);
		m = new Node(0, NodeType.INPUT, bf, outgoing);
		assertFalse(n.equals(m));
		
		//Totally different
		af = new AFBias(0);
		m = new Node(1, NodeType.BIAS, bf, outgoing);
		assertFalse(n.equals(m));		
	}
	
	@Test
	
	public void testEqualIsEqual(){
		Node m;
		ActivationFunction af = new AFSame();
		
		n = new Node(0, NodeType.INPUT, af, outgoing);	
		m = new Node(0, NodeType.INPUT, af, outgoing);
		
		assertTrue(n.equals(m));
	}

}
