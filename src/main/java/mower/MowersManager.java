package mower;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire de tondeuses
 * 
 * @author Alexandre
 *
 */
public class MowersManager implements MowerListener
{
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// CONSTANTS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String PARAM_SEPARATOR = "\\s+"; // "Separateur de parametres dans les fichiers : au moins un espace"
	
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
	private static MowersManager Instance = null;
	private List<Mower> MowersList;
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// CONSTRUCTORS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected MowersManager()
	{	
		this.init();
	}
	
	public static MowersManager getInstance()
	{
		if(null == Instance)
		{
			Instance = new MowersManager();
		}
		
		return Instance;
	}
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// INTERFACE 
	/// MowerListener
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onNewPosition(Mower mower)
	{
		// Obtenir l'index de la tondeuse dans la liste.
		// Si la tondeuse a été trouvée (cas nominal), et s'il ne s'agit pas de la dernière tondeuse, 
		// exécuter les actions sur la tondeuse suivante.
		
		int mowerIndex = MowersList.indexOf(mower);
		
		if(    (mowerIndex >= 0)
			&& (mowerIndex < (MowersList.size()-1))
		  )
		{
			MowersList.get(mowerIndex+1).execute();
		}
	}
	
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	/// PUBLIC METHODS
	///
	/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Retourne la liste des tondeuses
	 * @return
	 */
	public List<Mower> getMowersList()
	{
		return MowersList;
	}
	
	
	/**
	 * Retourne une référence sur la tondeuse présente à l'index donné de la liste
	 * S'il n'y a aucune tondeuse à l'index donné, retourne 'null'
	 * 
	 * @param index
	 * @return
	 */
	public Mower getMower(int index)
	{
		Mower mower = null;
		
		if(    (index >=0)
			&& (index < MowersList.size()))
		{
			mower = MowersList.get(index);
		}
		
		return mower;
	}
	
	
	/**
	 * Renseigne le Gestionnaire de tondeuses à partir du chemin d'un fichier
	 * 
	 * @param filePath
	 * @throws MowerException
	 * @throws IOException
	 */
	public void setup(String filePath) throws MowerException, IOException
	{
		this.setup(new File(filePath));
	}
	
	/**
	 * Renseigne le Gestionnaire de tondeuses à partir un fichier
	 * 
	 * @param file
	 * @throws MowerException
	 * @throws IOException
	 */
	public void setup(File file) throws MowerException, IOException
	{
		if(    (null != file)
			&& (true == file.exists())
			&& (true == file.isFile())
		  )
		{
			// Initialiser le Gestionnaire
			this.init();
			
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			
			String line;
			int lineIndex = 1;
			MowerBase mower = null;
			int groundWidth = 0;
			int groundHeight = 0;
			
			// Parcourir le fichier ligne à ligne et le traiter...
			while(true)
			{
				line = fileReader.readLine();
				
				// Si nous ne sommes pas arrivés à la fin du fichier,
				if(null != line)
				{
					// Traitement des lignes autres que la première :
					if(lineIndex > 1)
					{ 
						// Ligne paire :
						// On récupère les coordonnées initiales de la tondeuse, on l'instancie et on l'ajoute à la liste
						if(0 == (lineIndex %2))
						{
							MowerPosition mowerPosition = MowerPosition.getInstance(line);
							mower = new MowerBase(this, groundWidth, groundHeight, mowerPosition);
							MowersList.add(mower);
						}

						// Ligne impaire :
						// On récupère la liste des actions à effectuer sur la tondeuse et on renseigne la dernière instance 
						else
						{
							mower.setActionsList(line);
						}
					}
					
					// Traiter la première ligne : extraire les dimensions de la pelouse. 
					else
					{
						// 1- On épure la ligne : on supprime les caractères espaces, tabulations, Saut de ligne en début/fin de ligne
						// 2- On la découpe pour extraire les paramètres 'largeur' et 'hauteur'
						//  => On jette des exceptions si :
						//        - Il n'y a pas ces 2 paramètres
						//        - Ce ne sont pas des nombres entiers strictement positifs
						String[] parameters = line.trim().split(PARAM_SEPARATOR);
						
						if(2 == parameters.length)
						{
							// Extraire la largeur
							try
							{
								groundWidth  = new Integer(parameters[0]).intValue();
							}
							catch(NumberFormatException exception)
							{
								MowerException.throwInvalidParameterValue("width", "{1, +inf}", parameters[0]);
							}
							
							// Extraire la hauteur
							try
							{
								groundHeight  = new Integer(parameters[1]).intValue();
							}
							catch(NumberFormatException exception)
							{
								MowerException.throwInvalidParameterValue("height", "{1, +inf}", parameters[1]);
							}
						}
						
						else
						{
							MowerException.throwInvalidNumberOfParameters(2, parameters.length, line);
						}
					}
					
					lineIndex++;
				}
				
				// Sinon, si nous avons atteint la fin du fichier
				else
				{
					// Interrompre le parcours
					break;
				}
			}
		}
	}
	
	/**
	 * Execute les actions sur les tondeuses 
	 */
	public void execute()
	{
		// S'il y a au moins une tondeuse,
		if(false == MowersList.isEmpty())
		{
			// Faire exécuter à la première tondeuse ses actions.
			// La fin des actions de la tondeuse déclenchera les actions de la suivante et ainsi de suite 
			MowersList.get(0).execute();
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
	
	/**
	 * Initialise l'instance
	 */
	private void init()
	{
		MowersList = new ArrayList<Mower>();
	}
}
