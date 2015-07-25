package com.proctor.App.helper;


import android.util.Log;

import com.proctor.App.model.Audit;
import com.proctor.App.model.Location;
import com.proctor.App.model.Question;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */
public class Temp {


    public void setType()
    {
        Log.e("start save audit type","start save audit type");

        Audit audit = new Audit("shop");
        audit.save();
        Audit audit1 = new Audit("office");
        audit1.save();
        Audit audit2 = new Audit("park");
        audit2.save();
        Log.e("end save audit type","end save audit type");
    }
    public  void setLocation()
    {
        Audit audit = Audit.findById(Audit.class,1);
      //  Log.e("start save Location type","start save Location type");
        Location loc= new Location("Iffco Chowk",80,"Ashish","27-June-2015","shop");
        loc.save();

        Location loc1= new Location("Shankar Chowk",65,"deepak","12-June-2015","shop");
        loc1.save();
        Location loc2= new Location("BPTP Tower",28,"rajiv","20-June-2015","shop");
        loc2.save();
        Location loc3= new Location("Sohna Raod",76,"mohit","14-June-2015","shop");
        loc3.save();
        Location loc4= new Location("DLF",90,"Ashish","27-June-2015","shop");
        loc4.save();
        Location loc5= new Location("Unitech",100,"deepak","2-June-2015","office");
        loc5.save();
        Location loc6= new Location("Vatika",87,"rajiv","7-June-2015","office");
        loc6.save();
        Location loc7= new Location("MGF",10,"mohit","31-June-2015","office");
        loc7.save();
        Location loc8= new Location("Sector 10",90,"ashish","2-June-2015","office");
        loc8.save();
        Location loc9= new Location("DLF Phase 3",87,"rajiv","7-June-2015","park");
        loc9.save();
        Location loc10= new Location("MG Road",10,"deepak","31-June-2015","park");
        loc10.save();
        Location loc11= new Location("Udyog Vihar",10,"mohit","31-June-2015","park");
        loc11.save();
        Log.e("end save Location type","end save Location type");

    }
    public  void setQuestion() {

    //    Log.e("start save Question type","start save Question type");

        Question que1 = new Question("shop","food Counter","Is Clean?","YES",2,1);
        que1.save();

        Question que2 = new Question("shop","Fridge","Is Clean?","YES",2,2);
        que2.save();


        Question que3 = new Question("office","Reception","Is Clean?","YES",2,1);
        que3.save();

        Question que4 = new Question("office","Conference Room","Is Projector Working?","YES",2,2);
        que4.save();






        Question que5 = new Question("park","Grills","Is Available?","YES",2,1);
        que5.save();
        Question que51 = new Question("park","Grills","What are the different types of real data type in C ?","float, double, long double",2,2);
        que51.save();
        Question que52 = new Question("park","Grills"," What will you do to treat the constant 3.14 as a long double??","use 3.14L",2,3);
        que52.save();


        Question que6 = new Question("park","Flower Pot","Is Painted?","YES",5,1);
        que6.save();
        Log.e("end save Question type","end save Question type");

        Question que61 = new Question("park","Flower Pot","The Homolographic projection has the correct representation of"," tarea",5,2);
        que61.save();
        Log.e("end save Question type","end save Question type");

        Question que62 = new Question("park","Flower Pot","The latitudinal differences in pressure delineate a number of major pressure zones, which correspond with"," zones of climate",7,3);
        que62.save();
        Log.e("end save Question type","end save Question type");




        Question que7 = new Question("park","food Counter","Is Clean?","YES",2,1);
        que7.save();

        Question que8 = new Question("park","Fridge","Is Clean?","YES",2,2);
        que8.save();


        Question que9 = new Question("park","Reception","Is Clean?","YES",2,1);
        que9.save();

        Question que10 = new Question("park","Conference Room","Is Projector Working?","YES",2,2);
        que10.save();



    }


}
