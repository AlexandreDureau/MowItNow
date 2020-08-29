package mower;

import java.util.List;

public interface Mower
{
	public enum eAction
	{
		RotateRight, RotateLeft, MoveForward
	}
	
	/**
	 * 
	 * @return la position de la tondeuse
	 */
	public MowerPosition position();
	
	
	/**
	 * @return les dimensions de la pelouse à tondre sous la forme [width,height]
	 */
	public int[] groundSize();
	
	
	/**
	 * @return la liste des actions qu'effectuera la tondeuse
	 */
	public List<eAction> actionsList();
	
	
	/**
	 * execute les actions de la tondeuse
	 */
	public void execute();
}
