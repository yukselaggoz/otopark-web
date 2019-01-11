/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author yukselaggoz
 */
@Named(value = "defauldBean")
@Dependent
public class DefauldBean {

    private String username;
    private String password;
    private String plakaNo;
    private String tabloAdi;   
    private String bilgi;
    private int parktaCarNum = 0;
    private int borc = 0;
    private int sure = 0;
    private int stok;
    private int fiyat = 0;
        
    Connection baglanti;
    
    public DefauldBean() {
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
    
    public int rentACar()
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
    public int getDay() 
    {
        return sure;
    }
    public void setDay(int sure) 
    {
        this.sure = sure;
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
        
    public void setHangiAraba(String plakaNo)
    {
        this.plakaNo = plakaNo;
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
