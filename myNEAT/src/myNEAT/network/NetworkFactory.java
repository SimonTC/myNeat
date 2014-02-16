package myNEAT.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import myNEAT.network.activationFunctions.AFBias;
import myNEAT.network.activationFunctions.AFSame;
import myNEAT.network.activationFunctions.ActivationFunction;

/**
 * Responsible for creation of networks based on text files and for creating text files based on networks
 * @author Simon
 *
 */
public class NetworkFactory {
	//Variables used for the creation of the network
	private int genomeID;
	private ArrayList<Node> inputNodes, outputNodes, hiddenNodes, allNodes;
	private ArrayList<Connection> connections;
	
	public NeuralNetwork createNetwork(String genomePath) throws FileNotFoundException{
		//Open file and scanner
		File file = new File(genomePath);
		Scanner inputFile = new Scanner(file);
				
		//Ready lists
		inputNodes = new ArrayList<>();
		outputNodes = new ArrayList<>();
		hiddenNodes = new ArrayList<>();
		allNodes = new ArrayList<>();
		connections = new ArrayList<>();
		
		//Scan file
		Scanner curLine;;
	
		while (inputFile.hasNext()){
			curLine = new Scanner(inputFile.nextLine());
			if (!analyzeLine(curLine)){
				//The line started with 'genomeend'
				break;
			}			
		}
		
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork(genomeID, inputNodes, outputNodes, hiddenNodes, connections);
		
		//Close scanner
		inputFile.close();
		return nn;
		
	}
	
	private boolean analyzeLine(Scanner curLine){
		if (curLine.hasNext()){
			String token = curLine.next();
						
			if (token.equalsIgnoreCase("genomestart")){
				genomeID = curLine.nextInt();					
			} else if (token.equalsIgnoreCase("node")) {
				createNode(curLine);
			} else if (token.equalsIgnoreCase("conn")){
				createConnection(curLine);
			} else if (token.equalsIgnoreCase("genomeend")) {
				return false;
			}			
		}
		//Only return false if the statement 'genomeend' is encountered. Else continue
		return true;		
	}
	
	private void createNode(Scanner curLine){
		//TODO Add exception handler if read values are of the wrong type
		//TODO add exception handler if nodetype is wrong
		
		//Read values
		int nodeID = curLine.nextInt();
			int nodeTypeID = curLine.nextInt();
			int activationFunctionID = curLine.nextInt();
			double biasValue = curLine.nextDouble();
			
		//Create activation function
			ActivationFunction af = createActivationFunction(activationFunctionID, biasValue);
			
		//Create node
			Node n = new Node(nodeID, nodeTypeID, af, new ArrayList<Connection>());
		
		//Add node to correct list
			switch (nodeTypeID){
			case NeuralNetwork.BIAS: inputNodes.add(n); break;
			case NeuralNetwork.INPUT: inputNodes.add(n); break;
			case NeuralNetwork.HIDDEN: hiddenNodes.add(n); break;
			case NeuralNetwork.OUTPUT: outputNodes.add(n); break;			
			}
			
			allNodes.add(n);
	
	}
	
	private ActivationFunction createActivationFunction(int activationFunctionID, double biasValue){
		//TODO link this method with activation function. No need to keep track of types in two classes
		ActivationFunction af;
		switch (activationFunctionID){
		case 0: af = new AFSame(); break;
		case 1: af = new AFBias(biasValue); break;
		default: af = null; break;
		}
		return af;
	}
	
	private void createConnection(Scanner curLine){
		//TODO Add exception handler for wrong types
		
		//Read values
		int connectionID = curLine.nextInt();
		int inNodeID = curLine.nextInt();
		int outNodeID = curLine.nextInt();
		double connectionWeight = curLine.nextDouble();
		int enabled = curLine.nextInt();
		
		//Collect in and out node
		Node in = getNode(inNodeID);
		Node out = getNode(outNodeID);
		
		//Create connection
		Connection c = new Connection(connectionID, in, out, connectionWeight, enabled == 1);
		
		//Add connection to list
		connections.add(c);
		
	}
	
	private Node getNode(int nodeID){
		for (Node n : allNodes){
			if (n.getNodeID() == nodeID){
				return n;
			}
		}
		
		return null;
	}
	
	
}
