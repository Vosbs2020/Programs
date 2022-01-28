package example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;

public class Example {
    static List<String> listpaths;
    static int count;
    public static void main(String[] args) throws Exception {
        
        ;
        count=0;
        listpaths = new ArrayList<String>();
        String currentWorkingDir;
        /*
        Process process = Runtime.getRuntime().exec("net SHARE \\\\DESKTOP-ENEV6UR\\other 27.11.2020\\Радио");
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    StringWriter writer = new StringWriter();
    String line;

    while (null != (line = reader.readLine())) {
        writer.write(line);
    }
*/
    //System.out.println(writer.toString());
        currentWorkingDir = "Z:\\Студия\\Радио";
        File file = new File(currentWorkingDir);
        try(Writer shedulewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("New playlist talk"+".m3u8"), "windows-1251")))
        {
            showFilesAndDirectoryes(file,shedulewriter);
            shedulewriter.close();
        }
        listpaths.clear();
        System.out.println(count);
        
        currentWorkingDir = "Z:\\Старый other\\other 19.05.2021\\Автоматизация\\музыка для радио от Сергея Косякова";
        file = new File(currentWorkingDir);
        try(Writer shedulewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Администратор\\Desktop\\Git\\Programs\\Projects\\Example\\playlist4.m3u8"), "windows-1251")))
        {
            showFilesAndDirectoryes(file,shedulewriter);
            shedulewriter.close();
        }
        listpaths.clear();
        System.out.println(count);
        
        GeneratePlaylist g = new GeneratePlaylist();
        g.LoadFromFiles();
    }
    //метод определения расширения файла
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
        return fileName.substring(fileName.lastIndexOf(".")+1);
        // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }
    
    public static void showFilesAndDirectoryes (File f,Writer shedulewriter) throws Exception  {
        
        File[] files = f.listFiles();
        for(File p:files){
            if (!p.isDirectory ()) {
                //System.out.println (p.getName());
                if(getFileExtension(p).equals("mp3"))
                {
                    listpaths.add(p.getName());
                    //System.out.println (p.getAbsolutePath()); 
                    shedulewriter.write(p.getAbsolutePath()+'\n');
                    Tag tag; 
                    java.util.logging.Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
                    AudioFile audioFile = AudioFileIO.read(new File(p.getAbsolutePath()));
                    //System.out.println("Track length = " + audioFile.getAudioHeader().getTrackLength());
                    shedulewriter.write(String.valueOf(audioFile.getAudioHeader().getTrackLength())+'\n');
                    count++;
                }
            }
            if (p.isDirectory ()) { 
            try {
                showFilesAndDirectoryes (p,shedulewriter); 
            }
            catch(Exception e){
                 e.printStackTrace();
              }
            }
        }  
    }
}