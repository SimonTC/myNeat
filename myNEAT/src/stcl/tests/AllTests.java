package stcl.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import stcl.tests.evolution.AllTests_evolution;
import stcl.tests.genotypes.AllTests_genotypes;
import stcl.tests.phenotypes.AllTests_phenotypes;

@RunWith(Suite.class)
@SuiteClasses({AllTests_evolution.class, AllTests_genotypes.class, AllTests_phenotypes.class})
public class AllTests {

}
