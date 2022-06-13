package net.codejava;

import java.lang.*;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import java.util.Scanner;
import java.util.List;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


import org.apache.jena.ontology.*;
//import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ext.com.google.common.collect.ArrayListMultimap;
import org.apache.jena.ext.com.google.common.collect.Multimap;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFWriter;




public class Semantic_check {
	
	
	public static String fname1="C:\\Users\\Rudrani\\Desktop\\MSc books\\Sem3 books and materials\\cloud security papers\\ontology_structure\\Ontology_priti_given_11th.owl";  //Ontology-Model file
	
	
	public static String uri="http://www.semanticweb.org/rudrani/ontologies/2022/3/ontology_revised"; 

	public static String NS=uri+"#";
	
	public static String input="C:\\Users\\Rudrani\\Desktop\\MSc books\\Sem3 books and materials\\cloud security papers\\ontology_structure\\Rule_Set_final2.txt"; //Rule file

	//public static String input0="D:\\workspace-tc\\Testcaseg\\src\\test\\rule-set8.txt"; //Rule file
	
	public void semantic_checking()
	{
		/*
		 //1st way to do connection
		Model m=ModelFactory.createDefaultModel();
		File f = new File(fname1);
		if (f.exists() && f.isFile()){ 
			m.read("file:" + fname1);
		
		    m.write(System.out);
		}
	   else
		  System.out.println("Bad file location");
		
		  
		  
		//2nd way of connection
		Model m=ModelFactory.createDefaultModel();
		File f = new File(fname1);
		if (f.exists() && f.isFile())
		{ 
			m.read("file:" + fname1);
		m.write(System.out);
		}
		*/
		
		
		OntDocumentManager mgr=new OntDocumentManager();
		 OntModelSpec s=new OntModelSpec(OntModelSpec.OWL_MEM);
		s.setDocumentManager(mgr);
		
	 OntModel m1=ModelFactory.createOntologyModel(s,null);
		 
		 Model data=RDFDataMgr.loadModel(fname1);
		// FileManager.get().getLocationMapper().addAltEntry(uri, fname1);
			
		//	Model data = RDFDataMgr.loadModel(uri);
			
			m1.addSubModel(data);
			
			 InputStream in1=RDFDataMgr.open(fname1);
			 m1.read(in1,null);
			 
			 
			 try {
				in1.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			 
		
		 
		//OntClass c=m1.getOntClass(NS+"Health_assistant");
		
		//System.out.println(c.toString());
		
		//c.createIndividual();
		 
		 OntClass c = m1.getOntClass( NS + "Doctor" );
		
		 Individual p1 = m1.createIndividual( NS + "Doctor6",c);
		 
		
		
		OntProperty p = m1.getDatatypeProperty( NS + "has_Doctor_Name_Value");
		
		
//		 OntModel m2=ModelFactory.createOntologyModel(OWL_MEM_,m1);
		 String name="Priti";
		 
		 Statement st=m1.createLiteralStatement(p1, p, ResourceFactory.createTypedLiteral(name,XSDDatatype.XSDstring));
		 m1.add(st);
		 
		 m1.write(System.out);
		 
		 
		 try {
				m1.write(new FileWriter(fname1));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 
	//	 m1.createLiteral(NS);
		 
		 /*
		 
		 FileOutputStream f = null;
		 try {
		 	f = new FileOutputStream(fname1);
		 	} 
		 catch (FileNotFoundException e) {
		 	// TODO Auto-generated catch block
		 	e.printStackTrace();
		 }
			
		 
		    RDFWriter d=(RDFWriter) m1.getWriter("RDF/XML");
		    d.write(m1,f,null);
		*/ 
		   
		 try {
			reasoner(m1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private static void reasoner(OntModel m1) throws IOException
	{
		File f1 = new File(input);
		if (f1.exists()) {
			List<Rule> rules = Rule.rulesFromURL("file:" + input);
			GenericRuleReasoner r = new GenericRuleReasoner(rules);
			
			r.setOWLTranslation(true);           
			r.setTransitiveClosureCaching(true);
			
			InfModel infmodel = ModelFactory.createInfModel(r, m1);
			
		    m1.add(infmodel.getDeductionsModel());
		    System.out.println(".../Jena Done/....");
		   
		}
		else
			System.out.println("That rules file does not exist.");
	}
	
	
}
