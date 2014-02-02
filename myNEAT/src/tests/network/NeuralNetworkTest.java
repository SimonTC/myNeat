package tests.network;

import static org.junit.Assert.*;

import java.util.ArrayList;

import myNEAT.network.Connection;
import myNEAT.network.NeuralNetwork;
import myNEAT.network.Node;
import myNEAT.network.activationFunctions.AFSame;
import myNEAT.network.activationFunctions.ActivationFunction;

import org.junit.Before;
import org.junit.Test;

public class NeuralNetworkTest {
	
	ArrayList<Node> inputNodes;
	ArrayList<Node> outputNodes;
	ArrayList<Node> hiddenNodes;
	ArrayList<Connection> connections;
	
	@Before
	public void setUp() throws Exception {
		inputNodes = new ArrayList<>();
		outputNodes = new ArrayList<>();
		hiddenNodes = new ArrayList<>();
		connections = new ArrayList<>();
	}
	
	private void setupInputOutputNodes(){
		for (int i = 0; i < 3; i++){
			ActivationFunction af = new AFSame();
			inputNodes.add(new Node(i, NeuralNetwork.INPUT, af, new ArrayList<Connection>()));
			outputNodes.add(new Node(i + 3, NeuralNetwork.OUTPUT, af, new ArrayList<Connection>()));
		}
	}
	
	private void connectOneInputToOneOutput(){
		for (int i = 0; i < 3; i++){
			Node in = inputNodes.get(i);
			Node out = outputNodes.get(i);
			Connection c = new Connection(i, in, out, 1, true);
			in.addOutgoingConnection(c);
			connections.add(c);
		}
	}
	
	private void connectAllInputsToAllOutput(){
		int counter = 0;
		for (int i = 0; i < inputNodes.size(); i++){
			Node in = inputNodes.get(i);
			for (int j = 0; j < outputNodes.size(); j++){
				Node out = outputNodes.get(j);
				Connection c = new Connection(counter++, in, out, 1, true);
				in.addOutgoingConnection(c);
				connections.add(c);
			}			
		}
	}
	
	@Test
	/*
	 * Test if the correct input pattern is made. Also tests full activation
	 */
	public void testNeuralNetworkCorrectInputPattern(){
		//Set up input and output nodes
		setupInputOutputNodes();
		
		//Connect input nodes to output nodes
		connectOneInputToOneOutput();
		
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork(inputNodes, outputNodes, hiddenNodes, connections);
		
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
		setupInputOutputNodes();
		
		//Add hidden node
		ActivationFunction af = new AFSame();
		hiddenNodes.add(new Node(6, NeuralNetwork.HIDDEN, af, new ArrayList<Connection>()));
				
		//Connect all input nodes to output nodes
		connectAllInputsToAllOutput();
		
		//Connect input 1 to hidden node 
		Node in = inputNodes.get(1);
		Node out = hiddenNodes.get(0);
		Connection c = new Connection(7, in, out, 1, true);
		in.addOutgoingConnection(c);
		connections.add(c);
		
		//Connect hidden node to output 2
		in = hiddenNodes.get(0);
		out = outputNodes.get(2);
		c = new Connection(8, in, out, 1, true);
		in.addOutgoingConnection(c);
		connections.add(c);
		
		//Connect output 0 to itself
		in = outputNodes.get(0);
		out = outputNodes.get(0);
		c = new Connection(9, in, out, 1, true);
		in.addOutgoingConnection(c);
		connections.add(c);
		
		//Connect output 0 to hidden node
				in = outputNodes.get(0);
				out = hiddenNodes.get(0);
				c = new Connection(10, in, out, 1, true);
				in.addOutgoingConnection(c);
				connections.add(c);
		
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork(inputNodes, outputNodes, hiddenNodes, connections);
		
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
		
	private boolean isEqual(double exp, double act){
		double e = 0.0001;
		return (act > exp -e && act < exp + e);
	}
	@Test
	public void testIsAllOutputsReachable_ItIs(){
		//Set up input and output nodes
		setupInputOutputNodes();
				
		//Connect input nodes to output nodes
		connectOneInputToOneOutput();
				
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork(inputNodes, outputNodes, hiddenNodes, connections);
				
		//Test if they are reachable
		assertTrue(nn.isAllOutputsReachable());
				
				
	}
	
	@Test
	public void testIsAllOutputsReachable_Not(){
		//Set up input and output nodes
		setupInputOutputNodes();
				
		//Connect input nodes to output nodes
		for (int i = 0; i < 2; i++){
			Node in = inputNodes.get(i);
			Node out = outputNodes.get(i);
			Connection c = new Connection(i, in, out, 1, true);
			in.addOutgoingConnection(c);
			connections.add(c);
		}
				
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork(inputNodes, outputNodes, hiddenNodes, connections);
				
		//Test if they are reachable
		assertTrue(!nn.isAllOutputsReachable());
				
				
	}

}
