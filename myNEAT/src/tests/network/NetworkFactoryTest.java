package tests.network;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import myNEAT.network.Connection;
import myNEAT.network.NetworkFactory;
import myNEAT.network.NeuralNetwork;
import myNEAT.network.Node;
import myNEAT.network.activationFunctions.AFBias;
import myNEAT.network.activationFunctions.AFSame;
import myNEAT.network.activationFunctions.ActivationFunction;

import org.junit.Before;
import org.junit.Test;

public class NetworkFactoryTest {

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
	public void testCreateNetwork() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateGenomeFile() {
		//Create nodes
		
		//Create connections
				
				
				//Create neural network
				NeuralNetwork nn = new NeuralNetwork(0, inputNodes, outputNodes, hiddenNodes, connections);
		
				//Create genome file
				String filepath = "/tests/genomefile";
				NetworkFactory nf = new NetworkFactory();
				try {
					nf.createGenomeFile(nn, filepath);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					fail(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					fail(e.getMessage());
				}
				
				//Test if file was created correctly
				
	}
	
	private void createNodes(){
		Node i1, i2, i3; //Input nodes
		Node bias; //Bias node
		Node h1, h2; //Hidden nodes
		Node o1, o2; //Output nodes
		ActivationFunction afSame = new AFSame();
		ActivationFunction afBias = new AFBias(1);
		
		i1 = new Node(1, NeuralNetwork.INPUT, afSame, new ArrayList<Connection>());
		i2 = new Node(2, NeuralNetwork.INPUT, afSame, new ArrayList<Connection>());
		i3 = new Node(3, NeuralNetwork.INPUT, afBias, new ArrayList<Connection>());
		bias = new Node(4, NeuralNetwork.BIAS, afBias, new ArrayList<Connection>());
		inputNodes.add(i1);
		inputNodes.add(i2);
		inputNodes.add(i3);
		inputNodes.add(bias);
		
		o1 = new Node(5, NeuralNetwork.OUTPUT, afSame, new ArrayList<Connection>());
		o2 = new Node(6, NeuralNetwork.OUTPUT, afSame, new ArrayList<Connection>());
		outputNodes.add(o1);
		outputNodes.add(o2);
		
		h1 = new Node(7, NeuralNetwork.HIDDEN, afBias, new ArrayList<Connection>());
		h2 = new Node(8, NeuralNetwork.HIDDEN, afSame, new ArrayList<Connection>());
		hiddenNodes.add(h1);
		hiddenNodes.add(h2);
	}

}
