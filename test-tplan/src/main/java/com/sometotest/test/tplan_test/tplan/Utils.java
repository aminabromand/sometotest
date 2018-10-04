package com.sometotest.test.tplan_test.tplan;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils{
	public static File getFile( Object self, String resource_location, String resource_name ) {
		try{
			InputStream file_input_stream = self.getClass().getResourceAsStream( resource_location + File.separator + resource_name );

			File containing_file = new File( self.getClass().getProtectionDomain().getCodeSource().getLocation().toURI() );
			String temp_out_file_location = containing_file.getParentFile().getPath() + File.separator + "resources" + File.separator + "output" + File.separator + "out_" + resource_name;

			System.out.println( "getFile - temp_file_location: " + temp_out_file_location );
			File temp_out_file = new File( temp_out_file_location );

			Boolean directory_created = temp_out_file.getParentFile().mkdirs();
			Boolean file_created = temp_out_file.createNewFile();

			OutputStream file_output_stream = new FileOutputStream( temp_out_file );
			IOUtils.copy( file_input_stream, file_output_stream );

			file_output_stream.close();
			file_input_stream.close();

			return temp_out_file;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
