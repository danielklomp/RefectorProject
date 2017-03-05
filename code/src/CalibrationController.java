import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import lejos.nxt.Button;

public class CalibrationController {
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: Configures the relative values of the sensors 
	 * or loads old values for fast calibration.
	 */
	
	/**
	 * Starts the controller
	 * @param ucs The UpdatingColorSensor to be calibrated
	 * @param uls The UpdatingLightSensor to be calibrated
	 */
	public void run(UpdatingColorSensor ucs, UpdatingLightSensor uls) {
		File f = new File("calibration.dat"); 
		
		//If old calibration data exists
		if (f.exists()) {
			System.out.println("Do you want to load the old calibration?");
			System.out.println("Press ENTER for YES and any OTHER button for NO");
			
			int id = Button.waitForAnyPress();
			if (id == Button.ID_ENTER) {
				
				
				//read old calibration data
				try {
					FileInputStream fip = new FileInputStream(f);
					DataInputStream dis = new DataInputStream(fip);
					
					int ch, cl, lh, ll;
				
					lh = dis.readInt();
					ch = dis.readInt();
					ll = dis.readInt();
					cl = dis.readInt();
					
					ucs.setLow(cl);
					ucs.setHigh(ch);
					uls.setHigh(lh);
					uls.setLow(ll);
					
					dis.close();
					fip.close();
					System.out.println("Done!");
				}
				catch(IOException e) {
					System.out.println("Could not load the files. Reverting to old calibration.");
					FileOutputStream fos = null;
					DataOutputStream dos = null;
					try {
						f.delete();
						f.createNewFile();
						fos = new FileOutputStream(f);
						dos = new DataOutputStream(fos);
					} catch (Exception e1) {
						System.out.println("New error");
					}
					this.calibrateAndWrite(ucs, uls, dos);
					return;
				}
				
				return;
			}
			
		}
		
		//Write down new calibration data for fast calibration mode
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		try {
			f.delete();
			f.createNewFile();
			fos = new FileOutputStream(f);
			dos = new DataOutputStream(fos);
		} catch (Exception e) {
			System.out.println("Weirdass error");
		}
		this.calibrateAndWrite(ucs, uls, dos);
		
	}
	/**
	 * Calibrates the sensors and writes it to the file
	 * @param ucs The UpdatingColorSensor to be calibrated
	 * @param uls The UpdatingLightSensor to be calibrated
	 * @param dos The DataOutputStream that is to be used to write to the file
	 */
	private void calibrateAndWrite(UpdatingColorSensor ucs, UpdatingLightSensor uls, DataOutputStream dos) {
		try {
			this.calibrateWhite(ucs, uls, dos);
			this.calibrateBlack(ucs, uls, dos);
		} catch (IOException e) {
			System.out.println("Could not write for some reason");
		}
		try {
			dos.flush();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.finish();
	}


	/**
	 * Partially calibrates the UpdatingLightSensor and UpdatingColorSensor by setting their high value (whites). 
	 * @param colorSensor The ColorSensor that gets his new high value.
	 * @param lightSensor The LightSensor that gets his new high value.
	 */
	private void calibrateWhite(UpdatingColorSensor colorSensor, UpdatingLightSensor lightSensor, DataOutputStream dos) throws IOException {
		colorSensor.setFloodlight(true);
		lightSensor.setFloodlight(true);		
		
		System.out.println("Place both sensors on white");
		Button.ENTER.waitForPressAndRelease();
		
		int light_white = lightSensor.getNormalizedLightValue();
		lightSensor.setHigh(light_white);
		
		int color_white = colorSensor.getRawLightValue();
		colorSensor.setHigh(color_white);
			
		if (dos != null) {
			dos.writeInt(light_white);
			dos.writeInt(color_white);
		}
	}
	
	/**
	 * Partially calibrates the UpdatingLightSensor and UpdatingColorSensor by setting their low value (blacks). 
	 * @param colorSensor The ColorSensor that gets his new low value.
	 * @param lightSensor The LightSensor that gets his new low value.
	 */
	private void calibrateBlack(UpdatingColorSensor colorSensor, UpdatingLightSensor lightSensor, DataOutputStream dos) throws IOException {
		
		colorSensor.setFloodlight(true);
		lightSensor.setFloodlight(true);
		System.out.println("Place both sensors on black");
		Button.ENTER.waitForPressAndRelease();
		
		int light_black = lightSensor.getNormalizedLightValue();
		lightSensor.setLow(light_black);
		
		int color_black = colorSensor.getRawLightValue();
		colorSensor.setLow(color_black);
		
		if (dos != null) {
			dos.writeInt(light_black);
			dos.writeInt(color_black);
		}
	}

	/**
	 * Finished the calibration and gives a chance to place the robot on the starting position
	 * Can be used to clean up.
	 */
	public void finish() {
		System.out.println("Place the robot in the correct position.");
		Button.ENTER.waitForPressAndRelease();
	}
	
}