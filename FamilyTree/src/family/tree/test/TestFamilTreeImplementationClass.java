/**
 * 
 */
package family.tree.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import family.tree.implementation.FamilyTreeImplementation;
import family.tree.objects.FamilyTreeException;
import family.tree.objects.Node;
import family.tree.objects.Person;

/**
 * @author Swetha
 *
 */
public class TestFamilTreeImplementationClass {

	private Person male;
	private Person female;
	FamilyTreeImplementation implementation;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		implementation = new FamilyTreeImplementation();
		male = new Person("Evan", "M", 60);
		female = new Person("Diana", "F", 60);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		//Clear the references
		implementation = null;
		male = null;
		female = null;
		
	}

	@Test
	public void testAddAncestor() {
		try {
			implementation.addAncestor(male, female);				
		} catch (FamilyTreeException e1) {
			fail("addAncestor function should not throw any exception.");

		}
	}
	
	@Test
	public void testGetAncestor() {
		try {
			implementation.addAncestor(male, female);
			ArrayList<Node> ancestor = implementation.getAncestor();
			assertEquals("Ancestor size must be two (Husband & Wife)",2, ancestor.size());
		} catch (FamilyTreeException e1) {
			fail("addAncestor function should not throw any exception.");

		}
		
	}
	

}
