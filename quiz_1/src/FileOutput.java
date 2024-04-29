import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileOutput {
    /**
     * This method writes given content to file at given path.
     *
     * @param path    Path for the file content is going to be written.
     * @param content Content that is going to be written to file.//will change
     * @param append  Append status, true if wanted to append to file if it exists, false if wanted to create file from zero.
     * @param newLine True if wanted to append a new line after content, false if vice versa.

     */
    public static void writeToFile(String path, String[] contents, boolean append, boolean newLine) {//diffrence beetwen riginal code is last variable
        PrintStream ps = null;
        try {
            boolean last=false;
            for(String line:contents){
                if(line==null){
                    ps.print("");
                    continue;
                }
                if(contents[contents.length-1]==line){
                    last=true;
                } 
                ps = new PrintStream(new FileOutputStream(path, append));
                ps.print(line + (last ? "" : "\n"));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) { 
                ps.flush();
                ps.close();
            }
        }
    }
}