package com.criterya;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

public class OrcoAds {
	private final JFrame frame;

    private final EmbeddedMediaListPlayerComponent mediaPlayerComponent;

    public static void main(final String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
					new OrcoAds(args);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        });
    }

    public OrcoAds(String[] args) throws InterruptedException {
        frame = new JFrame("My First Media Player");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        mediaPlayerComponent = new EmbeddedMediaListPlayerComponent();
        MediaListPlayer mediaListPlayer = mediaPlayerComponent.getMediaPlayerFactory().newMediaListPlayer();
        /*
        mediaPlayerComponent.getMediaPlayer().setFullScreenStrategy(
        	    new DefaultAdaptiveRuntimeFullScreenStrategy(frame)
        	);
        	*/
        frame.setContentPane(mediaPlayerComponent);
        frame.setVisible(true);

        MediaList mediaList = mediaPlayerComponent.getMediaPlayerFactory().newMediaList();
        String[] options = {};
        mediaList.addMedia("video1.mp4", options);
        mediaList.addMedia("video2.avi", options);
        mediaList.addMedia("video3.avi", options);

        mediaListPlayer.setMediaList(mediaList);
        mediaListPlayer.setMode(MediaListPlayerMode.LOOP);
        mediaListPlayer.setMediaPlayer(mediaPlayerComponent.getMediaPlayer());
        //mediaListPlayer.play();
        for(;;) {
            Thread.sleep(500);
            mediaPlayerComponent.getMediaPlayer().setChapter(3);

            Thread.sleep(5000);
            mediaListPlayer.playNext();
        }
		
        //mediaPlayerComponent.getMediaPlayer().playMedia("video2.avi","video3.avi","video1.mp4");
        //mediaPlayerComponent.getMediaPlayer().toggleFullScreen();
    }
}
