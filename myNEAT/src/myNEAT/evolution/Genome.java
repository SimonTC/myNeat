package myNEAT.evolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import myNEAT.network.Connection;
import myNEAT.network.NeuralNetwork;
import myNEAT.network.Node;
import myNEAT.network.activationFunctions.AFBias;
import myNEAT.network.activationFunctions.AFSame;
import myNEAT.network.activationFunctions.ActivationFunction;

public class Genome {
	NeuralNetwork phenotype;
	int genomeID;
	private HashMap<Integer, Node> nodes;
	private HashMap<Integer, Connection> connections;
	
	/**
	 * Creates a new genome based on a genomefile
	 * @param genomePath
	 * @throws FileNotFoundException 
	 */
	public Genome(String genomePath) throws FileNotFoundException{
		//Open file and scanner
				File file = new File(genomePath);
				Scanner inputFile = new Scanner(file);
						
				//Ready lists
				nodes = new HashMap<>();
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
				
				//Close scanner
				inputFile.close();
	}
	
	/**
	 * Analyzes a line to decide which component should be created
	 * @param curLine
	 * @return
	 */
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
	
	/**
	 * Creates a node based on the information in the given line
	 * @param curLine
	 */
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
		
		//Add node to list
			nodes.put(nodeID, n);
	
	}
	
	/**
	 * Creates an activation function based on the given activation function id
	 * @param activationFunctionID
	 * @param biasValue
	 * @return
	 */
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
	
	/**
	 * Creates a connection based on the given line
	 * @param curLine
	 */
	private void createConnection(Scanner curLine){
		//TODO Add exception handler for wrong types
		
		//Read values
		int connectionID = curLine.nextInt();
		int inNodeID = curLine.nextInt();
		int outNodeID = curLine.nextInt();
		double connectionWeight = curLine.nextDouble();
		int enabled = curLine.nextInt();
		
		//Collect in and out node
		Node in = nodes.get(inNodeID);
		Node out = nodes.get(outNodeID);
		
		//Create connection
		Connection c = new Connection(connectionID, in, out, connectionWeight, enabled == 1);
		
		//Add connection to list
		connections.put(connectionID, c);
		
	}
}
