package mower;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import mower.MowerPosition.eOrientation;

public class TestMowersManager extends TestMower
{
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// CONSTANTS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// TYPES
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// STATIC METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// ATTRIBUTES
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// CONSTRUCTORS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// INTERFACE 
	/// interfaceName
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PUBLIC METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testNullFile()
	{
		MowersManager manager = MowersManager.getInstance();
		File file = null;
		try
		{
			manager.setup(file);
			
			Assert.fail("Erreur de paramètre non détectée sur fichier inexistant");
		}
		catch (MowerException | IOException exception)
		{
			Assert.assertEquals(MowerException.class, exception.getClass());
			Assert.assertEquals(MowerException.FILE_DOES_NOT_EXIST, ((MowerException)(exception)).getErrorCode());
		}
	}
	
	
	@Test
	public void testFileDoesNotExist()
	{
		MowersManager manager = MowersManager.getInstance();
		
		try
		{
			manager.setup(PACKAGE_TEST_DIR + "/thisFileDoesNotExist");
			Assert.fail("Erreur de paramètre non détectée sur fichier inexistant");
		}
		catch (MowerException | IOException exception)
		{
			Assert.assertEquals(MowerException.class, exception.getClass());
			Assert.assertEquals(MowerException.FILE_DOES_NOT_EXIST, ((MowerException)(exception)).getErrorCode());
		}
	}
	
	
	@Test
	public void testInvalidGroundSize()
	{
		this.testInvalidSetup("MowItNow_InvalidGroundSize_1.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_2.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_3.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_4.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_5.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_6.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_7.cfg", MowerException.INVALID_NUMBER_OF_PARAMETERS);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_8.cfg", MowerException.INVALID_NUMBER_OF_PARAMETERS);
	}
	
	
	@Test
	public void testInvalidPosition()
	{
		this.testInvalidSetup("MowItNow_InvalidPosition_1.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidPosition_2.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidPosition_3.cfg", MowerException.INVALID_NUMBER_OF_PARAMETERS);
	}
	
	
	@Test
	public void testInvalidAction()
	{
		this.testInvalidSetup("MowItNow_InvalidAction.cfg", MowerException.INVALID_PARAMETER_VALUE);
	}
	
	
	@Test
	public void testSetup()
	{
		MowersManager manager = MowersManager.getInstance();
		
		try
		{
			manager.setup(PACKAGE_TEST_DIR + "/" + "MowItNow.cfg");
			
			// Vérifier qu'on a bien 2 tondeuses,
			Assert.assertEquals(2, manager.getMowersList().size());
			
			Assert.assertNotEquals(null, manager.getMower(0));
			Assert.assertNotEquals(null, manager.getMower(1));
			Assert.assertEquals(null, manager.getMower(2));
			Assert.assertEquals(null, manager.getMower(-1));


			// Vérifier les paramètres de la 1ere tondeuse
			checkGroundSize(manager.getMower(0), 5, 5);
			checkPosition(manager.getMower(0), 1, 2, eOrientation.N);
			checkActionsList(manager.getMower(0), MOWER1_ACTIONS_LIST);

			// Vérifier les paramètres de la 2e tondeuse
			checkGroundSize(manager.getMower(1), 5, 5);
			checkPosition(manager.getMower(1), 3, 3, eOrientation.E);
			checkActionsList(manager.getMower(1), MOWER2_ACTIONS_LIST);
		}
		catch (MowerException | IOException exception)
		{
			Assert.fail(exception.getMessage());
			exception.printStackTrace();
		}
	}
	
	
	
	@Test
	public void testExecute()
	{
		MowersManager manager = MowersManager.getInstance();
		
		try
		{
			manager.setup(PACKAGE_TEST_DIR + "/" + "MowItNow.cfg");
			manager.execute();
			
			checkPosition(manager.getMower(0), 1, 3, eOrientation.N);
			checkPosition(manager.getMower(1), 5, 1, eOrientation.E);
		}
		catch (MowerException | IOException exception)
		{
			Assert.fail(exception.getMessage());
			exception.printStackTrace();
		}
	}
	
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PROTECTED METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PRIVATE METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
