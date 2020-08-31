package mower;

import org.junit.Assert;
import org.junit.Test;

import mower.MowerPosition.eOrientation;

public class TestMowerPosition extends TestMower
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
	public void test_GetInstance_Nominal_OrientationNorth()
	{
		try 
		{
			MowerPosition position = MowerPosition.getInstance("0 3 N");
			
			Assert.assertEquals(0,              position.x());
			Assert.assertEquals(3,              position.y());
			Assert.assertEquals(eOrientation.N, position.orientation());
		} 
		
		catch (MowerException exception) 
		{
			Assert.fail(exception.getMessage());
		}
	}
	
	
	
	@Test
	public void test_GetInstance_Nominal_OrientationEast()
	{
		try 
		{
			MowerPosition position = MowerPosition.getInstance("4 7 E");
			
			Assert.assertEquals(4,              position.x());
			Assert.assertEquals(7,              position.y());
			Assert.assertEquals(eOrientation.E, position.orientation());
		} 
		
		catch (MowerException exception) 
		{
			Assert.fail(exception.getMessage());
		}
	}

	
	
	@Test
	public void test_GetInstance_Nominal_OrientationWest()
	{
		try 
		{
			MowerPosition position = MowerPosition.getInstance("2 1 W");
			
			Assert.assertEquals(2,              position.x());
			Assert.assertEquals(1,              position.y());
			Assert.assertEquals(eOrientation.W, position.orientation());
		} 
		
		catch (MowerException exception) 
		{
			Assert.fail(exception.getMessage());
		}
	}
	
	
	
	@Test
	public void test_GetInstance_Nominal_OrientationSouth()
	{
		try 
		{
			MowerPosition position = MowerPosition.getInstance("7 5 S");
			
			Assert.assertEquals(7,              position.x());
			Assert.assertEquals(5,              position.y());
			Assert.assertEquals(eOrientation.S, position.orientation());
		} 
		
		catch (MowerException exception) 
		{
			Assert.fail(exception.getMessage());
		}
	}
	
	
	
	@Test
	public void test_GetInstance_Nominal_UselessSpaces()
	{
		try 
		{
			MowerPosition position = MowerPosition.getInstance("   0 3   N ");
			Assert.assertEquals(0,              position.x());
			Assert.assertEquals(3,              position.y());
			Assert.assertEquals(eOrientation.N, position.orientation());
		} 
		
		catch (MowerException exception) 
		{
			Assert.fail(exception.getMessage());
		}
	}
	
	
	
	@Test
	public void test_GetInstance_Fail_TooMuchParameters()
	{
		try 
		{
			MowerPosition.getInstance("  4 0 3   N ");
			Assert.fail("Exception should have been raised");
		} 
		
		catch (MowerException exception) 
		{
			Assert.assertEquals(MowerException.INVALID_NUMBER_OF_PARAMETERS, 
					            exception.getErrorCode());
		}
	}
	
	
	
	@Test
	public void test_GetInstance_Fail_InvalidOrientation()
	{
		try 
		{
			MowerPosition.getInstance("  0 3   p ");
			Assert.fail("Exception should have been raised");
		} 
		
		catch (MowerException exception) 
		{
			Assert.assertEquals(MowerException.INVALID_PARAMETER_VALUE, 
					            exception.getErrorCode());
		}
	}
	
	
	@Test
	public void test_GetInstance_Fail_InvalidValueX()
	{
		try 
		{
			MowerPosition.getInstance("  -1 3   n ");
			Assert.fail("Exception should have been raised");
		} 
		
		catch (MowerException exception) 
		{
			Assert.assertEquals(MowerException.INVALID_PARAMETER_VALUE, 
					            exception.getErrorCode());
		}
	}
	
	@Test
	public void test_GetInstance_Fail_InvalidXNotANumber()
	{
		try 
		{
			MowerPosition.getInstance("  g 3   N ");
			Assert.fail("Exception should have been raised");
		} 
		
		catch (MowerException exception) 
		{
			Assert.assertEquals(MowerException.INVALID_PARAMETER_VALUE, 
					            exception.getErrorCode());
		}
	}
	
	
	@Test
	public void test_GetInstance_Fail_InvalidValueY()
	{
		try 
		{
			MowerPosition.getInstance("  0 -9   N ");
			Assert.fail("Exception should have been raised");
		} 
		
		catch (MowerException exception) 
		{
			Assert.assertEquals(MowerException.INVALID_PARAMETER_VALUE, 
					            exception.getErrorCode());
		}
	}
	
	@Test
	public void test_GetInstance_Fail_InvalidYNotANumber()
	{
		try 
		{
			MowerPosition.getInstance("  4 ?  N ");
			Assert.fail("Exception should have been raised");
		} 
		
		catch (MowerException exception) 
		{
			Assert.assertEquals(MowerException.INVALID_PARAMETER_VALUE, 
					            exception.getErrorCode());
		}
	}
	
	@Test
	public void test_Increment()
	{
		try 
		{
			MowerPosition position = MowerPosition.getInstance("7 5 E");
			position.increment(2, 1);
			Assert.assertEquals(9,position.x());
			Assert.assertEquals(6,position.y());
			Assert.assertEquals(eOrientation.E,position.orientation());
		} 
		
		catch (MowerException exception) 
		{
			Assert.fail(exception.getMessage());
		}
	}
	
	
	@Test
	public void test_Decrement()
	{
		try 
		{
			MowerPosition position = MowerPosition.getInstance("7 5 S");
			position.decrement(2, 1);
			Assert.assertEquals(5,position.x());
			Assert.assertEquals(4,position.y());
			Assert.assertEquals(eOrientation.S,position.orientation());
		} 
		
		catch (MowerException exception) 
		{
			Assert.fail(exception.getMessage());
		}
	}
	
	@Test
	public void test_RotateLeft()
	{
		try 
		{
			MowerPosition position = MowerPosition.getInstance("7 5 S");
			position.rotateLeft();
			Assert.assertEquals(7,position.x());
			Assert.assertEquals(5,position.y());
			Assert.assertEquals(eOrientation.E,position.orientation());
			
			position.rotateLeft();
			Assert.assertEquals(7,position.x());
			Assert.assertEquals(5,position.y());
			Assert.assertEquals(eOrientation.N,position.orientation());
			
			position.rotateLeft();
			Assert.assertEquals(7,position.x());
			Assert.assertEquals(5,position.y());
			Assert.assertEquals(eOrientation.W,position.orientation());
			
			position.rotateLeft();
			Assert.assertEquals(7,position.x());
			Assert.assertEquals(5,position.y());
			Assert.assertEquals(eOrientation.S,position.orientation());
		} 
		
		catch (MowerException exception) 
		{
			Assert.fail(exception.getMessage());
		}
	}
	
	
	@Test
	public void test_RotateRight()
	{
		try 
		{
			MowerPosition position = MowerPosition.getInstance("7 5 E");
			position.rotateRight();
			Assert.assertEquals(7,position.x());
			Assert.assertEquals(5,position.y());
			Assert.assertEquals(eOrientation.S,position.orientation());
			
			position.rotateRight();
			Assert.assertEquals(7,position.x());
			Assert.assertEquals(5,position.y());
			Assert.assertEquals(eOrientation.W,position.orientation());
			
			position.rotateRight();
			Assert.assertEquals(7,position.x());
			Assert.assertEquals(5,position.y());
			Assert.assertEquals(eOrientation.N,position.orientation());
			
			position.rotateRight();
			Assert.assertEquals(7,position.x());
			Assert.assertEquals(5,position.y());
			Assert.assertEquals(eOrientation.E,position.orientation());
		} 
		
		catch (MowerException exception) 
		{
			Assert.fail(exception.getMessage());
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
