package problemsolver.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import problemsolver.donnees.Graphe_Complet;
import problemsolver.exceptions.ErreurDonneesException;

/**
 * 
 * @author florian
 *
 */
public class Parse_Graphe_SAX extends Parser<Graphe_Complet>{

	@Override
	public Graphe_Complet Parse(File f) throws ErreurDonneesException, FileNotFoundException, IOException {
    	
    	try {
			SAXParserFactory factory = SAXParserFactory.newInstance();

			SAXParser saxParser = factory.newSAXParser();
			MyXMLHandler handler = new MyXMLHandler();
			
			saxParser.parse(f, handler);
			
			return handler.getGraphComplet();
			
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    	}
    	
		return null;
    	
	}

	
	
}
