import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
/*
 * BufferedReader.java
 *
 * Created on 30. srpen 2007, 13:59
 */

/**
 *
 * @author Stepan
 */
public class BufferedReader {
    
//    private InputStream stream;
    private Reader reader;
    private char[] buffer;
    private static final int BUFFER_SIZE=20;
    private static final int EOF=-1;
    private int nBytes, nextByte;
    
    /** Creates a new instance of BufferedReader */
    public BufferedReader(InputStream stream) throws UnsupportedEncodingException {
        try {
            this.reader=new InputStreamReader(stream, Settings.getInstance().getEncoding());
        } catch (UnsupportedEncodingException ex) {            
            Settings.getInstance().setEncoding(null); //reset default encoding

            //could throw UnsupportedEncodingException, but that should not happen
            this.reader = new InputStreamReader(stream, Settings.getInstance().getEncoding());
        }
        this.buffer=new char[BUFFER_SIZE];
    }
    
    public int readByte() throws IOException {
        while(true) {
            if (nextByte >= nBytes) {
                fill();
                if (nextByte >= nBytes)
                    return -1;
            }
            
            return buffer[nextByte++];
        }
    }
    
    /**
     * Returns next char, but does not consume it.
     * @return          next byte in file or EOF
     */
    private int peekChar() throws IOException {
        if (nextByte >= nBytes)
            fill();
        if (nextByte >= nBytes)  //EOF
            return EOF;
        return buffer[nextByte];
    }
    
    public String readLine() throws IOException {
        StringBuffer s=null;
        while(true) {
            if (nextByte >= nBytes)
                fill();
            if (nextByte >= nBytes) { //EOF
                if(s!=null)
                    return s.toString();
                else
                    return null;
            }
            
            boolean eol = false;
            char c = 0;
            int i;
            
            charLoop:
                for (i = nextByte; i < nBytes; i++) {
                c = buffer[i];
                if (c == '\n' || c == '\r') {
                    eol = true;
                    break charLoop;
                }
                }
            
            int startChar = nextByte;
            nextByte = i;
            
            if (eol) {
                String str;
                if (s == null) {
                    str = new String(buffer, startChar, i - startChar);
                } else {
                    s.append(buffer, startChar, i-startChar);
                    
                    str = s.toString();
                }
                nextByte++;
                
                //if present, consume second char of "\r\n" (DOS newline)
                if(c=='\r' && peekChar()=='\n')
                    nextByte++;
                
                return str;
            }
            
            if (s == null)
                s = new StringBuffer();
            
            s.ensureCapacity(s.length()+ i-startChar );
            
            s.append(buffer, startChar, i-startChar);
        }
    }
    
    public void close() throws IOException {
        reader.close();
    }
    
    private void fill() throws IOException {
        int n;
//        do {
        n = reader.read(buffer, 0, buffer.length);
//        } while (n == 0);
        if (n > 0) {
            nBytes = n;
            nextByte = 0;
        }
    }
}
