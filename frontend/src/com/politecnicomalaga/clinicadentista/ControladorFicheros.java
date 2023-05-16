package com.politecnicomalaga.clinicadentista;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class ControladorFicheros {
		

	public static boolean writeBin(String line) {

	        //Prueba de grabación y lectura en binario

			FileOutputStream fo = null;
			boolean resultado = false;


			byte[] realBinaryData = line.getBytes();
	

			try {

				System.out.println("Vamos a intentar escribir " + line);

				fo = new FileOutputStream("data.bin");

				fo.write(realBinaryData);

				fo.flush();

				fo.close();
				resultado = true;
	

			} catch (IOException e) {

				System.out.println(e.getMessage());

			} finally {

				if (fo != null) {

					try {

						fo.close();

					} catch (IOException ioe) {

						System.out.println(ioe.getMessage());

					}

				}

			}
			return resultado;

	    

	}

	public static String readBin() {  //Ineficiente
	
	    // lectura en binario
	
		String resultado = "";
		FileInputStream fi = null;
	
		try {
	
			fi = new FileInputStream("data.bin");
	
			byte data[] = new byte[1];
			while (fi.read(data)!=-1) {
		
                resultado += (char)(data[0]);	
			
				System.out.print(" " + (char)data[0] + "-" + data[0] + " ");
	
			}
	
			System.out.println("");	
			fi.close();
			fi=null;
	
	
		} catch (IOException e) {
	
			System.out.println(e.getMessage());
	
		} finally {
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
				}
				
			}
		}
		return resultado;
	
	}

	public static boolean writeText(String fileName, String data) {

        //Prueba de grabación en texto

		FileWriter fo = null;
		PrintWriter pw = null;
		boolean resultado = false;
		
		try {

			fo = new FileWriter(fileName); //Abrimos el fichero, modo append false
			pw = new PrintWriter(fo); //Creamos el ayudante
			
			pw.print(data);

			pw.flush();

			fo.close();
			fo = null;
			resultado = true;

		} catch (IOException e) {

			System.out.println(e.getMessage());

		} finally {
			if (fo != null) {
				try {
					fo.close();
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
				}

			}
		}
		return resultado;

    

}

	public static String readText(String fileName) {  //Ineficiente
	
	    // lectura en texto
	
		String resultado = "";
		FileReader fi = null;
		Scanner sc = null;
		
	
		try {
	
			fi = new FileReader(fileName);
	        sc = new Scanner(fi);
			
			while (sc.hasNext()) {
		
	            resultado += sc.nextLine() + "\n";	
	
			}
			fi.close();
			fi = null;
	
	
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			}
		}
		return resultado;
	
	}

}
