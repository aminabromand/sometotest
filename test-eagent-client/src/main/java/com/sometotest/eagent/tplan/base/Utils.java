package com.sometotest.eagent.tplan.base;

import com.sometotest.eagent.activiti.ActivitiBase;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils{
	private final static Logger logger = LoggerFactory.getLogger( ActivitiBase.class );

	public static File getFile( Object self, String resource_location, String resource_name ) {
		try{
			File containing_file = new File( self.getClass().getProtectionDomain().getCodeSource().getLocation().toURI() );
			String temp_out_file_location = containing_file.getParentFile().getPath() + File.separator + "resources" + File.separator + resource_location + File.separator + resource_name;

			logger.info( "getFile - temp_file_location: " + temp_out_file_location );
			File temp_out_file = new File( temp_out_file_location );

			if ( temp_out_file.exists() ) {
				return temp_out_file;
			}

			Boolean directory_created = temp_out_file.getParentFile().mkdirs();
			Boolean file_created = temp_out_file.createNewFile();

			if ( !file_created ) {
				throw new RuntimeException( "could not create file" );
			}

			OutputStream file_output_stream = new FileOutputStream( temp_out_file );
			logger.info(resource_location + File.separator + resource_name);
			InputStream file_input_stream = self.getClass().getResourceAsStream( resource_location + File.separator + resource_name );
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
