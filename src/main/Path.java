package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Path
{
    public static String ASSETS = "./assets/" ;
    public static String DADOS = "./dados/" ;

    static
    {
        try (FileInputStream fis = new FileInputStream("paths.properties"))
        {
            Properties props = new Properties() ;
            props.load(fis) ;
            ASSETS = props.getProperty("ASSETS", ASSETS) ;
            DADOS = props.getProperty("DADOS", DADOS) ;
        }
        catch (IOException e)
        {
            System.out.println("Erro ao carregar paths: " + e.getMessage()) ;
            e.printStackTrace() ;
        }
    }
    
}
