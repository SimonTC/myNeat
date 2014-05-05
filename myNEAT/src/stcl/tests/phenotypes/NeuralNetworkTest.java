package stcl.tests.phenotypes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import stcl.myNEAT.phenotypes.Connection;
import stcl.myNEAT.phenotypes.NeuralNetwork;
import stcl.myNEAT.phenotypes.Node;
import stcl.myNEAT.phenotypes.NeuralNetwork.NodeType;
import stcl.myNEAT.phenotypes.activationFunctions.AFBias;
import stcl.myNEAT.phenotypes.activationFunctions.AFSame;
import stcl.myNEAT.phenotypes.activationFunctions.ActivationFunction;

public class NeuralNetworkTest {
	
	private HashMap<Integer, Node> inputNodes, outputNodes, hiddenNodes;
	private HashMap<Integer, Connection> connections;
	
	@Before
	public void setUp() throws Exception {
		inputNodes = new HashMap<>();
		outputNodes = new HashMap<>();
		hiddenNodes = new HashMap<>();
		connections = new HashMap<>();
	}
	
	@Test
	/*
	 * Test if the correct input pattern is made. Also tests full activation
	 */
	public void testNeuralNetworkCorrectInputPattern(){
		//Set up input and output nodes
		createNodes();
		
		//Connect input nodes to output nodes
		connectOneInputToOneOutput();
		
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork( inputNodes, outputNodes, hiddenNodes, connections);
		
		//Test that inputs == outputs
		double e = 0.0001;
		double[] inputs = {0, 1, 2};
		double[] output = nn.activate(inputs, true);
		for (int i = 0; i < output.length; i++){
			double out = output[i];
			double in = inputs[i];
			assertTrue(isEqual(in,out));
		}
	}
	
	@Test
	public void testActivate_Continouous() {
		//Set up input and output nodes
		createNodes();
		
		//Connect all input nodes to output nodes
		connectAllInputsToAllOutput();
		
		//Connect input id 2 to hidden node id 8
		Node in = inputNodes.get(2);
		Node out = hiddenNodes.get(8);
		Connection c = new Connection(10, in, out, 1, true);
		in.addOutgoingConnection(c);
		connections.put(10,c);
		
		//Connect hidden node id 8 to output id 7
		in = hiddenNodes.get(8);
		out = outputNodes.get(7);
		c = new Connection(11, in, out, 1, true);
		in.addOutgoingConnection(c);
		connections.put(11,c);
		
		//Connect output id 5 to itself
		in = outputNodes.get(5);
		out = outputNodes.get(5);
		c = new Connection(12, in, out, 1, true);
		in.addOutgoingConnection(c);
		connections.put(12,c);
		
		//Connect output id 5 to hidden node id 8
				in = outputNodes.get(5);
				out = hiddenNodes.get(8);
				c = new Connection(13, in, out, 1, true);
				in.addOutgoingConnection(c);
				connections.put(13,c);
		
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork( inputNodes, outputNodes, hiddenNodes, connections);
		
		//Do 1st activation 
		double[] inputs = {1,1,1};
		double[] outputs =nn.activate(inputs, false);
		assertTrue("O:0 A:1 " + outputs[0], isEqual(0, outputs[0]));
		assertTrue("O:1 A:1 " + outputs[1], isEqual(0, outputs[1]));
		assertTrue("O:2 A:1 " + outputs[2], isEqual(0, outputs[2]));
		
		//Do 2nd activation 
				outputs =nn.activate(inputs, false);
				assertTrue("O:0 A:2 " + outputs[0], isEqual(3, outputs[0]));
				assertTrue("O:1 A:2 " + outputs[1], isEqual(3, outputs[1]));
				assertTrue("O:2 A:2 " + outputs[2] , isEqual(3, outputs[2]));
				
				//Do 3rd activation 
				outputs =nn.activate(inputs, false);
				assertTrue("O:0 A:3 " + outputs[0], isEqual(6, outputs[0]));
				assertTrue("O:1 A:3 " + outputs[1], isEqual(3, outputs[1]));
				assertTrue("O:2 A:3 " + outputs[2], isEqual(4, outputs[2]));
				
				//Do 4th activation 
				outputs =nn.activate(inputs, false);
				assertTrue("O:0 A:4 " + outputs[0], isEqual(9, outputs[0]));
				assertTrue("O:1 A:4 " + outputs[1], isEqual(3, outputs[1]));
				assertTrue("O:2 A:4 " + outputs[2], isEqual(7, outputs[2]));
		
	}
	
	@Test
	public void testIsAllOutputsReachable_ItIs(){
		//Set up input and output nodes
		createNodes();
				
		//Connect input nodes to output nodes
		connectOneInputToOneOutput();
				
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork( inputNodes, outputNodes, hiddenNodes, connections);
				
		//Test if they are reachable
		assertTrue(nn.isAllOutputsReachable());
				
				
	}
	
	@Test
	public void testIsAllOutputsReachable_Not(){
		//Set up input and output nodes
		createNodes();
				
		//Connect input nodes to output nodes
		for (int i = 0; i < 2; i++){
			Node in = inputNodes.get(i + 1);
			Node out = outputNodes.get(i + 5);
			Connection c = new Connection(i, in, out, 1, true);
			in.addOutgoingConnection(c);
			connections.put(i,c);
		}
				
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork( inputNodes, outputNodes, hiddenNodes, connections);
				
		//Test if they are reachable
		assertTrue(!nn.isAllOutputsReachable());				
	}
	
	private void createNodes(){
		Node i1, i2, i3; //Input nodes
		Node h1, h2; //Hidden nodes
		Node o1, o2, o3; //Output nodes
		ActivationFunction afSame = new AFSame();
		
		i1 = new Node(1, NodeType.INPUT, afSame, new HashMap<Integer, Connection>());
		i2 = new Node(2, NodeType.INPUT, afSame, new HashMap<Integer, Connection>());
		i3 = new Node(3, NodeType.INPUT, afSame, new HashMap<Integer, Connection>());
		inputNodes.put(1,i1);
		inputNodes.put(2,i2);
		inputNodes.put(3,i3);
		
		o1 = new Node(5, NodeType.OUTPUT, afSame, new HashMap<Integer, Connection>());
		o2 = new Node(6, NodeType.OUTPUT, afSame, new HashMap<Integer, Connection>());
		o3 = new Node(7, NodeType.OUTPUT, afSame, new HashMap<Integer, Connection>());
		outputNodes.put(5,o1);
		outputNodes.put(6,o2);
		outputNodes.put(7,o3);
		
		h1 = new Node(8, NodeType.HIDDEN, afSame, new HashMap<Integer, Connection>());
		h2 = new Node(9, NodeType.HIDDEN, afSame, new HashMap<Integer, Connection>());
		hiddenNodes.put(8,h1);
		hiddenNodes.put(9,h2);
	}
	
	private void connectOneInputToOneOutput(){
		
		for (int i = 0; i < 3; i++){
			Node in = inputNodes.get(i + 1);
			Node out = outputNodes.get(i + 5);
			Connection c = new Connection(i, in, out, 1, true);
			in.addOutgoingConnection(c);
			connections.put(i,c);
		}
	}
	
	private void connectAllInputsToAllOutput(){
		int counter = 0;
		for (int i = 0; i < inputNodes.size(); i++){
			Node in = inputNodes.get(i + 1);
			for (int j = 0; j < outputNodes.size(); j++){
				Node out = outputNodes.get(j + 5);
				Connection c = new Connection(counter, in, out, 1, true);
				in.addOutgoingConnection(c);
				connections.put(counter,c);
				counter++;
			}			
		}
	}
	
	
		
	private boolean isEqual(double exp, double act){
		double e = 0.0001;
		return (act > exp -e && act < exp + e);
	}
	

}
