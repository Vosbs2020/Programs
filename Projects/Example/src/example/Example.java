package example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
        count=0;
        listpaths = new ArrayList<String>();
        String currentWorkingDir;
        currentWorkingDir = "Z:\\Студия\\Радио";
        File file = new File(currentWorkingDir);
        try(Writer shedulewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Playlist talk"+".m3u8"), "windows-1251")))
        {
            showFilesAndDirectoryes(file,shedulewriter);
            shedulewriter.close();
        }
        listpaths.clear();
        System.out.println(count);      
        currentWorkingDir = "Z:\\Старый other\\other 19.05.2021\\Автоматизация\\музыка для радио от Сергея Косякова";
        file = new File(currentWorkingDir);
        try(Writer shedulewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Playlist music.m3u8"), "windows-1251")))
        {
            showFilesAndDirectoryes(file,shedulewriter);
            shedulewriter.close();
        }
        listpaths.clear();
        System.out.println(count);
        GeneratePlaylist g = new GeneratePlaylist();
        g.LoadFromFiles();
    }
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    public static void showFilesAndDirectoryes (File f,Writer shedulewriter) throws Exception  {
        File[] files = f.listFiles();
        for(File p:files){
            if (!p.isDirectory ()) {
                if(getFileExtension(p).equals("mp3"))
                {
                    listpaths.add(p.getName());
                    shedulewriter.write(p.getAbsolutePath()+'\n');
                    Tag tag; 
                    java.util.logging.Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
                    AudioFile audioFile = AudioFileIO.read(new File(p.getAbsolutePath()));
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