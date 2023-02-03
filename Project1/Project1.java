import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;



public class Project1{
	public static void main(String args[]) {


		File inputFile = new File(args[0]);
		
		File outputFile = new File(args[1]);
		PrintStream outputStream;
//		PrintWriter output;
	

		try {
			outputStream = new PrintStream(outputFile);
//			output = new PrintWriter(outputFile);
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
			return;
		}

		Scanner sc;
		try {
			sc = new Scanner(inputFile);

		}catch(FileNotFoundException ex) {
			ex.printStackTrace(); 
			return;
		}
		
		FactoryImpl factory = new FactoryImpl();
		
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] splitLine = line.split(" ");
			
			
			
			if(splitLine[0].equals("AF")) {
				int id = Integer.parseInt(splitLine[1]);
				int val = Integer.parseInt(splitLine[2]);
				Product pro = new Product(id, val);
				factory.addFirst(pro);
				
			}
			
			if(splitLine[0].equals("AL")) {
				int id = Integer.parseInt(splitLine[1]);
				int val = Integer.parseInt(splitLine[2]);
				Product pro = new Product(id, val);
				factory.addLast(pro);
				
			}
			try {
				if(splitLine[0].equals("A")) {
					int index = Integer.parseInt(splitLine[1]);
					int id = Integer.parseInt(splitLine[2]);
					int val = Integer.parseInt(splitLine[3]);
					Product pro = new Product(id, val);
					factory.add(index, pro);
					
				}
			}catch(IndexOutOfBoundsException ex){
				outputStream.println("Index out of bounds.");
			}
			
			
			try {
				if(splitLine[0].equals("RF")) {
					outputStream.println(factory.removeFirst());
					
				}
			}catch(NoSuchElementException ex) {
				outputStream.println("Factory is empty.");
			}
			
			
			try {
				if(splitLine[0].equals("RL")) {
					outputStream.println(factory.removeLast());
					
				}
			}catch(NoSuchElementException ex) {
				outputStream.println("Factory is empty.");
			}
			try {
				if(splitLine[0].equals("RI")) {
					int index = Integer.parseInt(splitLine[1]);
					outputStream.println(factory.removeIndex(index));
				}
			}catch(IndexOutOfBoundsException ex){
				outputStream.println("Index out of bounds.");
				
			}
			
			try {
				if(splitLine[0].equals("RP")) {
					int val = Integer.parseInt(splitLine[1]);
					outputStream.println(factory.removeProduct(val));
				}
				
			}catch(NoSuchElementException ex){
				outputStream.println("Product not found.");
				
			}
			
			
			try {
				if(splitLine[0].equals("F")) {
					int id = Integer.parseInt(splitLine[1]);
				outputStream.println(factory.find(id));
					
				}
			}catch(NoSuchElementException ex) {
				outputStream.println("Product not found.");
			}
			
			try {
				if(splitLine[0].equals("G")) {
					int id = Integer.parseInt(splitLine[1]);
					outputStream.println(factory.get(id));
				}
			}catch(IndexOutOfBoundsException ex) {
				outputStream.println("Index out of bounds.");
			}
			
			try {
				if(splitLine[0].equals("U")) {
					int id = Integer.parseInt(splitLine[1]);
					int val = Integer.parseInt(splitLine[2]);
					outputStream.println(factory.update(id, val));
				}
			}catch(NoSuchElementException ex) {
				outputStream.println("Product not found.");
			}
			
			
			if(splitLine[0].equals("FD")) {
				outputStream.println(factory.filterDuplicates());
				
			}
			
			if(splitLine[0].equals("R")) {
				factory.reverse();
				outputStream.println(factory.print());
				
			}
		
			if(splitLine[0].equals("P")) {
				outputStream.println(factory.print());
			}
		
	
		}
		sc.close();
		outputStream.close();	
	}


}
