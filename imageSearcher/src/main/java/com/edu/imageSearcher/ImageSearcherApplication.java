package com.edu.imageSearcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.NotFoundException;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


@SpringBootApplication
public class ImageSearcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageSearcherApplication.class, args);
		System.out.println("hola");
		System.setProperty("webdriver.gecko.driver", "/home/hilda/Escritorio/imageSearcher/geckodriver"); 

//variables**********************************
	ArrayList<String> lista = leer();
	ArrayList<String> listaExcepciones = new ArrayList<String>();
	WebDriver driver = new FirefoxDriver();
	WebElement images;
        WebElement firstImage;
	WebElement theLink;
	String link;
	WebElement theHead;
	WebElement theImage;
	String finalLink;
	WebElement imageToSave;
	File scrFile;
//*******************************************
	
	   for(int i = 39; i < lista.size(); i++){
	try{   
	driver.navigate().to("https://www.google.cl/search?hl=en-419&tbm=isch&source=hp&biw=&bih=&ei=ipT_X5_uK9ew5OUP3_aB2Ao&q=" + lista.get(i));
        images = driver.findElement(By.xpath("//div[@class='islrc']"));
	firstImage = images.findElement(By.tagName("div"));
	firstImage.click();
	theLink = firstImage.findElement(By.tagName("a"));
	link = theLink.getAttribute("href");
	driver.navigate().to(link);
	theHead = driver.findElement(By.tagName("head"));
	theImage = theHead.findElement(By.xpath("/html/head/meta[3]"));
	finalLink = theImage.getAttribute("content");	
	driver.navigate().to(finalLink);
	imageToSave = driver.findElement(By.tagName("img"));
    	scrFile = imageToSave.getScreenshotAs(OutputType.FILE);
	try{    
	FileUtils.copyFile(scrFile, new File("./src/main/resources/imgs/" + lista.get(i) + ".png"));
	}catch(IOException e){
		System.out.println("Caught IOException: " +  e.getMessage());		
	}    
	

	System.out.println(driver.getTitle());

	System.out.println(driver.getCurrentUrl());

	System.out.println(finalLink);

	}catch(NoSuchElementException /*NotFoundException   WebDriverException*/  e){
		listaExcepciones.add(lista.get(i));	
	}
	}
	driver.quit();
	System.out.println("las búsquedas que tuvieron excepciones son: ");
	for(int i = 0; i < listaExcepciones.size(); i++){System.out.println(listaExcepciones.get(i));}
	System.out.println("adiós!");
	}




	public static ArrayList<String> leer (){
	ArrayList<String> lista = new ArrayList<String>();
	 try{	
	 FileReader fr=new FileReader("busquedas.txt");    
          BufferedReader br=new BufferedReader(fr);    

	 String data = br.readLine();
	 while(data != null) {
		lista.add(data);		
		data = br.readLine();
	 }
         
	 br.close();
	 fr.close();
  
        }catch (Exception e){System.out.println("Excepcion leyendo fichero: busquedas "+e);}	
	return lista;	
	}

}
