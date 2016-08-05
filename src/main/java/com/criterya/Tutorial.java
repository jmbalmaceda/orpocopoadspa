package com.criterya;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.activemq.ActiveMQConnectionFactory;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventListener;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

public class Tutorial implements ExceptionListener, MessageListener {
	private final JFrame frame;
	private String[] options = {};
	private boolean ads = false;
	private boolean clearAds = false;
	private String adsFile;
	private Hashtable<String, String> adsCodeFile = new Hashtable<>();

	private final EmbeddedMediaListPlayerComponent mediaPlayerComponent;

	public static void main(final String[] args) {
		new NativeDiscovery().discover();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Tutorial();
			}
		});
	}

	public Tutorial() {
		adsCodeFile.put("CAGNOLI", "Publicidad_CAGNOLI.mp4");
		adsCodeFile.put("COCACOLA", "Publicidad_COCACOLA.mp4");
		adsCodeFile.put("FIBERTEL", "Publicidad_FIBERTEL.mp4");

		frame = new JFrame("OrcoAds");
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
		mediaPlayerComponent.getMediaPlayer().setFullScreenStrategy(
				new DefaultAdaptiveRuntimeFullScreenStrategy(frame)
				);
		frame.setContentPane(mediaPlayerComponent);
		frame.setVisible(true);
		mediaPlayerComponent.getMediaPlayer().setFullScreen(true);

		makePlaylist();
		mediaPlayerComponent.getMediaListPlayer().setMode(MediaListPlayerMode.LOOP);
		mediaPlayerComponent.getMediaListPlayer().addMediaListPlayerEventListener(new MediaListPlayerEventListener() {

			@Override
			public void stopped(MediaListPlayer arg0) {
			}

			@Override
			public void played(MediaListPlayer arg0) {
			}

			@Override
			public void nextItem(MediaListPlayer arg0, libvlc_media_t arg1, String file) {
				if (ads){
					ads = false;
					clearAds=true;
				}else if (clearAds){
					makePlaylist();
					clearAds = false;
					mediaPlayerComponent.getMediaListPlayer().playNext();
				}else
					System.out.println("nextItem");
			}

			@Override
			public void mediaSubItemAdded(MediaListPlayer arg0, libvlc_media_t arg1) {
			}

			@Override
			public void mediaStateChanged(MediaListPlayer arg0, int arg1) {
			}

			@Override
			public void mediaParsedChanged(MediaListPlayer arg0, int arg1) {
			}

			@Override
			public void mediaMetaChanged(MediaListPlayer arg0, int arg1) {
			}

			@Override
			public void mediaFreed(MediaListPlayer arg0) {
			}

			@Override
			public void mediaDurationChanged(MediaListPlayer arg0, long arg1) {
			}
		});
		mediaPlayerComponent.getMediaListPlayer().play();
		try {
			initTopicConsumer();
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
	}

	private void makePlaylist(){
		mediaPlayerComponent.getMediaListPlayer().getMediaList().clear();
		mediaPlayerComponent.getMediaListPlayer().getMediaList().addMedia("Publicidad_CAGNOLI.mp4", options);
		mediaPlayerComponent.getMediaListPlayer().getMediaList().addMedia("Publicidad_FIBERTEL.mp4", options);
	}

	private void prepareAds(String adsCode){
		if (!(ads || clearAds)){
			this.ads = true;
			this.clearAds = false;
			this.adsFile = adsCodeFile.get(adsCode);
			mediaPlayerComponent.getMediaListPlayer().getMediaList().clear();
			mediaPlayerComponent.getMediaListPlayer().getMediaList().addMedia(adsFile, options);
			mediaPlayerComponent.getMediaListPlayer().playNext();
		}
	}

	private void initTopicConsumer() throws JMSException{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		// Create a Connection
		Connection connection = connectionFactory.createConnection();
		connection.start();

		connection.setExceptionListener(this);

		// Create a Session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create the destination (Topic or Queue)
		Topic topic = session.createTopic("OrcoAdsTopic");
		MessageConsumer topicConsumer = session.createConsumer(topic);
		topicConsumer.setMessageListener(this);
		connection.start();
	}

	@Override
	public void onException(JMSException arg0) {
		System.out.println(arg0);
	}

	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage){
			TextMessage textMessage = (TextMessage)message;
			try {
				String text = textMessage.getText();
				System.out.println("Mensaje Recibido: "+text);
				Tutorial.this.prepareAds(text);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
