package stcl.tests.phenotypes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import stcl.tests.phenotypes.activationFunctions.AllTests_phenotypes_activationFunctions;

@RunWith(Suite.class)
@SuiteClasses({ ConnectionTest.class, NeuralNetworkTest.class, NodeTest.class, AllTests_phenotypes_activationFunctions.class })
public class AllTests_phenotypes {

}
