package mower;

/**
 * Contient les informations quand à la position d'une tondeuse
 * @author Alexandre
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
	public enum eOrientation {N,E,W,S}
	
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
	protected eOrientation Orientation;
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// CONSTRUCTORS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected MowerPosition(){}
	
	/**
	 * Instancie un objet 'MowerPosition' à partir d'un texte.
	 * Celui ci est censé répondre au format "x y d" , où 'x' est l'abscisse, 'y' l'ordonnée et 'd' l'orientation de
	 * la tondeuse. 
	 * NB : La méthode est tolérante avec le nombre d'espaces utilisés pour séparer les champs
	 *  
	 * @param text Chaine de caractères donnée en paramètre
	 * @return La position correspondant à la chaine de caractères
	 * @throws MowerException si le format de la chaine de caractères est invalide
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
			position.setOrientation(parameters[2]);
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
	 * Indique l'abscisse de la tondeuse
	 * 
	 * @return l'abscisse de la tondeuse
	 */
	public int x()
	{
		return X;
	}
	
	
	/**
	 * Indique l'ordonnée de la tondeuse
	 * 
	 * @return l'ordonnée de la tondeuse
	 */
	public int y()
	{
		return Y;
	}
	
	/**
	 * Indique l'orientation de la tondeuse
	 * 
	 * @return l'orientation de la tondeuse
	 */
	public eOrientation orientation()
	{
		return Orientation;
	}
	
	
	/**
	 * Effectue une rotation de 90° vers la droite
	 */
	public void rotateRight()
	{
		switch(Orientation)
		{
			case N : Orientation = eOrientation.E; break;
			case E : Orientation = eOrientation.S; break;
			case W : Orientation = eOrientation.N; break;
			case S : Orientation = eOrientation.W; break;
			default: /* Cas non atteignable*/
		}
	}
	
	
	/**
	 * Effectue une rotation de 90° vers la gauche
	 */
	public void rotateLeft()
	{
		switch(Orientation)
		{
			case N : Orientation = eOrientation.W; break;
			case E : Orientation = eOrientation.N; break;
			case W : Orientation = eOrientation.S; break;
			case S : Orientation = eOrientation.E; break;
			default: /* Cas non atteignable*/
		}
	}
	
	
	/**
	 * Incrémente les coordonnées avec les valeurs données en paramètre
	 * 
	 * @param x_increment Incrément de l'abscisse
	 * @param y_increment Incrément de l'ordonnée
	 */
	public void increment(int x_increment, int y_increment)
	{
		X +=x_increment;
		Y +=y_increment;
	}
	
	
	/**
	 * Décrémente les coordonnées avec les valeurs données en paramètre
	 * 
	 * @param x_decrement Décrément de l'abscisse
	 * @param y_decrement Décrément de l'ordonnée
	 */
	public void decrement(int x_decrement, int y_decrement)
	{
		X -=x_decrement;
		Y -=y_decrement;
	}
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PROTECTED METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	
	
	protected void setOrientation(String parameter) throws MowerException
	{
		try
		{
			Orientation = eOrientation.valueOf(parameter);		
		}
		
		catch(IllegalArgumentException exception)
		{
			MowerException.throwInvalidParameterValue("Orientation", "{N,E,W,S}", parameter);
		}
	}
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PRIVATE METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
