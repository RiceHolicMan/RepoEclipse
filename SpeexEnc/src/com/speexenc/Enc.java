package com.speexenc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Enc extends Thread{
	
	private File infile;
	private File outfile;
	private FileInputStream fis;
	private FileOutputStream fos;
	private DataInputStream dis;
	private final String PATH="/sdcard/";
	private final String INNAME="answer8kTest.wav";
	private final String OUTNAME="answerEnc8.txt";
	private Speex speex;
	private short[] inArray=new short[160];
	private short[] middle=new short[160];
	private byte[] outArray=new byte[40];
	
	public Enc(){
		speex=new Speex();
		infile=new File(PATH,INNAME);
		outfile=new File(PATH,OUTNAME);
		try {
			try {
				infile.createNewFile();
				outfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fis=new FileInputStream(infile);
			//dis=new DataInputStream(fis);
		    fos=new FileOutputStream(outfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void fileEncode(){
	  int len=0;
	  int splen=0;
	  byte[] temp=new byte[320];
	  try {
		len=(fis.available())/320;
		for(int i=0;i<len;i++){
			/*
			for(int m=0;m<160;m++){
			  inArray[m]=dis.readShort();
			}
			*/
			//splen=speex.getFrameSize();
			fis.read(temp);
			for(int t=0;t<160;t++)
				inArray[t]=(short)(temp[t*2]|(temp[t*2+1]<<8));
			speex.encode(inArray, 0, outArray, 160);
			fos.write(outArray);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	/*
	public void fileEncode(){
		int cDecode=0;
		byte[] tArray=new byte[20];
		boolean judge;
		try{
            int templen=(fis.available())/20;
            for(int temp=0;temp<templen;temp++){
			fis.read(outArray);
			speex.decode(outArray, middle, 20);
			for(cDecode=0;cDecode<160;cDecode++){
                inArray[cDecode*2+1]=(byte)((middle[cDecode]>>8)&0xff);
	            inArray[cDecode*2]=(byte)(middle[cDecode]&0xff);
			}
			fos.write(inArray);
			//speex.encode(middle, 0, tArray, 160);
			//judge=outArray.equals(tArray);
            }
		//	out.write(data);
			fos.flush();
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(fis!=null)
					fis.close();
				if(fos!=null)
					fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	*/
	
}