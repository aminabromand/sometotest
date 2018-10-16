package com.sometotest.base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class Base64Test{

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[]{ 'X', 'p', 'T', 'U', 'v', 'i', 's', 'B', 'q', 'l', '1', '9', 'U', 'k', 'i', '0' };

	public void run( String[] args ) throws IOException{
		String inputFilePath = args[0] + File.separator + "test.png";
		String encodedFilePath = args[0] + File.separator + "encoded";
		String outputFilePath = args[0] + File.separator + "output.docx";

		File inputFile = new File( inputFilePath );
		File encodedFile = new File( encodedFilePath );
		File outputFile = new File( outputFilePath );

		encodedFile.createNewFile();
		outputFile.createNewFile();

		System.out.println(inputFile.exists());
		System.out.println(encodedFile.getPath());


		copy(inputFile, encodedFile, "encode");
		copy(encodedFile, outputFile, "decode");

	}

	private void copy(File inFile, File outFile, String action) {
		int length = 0;
		try( InputStream is = new FileInputStream( inFile ); OutputStream os = new FileOutputStream( outFile ) ){

			int input_length = 0;
			if( action == null ) input_length = 0;
			if (action.equals( "encode" )) {
				input_length = 1023;

			} else if(action.equals( "decode" )) {
				input_length = 1364;
			}

			byte[] inBuffer = new byte[input_length];
			byte[] tempBuffer;
			byte[] outBuffer = new byte[0];

			while((length = is.read( inBuffer )) > 0){
				tempBuffer = Arrays.copyOfRange( inBuffer, 0, length );

				if( action == null )
					outBuffer = tempBuffer;
				else if( action.equals( "encode" ) ){

					outBuffer = Base64.getEncoder().encode( tempBuffer );



//		****	This part demonstrates the relation of 3 bytes to 4 base64 ascii characters ****
//
//					byte[] chunk;
//					byte[] chunk2;
//
//					for (int i=1; i<=8; i++) {
//						chunk = Arrays.copyOfRange( tempBuffer, 0, i );
//						System.out.println(i + " bytes: "+ new String(chunk, "UTF-8"));
//						chunk2 = Base64.getEncoder().encode( chunk );
//						System.out.println(i + " base64: "+ new String(chunk2, "UTF-8"));
//					}


				} else if( action.equals( "decode" ) ) {
					outBuffer = Base64.getDecoder().decode( tempBuffer );
				}

				os.write( outBuffer, 0, outBuffer.length );
			}

		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public void runOld( String[] args ){
		System.out.print( "Enter string to encrypt: " );
		String s = new Scanner( System.in ).next();

		String encrypted = encrypt( s );
		StringSelection stringSelection = new StringSelection( encrypted );
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents( stringSelection, null );

		System.out.println( "Encrypted string " + encrypted + " has been copied to clipboard");
	}

	private String encrypt( String stringToEncrypt ){
		try{
			Key key = generateKey();
			Cipher c = Cipher.getInstance( ALGO );
			c.init( Cipher.ENCRYPT_MODE, key );
			byte[] encVal = c.doFinal( stringToEncrypt.getBytes() );

			return new String( Base64.getEncoder().encode( encVal ),"UTF-8" );
		} catch(Exception e){
			System.out.println("Encryption failed. Error: " + e );
			return null;
		}
	}

	public String decrypt( String stringToDecrypt ){
		try{
			Key key = generateKey();
			Cipher c = Cipher.getInstance( ALGO );
			c.init( Cipher.DECRYPT_MODE, key );
			byte[] decodedValue = Base64.getDecoder().decode( stringToDecrypt.getBytes( "UTF-8" ) );
			byte[] decValue = c.doFinal( decodedValue );

			return new String( decValue );
		} catch(Exception e){
			System.out.println( "Decryption failed. Error: " + e );
			return null;
		}
	}

	private static Key generateKey() {
		return new SecretKeySpec( keyValue, ALGO );
	}
}
