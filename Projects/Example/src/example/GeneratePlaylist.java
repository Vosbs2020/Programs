package example;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
/**
 *
 * @author Evgeny
 */
public class GeneratePlaylist {
    
        class Type implements Comparable <Type>
        {
            float length;
            String name;
            @Override
            public int compareTo(Type tmp)
            {
                return (int)(this.length - tmp.length);
            }
            @Override
            public String toString() 
            {
                return "{" +
                "name='" + name + '\'' +
                ", age=" + length +
                '}';
            }
        }
        public ArrayList<Type> music,talk;
        SecureRandom random;
        public GeneratePlaylist()
        {
            music = new ArrayList<Type>();
            talk = new ArrayList<Type>();
            random = new SecureRandom();
        }
        public void LoadFromFiles(String nameShedule, String pathShedule, String[] nameDay, String pathDay
                ) throws FileNotFoundException, UnsupportedEncodingException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException
        {
            try {
                File file ;
                file = new File("Playlist music.m3u8");
                FileInputStream fStream = new FileInputStream(file);
                InputStreamReader fr = new InputStreamReader(fStream, "windows-1251");
                BufferedReader reader = new BufferedReader(fr);
                Type tmp = new Type();
                tmp.length = (float) 0.0;
                tmp.name = "";
                String line = reader.readLine();
                while (line != null) {
                    tmp.name = line;
                    line = reader.readLine();
                    tmp.length = Float.parseFloat(line);
                    music.add(tmp);
                    line = reader.readLine();
                    tmp = new Type();
                }
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
            } catch (IOException e) {
                    e.printStackTrace();
            }
            try {
                File file;
                file = new File("Playlist talk.m3u8");
                FileInputStream fStream = new FileInputStream(file);
                InputStreamReader fr;
                fr= new InputStreamReader(fStream, "windows-1251");
                BufferedReader reader = new BufferedReader(fr);
                Type tmp = new Type();
                tmp.length = (float) 0.0;
                tmp.name = "";
                String line = reader.readLine();
                while (line != null) {
                    tmp.name = line;
                    line = reader.readLine();
                    tmp.length = Float.parseFloat(line);
                    talk.add(tmp);
                    line = reader.readLine();
                    tmp = new Type();
                }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            Collections.sort(music);
            Collections.sort(talk);
            //try(Writer shedulewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Расписание работы радио"+".txt"), "windows-1251")))
            try(Writer shedulewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathShedule+nameShedule+".txt"), "windows-1251")))
            {
                shedulewriter.write(nameShedule+"\n");
                ArrayList<Type>  vec;
                for(int j=0;j<5;j++)
                {
                    String str="";
                    switch(j){
                        case 0:
                            str="понедельник";      
                            shedulewriter.write(str+"\n");
                            shedulewriter.write("9.00 – 10.00 – музыка (Жанр:Heavy metal;)\n");
                        break;
                        case 1:
                            str="вторник";
                            shedulewriter.write(str+"\n");
                            shedulewriter.write("9.00 - 10.00 – музыка (Жанр: Blues;)\n");
                        break;
                        case 2:
                            str="среда";
                            shedulewriter.write(str+"\n");
                            shedulewriter.write("9.00 - 10.00 – музыка (Жанр: Drum’n’bass;)\n");
                        break;
                        case 3:
                            str="четверг";
                            shedulewriter.write(str+"\n");
                            shedulewriter.write("9.00 - 10.00 – музыка (Жанр:Rock;)\n");
                        break;
                        case 4:
                            str="пятница";
                            shedulewriter.write(str+"\n");
                            shedulewriter.write("9.00 - 10.00 – музыка (Жанр: Rap;)\n");
                        break;
                        default:
                            str="playlist";
                        break;
                    }             
                    float length = (float) 3640.0, currentlength = 0.0f;//отсчитываем 1 час
                    vec = new ArrayList<Type> ();
                    currentlength = AddMusic(music, vec, currentlength, length);
                    length = (float) (2.5 * 3600.0+40.0);//отсчитываем 2.5 часа и 40 секунд
                    currentlength = AddTalk(talk, vec, currentlength, length);
                    if((int)60 / (((int)currentlength)/60) == 0)
                        shedulewriter.write("10.00 – 10.");   
                    else
                        shedulewriter.write("10.00 – 11.");   
                    int k = ((int)currentlength/60) % 60;
                    shedulewriter.write(Integer.toString(k));
                    String path = vec.get(vec.size()-1).name;
                    File f = new File(path);
                    String fName = f.getName();
                    fName = fName.substring(0, fName.lastIndexOf('.'));
                    shedulewriter.write("-\""+fName+"\"\n");//записываем название разговора в расписание  
                    if((int)60 / (((int)currentlength)/60) == 0)
                        shedulewriter.write("10.");   
                    else
                        shedulewriter.write("11.");   
                    k = (((int)currentlength/60)) % 60;
                    shedulewriter.write(Integer.toString(k)+" - 11.30 - ");   
                    currentlength = AddMusic(music, vec, currentlength, length);
                    switch(j){
                        case 0:
                            str="понедельник";      
                            shedulewriter.write("музыка (Жанр: Rock;)\n");
                        break;
                        case 1:
                            str="вторник";
                            shedulewriter.write("музыка (Жанр: Electrohouse;)\n");
                        break;
                        case 2:
                            str="среда";
                            shedulewriter.write("музыка (Жанр:Pop;)\n");
                        break;
                        case 3:
                            str="четверг";
                            shedulewriter.write("(Жанр: Reggae;)\n");
                        break;
                        case 4:
                            str="пятница";
                            shedulewriter.write("музыка (Жанр: Hip-hop;)\n");
                        break;
                        default:
                            str="playlist";
                        break;
                    }   
                    shedulewriter.write("11.30 – 11."); 
                    length = (float) ((float) 10800.0+40.0);
                    currentlength = AddTalk(talk, vec, currentlength, length);
                    shedulewriter.write((Integer.toString(((int)currentlength/60) % 60)));
                    path = vec.get(vec.size()-1).name;
                    f = new File(path);
                    fName = f.getName();
                    fName = fName.substring(0, fName.lastIndexOf('.'));
                    shedulewriter.write("-\""+fName+"\"\n"); 
                    shedulewriter.write("11."+(Integer.toString(((int)currentlength/60) % 60))+"– 12.00 – музыка");
                    switch(j){
                        case 0:
                            str="понедельник";      
                            shedulewriter.write("(Жанр: Alternative;)\n");
                        break;
                        case 1:
                            str="вторник";
                            shedulewriter.write("(Жанр: Alternative;)\n");
                        break;
                        case 2:
                            str="среда";
                            shedulewriter.write("(Жанр:Blues;)\n");
                        break;
                        case 3:
                            str="четверг";
                            shedulewriter.write(" (Жанр:Dubstep;)\n");
                        break;
                        case 4:
                            str="пятница";
                            shedulewriter.write("(Жанр: Rnb;)\n");
                        break;
                        default:
                            str="playlist";
                        break;
                    }
                    currentlength = AddMusic(music, vec, currentlength, length);
                    //try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str+".m3u8"), "windows-1251")))
                    try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathDay+nameDay[j]+".m3u8"), "windows-1251")))
                    {
                        writer.write("#EXTM3U\n");
                        for (int i = 0; i < vec.size(); i++)
                        {
                            writer.write("#EXTINF:");
                            writer.write(Integer.toString((int)vec.get(i).length));
                            writer.write(",");
                            writer.write(vec.get(i).name);
                            writer.write("\n");
                            writer.write(vec.get(i).name);
                            writer.write("\n");
                        }       
                        writer.flush();
                    }
                    catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                    vec.clear();
                }
                shedulewriter.close();
            }
        }
        public float AddMusic(ArrayList<Type> src, ArrayList<Type> dst,float currentlength, float maxlength)
        {
            int num;
            for (int i = 0; i < 100; i++)
            {
		num = Random(src);
		if (src.get(num).length + currentlength <= maxlength)
		{
                    dst.add(src.get(num));
                    currentlength += dst.get(dst.size()-1).length;
		}
            }    
            for (int ii = 0; ii < src.size(); ii++)
            for (int i = 0; i < src.size(); i++)
            {   
		if (src.get(i).length + currentlength <= maxlength)
		{
                    dst.add(src.get(i));
                    currentlength += dst.get(dst.size()-1).length;
		}
            }
            return currentlength;
        }
        public int Random(ArrayList<Type> vector)
        {
            int min = 0, max = vector.size()-1;
            return min + random.nextInt(max - min + 1);
        }
        float AddTalk(ArrayList<Type> src,ArrayList<Type> dst, float currentlength,float maxlength)
        {
            int num;
            boolean b = false;
            for (int i = 0; i < 1; i++)
            {
                num = Random(src);
		if (src.get(num).length + currentlength <= maxlength)
		{
                    b = true;
                    dst.add(src.get(num));
                    currentlength += dst.get(dst.size()-1).length;
		}
		if (!b)
                    AddTalk(src, dst, currentlength, maxlength);
            }
            return currentlength;
        }
}
