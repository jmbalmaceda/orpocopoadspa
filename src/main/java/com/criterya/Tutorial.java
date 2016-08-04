package com.criterya;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventListener;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

import com.sun.jna.NativeLibrary;

public class Tutorial {
	private final JFrame frame;
	private int aux = 0;
	private String[] options = {};
	private boolean ads = false;

	private final EmbeddedMediaListPlayerComponent mediaPlayerComponent;

	public static void main(final String[] args) {
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
		new NativeDiscovery().discover();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Tutorial("video4.avi");
			}
		});
	}

	public Tutorial(String path) {
		frame = new JFrame("My First Media Player");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				aux++;
				if (aux==1){
					ads=true;
					mediaPlayerComponent.getMediaListPlayer().stop();
					mediaPlayerComponent.getMediaListPlayer().getMediaList().clear();
					mediaPlayerComponent.getMediaListPlayer().getMediaList().addMedia("video4.avi", options);
					mediaPlayerComponent.getMediaListPlayer().play();
				}else if (aux==2){
					ads = true;
					mediaPlayerComponent.getMediaPlayer().playMedia("video2.avi", options);
				}else{
					mediaPlayerComponent.release();
					System.exit(0);
				}
			}

		});
		mediaPlayerComponent = new EmbeddedMediaListPlayerComponent();
		mediaPlayerComponent.getMediaPlayer().setFullScreenStrategy(
				new DefaultAdaptiveRuntimeFullScreenStrategy(frame)
				);
		frame.setContentPane(mediaPlayerComponent);
		frame.setVisible(true);
		mediaPlayerComponent.getMediaPlayer().setFullScreen(true);
		//mediaPlayerComponent.getMediaListPlayer().getMediaList().addMedia("video4.avi", options);
		mediaPlayerComponent.getMediaListPlayer().getMediaList().addMedia("video3.avi", options);
		mediaPlayerComponent.getMediaListPlayer().setMode(MediaListPlayerMode.LOOP);
		mediaPlayerComponent.getMediaListPlayer().addMediaListPlayerEventListener(new MediaListPlayerEventListener() {
			
			@Override
			public void stopped(MediaListPlayer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void played(MediaListPlayer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void nextItem(MediaListPlayer arg0, libvlc_media_t arg1, String arg2) {
				System.out.println("nextItem");
				if (ads){
					mediaPlayerComponent.getMediaListPlayer().getMediaList().addMedia("video3.avi", options);
					ads = false;
					mediaPlayerComponent.getMediaListPlayer().play();
				}
			}
			
			@Override
			public void mediaSubItemAdded(MediaListPlayer arg0, libvlc_media_t arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaStateChanged(MediaListPlayer arg0, int arg1) {
				System.out.println("mediaStateChanged "+arg0.isPlaying()+ " "+ads);
			}
			
			@Override
			public void mediaParsedChanged(MediaListPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaMetaChanged(MediaListPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaFreed(MediaListPlayer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mediaDurationChanged(MediaListPlayer arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		mediaPlayerComponent.getMediaListPlayer().play();

	}
}
