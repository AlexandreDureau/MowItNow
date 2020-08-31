package mower;

import java.util.ArrayList;
import java.util.List;


/**
 * C'est une tondeuse
 * @author Alexandre
 *
 */
public class MowerBase implements Mower
{
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// CONSTANTS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final char KEY_ROTATE_RIGHT  = 'D';
	public static final char KEY_ROTATE_LEFT   = 'G';
	public static final char KEY_MOVE_FORWARD  = 'A';
	
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
	protected MowerListener Listener;
	protected MowerPosition Position;
	protected List<eAction> ActionsList;
	protected int GroundWidth;
	protected int GroundHeight;
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// CONSTRUCTORS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MowerBase(MowerListener listener, int groundWidth, int groundHeight, MowerPosition Position) throws MowerException
	{
		this.init(listener, groundWidth, groundHeight, Position);		
	}
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// INTERFACE 
	/// Mower
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public MowerPosition position()
	{
		return Position;
	}

	@Override
	public int[] groundSize()
	{
		return new int[] {GroundWidth, GroundHeight};
	}

	@Override
	public List<eAction> actionsList()
	{
		List<eAction> actionsList = new ArrayList<eAction>();
		actionsList.addAll(ActionsList);

		return actionsList;
	}
	
	
	@Override
	public void execute()
	{
		// Exécuter les actions les unes à la suite des autres.
		for(int i=0; i< ActionsList.size(); i++)
		{
			this.executeAction(ActionsList.get(i));
		}

		// A la fin de l'exécution, si un listener a bien été renseigné, le notifier 
		if(null != Listener)
		{
			Listener.onActionsExecuted(this);
		}
	}
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PUBLIC METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Programme les actions à effectuer par la tondeuse, à partir d'un texte
	 * 
	 * @param text Chaine de caractères représentant la liste des actions
	 * @throws MowerException si la chaine de caractères données ne correspond pas au format attendu
	 */
	public void setActionsList(String text) throws MowerException
	{
		ActionsList.clear();
		
		// Rationaliser le texte : On supprime les caractères non significatifs en tête et queue de texte
		text = text.trim();
		
		// Chaque caractère correspond à une action : 
		for(int i=0; i<text.length(); i++)
		{
			this.addAction(text.charAt(i));
		}
	}


	public void setGroundSize(int width, int height) throws MowerException
	{
		if(width > 0)
		{
			GroundWidth = width;
		}
		else
		{
			MowerException.throwInvalidParameterValue("Ground width", ">0", "" + width);
		}
		
		if(height > 0)
		{
			GroundHeight = height;
		}
		else
		{
			MowerException.throwInvalidParameterValue("Ground height", ">0", "" + height);
		}
	}
	
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PROTECTED METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected void addAction(char action) throws MowerException
	{
		switch(action)
		{
			case KEY_ROTATE_RIGHT : ActionsList.add(eAction.RotateRight); break;
			case KEY_ROTATE_LEFT  : ActionsList.add(eAction.RotateLeft); break;
			case KEY_MOVE_FORWARD : ActionsList.add(eAction.MoveForward); break;
			default               : MowerException.throwInvalidParameterValue("Action", "{G,D,A}", "" + action);
		}
	}
	

	protected void executeAction(eAction action)
	{
		switch(action)
		{
			case RotateRight: Position.rotateRight(); break;
			case RotateLeft : Position.rotateLeft();  break;
			case MoveForward: 
			{
				switch(Position.orientation())
				{
					case N : this.moveNorth(); break;
					case E : this.moveEast();  break;
					case W : this.moveWest();  break;
					case S : this.moveSouth(); break;
					default: // Cas non atteignable
				}
				
				break;
			}
			
			default:
			{
				// Cas non atteignable
			}
		}
	}

	
	protected void moveNorth()
	{
		// Si on a de la marge vers le nord, on incrémente l'ordonnée de la tondeuse de 1
		if((GroundHeight - Position.y())>0)
		{
			Position.increment(0,1);
		}
	}
	
	protected void moveEast()
	{
		// Si on a de la marge vers l'est, on incrémente l'abscisse de la tondeuse de 1 
		if((GroundWidth - Position.x())>0)
		{
			Position.increment(1,0);
		}
	}
	
	protected void moveWest()
	{
		// Si on a de la marge vers l'ouest, on décrémente l'abscisse de la tondeuse de 1 
		if((Position.x())>0)
		{
			Position.decrement(1,0);
		}	
	}
	
	protected void moveSouth()
	{
		// Si on a de la marge vers le sud, on décrémente l'ordonnée de la tondeuse de 1
		if((Position.y())>0)
		{
			Position.decrement(0,1);
		}
	}
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PRIVATE METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	private void init(MowerListener listener, int groundWidth, int groundHeight, MowerPosition position) throws MowerException
	{
		ActionsList = new ArrayList<eAction>();
		Listener = listener;
		this.setGroundSize(groundWidth, groundHeight);
		Position = position;
	}


}
