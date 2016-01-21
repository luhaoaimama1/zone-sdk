package seri;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SeriEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8697888640490020240L;
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		SeriEntity temp = new SeriEntity();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("cache.txt"));
		out.writeObject(temp);
		out.close();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("cache.txt"));
		SeriEntity temp2=(SeriEntity)in.readObject();
		in.close();
	}

}
