/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;
/**
 *
 * @author User
 */
public class Example {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        /*
        System.out.println("Java Правит Интернетом!");
        System.out.println(md5Apache("root"));
        System.out.println(md5Apache("bvn13@mail.ru"));
        System.out.println(md5Apache(md5Apache("00000000")));
        System.out.println(md5Apache("00000000"));
        try{
             String url = "jdbc:mysql://localhost/test?serverTimezone=Europe/Moscow";
             String username = "root";
             String password = "root";
             Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
             try (Connection conn = DriverManager.getConnection(url, username, password)){
                  
                System.out.println("Connection to Store DB succesfull!");
                Statement statement = conn.createStatement();
                
                String pass = md5Apache("root");
                //LocalDate date = LocalDate.now( ZoneId.of( "Europe/Moscow" ) );
                
                //String sql = "INSERT users(nickname,email,password) VALUES ('i-rinat','rinat@mail.ru',?)";
                String sql = "SELECT nickname from users";
                
                ResultSet resultSet = statement.executeQuery(sql);
                while(resultSet.next()){
                    if (Objects.equals(resultSet.getString(1), new String("Java")))
                        System.out.println("Input different nickname");
                }
              
             }
         }
         catch(Exception ex){
             System.out.println("Connection failed...");
              
             System.out.println(ex);
         }
        */
        GeneratePlaylist g = new GeneratePlaylist();
        g.LoadFromFiles();
        /*
        //Email e = new Email();
        //e.Send();
        */
    }
     
    /*
    public static void showFilesAndDirectoryes (File f) throws Exception  {
        
        if (!f.isDirectory ()) {
        System.out.println (f.getName ());
        }
        
        if (f.isDirectory ()) { 
            try {
                System.out.println(f.getCanonicalFile()); 
                File[] child = f.listFiles();
                
                for (int i = 0; i < child.length; i++) {
                    System.out.println(child[i].getParent());   
                    showFilesAndDirectoryes (child[i]);     
                }//for
         
            }//try
            catch(Exception e){
                e.printStackTrace();
            }//catch
        } //if  
    }
    */
}