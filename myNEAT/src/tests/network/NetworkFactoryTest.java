package tests.network;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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

	private HashMap<Integer, Node> inputNodes, outputNodes, hiddenNodes, allNodes;
	private HashMap<Integer, Connection> connections;
	
	@Before
	public void setUp() throws Exception {
		inputNodes = new HashMap<>();
		outputNodes = new HashMap<>();
		hiddenNodes = new HashMap<>();
		allNodes = new HashMap<>();
		connections = new HashMap<>();
	}
	
	
	@Test
	public void testCreateNetwork() {
		//Find genome file
		String filepath = new File("").getAbsolutePath();
		filepath += "/Test documents/genomefile to create from";
		
		//Create genome
		NetworkFactory nf = new NetworkFactory();
		try {
			NeuralNetwork nn = nf.createNetwork(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

	@Test
	public void testCreateGenomeFile() {
		//Create nodes
		createNodes();
		//Create connections
				createConnections();
				
				//Create neural network
				NeuralNetwork nn = new NeuralNetwork(0, inputNodes, outputNodes, hiddenNodes, connections);
		
				//Create genome file
				String filepath = new File("").getAbsolutePath();
				filepath += "/Test documents/created genomefile";
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
				
				//Test if file was created and written correctly
				assertTrue(infoInFileIsCorrect(filepath));
	}
	
	private void createNodes(){
		Node i1, i2, i3; //Input nodes
		Node bias; //Bias node
		Node h1, h2; //Hidden nodes
		Node o1, o2; //Output nodes
		ActivationFunction afSame = new AFSame();
		ActivationFunction afBias = new AFBias(1);
		
		i1 = new Node(1, NeuralNetwork.INPUT, afSame, new HashMap<Integer, Connection>());
		i2 = new Node(2, NeuralNetwork.INPUT, afSame, new HashMap<Integer, Connection>());
		i3 = new Node(3, NeuralNetwork.INPUT, afBias, new HashMap<Integer, Connection>());
		bias = new Node(4, NeuralNetwork.BIAS, afBias, new HashMap<Integer, Connection>());
		inputNodes.put(1,i1);
		inputNodes.put(2,i2);
		inputNodes.put(3,i3);
		inputNodes.put(4,bias);
		
		o1 = new Node(5, NeuralNetwork.OUTPUT, afSame, new HashMap<Integer, Connection>());
		o2 = new Node(6, NeuralNetwork.OUTPUT, afSame, new HashMap<Integer, Connection>());
		outputNodes.put(5,o1);
		outputNodes.put(6,o2);
		
		h1 = new Node(7, NeuralNetwork.HIDDEN, afBias, new HashMap<Integer, Connection>());
		h2 = new Node(8, NeuralNetwork.HIDDEN, afSame, new HashMap<Integer, Connection>());
		hiddenNodes.put(7,h1);
		hiddenNodes.put(8,h2);
		
		allNodes.putAll(inputNodes);
		allNodes.putAll(outputNodes);
		allNodes.putAll(hiddenNodes);
	}
	
	private void createConnections(){
		Connection c0, c1, c2, c3, c4, c5, c6, c7;
		c0 = new Connection(0, allNodes.get(1), allNodes.get(5), 0.1, true);
		c1 = new Connection(1, allNodes.get(1), allNodes.get(7), 0.5, true);
		c2 = new Connection(2, allNodes.get(2), allNodes.get(7), 0.6, true);
		c3 = new Connection(3, allNodes.get(2), allNodes.get(8), 0.3, true);
		c4 = new Connection(4, allNodes.get(4), allNodes.get(8), 5.7, true);
		c5 = new Connection(5, allNodes.get(7), allNodes.get(8), 1.2, true);
		c6 = new Connection(6, allNodes.get(8), allNodes.get(6), 0.001, true);
		c7 = new Connection(7, allNodes.get(6), allNodes.get(5), 0.3, false);
		
		connections.put(0, c0);
		connections.put(1, c1);
		connections.put(2, c2);
		connections.put(3, c3);
		connections.put(4, c4);
		connections.put(5, c5);
		connections.put(6, c6);
		connections.put(7, c7);
		
	}
	
	private boolean infoInFileIsCorrect(String filepath){
		//Open file and scanner
		File file = new File(filepath);
		try {
			Scanner inputFile = new Scanner(file);
			String s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("genomestart 0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("node 1 1 0 0.0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("node 2 1 0 0.0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("node 3 1 1 0.0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("node 4 0 1 1.0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("node 5 2 0 0.0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("node 6 2 0 0.0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("node 7 3 1 0.0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("node 8 3 0 0.0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("conn 0 1 5 0.1 1")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("conn 1 1 7 0.5 1")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("conn 2 2 7 0.6 1")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("conn 3 2 8 0.3 1")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("conn 4 4 8 5.7 1")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("conn 5 7 8 1.2 1")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("conn 6 8 6 0.001 1")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("conn 7 6 5 0.3 0")) return false; s = inputFile.nextLine();
			if (!s.equalsIgnoreCase("genomeend")) return false; 
			
			inputFile.close();
			return true;

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
			
	}
}
