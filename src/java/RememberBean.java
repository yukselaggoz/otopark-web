import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "rememberBean" )

@SessionScoped
public class RememberBean 
{
        private String username;
        private String password;
        private int parktaCarNum = 0;
        private int borc = 0;
        private int sure = 0;
        private String tabloAdi;
        private int stok;
        private String bilgi;
        private int fiyat = 0;
        
        Connection baglanti;
        private String plakaNo;
        
        public RememberBean()
        {
            try
            {
                 // Bağlantı kuruluyor.
                 baglanti = DriverManager.getConnection("jdbc:derby://localhost:1527/ArabaKiralamaDB;user=APP;password=APP;");
            }
            catch(SQLException e)
            {
                 e.printStackTrace();
            }
        }  
        public int parktaACar()
        {
             parktaCarNum++;
             return parktaCarNum;
        }
       
        public String getUsername() 
	{
            return username;
	}
	public void setUsername(String username) 
	{
		this.username = username;
	}
        public int getSure() 
	{
            return sure;
	}
	public void setSure(int day) 
	{
		this.sure = day;
	}
	public String getPassword() 
	{
            return password;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
        
    
        public String login() throws SQLException
        {
            // Bağlantı üzerinden sorguyu çalıştır.
            Statement sta = baglanti.createStatement();
            ResultSet res = sta.executeQuery("select KullanıcıAdı, Şifre from Users");
            
            while (res.next()) 
            {
                 if(username.equals(res.getString("KullanıcıAdı")) && password.equals(res.getString("Şifre")) )
                 {
                        return "index.xhtml";
                 }
            }
            return "uyegirisi.xhtml";
        }
        
        public void setHangiTablo(String tabloAdi)
        {
           this.tabloAdi = tabloAdi;
        }
        
        public String getHangiTablo()
        {
            return tabloAdi;
        }
        
        public void setHangiAraba(String arabaAdi)
        {
            this.plakaNo = arabaAdi;
        }
        
        public String getHangiAraba()
        {
            return plakaNo;
        }
        
        public void setBilgi(String bilgi)
        {
            this.bilgi = bilgi;
        }
        
        public String getBilgi()
        {
            return bilgi;
        }
        
        public void ayristir()
        {
            if(bilgi.length() != 0)
            {
                String[] s1 = bilgi.split(" ");
                tabloAdi = s1[0];
                plakaNo = s1[1];
            }
            else
            {
                // Nothing
            }
        }
         
        public String stok() throws SQLException
        {
            ayristir();
           
            // Bağlantı üzerinden sorguyu çalıştır.
            Statement sta = baglanti.createStatement();
            
            if(bilgi.length() == 0)
                return " ";
            else
            {
                ResultSet res = sta.executeQuery("select Stok from " + tabloAdi + " where ID = " + plakaNo);
                    
                if(res.next())
                {
                    return res.getString("Stok");
                }
                return " ";
            }
            
        }
        
        public String fiyat() throws SQLException
        {
            ayristir();
           
            // Bağlantı üzerinden sorguyu çalıştır.
            Statement sta = baglanti.createStatement();
            
            if(bilgi.length() == 0)
                return " ";
            else
            {
                ResultSet res = sta.executeQuery("select Fiyat from " + tabloAdi + " where ID = " + plakaNo);

                if(res.next())
                {
                    borc += res.getInt("Fiyat") * sure;
                    return (Integer.toString((res.getInt("Fiyat") * sure)) + "TL");
                }
                return " ";
            }
            
        }
       
        
    
       
        
       
                
        

        
        
        
}
