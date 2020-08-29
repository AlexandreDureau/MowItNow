package mower;

/**
 * Contient les informations quand à la position d'une tondeuse
 * @author Alexandre
 *
 */
public class MowerPosition
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
	public enum eDirection {N,E,W,S}
	
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
	protected int X;
	protected int Y;
	protected eDirection Direction;
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// CONSTRUCTORS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Instancie un objet 'MowerPosition' 
	 */
	protected MowerPosition(){}
	
	/**
	 * Instancie un objet 'MowerPosition' à partir d'un texte.
	 * Celui ci est censé répondre au format "x y d" , où 'x' est l'abscisse, 'y' l'ordonnée et 'd' l'orientation de
	 * la tondeuse. 
	 * NB : La méthode est tolérante avec le nombre d'espaces utilisés pour séparer les champs
	 *  
	 * @param text
	 * @return
	 * @throws MowerException
	 */
	public static MowerPosition getInstance(String text) throws MowerException
	{
		MowerPosition position = null;
		
		String[] parameters = text.trim().split("\\s+");
		
		if(3 == parameters.length)
		{
			position = new MowerPosition();

			position.setX(parameters[0]);
			position.setY(parameters[1]);
			position.setDirection(parameters[2]);
		}
		else
		{
			MowerException.throwInvalidNumberOfParameters(3, parameters.length, text);
		}
		
		return position;
	}
	
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
	
	/** 
	 * @return l'abscisse de la tondeuse
	 */
	public int x()
	{
		return X;
	}
	
	
	/**
	 * 
	 * @return l'ordonnée de la tondeuse
	 */
	public int y()
	{
		return Y;
	}
	
	/**
	 * @return la direction de la tondeuse
	 */
	public eDirection direction()
	{
		return Direction;
	}
	
	
	/**
	 * Effectue une rotation de 90° vers la droite
	 */
	public void rotateRight()
	{
		switch(Direction)
		{
			case N : Direction = eDirection.E; break;
			case E : Direction = eDirection.S; break;
			case W : Direction = eDirection.N; break;
			case S : Direction = eDirection.W; break;
			default: /* Cas non atteignable*/
		}
	}
	
	
	/**
	 * Effectue une rotation de 90° vers la gauche
	 */
	public void rotateLeft()
	{
		switch(Direction)
		{
			case N : Direction = eDirection.W; break;
			case E : Direction = eDirection.N; break;
			case W : Direction = eDirection.S; break;
			case S : Direction = eDirection.E; break;
			default: /* Cas non atteignable*/
		}
	}
	
	
	/**
	 * Incrémente les coordonnées avec les valeurs données en paramètre
	 * 
	 * @param x_increment
	 * @param y_increment
	 */
	public void increment(int x_increment, int y_increment)
	{
		X +=x_increment;
		Y +=y_increment;
	}
	
	
	/**
	 * Décrémente les coordonnées avec les valeurs données en paramètre
	 * 
	 * @param x_increment
	 * @param y_increment
	 */
	public void decrement(int x_increment, int y_increment)
	{
		X -=x_increment;
		Y -=y_increment;
	}
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PROTECTED METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Renseigne l'abscisse de la tondeuse 
	 * @param parameter
	 * @throws MowerException
	 */
	protected void setX(String parameter) throws MowerException
	{
		try
		{
			int x = new Integer(parameter).intValue();
			
			if(x >=0)
			{
				X = x;
			}
			
			else
			{
				MowerException.throwInvalidParameterValue("X", "{0," + "+inf}", parameter);
			}
		}
		catch(NumberFormatException e)
		{
			MowerException.throwInvalidParameterValue("X", "{0," + "+inf}", parameter);
		}
	}
	
	/**
	 * Renseigne l'ordonnée de la tondeuse
	 * @param parameter
	 * @throws MowerException
	 */
	protected void setY(String parameter) throws MowerException
	{
		try
		{
			int y = new Integer(parameter).intValue();
			
			if(y >=0)
			{
				Y = y;
			}
			
			else
			{
				MowerException.throwInvalidParameterValue("Y", "{0," + "+inf}", parameter);
			}
		}
		catch(NumberFormatException e)
		{
			MowerException.throwInvalidParameterValue("Y", "{0," + "+inf}", parameter);
		}	
	}
	
	
	/**
	 * Renseigne la direction de la tondeuse
	 * @param parameter
	 * @throws MowerException
	 */
	protected void setDirection(String parameter) throws MowerException
	{
		try
		{
			Direction = eDirection.valueOf(parameter);
			
			if(null == Direction)
			{
				MowerException.throwInvalidParameterValue("Direction", "{N,E,W,S}", parameter);
			}		
		}
		
		catch(IllegalArgumentException exception)
		{
			MowerException.throwInvalidParameterValue("Direction", "{N,E,W,S}", parameter);
		}
	}
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PRIVATE METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
