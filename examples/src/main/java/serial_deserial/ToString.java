package serial_deserial;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.iharder.Base64;

public class ToString {

	public static void main(String[] args) throws IOException{
		
		String mySerializableObject = "new object";
		byte[] arr = mySerializableObject.getBytes();
		System.out.println("1: "+Base64.encodeBytes(arr));
		
		byte[] r = Base64.decode(Base64.encodeBytes(arr));
		System.out.println("2: "+ new String(arr));
		
		String result1 = Base64.encodeObject( mySerializableObject );
		
		System.out.println("3: "+ new String(Base64.decode(result1)));
		String result2 = Base64.encodeBytes( new byte[]{ 3, 34, 116, 9 } );
		
		System.out.println("4: "+result1);
		System.out.println(result2);
		
		OutputStream out = new Base64.OutputStream( 
                new FileOutputStream( "out.txt" ) );
		
	}
}
