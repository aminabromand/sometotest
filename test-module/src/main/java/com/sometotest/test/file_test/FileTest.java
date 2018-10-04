package com.sometotest.test.file_test;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;

public class FileTest{
	
	public void do_file_test(){

		try{

			System.out.println( "====================================" );

			System.out.println( "main - getClass: " + this.getClass() );
			System.out.println( "main - getClass.getResource: " + this.getClass().getResource( File.separator + "file_test" + File.separator + "file_test_exist.txt" ) );
			System.out.println( "main - getClass.getResource.toURI: " + this.getClass().getResource( File.separator + "file_test" + File.separator + "file_test_exist.txt" ).toURI() );
			System.out.println( "main - getClass.getResource.toExternalForm: " + this.getClass()
							.getResource( File.separator + "file_test" + File.separator + "file_test_exist.txt" ).toExternalForm() );
			System.out.println( "main - getClass.getResource that doesn't exist: " + this.getClass().getResource( File.separator + "test" + File.separator + "test2.txt" ) );
			System.out.println( "main - getClass.getProtectionDomain.getCodeSource: " + this.getClass().getProtectionDomain().getCodeSource() );
			System.out.println( "main - getClass.getProtectionDomain.getCodeSource.getLocation: " + this.getClass().getProtectionDomain().getCodeSource().getLocation() );
			System.out.println( "main - getClass.getProtectionDomain.getCodeSource.getLocation.toURI: " + this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI() );


			FileReader test_fileReader;
			BufferedReader test_bufferedReader;

			File test_file;
			String test_line;

			String test_resource_location;
			String test_resource_name;

//			TEST read internal File
			test_resource_location = File.separator + "file_test";
			test_resource_name = "file_test_read_internal.txt";
			test_file = getFile( test_resource_location, test_resource_name );
			test_fileReader = new FileReader( test_file );
			test_bufferedReader = new BufferedReader( test_fileReader );
			test_line = test_bufferedReader.readLine();
			test_fileReader.close();
			System.out.println( "main - file_test_read_internal.txt first line: " + test_line );


//			TEST read external File (the module options are extended to add the resources folder to the classpath in intelliJ)
			test_resource_location = File.separator + "input";
			test_resource_name = "file_test_read_external.txt";
			test_file = getFile_outside( test_resource_location, test_resource_name );
			test_fileReader = new FileReader( test_file );
			test_bufferedReader = new BufferedReader( test_fileReader );
			test_line = test_bufferedReader.readLine();
			test_fileReader.close();
			System.out.println( "main - file_test_read_external.txt first line: " + test_line );


			System.out.println( "====================================!" );

			System.out.println( "main - creating file" );

			String resource_name = "output_file.txt";
			File containing_file = new File( this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI() );
			String temp_out_file_location = containing_file.getParentFile().getPath() + File.separator + "resources" + File.separator + "output" + File.separator + "out_" + resource_name;
			File temp_out_file = new File( temp_out_file_location );
			Boolean created = temp_out_file.createNewFile();

			System.out.println( "main - created file: " + created );

		} catch(Exception e) {

			e.printStackTrace();

		}
		
	}

	private File getFile( String resource_location, String resource_name ) {
		try{
			InputStream file_input_stream = this.getClass().getResourceAsStream( resource_location + File.separator + resource_name );

			File containing_file = new File( this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI() );
			String temp_out_file_location = containing_file.getParentFile().getPath() + File.separator + "resources" + File.separator + "output" + File.separator + "out_" + resource_name;

			System.out.println( "getFile - temp_file_location: " + temp_out_file_location );
			File temp_out_file = new File( temp_out_file_location );

			Boolean directory_created = temp_out_file.getParentFile().mkdirs();
			Boolean file_created = temp_out_file.createNewFile();

			if( file_input_stream == null ){
				throw new RuntimeException();
			} else {
				OutputStream file_output_stream = new FileOutputStream( temp_out_file );
				IOUtils.copy( file_input_stream, file_output_stream );
				file_output_stream.close();
			}
			file_input_stream.close();

			return temp_out_file;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	private File getFile_outside( String resource_location, String resource_name  ){
		try{
			String path = resource_location + File.separator + resource_name;
			System.out.println( "getFile_outside - path: " + path );
			URL propertiesFileUrl = this.getClass().getResource( path );
			String p = propertiesFileUrl.getPath();
			System.out.println( "getFile_outside - p: " + p );
			return new File( p );
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
