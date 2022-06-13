package net.codejava;

import java.lang.*;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
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
import org.apache.jena.ext.com.google.common.collect.ArrayListMultimap;
import org.apache.jena.ext.com.google.common.collect.Multimap;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFWriter;

public class MongoDBApp 
{

	public static void main(String[] args) throws IOException 
	{
		int flag=0;
		long r=0, pid=0, pa_id1=0, ha_id=0, pa_id=0;
		boolean stethovalue=false,ecgvalue=false,alcovalue=false,smovalue=false;
		String othhab="",dietadv="",exmobsr="",testreview="",testriskt="",lifestyle="",referral="",recommendtest="",presmedi="",provdiagnos="",testname="",testres="",testrev="",testrisk="",disinfo="",prsntill="",surarea="",surrem="",remarkdata="",famlyhistry="",symp_area="",symp_dur="",symp_type="",symp_charac="";
		Scanner in = new Scanner(System.in);
		// TODO Auto-generated method stub
		String uri = "mongodb://localhost:27017";
		MongoClient mongoclient=MongoClients.create(uri);
		MongoIterable<String> dbNames=mongoclient.listDatabaseNames();
		for (String dbName : dbNames)
		{
			System.out.println(dbName);
		}
		MongoDatabase database = mongoclient.getDatabase("HL7_SCHEMA");
		
		MongoCollection<Document> PATIENT = database.getCollection("PATIENT");
		MongoCollection<Document> PRESCRIPTION = database.getCollection("PRESCRIPTION");
		MongoCollection<Document> PERSONAL_HABITS = database.getCollection("PERSONAL_HABITS");
		MongoCollection<Document> SURGICAL_HISTORY = database.getCollection("SURGICAL_HISTORY");
		MongoCollection<Document> GENERAL_SURVEY = database.getCollection("GENERAL SURVEY");
		MongoCollection<Document> REMARKS = database.getCollection("REMARKS");
		MongoCollection<Document> SYMPTOM = database.getCollection("SYMPTOM");
		MongoCollection<Document> DIAGNOSTIC_REPORT = database.getCollection("DIAGNOSTIC_REPORT");
		MongoCollection<Document> HOSPITAL = database.getCollection("HOSPITAL");
		MongoCollection<Document> MEDICINE = database.getCollection("MEDICINE");
		MongoCollection<Document> DISEASE = database.getCollection("DISEASE");
		MongoCollection<Document> DOCTOR = database.getCollection("DOCTOR");
		MongoCollection<Document> COMMUNICATION_MEDIUM= database.getCollection("COMMUNICATION_MEDIUM");
		MongoCollection<Document> HEALTH_ASSISTANT= database.getCollection("HEALTH_ASSISTANT");
		  
		
		System.out.println("Enter Hospital id:");
		int hid = in.nextInt();
		in.nextLine();
		System.out.println("Enter Hospital name:");
		String hname = in.nextLine();
		System.out.println("Enter Hospital address:");
		String hadd = in.nextLine();
		System.out.println("Enter Hospital contact info:");
		long hcon = in.nextLong();
		
		Document hos1=Document.parse("{H_id:"+hid+",Hname:'"+hname+"',"
			 	+"H_address:'"+hadd+"',"
			    +"H_contactInfo:'"+hcon+"'}");
		HOSPITAL.insertOne(hos1);
		
		System.out.println("Enter communication medium id:");
		int cid = in.nextInt();
		in.nextLine();
		System.out.println("Enter communication medium name:");
		String cname = in.nextLine();
		
	
	    Document com1=Document.parse("{C_id:"+cid+",C_Name:'"+cname+"'}");
	    COMMUNICATION_MEDIUM.insertOne(com1);
		
		
		
		System.out.println("Enter Registration number for Doctor:");
		String rno = in.nextLine();
		 try
		    {
		        r=Long.parseLong(rno);
		    }
		    catch(NumberFormatException e)
		    {
		        flag=1;
		    }
		    if(flag==1)
		    {
		        System.out.println("Registration number must be numeric. Reenter the value."); 
		        System.exit(1);
		    }
		    
		System.out.println("Enter Name for Doctor:");
		String dname = in.nextLine();
		System.out.println("Enter Qualification for Doctor:");
		String dqual = in.nextLine();
		System.out.println("Enter Designation for Doctor:");
		String ddes = in.nextLine();
		System.out.println("Enter Gender for Doctor:");
		String dgen = in.nextLine();
		System.out.println("Enter Hospital name for Doctor:");
		String hos = in.nextLine();
		

		Document Doctor = Document.parse("{Regn_no: "+r+", D_name: '"+dname+"',"
					+"D_qualification: '"+dqual+"',"
							+"D_designation: '"+ddes+"',"
					+"D_gender: '"+dgen+"',"
							+"hospital_name: '"+hos+"'}");
		DOCTOR.insertOne(Doctor);
		
		
		System.out.println("Enter id number for Health assistant:");
		String hno = in.nextLine();
		 try
		    {
			 ha_id=Long.parseLong(hno);
		    }
		    catch(NumberFormatException e)
		    {
		        flag=1;
		    }
		    if(flag==1)
		    {
		        System.out.println("Id number must be numeric. Reenter the value."); 
		        System.exit(1);
		    }
		    
		System.out.println("Enter Name for Health Assistant:");
		String ha_name = in.nextLine();
		System.out.println("Enter Qualification for Health Assistant:");
		String ha_qual = in.nextLine();
		System.out.println("Enter Designation for Health Assistant:");
		String ha_des = in.nextLine();
		
		
		Document health_assistant1=Document.parse("{HA_id:"+ha_id+",HA_name:'"+ha_name+"',"
		    +"HA_qualification:'"+ha_qual+"',"
		    +"HA_designation:'"+ha_des+"',"
		    +"Com_med_id:"+cid+"}");
		HEALTH_ASSISTANT.insertOne(health_assistant1);
		
		
		System.out.println("Enter disease id:");
		int did = in.nextInt();
		in.nextLine();
		System.out.println("Enter 'Y' if exists Patient's disease information:");
		String dis = in.nextLine();
		if(dis.equals("Y"))
		{
			System.out.println("Enter Patient's disease name:");
			disinfo = in.nextLine();
		}
		
		
		Document disease1=Document.parse("{D_id:"+did+",DISEASE_name:'"+disinfo+"'}");
	    DISEASE.insertOne(disease1);
	    
		
		System.out.println("Enter medicine id:");
		int meid = in.nextInt();
		in.nextLine();
		System.out.println("Enter medicine name:");
		String mname = in.nextLine();
		System.out.println("Enter medicine company name:");
		String m_compa = in.nextLine();
		System.out.println("Enter medicine composition:");
		String m_compo = in.nextLine();
		System.out.println("Enter medicine price:");
		int m_price = in.nextInt();
		
		Document medicine1=Document.parse("{M_id:"+meid+",M_name:'"+mname+"',"
			    +"M_company:'"+m_compa+"',"
			    +"M_composition:'"+m_compo+"',"
			    +"M_price:"+m_price+"}");
			    MEDICINE.insertOne(medicine1);
		
				    
		System.out.println("Hello. If you are a Doctor, then Press 1 or else press 0 for being Health assistant.");
		int user = in.nextInt();		    
		if(user==0)
		{
			//health assistant 
			System.out.println("View: Press 1\nPatient DataStore: Press 2");
            int ch=in.nextInt();
            switch(ch)
            {
                case 1: 
                		System.out.println("Enter valid patient id: ");
                        pa_id=in.nextLong();
                        BasicDBObject whereQuery = new BasicDBObject();
                        whereQuery.put("P_ID",pa_id);
                        FindIterable<Document> cursor=PRESCRIPTION.find(whereQuery);
                        for(Document d:cursor)
                        {
                        	System.out.println(d);
                        }
                       
                        
                        break;
                case 2: //patient personal details
                        System.out.println("Enter Patient id:");
                        String pno = in.nextLine();
                        try
                        {
                           pid=Long.parseLong(pno);
                        }
                        catch(NumberFormatException e)
                        {
                           flag=1;
                        }
                        if(flag==1)
                        {
                           System.out.println("Id value must be numeric.Reenter values."); 
                           System.exit(1);
                        }
                        System.out.println("Enter Patient name:");
                        String pname = in.nextLine();
                        if(pname.indexOf(' ')==-1)
                        { 
                           System.out.println("name should be comprised of first name and last name separated by space.."); 
                           System.exit(1);  
                        }
                        System.out.println("Enter Patient location id:");
                        long lid = in.nextLong();
                        in.nextLine();
                        System.out.println("Enter Patient occupation:");
                        String occu = in.nextLine();
                        System.out.println("Enter Patient gender:");
                        String pgen = in.nextLine();
                        if(!(pgen.equalsIgnoreCase("Male")||pgen.equalsIgnoreCase("Female")||pgen.equalsIgnoreCase("Transgender")))
                        {
                           System.out.println("Gender should be any of male, female or trans.Reeenter values."); 
                           System.exit(1);
                        }
                        System.out.println("Enter Patient age:");
                        int page = in.nextInt();
                        if(page>99)
                        {
                           System.out.println("age should be below 100.."); 
                           System.exit(1);
                        }
                        in.nextLine();
                        System.out.println("Enter Patient religion:");
                        String prel = in.nextLine();
                        System.out.println("Enter Patient status:");
                        String sta = in.nextLine();
                        System.out.println("Enter Patient visiting date(yyyy-mm-dd):"); 
                        String date = in.nextLine();
                        System.out.println("Enter Patient visiting time(HH:MM:SS):");
                        String time = in.nextLine();
                        String datetime= new String(date+'T'+time);
                        System.out.println("Enter Patient address:");
                        String padd = in.nextLine();
                        System.out.println("If Patient has stetho report, press 'Y'else press 'N':");
                        String stetho = in.nextLine();
                        if(stetho.equals('Y'))
                        {
                           stethovalue=true;
                        }
                        else
                        {
                           stethovalue=false;
                        }
                        System.out.println("If Patient has ECG report, press 'Y'else press 'N':");
                        String ecg = in.nextLine();
                        if(ecg.equals('Y'))
                        {
                          ecgvalue=true;
                        }
                        else
                        {
                          ecgvalue=false;
                        }
                        System.out.println("Enter 'Y' if exists Patient's family history:");
		                String famhis = in.nextLine();
		                if(famhis.equals("Y"))
		                {
			               System.out.println("Enter Patient's family history:");
			               famlyhistry = in.nextLine();
		                }
		                System.out.println("Enter Patient's chief complaints:");
		                String compl = in.nextLine();
                        System.out.println("Enter id number for Health assistant:");
		                ha_id = in.nextLong();
                        System.out.println("Enter Registration number for Doctor visited:");
		                long d_id = in.nextLong();
                        //symptom details
                        System.out.println("Enter symptom id:");
		                int sid = in.nextInt();
		                in.nextLine();
		                System.out.println("Enter 'Y' if exists Patient's symptom area information:");
		                String symp_areat = in.nextLine();
		                if(symp_areat.equals("Y"))
		                {
			              System.out.println("Enter Patient's symptom area:");
			              symp_area = in.nextLine();
		                }
		                System.out.println("Enter 'Y' if exists Patient's symptom duration information:");
		                String symp_durt = in.nextLine();
		                if(symp_durt.equals("Y"))
		                {
			              System.out.println("Enter Patient's symptom duration:");
			              symp_dur = in.nextLine();
		                }
		                System.out.println("Enter 'Y' if exists Patient's symptom type:");
		                String symp_typet = in.nextLine();
		                if(symp_typet.equals("Y"))
		                {
			               System.out.println("Enter Patient's symptom type:");
			               symp_type = in.nextLine();
		                }
		                System.out.println("Enter 'Y' if exists Patient's symptom characteristics information:");
		                String symp_char = in.nextLine();
		                if(symp_char.equals("Y"))
		                {
			                System.out.println("Enter Patient's symptom characteristics:");
			                symp_charac = in.nextLine();
		                }
                  //      MongoCollection<Document> SYMPTOM= database.getCollection("SYMPTOM");
	                    Document symptom1=Document.parse("{Sid:"+sid+",PA_id:"+pid+","
						+"SYMPTOM_AREA:'"+symp_area+"',"
	                    +"SYMPTOM_DURATION:'"+symp_dur+"',"
	                    +"SYMPTOM_TYPE:'"+symp_type+"',"
	                    +"SYMPTOM_CHARACTERISTICS:'"+symp_charac+"'}");
	                    SYMPTOM.insertOne(symptom1);

              //          MongoCollection<Document> PATIENT = database.getCollection("PATIENT");
                        Document patient1=Document.parse("{P_id: "+pid+", P_name: '"+pname+"',"
                        +"P_locationId: "+lid+","
                        +"P_occupation: '"+occu+"',"
                        +"P_gender: '"+pgen+"',"
                        +"P_age: "+page+","
                        +"P_religion: '"+prel+"',"
                        +"P_status: '"+sta+"',"
                        +"P_VisitingDtTime: '"+datetime+"',"
                        +"P_address: '"+padd+"',"
                        +"P_hasStetho: "+stethovalue+","
                        +"P_hasECG: "+ecgvalue+","
                        +"Family_History: '"+famlyhistry+"',"
                        +"Chief_Complaints: '"+compl+"',"
                        +"HA_ID:"+ha_id+","
                        +"D_ID:"+d_id+"}");
                        PATIENT.insertOne(patient1);

                        //general survey details
                        System.out.println("Enter Patient BMI rate:");
		                String bmi = in.nextLine();
		                System.out.println("Enter Patient Bowel value:");
		                String bow = in.nextLine();
		                System.out.println("Enter Patient BP(systolic/diastolic):");
		                String bp = in.nextLine();
		                System.out.println("Enter Patient food habit:");
		                String food = in.nextLine();
		                System.out.println("Enter Patient gait data:");
		                String gait = in.nextLine();
		                System.out.println("Enter Patient general condition:");
		                String gencon = in.nextLine();
		                System.out.println("Enter Patient height(in cms):");
		                String hei = in.nextLine();
		                System.out.println("Enter Patient nutrition:");
		                String nutri = in.nextLine();
		                System.out.println("Enter Patient posture:");
		                String post = in.nextLine();
		                System.out.println("Enter Patient pulse rate:");
		                String pulse = in.nextLine();
		                System.out.println("Enter Patient sleep:");
		                String sleep = in.nextLine();
		                System.out.println("Enter Patient SPO2:");
		                String spo2 = in.nextLine();
		                System.out.println("Enter Patient temperature(degree F):");
		                String temp = in.nextLine();
		                System.out.println("Enter Patient weight(in kgs):");
		                String weig = in.nextLine();
                        //personal habits details
		                System.out.println("If Patient is alcoholic, press 'Y' else press 'N':");
		                String alco = in.nextLine();
		                if(alco.equals('Y'))
		                {
			               alcovalue=true;
		                }
		                else
		                {
			               alcovalue=false;
		                }
		                System.out.println("If Patient is a smoker, press 'Y' else press 'N':");
		                String smo = in.nextLine();
		                if(smo.equals('Y'))
		                {
			              smovalue=true;
		                }
		                else
		                {
			              smovalue=false;
		                }
		
		                System.out.println("Enter 'Y' if Patient's other habit exists:");
		                String hab = in.nextLine();
		                if(hab.equals("Y"))
		                {
			               System.out.println("Enter Patient's other habit:");
			               othhab = in.nextLine();
		                }
                        //surgical history details
                        System.out.println("Enter 'Y' if any surgical history exists:");
		                String surhis = in.nextLine();
		                if(surhis.equals("Y"))
		                {
			               System.out.println("Enter Patient's surgical area:");
			               surarea = in.nextLine();
			               System.out.println("Enter Patient's surgical remark:");
			               surrem = in.nextLine();
		                }
                        //remark details
		                System.out.println("Enter 'Y' if exists Patient's remark:");
		                String remark = in.nextLine();
		                if(remark.equals("Y"))
		                {
			               System.out.println("Enter Patient's remark:");
			               remarkdata = in.nextLine();
		                }
                        
                        //diagnosis report details
                        System.out.println("Enter diagnostic report id:");
		                int diagid = in.nextInt();
		                in.nextLine();
		                System.out.println("Enter 'Y' if diagnosis report exists and enter 'N' if no:");
		                String finaldiag = in.nextLine();
		                if(finaldiag.equals("Y"))
		                {
			                System.out.println("Enter Patient's pathology test name:");
			                testname = in.nextLine();
			                System.out.println("Enter Patient's pathology test result:");
			                testres = in.nextLine();
			                System.out.println("Enter 'Y' if exists Patient's pathology test review:");
			                testreview = in.nextLine();
			                if(testreview.equals("Y"))
			                {
				              System.out.println("Enter Patient's pathology test review:");
				              testrev = in.nextLine();
			                }
			                System.out.println("Enter 'Y' if exists Patient's pathology test risk status:");
			                testriskt = in.nextLine();
			                if(testriskt.equals("Y"))
			                {
				               System.out.println("Enter Patient's pathology test risk:");
				               testrisk = in.nextLine();
			                }
		                }

               //         MongoCollection<Document> GENERAL_SURVEY = database.getCollection("GENERAL_SURVEY");
	                    Document general_survey1=Document.parse("{p_id:"+pid+",BMI_rate:'"+bmi+"',"
	                    +"Bowel:'"+bow+"',"
	                    +"BP:'"+bp+"',"
	                    +"Food_Habit:'"+food+"',"
	                    +"Gait:'"+gait+"',"
	                    +"Genaral_Condition:'"+gencon+"',"
	                    +"Height:'"+hei+"',"
	                    +"Nutrition:'"+nutri+"',"
	                    +"Posture:'"+post+"',"
	                    +"Pulse_Rate:'"+pulse+"',"
	                    +"Sleep:'"+sleep+"',"
	                    +"SPO2:'"+spo2+"',"
	                    +"Temperature:'"+temp+"',"
	                    +"Weight:'"+weig+"'}");
	                    GENERAL_SURVEY.insertOne(general_survey1);

            //            MongoCollection<Document> PERSONAL_HABITS= database.getCollection("PERSONAL_HABITS");
	                    Document personal_habits1=Document.parse("{p_id:"+pid+",Is_Alcoholic:"+alcovalue+","
	                    +"Is_Smoker:"+smovalue+","
	                    +"Other_Habit:'"+othhab+"'}");
	                    PERSONAL_HABITS.insertOne(personal_habits1);
	    
	              //      MongoCollection<Document> SURGICAL_HISTORY= database.getCollection("SURGICAL_HISTORY");
	                    Document surgical_history1=Document.parse("{p_id:"+pid+",Surgical_Area:'"+surarea+"',"
	                    +"Surgery_Remark:'"+surrem+"'}");
	                    SURGICAL_HISTORY.insertOne(surgical_history1);
	    
	            //        MongoCollection<Document> REMARKS= database.getCollection("REMARKS");
	                    Document remarks1=Document.parse("{p_id:"+pid+",Remarks:'"+remarkdata+"'}");
	                    REMARKS.insertOne(remarks1);

              //          MongoCollection<Document> DIAGNOSTIC_REPORT= database.getCollection("DIAGNOSTIC_REPORT");
	                    Document diagnostic_report1=Document.parse("{Diag_id:"+diagid+",P_id:"+pid+","
                        +"Pathology_Test_Name:'"+testname+"',"
	                    +"Test_Result:'"+testres+"',"
	                    +"Test_Review:'"+testrev+"',"
	                    +"Test_Risk_Status:'"+testrisk+"'}");
	                    DIAGNOSTIC_REPORT.insertOne(diagnostic_report1);
                        break;
                default: System.out.println("Invalid choice");
            }
			
		}
		else if(user==1)
		{
			//doctor
            System.out.println("View: Press 1\nPatient Prescription Store: Press 2");
            int ch1=in.nextInt();
            switch(ch1)
            {
                case 1: System.out.println("Enter valid patient id: ");
                        pa_id1=in.nextLong();
                        System.out.println("PATIENT PERSONAL DETAILS: ");
                     //   database.PATIENT.find({P_id:"+pa_id1+"});
                       
                        BasicDBObject whereQuery = new BasicDBObject();
                        whereQuery.put("P_id",pa_id1);
                        FindIterable<Document> cursor=PATIENT.find(whereQuery);
                        for(Document d:cursor)
                        {
                        	System.out.println(d);
                        }
                        
                        System.out.println("GENERAL SURVEY DETAILS: ");
                    //    database.GENERAL_SURVEY.find({p_id:"+pa_id1+"});
                        
                        BasicDBObject whereQuery1 = new BasicDBObject();
                        whereQuery1.put("P_ID",pa_id);
                        FindIterable<Document> cursor1=GENERAL_SURVEY.find(whereQuery1);
                        for(Document d:cursor1)
                        {
                        	System.out.println(d);
                        }
                        
                        System.out.println("PERSONAL HABIT DETAILS: ");
                     //   database.PERSONAL_HABITS.find({p_id:"+pa_id1+"});
                        
                        BasicDBObject whereQuery2 = new BasicDBObject();
                        whereQuery2.put("P_ID",pa_id);
                        FindIterable<Document> cursor2=PERSONAL_HABITS.find(whereQuery2);
                        for(Document d:cursor2)
                        {
                        	System.out.println(d);
                        }
                        
                        
                        System.out.println("SURGICAL HISTORY DETAILS: ");
                  //      database.SURGICAL_HISTORY.find({p_id:"+pa_id1+"});
                        
                        
                        BasicDBObject whereQuery3 = new BasicDBObject();
                        whereQuery3.put("P_ID",pa_id);
                        FindIterable<Document> cursor3=SURGICAL_HISTORY.find(whereQuery3);
                        for(Document d:cursor3)
                        {
                        	System.out.println(d);
                        }
                        
                        System.out.println("REMARK DETAILS: ");
                  //      database.REMARKS.find({p_id:"+pa_id1+"});
                        
                        BasicDBObject whereQuery4 = new BasicDBObject();
                        whereQuery4.put("P_ID",pa_id);
                        FindIterable<Document> cursor4=REMARKS.find(whereQuery4);
                        for(Document d:cursor4)
                        {
                        	System.out.println(d);
                        }
                        
                        System.out.println("SYMPTOM DETAILS: "); //from patient collection, we need to get the symptom id for a patient having pid= pa_id
                   //     database.SYMPTOM.find({PA_id:"+pa_id1+"});
                        
                        BasicDBObject whereQuery5 = new BasicDBObject();
                        whereQuery5.put("P_ID",pa_id);
                        FindIterable<Document> cursor5=SYMPTOM.find(whereQuery5);
                        for(Document d:cursor5)
                        {
                        	System.out.println(d);
                        }
                        
                        System.out.println("DIAGNOSTIC REPORT DETAILS: ");
                  //      database.DIAGNOSTIC_REPORT.find({P_id:"+pa_id1+"});
                        
                        BasicDBObject whereQuery6 = new BasicDBObject();
                        whereQuery6.put("P_ID",pa_id);
                        FindIterable<Document> cursor6=DIAGNOSTIC_REPORT.find(whereQuery6);
                        for(Document d:cursor6)
                        {
                        	System.out.println(d);
                        }	
                        
                       break;
                case 2: System.out.println("Enter valid patient id: ");
                        long pa_id2=in.nextLong();
                        System.out.println("Enter Patient name:");
		                String pname1 = in.nextLine();
                        System.out.println("Enter 'Y' if exists Patient's present illness history:");
                        String presill = in.nextLine();
                        if(presill.equals("Y"))
                        {
                          System.out.println("Enter Patient's present illness history:");
                          prsntill = in.nextLine();
                        }
                        System.out.println("Enter 'Y' if exists Patient's provisional diagnosis info:");
                        String provdiag = in.nextLine();
                        if(provdiag.equals("Y"))
                        {
                            System.out.println("Enter Patient's provisional diagnosis info:");
                            provdiagnos = in.nextLine();
                        }
                        System.out.println("Enter final diagnostic report id:");
		                int diagid1 = in.nextInt();
		                System.out.println("Enter patient's symptom id:");
		                int sid1 = in.nextInt();
                        System.out.println("Enter your Registration number:");
		                long d_id1 = in.nextLong();
                        in.nextLine();
                        System.out.println("Enter your Name:");
		                String dname1 = in.nextLine();
                        System.out.println("Enter your Hospital name:");
		                String hname1 = in.nextLine();
                        

                        System.out.println("Enter prescription id:");
		                int pr_id = in.nextInt();
		                in.nextLine();
                        System.out.println("Press 'Y' if Patient's dietary advice exists:");
		                String diet = in.nextLine();
		                if(diet.equals('Y'))
		                {
			               System.out.println("Enter Patient's dietary advice:");
			               dietadv = in.nextLine();
		                }
		                System.out.println("Enter 'Y' if exists Patient's examination observation data:");
		                String examobs = in.nextLine();
		                if(examobs.equals('Y'))
		                {
			              System.out.println("Enter examination observation data:");
			              exmobsr = in.nextLine();
		                }
                        System.out.println("Enter disease id:");
		                int dis_id = in.nextInt();
		                System.out.println("Enter 'Y' if Patient's lifestyle exists:");
		                String life = in.nextLine();
		                if(life.equals('Y'))
		                {
			              System.out.println("Enter Patient's lifestyle:");
			              lifestyle = in.nextLine();
		                }
		                System.out.println("Enter 'Y' if Patient's referral exists:");
		                String refer = in.nextLine();
		                if(refer.equals('Y'))
		                {
			              System.out.println("Enter Patient's referral:");
			              referral = in.nextLine();
		                }
		                System.out.println("Enter 'Y' if Patient's recommended test exists:");
		                String rectest = in.nextLine();
		                if(rectest.equals("Y"))
		                {
			                 System.out.println("Enter Patient's recommended tests:");
			                 recommendtest = in.nextLine();
		                }
		                System.out.println("Enter prescribed medicine's id:");
		                int mid1 = in.nextInt();
                        in.nextLine();
		                System.out.println("Enter Patient's prescribed medicine dosage:");
		                String dosage = in.nextLine();

                //        MongoCollection<Document> PRESCRIPTION= database.getCollection("PRESCRIPTION");
                        Document pres1=Document.parse("{PRES_ID:"+pr_id+",P_ID:"+pa_id2+","
                        +"P_NAME:'"+pname1+"',"
                        +"DOC_ID:"+d_id1+","
                        +"DOC_NAME:'"+dname1+"',"
                        +"HOS_NAME:'"+hname1+"',"
                        +"Present_Illness_History:'"+prsntill+"',"
                        +"Provisional_Diagnosis:'"+provdiagnos+"',"
                        +"Final_Diagnosis:"+diagid1+","
                        +"Disease_Info:"+dis_id+","
                        +"Symp_ID:"+sid1+","
                        +"Dietary_advice:'"+dietadv+"',"
                        +"Lifestyle_advice:'"+lifestyle+"',"
                        +"Examination_Observation:'"+exmobsr+"',"
                        +"Prescribed_Medications:"+mid1+","
                        +"Prescribed_Medication_Dosage:'"+dosage+"',"
                        +"Recommended_tests:'"+recommendtest+"',"
                        +"Patient_Referral:'"+referral+"'}");
                        break;
                default: System.out.println("Invalid choice");
            }

		}
		else
		{
			System.out.println("Wrong input.");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		
		MongoCollection<Document> DOCTOR = database.getCollection("DOCTOR");
		
		Document doctor1 = Document.parse("{Regn_no: 42760, D_name: 'Dr.S.B.Acharya',"
				+"D_qualification: 'MBBS',"
				+"D_designation: 'General Physician',"
				+"D_gender:'Male',"
				+"hospital_name:'Medicare Hospital'}");
		DOCTOR.insertOne(doctor1);
		*/
		/*
		System.out.println("Enter Registration number for Doctor2:");
		String rno = in.nextLine();
		 try
		    {
		        r=Long.parseLong(rno);
		    }
		    catch(NumberFormatException e)
		    {
		        flag=1;
		    }
		    if(flag==1)
		    {
		        System.out.println("Registration number must be numeric. Reenter the value."); 
		        System.exit(1);
		    }
		    
		System.out.println("Enter Name for Doctor2:");
		String dname = in.nextLine();
		System.out.println("Enter Qualification for Doctor2:");
		String dqual = in.nextLine();
		System.out.println("Enter Designation for Doctor2:");
		String ddes = in.nextLine();
		System.out.println("Enter Gender for Doctor2:");
		String dgen = in.nextLine();
		System.out.println("Enter Hospital name for Doctor2:");
		String hos = in.nextLine();
		
		
		System.out.println("Enter Hospital id:");
		int hid = in.nextInt();
		in.nextLine();
		System.out.println("Enter Hospital name:");
		String hname = in.nextLine();
		System.out.println("Enter Hospital address:");
		String hadd = in.nextLine();
		System.out.println("Enter Hospital contact info:");
		long hcon = in.nextLong();
		
		
		
		System.out.println("Enter Patient id:");
		String pno = in.nextLine();
	    try
	    {
	        pid=Long.parseLong(pno);
	    }
	    catch(NumberFormatException e)
	    {
	        flag=1;
	    }
	    if(flag==1)
	    {
	        System.out.println("Id value must be numeric.Reenter values."); 
	        System.exit(1);
	    }
		System.out.println("Enter Patient name:");
		String pname = in.nextLine();
		if(pname.indexOf(' ')==-1)
	    { 
	        System.out.println("name should be comprised of first name and last name separated by space.."); 
	        System.exit(1);  
	    }
		System.out.println("Enter Patient location id:");
		long lid = in.nextLong();
		in.nextLine();
		System.out.println("Enter Patient occupation:");
		String occu = in.nextLine();
		System.out.println("Enter Patient gender:");
		String pgen = in.nextLine();
		if(!(pgen.equalsIgnoreCase("Male")||pgen.equalsIgnoreCase("Female")||pgen.equalsIgnoreCase("Transgender")))
		{
		    System.out.println("Gender should be any of male, female or trans.Reeenter values."); 
		    System.exit(1);
		}
		System.out.println("Enter Patient age:");
		int page = in.nextInt();
		if(page>99)
	    {
	        System.out.println("age should be below 100.."); 
	        System.exit(1);
	    }
		in.nextLine();
		System.out.println("Enter Patient religion:");
		String prel = in.nextLine();
		System.out.println("Enter Patient status:");
		String sta = in.nextLine();
		System.out.println("Enter Patient visiting date(yyyy-mm-dd):"); 
		String date = in.nextLine();
		System.out.println("Enter Patient visiting time(HH:MM:SS):");
		String time = in.nextLine();
		String datetime= new String(date+'T'+time);
		System.out.println("Enter Patient address:");
		String padd = in.nextLine();
		System.out.println("If Patient has stetho report, press 'Y'else press 'N':");
		String stetho = in.nextLine();
		if(stetho.equals('Y'))
		{
			stethovalue=true;
		}
		else
		{
			stethovalue=false;
		}
		System.out.println("If Patient has ECG report, press 'Y'else press 'N':");
		String ecg = in.nextLine();
		if(ecg.equals('Y'))
		{
			ecgvalue=true;
		}
		else
		{
			ecgvalue=false;
		}
		
		System.out.println("Enter Registration number for Doctor visited:");
		long d_id = in.nextLong();
		in.nextLine();
		System.out.println("Enter Patient BMI rate:");
		String bmi = in.nextLine();
		System.out.println("Enter Patient Bowel value:");
		String bow = in.nextLine();
		System.out.println("Enter Patient BP(systolic/diastolic):");
		String bp = in.nextLine();
		System.out.println("Enter Patient food habit:");
		String food = in.nextLine();
		System.out.println("Enter Patient gait data:");
		String gait = in.nextLine();
		System.out.println("Enter Patient general condition:");
		String gencon = in.nextLine();
		System.out.println("Enter Patient height(in cms):");
		String hei = in.nextLine();
		System.out.println("Enter Patient nutrition:");
		String nutri = in.nextLine();
		System.out.println("Enter Patient posture:");
		String post = in.nextLine();
		System.out.println("Enter Patient pulse rate:");
		String pulse = in.nextLine();
		System.out.println("Enter Patient sleep:");
		String sleep = in.nextLine();
		System.out.println("Enter Patient SPO2:");
		String spo2 = in.nextLine();
		System.out.println("Enter Patient temperature(degree F):");
		String temp = in.nextLine();
		System.out.println("Enter Patient weight(in kgs):");
		String weig = in.nextLine();
		System.out.println("If Patient is alcoholic, press 'Y' else press 'N':");
		String alco = in.nextLine();
		if(alco.equals('Y'))
		{
			alcovalue=true;
		}
		else
		{
			alcovalue=false;
		}
		System.out.println("If Patient is a smoker, press 'Y' else press 'N':");
		String smo = in.nextLine();
		if(smo.equals('Y'))
		{
			smovalue=true;
		}
		else
		{
			smovalue=false;
		}
		
		System.out.println("Enter 'Y' if Patient's other habit exists:");
		String hab = in.nextLine();
		if(hab.equals("Y"))
		{
			System.out.println("Enter Patient's other habit:");
			othhab = in.nextLine();
		}
		
		System.out.println("Press 'Y' if Patient's dietary advice exists:");
		String diet = in.nextLine();
		if(diet.equals('Y'))
		{
			System.out.println("Enter Patient's dietary advice:");
			dietadv = in.nextLine();
		}
		System.out.println("Enter 'Y' if exists Patient's examination observation data:");
		String examobs = in.nextLine();
		if(examobs.equals('Y'))
		{
			System.out.println("Enter examination observation data:");
			exmobsr = in.nextLine();
		}
		System.out.println("Enter 'Y' if Patient's lifestyle exists:");
		String life = in.nextLine();
		if(life.equals('Y'))
		{
			System.out.println("Enter Patient's lifestyle:");
			lifestyle = in.nextLine();
		}
		System.out.println("Enter 'Y' if Patient's referral exists:");
		String refer = in.nextLine();
		if(refer.equals('Y'))
		{
			System.out.println("Enter Patient's referral:");
			referral = in.nextLine();
		}
		
		System.out.println("Enter prescription id:");
		int pr_id = in.nextInt();
		in.nextLine();
		
		System.out.println("Enter 'Y' if Patient's recommended test exists:");
		String rectest = in.nextLine();
		if(rectest.equals("Y"))
		{
			System.out.println("Enter Patient's recommended tests:");
			recommendtest = in.nextLine();
		}
		
		
		System.out.println("Enter prescribed medicine's id:");
		int mid = in.nextInt();
		in.nextLine();
		System.out.println("Enter Patient's prescribed medicine dosage:");
		String dosage = in.nextLine();
			
		
		
		System.out.println("Enter 'Y' if exists Patient's provisional diagnosis info:");
		String provdiag = in.nextLine();
		if(provdiag.equals("Y"))
		{
			System.out.println("Enter Patient's provisional diagnosis info:");
			provdiagnos = in.nextLine();
		}
		
		System.out.println("Enter communication medium id:");
		int cid = in.nextInt();
		in.nextLine();
		System.out.println("Enter communication medium name:");
		String cname = in.nextLine();
		System.out.println("Enter diagnostic report id:");
		int diagid = in.nextInt();
		in.nextLine();
		System.out.println("Enter 'Y' if final diagnosis report exists and enter 'N' if no:");
		String finaldiag = in.nextLine();
		if(finaldiag.equals("Y"))
		{
			System.out.println("Enter Patient's pathology test name:");
			testname = in.nextLine();
			System.out.println("Enter Patient's pathology test result:");
			testres = in.nextLine();
			System.out.println("Enter 'Y' if exists Patient's pathology test review:");
			testreview = in.nextLine();
			if(testreview.equals("Y"))
			{
				System.out.println("Enter Patient's pathology test review:");
				testrev = in.nextLine();
			}
			System.out.println("Enter 'Y' if exists Patient's pathology test risk status:");
			testriskt = in.nextLine();
			if(testriskt.equals("Y"))
			{
				System.out.println("Enter Patient's pathology test risk:");
				testrisk = in.nextLine();
			}
		}
		
		System.out.println("Enter id number for Health assistant:");
		String hno = in.nextLine();
		 try
		    {
			 ha_id=Long.parseLong(hno);
		    }
		    catch(NumberFormatException e)
		    {
		        flag=1;
		    }
		    if(flag==1)
		    {
		        System.out.println("Id number must be numeric. Reenter the value."); 
		        System.exit(1);
		    }
		    
		System.out.println("Enter Name for Health Assistant:");
		String ha_name = in.nextLine();
		System.out.println("Enter Qualification for Health Assistant:");
		String ha_qual = in.nextLine();
		System.out.println("Enter Designation for Health Assistant:");
		String ha_des = in.nextLine();
		System.out.println("Enter disease id:");
		int did = in.nextInt();
		in.nextLine();
		
		
		
		
		System.out.println("Enter 'Y' if exists Patient's disease information:");
		String dis = in.nextLine();
		if(dis.equals("Y"))
		{
			System.out.println("Enter Patient's disease name:");
			disinfo = in.nextLine();
		}
		
		System.out.println("Enter medicine id:");
		int meid = in.nextInt();
		in.nextLine();
		System.out.println("Enter medicine name:");
		String mname = in.nextLine();
		System.out.println("Enter medicine company name:");
		String m_compa = in.nextLine();
		System.out.println("Enter medicine composition:");
		String m_compo = in.nextLine();
		System.out.println("Enter medicine price:");
		int m_price = in.nextInt();
		
		System.out.println("Enter symptom id:");
		int sid = in.nextInt();
		in.nextLine();
		System.out.println("Enter 'Y' if exists Patient's symptom area information:");
		String symp_areat = in.nextLine();
		if(symp_areat.equals("Y"))
		{
			System.out.println("Enter Patient's symptom area:");
			symp_area = in.nextLine();
		}
		System.out.println("Enter 'Y' if exists Patient's symptom duration information:");
		String symp_durt = in.nextLine();
		if(symp_durt.equals("Y"))
		{
			System.out.println("Enter Patient's symptom duration:");
			symp_dur = in.nextLine();
		}
		System.out.println("Enter 'Y' if exists Patient's symptom type:");
		String symp_typet = in.nextLine();
		if(symp_typet.equals("Y"))
		{
			System.out.println("Enter Patient's symptom type:");
			symp_type = in.nextLine();
		}
		System.out.println("Enter 'Y' if exists Patient's symptom characteristics information:");
		String symp_char = in.nextLine();
		if(symp_char.equals("Y"))
		{
			System.out.println("Enter Patient's symptom characteristics:");
			symp_charac = in.nextLine();
		}
		
		System.out.println("Enter 'Y' if exists Patient's present illness history:");
		String presill = in.nextLine();
		if(presill.equals("Y"))
		{
			System.out.println("Enter Patient's present illness history:");
			prsntill = in.nextLine();
		}
		
		System.out.println("Enter 'Y' if any surgical history exists:");
		String surhis = in.nextLine();
		if(surhis.equals("Y"))
		{
			System.out.println("Enter Patient's surgical area:");
			surarea = in.nextLine();
			System.out.println("Enter Patient's surgical remark:");
			surrem = in.nextLine();
		}
		System.out.println("Enter 'Y' if exists Patient's remark:");
		String remark = in.nextLine();
		if(remark.equals("Y"))
		{
			System.out.println("Enter Patient's remark:");
			remarkdata = in.nextLine();
		}
		
		System.out.println("Enter 'Y' if exists Patient's family history:");
		String famhis = in.nextLine();
		if(famhis.equals("Y"))
		{
			System.out.println("Enter Patient's family history:");
			famlyhistry = in.nextLine();
		}
		System.out.println("Enter Patient's chief complaints:");
		String compl = in.nextLine();
		
		
		MongoCollection<Document> HOSPITAL= database.getCollection("HOSPITAL");
	    Document hos1=Document.parse("{H_id:"+hid+",Hname:'"+hname+"',"
	    +"H_address:'"+hadd+"',"
	    +"H_contactInfo:'"+hcon+"'}");
	    HOSPITAL.insertOne(hos1);
	
	    MongoCollection<Document> DOCTOR = database.getCollection("DOCTOR");
		Document doctor2 = Document.parse("{Regn_no: "+r+", D_name: '"+dname+"',"
		+"D_qualification: '"+dqual+"',"
				+"D_designation: '"+ddes+"',"
		+"D_gender: '"+dgen+"',"
				+"hospital_name: '"+hos+"'}");
		DOCTOR.insertOne(doctor2);
	    
	    MongoCollection<Document> SYMPTOM= database.getCollection("SYMPTOM");
	    Document symptom1=Document.parse("{Sid:"+sid+",SYMPTOM_AREA:'"+symp_area+"',"
	    +"SYMPTOM_DURATION:'"+symp_dur+"',"
	    +"SYMPTOM_TYPE:'"+symp_type+"',"
	    +"SYMPTOM_CHARACTERISTICS:'"+symp_charac+"'}");
	    SYMPTOM.insertOne(symptom1);
	    
	    MongoCollection<Document> DISEASE= database.getCollection("DISEASE");
	   
	    
	    Document disease1=Document.parse("{D_id:"+did+",DISEASE_name:'"+disinfo+"'}");
	    DISEASE.insertOne(disease1);
	
		
	    
	    
	    MongoCollection<Document> DIAGNOSTIC_REPORT= database.getCollection("DIAGNOSTIC_REPORT");
	    Document diagnostic_report1=Document.parse("{Diag_id:"+diagid+",Pathology_Test_Name:'"+testname+"',"
	    +"Test_Result:'"+testres+"',"
	    +"Test_Review:'"+testrev+"',"
	    +"Test_Risk_Status:'"+testrisk+"'}");
	    DIAGNOSTIC_REPORT.insertOne(diagnostic_report1);
	    
	    MongoCollection<Document> COMMUNICATION_MEDIUM= database.getCollection("COMMUNICATION_MEDIUM");
	    Document com1=Document.parse("{C_id:"+cid+",C_Name:'"+cname+"'}");
	    COMMUNICATION_MEDIUM.insertOne(com1);
	    
	    MongoCollection<Document> HEALTH_ASSISTANT= database.getCollection("HEALTH_ASSISTANT");
	    Document health_assistant1=Document.parse("{HA_id:"+ha_id+",HA_name:'"+ha_name+"',"
	    +"HA_qualification:'"+ha_qual+"',"
	    +"HA_designation:'"+ha_des+"',"
	    +"Com_med_id:"+cid+"}");
	    HEALTH_ASSISTANT.insertOne(health_assistant1);
	    
	    MongoCollection<Document> PATIENT = database.getCollection("PATIENT");
	    Document patient1=Document.parse("{P_id: "+pid+", P_name: '"+pname+"',"
	    	    +"P_locationId: "+lid+","
	    	    +"P_occupation: '"+occu+"',"
	    	    +"P_gender: '"+pgen+"',"
	    	    +"P_age: "+page+","
	    	    +"P_religion: '"+prel+"',"
	    	    +"P_status: '"+sta+"',"
	    	    +"P_VisitingDtTime: '"+datetime+"',"
	    	    +"P_address: '"+padd+"',"
	    	    +"P_hasStetho: "+stethovalue+","
	    	    +"P_hasECG: "+ecgvalue+","
	    	    +"Family_History: '"+famlyhistry+"',"
	    	    +"Chief_Complaints: '"+compl+"',"
	    	    +"HA_ID:"+ha_id+","
	    	    +"D_ID:"+d_id+"}");
	    PATIENT.insertOne(patient1);
	    
	    MongoCollection<Document> GENERAL_SURVEY = database.getCollection("GENERAL_SURVEY");
	    Document general_survey1=Document.parse("{p_id:"+pid+",BMI_rate:'"+bmi+"',"
	    +"Bowel:'"+bow+"',"
	    +"BP:'"+bp+"',"
	    +"Food_Habit:'"+food+"',"
	    +"Gait:'"+gait+"',"
	    +"Genaral_Condition:'"+gencon+"',"
	    +"Height:'"+hei+"',"
	    +"Nutrition:'"+nutri+"',"
	    +"Posture:'"+post+"',"
	    +"Pulse_Rate:'"+pulse+"',"
	    +"Sleep:'"+sleep+"',"
	    +"SPO2:'"+spo2+"',"
	    +"Temperature:'"+temp+"',"
	    +"Weight:'"+weig+"'}");
	    GENERAL_SURVEY.insertOne(general_survey1);
	    
	    MongoCollection<Document> MEDICINE= database.getCollection("MEDICINE");
	    Document medicine1=Document.parse("{M_id:"+meid+",M_name:'"+mname+"',"
	    +"M_company:'"+m_compa+"',"
	    +"M_composition:'"+m_compo+"',"
	    +"M_price:"+m_price+"}");
	    MEDICINE.insertOne(medicine1);
	    
	    MongoCollection<Document> PERSONAL_HABITS= database.getCollection("PERSONAL_HABITS");
	    Document personal_habits1=Document.parse("{p_id:"+pid+",Is_Alcoholic:"+alcovalue+","
	    +"Is_Smoker:"+smovalue+","
	    +"Other_Habit:'"+othhab+"'}");
	    PERSONAL_HABITS.insertOne(personal_habits1);
	    
	    MongoCollection<Document> SURGICAL_HISTORY= database.getCollection("SURGICAL_HISTORY");
	    Document surgical_history1=Document.parse("{p_id:"+pid+",Surgical_Area:'"+surarea+"',"
	    +"Surgery_Remark:'"+surrem+"'}");
	    SURGICAL_HISTORY.insertOne(surgical_history1);
	    
	    MongoCollection<Document> REMARKS= database.getCollection("REMARKS");
	    Document remarks1=Document.parse("{p_id:"+pid+",Remarks:'"+remarkdata+"'}");
	    REMARKS.insertOne(remarks1);
	    
	    MongoCollection<Document> PRESCRIPTION= database.getCollection("PRESCRIPTION");
	    Document pres1=Document.parse("{PRES_ID:"+pr_id+",P_ID:"+pid+","
	    		+"P_NAME:'"+pname+"',"
	    		+"DOC_ID:'"+r+"',"
	    		+"DOC_NAME:'"+dname+"',"
	    		+"HOS_NAME:'"+hname+"',"
	    	    +"Present_Illness_History:'"+prsntill+"',"
	    	    +"Provisional_Diagnosis:'"+provdiagnos+"',"
	    	    +"Final_Diagnosis:"+diagid+","
	    	    +"Disease_Info:"+did+","
	    	    +"Symp_ID:"+sid+","
	    	    +"Dietary_advice:'"+dietadv+"',"
	    	    +"Lifestyle_advice:'"+lifestyle+"',"
	    	    +"Examination_Observation:'"+exmobsr+"',"
	    	    +"Prescribed_Medications:"+mid+","
	    	    +"Prescribed_Medication_Dosage:'"+dosage+"',"
	    	    +"Recommended_tests:'"+recommendtest+"',"
	    	    +"Patient_Referral:'"+referral+"'}");
	    */
		/*
		MongoCollection<Document> PRESCRIPTION= database.getCollection("PRESCRIPTION");
	    Document pres1=Document.parse("{PRES_ID:1,P_ID:10065,"
			    +"P_NAME:'Anil Mondal',"
			    +"DOC_ID:42760,"
			    +"DOC_NAME:'Dr.S.B.Acharya',"
			    +"HOS_NAME:'Medicare Hospital',"
			    +"Present_Illness_History:'',"
			    +"Provisional_Diagnosis:'ECG',"
			    +"Final_Diagnosis:1,"
			    +"Disease_Info:1,"
			    +"Symp_ID:1,"
			    +"Dietary_advice:'',"
			    +"Lifestyle_advice:'',"
			    +"Examination_Observation:'',"
			    +"Prescribed_Medications:1,"
			    +"Prescribed_Medication_Dosage:'Inhale Twice Daily',"
			    +"Recommended_tests:'ECG',"
			    +"Patient_Referral:''}");
	    PRESCRIPTION.insertOne(pres1);
	    
	    
		*/
		
		Semantic_check obj1 = new Semantic_check();		    
	    obj1.semantic_checking();	    
	 }
	

}


/*
class Semantic_Check {
	
	public static String fname1="C:\\Users\\Rudrani\\Desktop\\MSc books\\Sem3 books and materials\\cloud security papers\\ontology_structure\\Ontology_priti_given_11th.owl"; //Ontology-Model file
	
	
	public static String uri="http://www.semanticweb.org/rudrani/ontologies/2022/3/ontology_revised"; 

	public static String NS=uri+"#";

	//public static String input0="D:\\workspace-tc\\Testcaseg\\src\\test\\rule-set8.txt"; //Rule file
	
	public static void semantic_checking() throws IOException
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
			 
		 m1.write(System.out); 
		 
		//OntClass c=m1.getOntClass(NS+"Health_assistant");
		
		//System.out.println(c.toString());
		
		//c.createIndividual();
		 
		 OntClass c = m1.getOntClass( NS + "Doctor" );
		 Individual p1 = m1.createIndividual( NS + "Doctor3",c);
		 
	//	m1.write(new FileWriter(fname1));
		 
//		 OntModel m2=ModelFactory.createOntologyModel(OWL_MEM_,m1);
		 
		 
		// m1.write(System.out);
		 
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
		 
	}
	
}

*/