package mower;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import mower.Mower.eAction;
import mower.MowerPosition.eDirection;

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
	public void testInvalidGroundSize()
	{
		this.testInvalidSetup("MowItNow_InvalidGroundSize_1.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_2.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_3.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_4.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_5.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_6.cfg", MowerException.INVALID_PARAMETER_VALUE);
		this.testInvalidSetup("MowItNow_InvalidGroundSize_7.cfg", MowerException.INVALID_NUMBER_OF_PARAMETERS);
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
			
			// Vérifier qu'on a bien 2 tondeuse,
			Assert.assertEquals(2, manager.getMowersList().size());
			
			// Vérifier les paramètres de la 1ere tondeuse
			checkGroundSize(manager.getMower(0), 5, 5);
			checkPosition(manager.getMower(0), 1, 2, eDirection.N);
			checkActionsList(manager.getMower(0), MOWER1_ACTIONS_LIST);

			// Vérifier les paramètres de la 2e tondeuse
			checkGroundSize(manager.getMower(1), 5, 5);
			checkPosition(manager.getMower(1), 3, 3, eDirection.E);
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
			
			checkPosition(manager.getMower(0), 1, 3, eDirection.N);
			checkPosition(manager.getMower(1), 5, 1, eDirection.E);
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
	
	private void testInvalidSetup(String fileName, int errorCode)
	{
		MowersManager manager = MowersManager.getInstance();
		
		try
		{
			manager.setup(PACKAGE_TEST_DIR + "/" + fileName);
			Assert.fail("Erreur de paramètre non détectée sur " + fileName);
		}
		catch (MowerException exception)
		{
			Assert.assertEquals(errorCode, exception.getErrorCode());
		}
		catch (IOException exception)
		{
			Assert.fail(exception.getMessage());
			exception.printStackTrace();
		}
	}
	
	private void checkGroundSize(Mower mower, int width, int height)
	{
		int[] groundSize = mower.groundSize();
		Assert.assertEquals(2, groundSize.length);
		Assert.assertEquals(width, groundSize[0]);
		Assert.assertEquals(height, groundSize[1]);
	}
	
	private void checkPosition(Mower mower, int x, int y, eDirection direction)
	{
		Assert.assertEquals(x,         mower.position().x());
		Assert.assertEquals(y,         mower.position().y());
		Assert.assertEquals(direction, mower.position().direction());
	}
	
	
	private void checkActionsList(Mower mower, eAction[] actionsList)
	{
		List<eAction> mowerActionsList = mower.actionsList();
		
		Assert.assertEquals(actionsList.length, mowerActionsList.size());

		for(int i=0; i<actionsList.length; i++)
		{
			Assert.assertEquals(actionsList[i], mowerActionsList.get(i));
		}
	}
	
	
	
}
