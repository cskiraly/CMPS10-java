import java.io.*;
import net.tinyos.comm.*;

public class UsbIssCmps09 {

    private SerialPort port;
    private String portName;
    private InputStream in;
    private OutputStream out;

    public UsbIssCmps09(String portName) throws IOException, UnsupportedCommOperationException{
    	this.portName = portName;
    	open();
    	setI2CMode();
    }

    private void open() throws IOException, UnsupportedCommOperationException {
        System.out.println("Opening port " + portName);
        port = new TOSSerial(portName);
        in = port.getInputStream();
        out = port.getOutputStream();
 
        //port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        port.setSerialPortParams(19200, 8, SerialPort.STOPBITS_2, false);
    }

    private void setI2CMode() throws IOException {
    	byte[] cmd = {
    	        0x5A,	// USB_ISS command
    	        0x02,	// Set mode
    	        0x40,	// Set mode to 100KHz I2C
    	        0x00    // Spare pins set to output low
    	};
    	int[] resp = new int[2];
    	
        System.out.println("Writing " + cmd[0] + " " + cmd[1] + " " + cmd[2] + " " + cmd[3]);
    	out.write(cmd);
    	out.flush();
    	for(int r = 0; r < resp.length; r++) {
    		resp[r] = in.read();
    		if (resp[r] < 0) {
//        		System.out.println("**set_i2c_mode: read error");
			r--;
    		}
    	}
        System.out.println("Response " + resp[0] + " " + resp[1]);
    	if (resp[0] != 0xFF) {
    		throw new IOException("**set_i2c_mode: Error setting I2C mode!**");
    	}
  }

    public int getDirection() throws IOException {
    	int bearing;
    	byte[] cmd = {
    			0x55,	// USBI2C command for single byte address device
    			(byte)0xC1,	// CMPS09 address with R/W bit set high
    			0x00,	// Register we want to read from (0 is software version)
    			0x06
    	};
    	int[] resp = new int[6];

    	out.write(cmd);
    	out.flush();
    	for(int r = 0; r < resp.length; r++) {
    		resp[r] = in.read();
    		if (resp[r] < 0) {
//        		System.out.println("**set_i2c_mode: read error");
			r--;
    		}
    	}
    	
    	bearing = ((resp[2] << 8) + resp[3]) /10;
    	return bearing;
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, UnsupportedCommOperationException {
			UsbIssCmps09 compass = new UsbIssCmps09(args[0]);
			while(true) {
				System.out.println(compass.getDirection());
			}
	}

}
