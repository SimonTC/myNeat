package stcl.myNEAT.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import stcl.myNEAT.network.activationFunctions.AFBias;
import stcl.myNEAT.network.activationFunctions.AFSame;
import stcl.myNEAT.network.activationFunctions.ActivationFunction;

/**
 * Responsible for creation of networks based on text files and for creating text files based on networks
 * @author Simon
 *
 */
public class NetworkFactory {
	//Variables used for the creation of the network
	private int genomeID;
	private HashMap<Integer, Node> inputNodes, outputNodes, hiddenNodes, allNodes;
	private HashMap<Integer, Connection> connections;
	String delimiter = " ";
	
	public NetworkFactory(){
		inputNodes = new HashMap<>();
		outputNodes = new HashMap<>();
		hiddenNodes = new HashMap<>();
		allNodes = new HashMap<>();
		connections = new HashMap<>();
	}
	
	public NeuralNetwork createNetworkFromFile(String genomePath) throws FileNotFoundException{
		//Open file and scanner
		File file = new File(genomePath);
		Scanner inputFile = new Scanner(file);
				
		//Ready lists
		inputNodes = new HashMap<>();
		outputNodes = new HashMap<>();
		hiddenNodes = new HashMap<>();
		allNodes = new HashMap<>();
		connections = new HashMap<>();
		
		//Scan file
		Scanner curLine;
			
		while (inputFile.hasNext()){
			curLine = new Scanner(inputFile.nextLine());
			curLine.useLocale(Locale.US); //Has to use US locale to make sure that '.' is used as decimal delimiter
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
			Node n = new Node(nodeID, nodeTypeID, af, new HashMap<Integer, Connection>());
		
		//Add node to correct list
			switch (nodeTypeID){
			case NeuralNetwork.BIAS: inputNodes.put(nodeID, n); break;
			case NeuralNetwork.INPUT: inputNodes.put(nodeID, n); break;
			case NeuralNetwork.HIDDEN: hiddenNodes.put(nodeID, n); break;
			case NeuralNetwork.OUTPUT: outputNodes.put(nodeID, n); break;			
			}
			
			allNodes.put(nodeID, n);
	
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
		Node in = allNodes.get(inNodeID);
		Node out = allNodes.get(outNodeID);
		
		//Create connection
		Connection c = new Connection(connectionID, in, out, connectionWeight, enabled == 1);
		
		//Add connection to list
		connections.put(connectionID, c);
		
	}
	
	public void createGenomeFile(NeuralNetwork network, String filePath) throws FileNotFoundException, IOException{
		//Read info from neural network
		genomeID = network.getID();
		allNodes = network.getAllNodes();
		distributeNodes();
		connections = network.getConnections();
		
		//Write to file
		File genomeFile = new File(filePath);
		PrintWriter output = new PrintWriter(genomeFile);
		
		//Write first line
		output.println("genomestart " + genomeID);
		
		//Write nodes
		printNodes(output);
		
		//Write connections
		printConnections(output);
		
		//Write end line
		output.println("genomeend");
		
		output.close();
		
	}
	
	private void distributeNodes(){
		for (Node n : allNodes.values()){
			switch (n.getType()){
			case NeuralNetwork.BIAS: inputNodes.put(n.getNodeID(), n); break;
			case NeuralNetwork.INPUT: inputNodes.put(n.getNodeID(), n); break;
			case NeuralNetwork.HIDDEN: hiddenNodes.put(n.getNodeID(), n); break;
			case NeuralNetwork.OUTPUT: outputNodes.put(n.getNodeID(), n); break;
			}
		}
	}

	private void printNodes(PrintWriter output){
		//Write input nodes
		Node bias = null;
		for (Node n : inputNodes.values()){
			if (n.getType() == NeuralNetwork.BIAS){
				//We save bias for later
				bias = n;
			} else {
				output.println(writeNodeInfo(n));
			}
		}
		output.println(writeNodeInfo(bias));
		
		//Write output nodes
		for (Node n : outputNodes.values()){
			output.println(writeNodeInfo(n));			
		}
		
		//Write hidden nodes
				for (Node n : hiddenNodes.values()){
					output.println(writeNodeInfo(n));			
				}		
		
	}
	
	private String writeNodeInfo(Node n){
		String s;
		
		//Read values
				int nodeID = n.getNodeID();
				int nodeTypeID = n.getType();
				int activationFunctionID = n.getActivationFunction().getType();
				double biasValue;
				if (nodeTypeID == NeuralNetwork.BIAS){
					biasValue = n.getActivationFunction().getActivation(0); //Input value doesn't matter as output from bias is constant
				} else {
					biasValue = 0;
				}
		s = "node" + delimiter + nodeID + delimiter + nodeTypeID + delimiter + activationFunctionID + delimiter + biasValue;
		return s;
	}
	
	private void printConnections(PrintWriter output){
		for (Connection c : connections.values()){
			//Read values
			int connectionID = c.getConnectionID();
			int inNodeID = c.getIn().getNodeID();
			int outNodeID = c.getOut().getNodeID();
			double connectionWeight = c.getWeight();
			int enabled;
			if (c.isEnabled()){
				enabled = 1;
			} else {
				enabled = 0;
			}
			
			//Write info
			String s = "conn" + delimiter + connectionID + delimiter + inNodeID + delimiter + outNodeID + delimiter + connectionWeight + delimiter + enabled;
			output.println(s);
		}
		
	}
}