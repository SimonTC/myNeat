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
	
	@Test
	public void testNeuralNetworkCorrectInputPattern(){
		//Set up input and output nodes
		for (int i = 0; i < 3; i++){
			ActivationFunction af = new AFSame();
			inputNodes.add(new Node(i, NeuralNetwork.INPUT, af));
			outputNodes.add(new Node(i + 3, NeuralNetwork.OUTPUT, af));
		}
		
		//Create connections
		for (int i = 0; i < 3; i++){
			Node in = inputNodes.get(i);
			Node out = outputNodes.get(i);
			Connection c = new Connection(i, in, out, 1, true);
			connections.add(c);
		}
		
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork(inputNodes, outputNodes, hiddenNodes, connections);
		
		//Do full activation
		double[] inputs = {0,1,2};
		double[] outputs = nn.activate(inputs, true);
		
		//Test result
		double e = 0.0001;
		double out;
		for (int i = 0; i<outputs.length; i++){
			out = outputs[i];
			assertTrue(out > i - e && out < i + e);
		}
	}
	
	@Test
	public void testActivate() {
		fail("Not yet implemented");
	}

}
