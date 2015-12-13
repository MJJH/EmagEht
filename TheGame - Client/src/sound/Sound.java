package sound;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class Sound {

    static Object sound1;
    private Clip clip;

    public Sound(String fileName)
    { 
        AudioInputStream aiStream = null;
        
        try
        {
            aiStream = AudioSystem.getAudioInputStream(new File("src/resources/"+fileName));
            clip = AudioSystem.getClip();
            clip.open(aiStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                if (aiStream != null)
                {
                    aiStream.close();
                }
            } catch (IOException ex)
            {
                Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void play()
    {
        if (clip != null)
        {
            new Thread("playSound") {
                @Override
                public void run()
                {
                    synchronized (clip)
                    {
                        clip.stop();
                        clip.setFramePosition(0);
                        clip.start();
                    }
                }
            }.start();
        }
    }

    public void stop()
    {
        if (clip == null)
        {
            return;
        }
        clip.stop();
    }

    public void loop()
    {
        if (clip != null)
        {
            new Thread("loopSound") {
                @Override
                public void run()
                {
                    synchronized (clip)
                    {
                        clip.stop();
                        clip.setFramePosition(0);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                }
            }.start();
        }
    }

    public boolean isActive()
    {
        return clip.isActive();
    }
}
