package mower;

import java.util.List;

/**
 * Interface regroupant les méthodes que doivent implémenter une tondeuse
 * @author Alexandre
 *
 */
public interface Mower
{
	public enum eAction
	{
		RotateRight, RotateLeft, MoveForward
	}
	
	/**
	 * Indique la position de la tondeuse
	 * @return la position de la tondeuse
	 */
	public MowerPosition position();
	
	
	/**
	 * Indique les dimensions de la pelouse
	 * @return les dimensions de la pelouse à tondre sous la forme [width,height]
	 */
	public int[] groundSize();
	
	
	/**
	 * Retourne la liste des actions programmées pour la tondeuse
	 * @return la liste des actions programmées pour la tondeuse
	 */
	public List<eAction> actionsList();
	
	
	/**
	 * execute les actions de la tondeuse
	 */
	public void execute();
}
