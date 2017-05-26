import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrentDirectoryMain {
	
	public static void main(String[] args) throws IOException {
		 
		String workingDirectory = getBasePathForClass(CurrentDirectoryMain.class);
		System.out.println("Working Directory = " + workingDirectory);
		
		// Files.newBufferedWriter() uses UTF-8 encoding by default
		// the file will be automatically closed
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("C://Users//GOXR3PLUS//Desktop//CurrentDirectory.txt"))) {
			writer.write("Working Directory = " + workingDirectory);
		}
	
		
	}
	
	/**
	 * Returns the absolute path of the current directory in which the given
	 * class file is.
	 * 
	 * @param classs
	 *        * @return The absolute path of the current directory in which the
	 *        class file is. <b>[it ends with File.Separator!!]</b>
	 * @author GOXR3PLUS[StackOverFlow user] + bachden [StackOverFlow user]
	 */
	public static final String getBasePathForClass(Class<?> classs) {
		
		// Local variables
		File file;
		String basePath = "";
		boolean failed = false;
		
		// Let's give a first try
		try {
			file = new File(classs.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			
			basePath = ( file.isFile() || file.getPath().endsWith(".jar") || file.getPath().endsWith(".zip") ) ? file.getParent() : file.getPath();
		} catch (URISyntaxException ex) {
			failed = true;
			Logger.getLogger(classs.getName()).log(Level.WARNING, "Cannot firgue out base path for class with way (1): ", ex);
		}
		
		// The above failed?
		if (failed)
			try {
				file = new File(classs.getClassLoader().getResource("").toURI().getPath());
				basePath = file.getAbsolutePath();
				
				// the below is for testing purposes...
				// starts with File.separator?
				// String l = local.replaceFirst("[" + File.separator +
				// "/\\\\]", "")
			} catch (URISyntaxException ex) {
				Logger.getLogger(classs.getName()).log(Level.WARNING, "Cannot firgue out base path for class with way (2): ", ex);
			}
		
		// fix to run inside Eclipse
		if (basePath.endsWith(File.separator + "lib") || basePath.endsWith(File.separator + "bin") || basePath.endsWith("bin" + File.separator)
				|| basePath.endsWith("lib" + File.separator)) {
			basePath = basePath.substring(0, basePath.length() - 4);
		}
		// fix to run inside NetBeans
		if (basePath.endsWith(File.separator + "build" + File.separator + "classes")) {
			basePath = basePath.substring(0, basePath.length() - 14);
		}
		// end fix
		if (!basePath.endsWith(File.separator))
			basePath += File.separator;
		
		return basePath;
	}
}
