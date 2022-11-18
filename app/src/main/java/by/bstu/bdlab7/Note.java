package by.bstu.bdlab7;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Note {
    public String Text;
    public String Date;

    public Note(String text, String date)
    {
        Text = text;
        Date = date;
    }

    public static ArrayList<Note> Collection = new ArrayList<Note>();

    public static void Serialize(File dir){
        try{
            Gson gson = new Gson();
            FileWriter fw = new FileWriter(new File(dir, "json.json"));
            String json = gson.toJson(Note.Collection);
            gson.toJson(Note.Collection, fw);
            fw.close();
        }
        catch(Exception ex){

        }
    }

    public static ArrayList<Note> Deserialize(File dir){
        ArrayList<Note> newCollection = new ArrayList<Note>();
        try{
            Gson gson = new Gson();
            FileReader fr = new FileReader(new File(dir, "json.json"));
            newCollection = gson.fromJson(fr, new TypeToken<ArrayList<Note>>(){}.getType());
            Note.Collection = newCollection;
        }
        catch(Exception ex){

        }
        return newCollection;
    }
}
