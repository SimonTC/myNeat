package stcl.myNEAT.genotypes;

import java.io.FileNotFoundException;
import java.util.HashMap;

import stcl.myNEAT.phenotypes.Connection;
import stcl.myNEAT.phenotypes.Node;

/**
 * A GenomeIO class is responsible translating a genome file into the internal genome format 
 * and writing the genome back to a file
 * @author Simon
 *
 */
public abstract class GenomeIO {
		
	/**
	 * Creates a genome from the given file
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException 
	 */
	public abstract Genome readGenomeFile(String filePath) throws FileNotFoundException;
	
	/**
	 * Creates a text file representation of the genome
	 * @param filepath
	 * @throws FileNotFoundException 
	 */
	public abstract void printGenomeToFile (Genome genome, String filePath) throws FileNotFoundException;
}
