import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.BrickFinder;
import lejos.hardware.video.Video;

public class VideoTest
{
	private static final int WIDTH = 176; //160 works
    private static final int HEIGHT = 144; //120
    //private static final int NUM_PIXELS = WIDTH * HEIGHT;
    //private static final int BUFFER_SIZE = NUM_PIXELS * 2;
    /**
     * @param args
     */
    public static void main(String[] args)
    {
    	System.out.println("Started");
    	Video webcam = BrickFinder.getLocal().getVideo();
    	BufferedOutputStream os;
    	BufferedInputStream is;
    	ServerSocket data;
    	Socket ss;
    	try {
    		webcam.open(WIDTH, HEIGHT);
			data = new ServerSocket(55555);
			System.out.println("Accepting");
			ss = data.accept();
			os = new BufferedOutputStream(ss.getOutputStream());
			is = new BufferedInputStream(ss.getInputStream());
			byte[] frame = webcam.createFrame();
			
			int b = is.read();
			while (b == 0)
			{
				webcam.grabFrame(frame);
				os.write(frame);
				os.flush();
				b = is.read();
			}
			os.close();
			is.close();
			ss.close();
			data.close();
		} 
    	catch (IOException e) 
    	{
    		System.err.println(e);
		}
    	
    }
 
}