package com.criterya;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventListener;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

import com.sun.jna.NativeLibrary;

public class Tutorial_1 {
	private final JFrame frame;

    private final EmbeddedMediaListPlayerComponent mediaPlayerComponent;

    public static void main(final String[] args) {
    	NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tutorial_1("video4.avi");
            }
        });
    }

    public Tutorial_1(String path) {
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
        MediaList mediaList = mediaPlayerComponent.getMediaPlayerFactory().newMediaList();
        String[] options = {};
        mediaList.addMedia("video4.avi", options);
        mediaList.addMedia("video4.avi", options);
        mediaList.addMedia("video3.avi", options);
        mediaList.addMedia("video1.mp4", options);
        mediaList.addMedia("video4.avi", options);
        
        MediaListPlayer mediaListPlayer = mediaPlayerComponent.getMediaPlayerFactory().newMediaListPlayer();
        
        mediaListPlayer.setMediaList(mediaList);
        mediaListPlayer.setMode(MediaListPlayerMode.LOOP);
        //mediaListPlayer.setMediaPlayer(mediaPlayerComponent.getMediaPlayer());
        mediaListPlayer.addMediaListPlayerEventListener(new MediaListPlayerEventListener() {
			
			@Override
			public void stopped(MediaListPlayer mediaListPlayer) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void played(MediaListPlayer mediaListPlayer) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void nextItem(MediaListPlayer mediaListPlayer, libvlc_media_t item,
					String itemMrl) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaSubItemAdded(MediaListPlayer mediaListPlayer,
					libvlc_media_t subItem) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaStateChanged(MediaListPlayer mediaListPlayer, int newState) {
				System.out.println("dasda");
				if (!mediaListPlayer.isPlaying())
					mediaListPlayer.playNext();
				
			}
			
			@Override
			public void mediaParsedChanged(MediaListPlayer mediaListPlayer,
					int newStatus) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaMetaChanged(MediaListPlayer mediaListPlayer, int metaType) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaFreed(MediaListPlayer mediaListPlayer) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaDurationChanged(MediaListPlayer mediaListPlayer,
					long newDuration) {
				// TODO Auto-generated method stub
				
			}
		});
        frame.setContentPane(mediaPlayerComponent);
        frame.setVisible(true);
        mediaListPlayer.play();
        /*
        // Reproducir un sólo video y terminar
        mediaPlayerComponent.getMediaPlayer().playMedia(path);
        //*/
        
    }
}
