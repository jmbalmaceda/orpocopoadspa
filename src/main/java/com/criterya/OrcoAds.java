package com.criterya;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventListener;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

public class OrcoAds {
	private final JFrame frame;

    private final EmbeddedMediaListPlayerComponent mediaPlayerComponent;

    public static void main(final String[] args) {
    	NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        mediaPlayerComponent = new EmbeddedMediaListPlayerComponent();
        final MediaListPlayer mediaListPlayer = mediaPlayerComponent.getMediaPlayerFactory().newMediaListPlayer();
        //*
        mediaPlayerComponent.getMediaPlayer().setFullScreenStrategy(
        	    new DefaultAdaptiveRuntimeFullScreenStrategy(frame)
        	);
        //*/

        MediaList mediaList = mediaPlayerComponent.getMediaPlayerFactory().newMediaList();
        String[] options = {};
        mediaList.addMedia("video4.avi", options);
        mediaList.addMedia("video1.mp4", options);
        mediaList.addMedia("video2.avi", options);
        mediaList.addMedia("video3.avi", options);

        mediaListPlayer.setMediaList(mediaList);
        mediaListPlayer.setMode(MediaListPlayerMode.LOOP);
        mediaListPlayer.setMediaPlayer(mediaPlayerComponent.getMediaPlayer());
        
        frame.setContentPane(mediaPlayerComponent);
        frame.setVisible(true);
        
        mediaListPlayer.addMediaListPlayerEventListener(new MediaListPlayerEventListener() {
			
			@Override
			public void stopped(MediaListPlayer arg0) {
				System.out.println("stopped "+arg0);
			}
			
			@Override
			public void played(MediaListPlayer arg0) {
				System.out.println("played "+arg0);
			}
			
			@Override
			public void nextItem(MediaListPlayer arg0, libvlc_media_t arg1, String arg2) {
				System.out.println("nextItem, "+arg0+" "+arg1+ " "+ arg2);
			}
			
			@Override
			public void mediaSubItemAdded(MediaListPlayer arg0, libvlc_media_t arg1) {
				System.out.println("mediaSubItemAdded, "+arg0+" "+arg1);
			}
			
			@Override
			public void mediaStateChanged(MediaListPlayer arg0, int arg1) {
				System.out.println("mediaStateChanged, "+arg0+" "+arg1);
				System.out.println(arg0.isPlaying());
				if (!arg0.isPlaying())
					mediaListPlayer.playNext();
			}
			
			@Override
			public void mediaParsedChanged(MediaListPlayer arg0, int arg1) {
				System.out.println("mediaParsedChanged, "+arg0+" "+arg1);
			}
			
			@Override
			public void mediaMetaChanged(MediaListPlayer arg0, int arg1) {
				System.out.println("mediaMetaChanged, "+arg0+" "+arg1);
			}
			
			@Override
			public void mediaFreed(MediaListPlayer arg0) {
				System.out.println("mediaFreed, "+arg0);
			}
			
			@Override
			public void mediaDurationChanged(MediaListPlayer arg0, long arg1) {
				System.out.println("");
			}
		});
        //*
        for(;;) {
            Thread.sleep(500);
            //mediaPlayerComponent.getMediaPlayer().setChapter(3);

            //Thread.sleep(5000);
            mediaListPlayer.play();
        }
		//*/
        //mediaPlayerComponent.getMediaPlayer().playMedia("video2.avi","video3.avi","video1.mp4");
        //mediaPlayerComponent.getMediaPlayer().toggleFullScreen();
    }
}
