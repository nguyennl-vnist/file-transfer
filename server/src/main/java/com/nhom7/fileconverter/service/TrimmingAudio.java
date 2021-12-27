package com.nhom7.fileconverter.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TrimmingAudio extends AudioInputStream {

	private final AudioInputStream stream;
	private final long startByte, endByte;
	private long t_bytesRead = 0;

public TrimmingAudio(AudioFormat audioFormat,AudioInputStream audioInputStream,long startMilli,long endMilli){
    super(new ByteArrayInputStream(new byte[0]),audioFormat,AudioSystem.NOT_SPECIFIED);
    stream=audioInputStream;
    //calculate where to start and where to end
    startByte=(long)((startMilli/1000)*stream.getFormat().getFrameRate()*stream.getFormat().getFrameSize());
    endByte=(long)((endMilli/1000)*stream.getFormat().getFrameRate()*stream.getFormat().getFrameSize());
}

	@Override
	public int available() throws IOException {
		return (int) (endByte - startByte - t_bytesRead);
	}

	public int read(byte[] abData, int nOffset, int nLength) throws IOException {
		int bytesRead = 0;
		if (t_bytesRead < startByte) {
			do {
				bytesRead = (int) skip(startByte - t_bytesRead);
				t_bytesRead += bytesRead;
			} while (t_bytesRead < startByte);
		}
		if (t_bytesRead >= endByte)// end reached. signal EOF
			return -1;

		bytesRead = stream.read(abData, 0, nLength);
		if (bytesRead == -1)
			return -1;
		else if (bytesRead == 0)
			return 0;

		t_bytesRead += bytesRead;
		if (t_bytesRead >= endByte)// "trim" the extra by altering the number of bytes read
			bytesRead = (int) (bytesRead - (t_bytesRead - endByte));

		return bytesRead;
	}
	
	public static byte [] getAudioDataBytes(byte [] sourceBytes, AudioFormat audioFormat) throws UnsupportedAudioFileException, IllegalArgumentException, Exception{
        if(sourceBytes == null || sourceBytes.length == 0 || audioFormat == null){
            throw new IllegalArgumentException("Illegal Argument passed to this method");
        }

        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        AudioInputStream sourceAIS = null;
        AudioInputStream convert1AIS = null;
        AudioInputStream convert2AIS = null;

        try{
            bais = new ByteArrayInputStream(sourceBytes);
            sourceAIS = AudioSystem.getAudioInputStream(bais);
            AudioFormat sourceFormat = sourceAIS.getFormat();
            AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), sourceFormat.getChannels()*2, sourceFormat.getSampleRate(), false);
            convert1AIS = AudioSystem.getAudioInputStream(convertFormat, sourceAIS);
            convert2AIS = AudioSystem.getAudioInputStream(audioFormat, convert1AIS);

            baos = new ByteArrayOutputStream();

            byte [] buffer = new byte[8192];
            while(true){
                int readCount = convert2AIS.read(buffer, 0, buffer.length);
                if(readCount == -1){
                    break;
                }
                baos.write(buffer, 0, readCount);
            }
            return baos.toByteArray();
        } catch(UnsupportedAudioFileException uafe){
            //uafe.printStackTrace();
            throw uafe;
        } catch(IOException ioe){
            //ioe.printStackTrace();
            throw ioe;
        } catch(IllegalArgumentException iae){
            //iae.printStackTrace();
            throw iae;
        } catch (Exception e) {
            //e.printStackTrace();
            throw e;
        }finally{
            if(baos != null){
                try{
                    baos.close();
                }catch(Exception e){
                }
            }
            if(convert2AIS != null){
                try{
                    convert2AIS.close();
                }catch(Exception e){
                }
            }
            if(convert1AIS != null){
                try{
                    convert1AIS.close();
                }catch(Exception e){
                }
            }
            if(sourceAIS != null){
                try{
                    sourceAIS.close();
                }catch(Exception e){
                }
            }
            if(bais != null){
                try{
                    bais.close();
                }catch(Exception e){
                }
            }
        }
    }

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		AudioInputStream music = null;
//		music = AudioSystem.getAudioInputStream(new File("sample3.wav"));
//		music = new TrimmingAudio(music.getFormat(), music, 0, 30000);
//		AudioSystem.write(music, AudioFileFormat.Type.WAVE, new File("out.wav"));
	}
}
