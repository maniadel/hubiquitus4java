/*
 * Copyright (c) Novedia Group 2012.
 *
 *     This file is part of Hubiquitus.
 *
 *     Hubiquitus is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Hubiquitus is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Hubiquitus.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.hubiquitus.hapi.utils.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class FileUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
    public static void ecrire(String repFic, String nomFic, String texte) {
        //on met try si jamais il y a une exception
        try {
            /**
             * BufferedWriter a besoin d un FileWriter,
             * les 2 vont ensemble, on donne comme argument le nom du fichier
             * true signifie qu on ajoute dans le fichier (append), on ne marque pas par dessus

             */
            FileWriter fw = new FileWriter(repFic + nomFic, true);

            // le BufferedWriter output auquel on donne comme argument le FileWriter fw cree juste au dessus
            BufferedWriter output = new BufferedWriter(fw);

            //on marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)
            output.write(texte);
            //on peut utiliser plusieurs fois methode write

            output.flush();
            //ensuite flush envoie dans le fichier, ne pas oublier cette methode pour le BufferedWriter

            output.close();
            //et on le ferme
            logger.info("fichier cree");
        } catch (IOException ioe) {
            System.out.print("Erreur : ");
            logger.error(ioe.getMessage());
        }

    }
    
    public static String modifier(List<JSONObject> config, String path){    	
    	String answer = null;
    	
		Document document = null;
		Element racine = null;
		boolean found=true;
		
		SAXBuilder sxb = new SAXBuilder();
	    try
	    {
	       //Le parsing est terminé ;)
	    	document = sxb.build(new File(path));
	    }
	    catch(Exception e){logger.error(e.getMessage());}
	    
	    
	  //On initialise un nouvel élément racine avec l'élément racine du document.
	    racine = document.getRootElement();
	    List children = racine.getChildren();
		Iterator i = children.iterator();
		XMLOutputter output = new XMLOutputter();
		Namespace ns1 = Namespace.getNamespace("http://www.springframework.org/schema/beans");
	    OutputStream out = null;
		try {
			out = new FileOutputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	    
		while(!found || i.hasNext()){
	    	Element util = (Element)i.next();
	    	if(util.getName().equalsIgnoreCase("list") && util.getAttributeValue("id").equalsIgnoreCase("keywordsList")){
	    		found  = true;
	    		for(int cpt=0; cpt<config.size(); cpt++){
					Element value = new Element("value");
					value.setText(config.get(cpt).get("ch")+"#"+config.get(cpt).get("pgm"));
					value.setNamespace(ns1);
					util.addContent(value);
    			}
	    		try {
    				output.output(document, out);
    				answer = "File modified";
				} catch (IOException e) {
					logger.error(e.getMessage());	
				}
	    	}
	    }
	    return answer;
    }
}
