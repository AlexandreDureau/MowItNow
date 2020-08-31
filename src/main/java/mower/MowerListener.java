package mower;

public interface MowerListener
{
	/**
	 * Callback appelée lorsqu'une tondeuse a effectué ses actions
	 * @param mower Référence de la tondeuse ayant effectué ses actions
	 */
	public void onActionsExecuted(Mower mower);
}
