package twitter.client;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class TwitterW extends JFrame {

	private JButton jButton1;
	private static JLabel jLabel1;
	private static JList<Tweet> jList1;
	private static JList<Tweet> jList2;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JTabbedPane jTabbedPane1;
	private JTextField jTextField1;

	private static TimelineClient client;
	public static List<Tweet> tweets = new ArrayList<Tweet>();
	public static List<Tweet> tweetsFriends = new ArrayList<Tweet>();
	
	public TwitterW() {
		initComponents();
		initUserInfo();
	}

	private void initComponents() {

		jTabbedPane1 = new JTabbedPane();
		jPanel1 = new JPanel();
		jButton1 = new JButton();
		jTextField1 = new JTextField();
		jLabel1 = new JLabel();
		jScrollPane1 = new JScrollPane();
		jList1 = new JList<Tweet>();
		jPanel2 = new JPanel();
		jScrollPane2 = new JScrollPane();
		jList2 = new JList<Tweet>();

		jList1.setCellRenderer(new TweetCellRenderer());
		jList2.setCellRenderer(new TweetCellRenderer());

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		jButton1.setText("Update");

		jLabel1.setPreferredSize(new java.awt.Dimension(48, 48));
		jLabel1.setRequestFocusEnabled(false);

		jList1.setFixedCellHeight(60);
		jScrollPane1.setViewportView(jList1);

		javax.swing.GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jScrollPane1)
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addGap(18, 18, 18)
	                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
	                        .addGap(18, 18, 18)
	                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
	                .addContainerGap())
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
	                .addGap(18, 18, 18)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(jTextField1)
	                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
	                .addContainerGap())
	        );

		jTabbedPane1.addTab("User Timeline", jPanel1);

		jList2.setFixedCellHeight(60);
		jScrollPane2.setViewportView(jList2);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
	                .addContainerGap())
	        );
	        jPanel2Layout.setVerticalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
	                .addContainerGap())
	        );

		jTabbedPane1.addTab("Friends Timeline", jPanel2);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(0, 0, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(0, 0, Short.MAX_VALUE))
	        );

		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus();
				jTextField1.setText("");
			}
		});

		jList1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (e.getClickCount() == 2) {
						int index = jList1.locationToIndex(e.getPoint());
						ListModel<Tweet> dlm = jList1.getModel();
						Tweet tweet = (Tweet) dlm.getElementAt(index);
						jList1.ensureIndexIsVisible(index);
						Desktop.getDesktop().browse(
								new URI("https://twitter.com/"
										+ tweet.getUser().getScreenName()
										+ "/status/" + tweet.getId()));
					}
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});

		jList2.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (e.getClickCount() == 2) {
						int index = jList2.locationToIndex(e.getPoint());
						ListModel<Tweet> dlm = jList2.getModel();
						Tweet tweet = (Tweet) dlm.getElementAt(index);
						jList1.ensureIndexIsVisible(index);
						Desktop.getDesktop().browse(
								new URI("https://twitter.com/"
										+ tweet.getUser().getScreenName()
										+ "/status/" + tweet.getId()));
					}
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		pack();
	}

	private void initUserInfo() {
		client = new TimelineClient();
		client.login();
	}

	public static void getUserTimeline() {
		// Timeline
		jList1.setModel(new AbstractListModel<Tweet>() {
			public int getSize() {
				return tweets.size();
			}

			public Tweet getElementAt(int i) {
				return tweets.get(i);
			}
		});

		// Image du profil
		try {
			URL url = new URL(tweets.get(0).getUser().getProfileImageUrl());
			Image image = ImageIO.read(url);
			jLabel1.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getFriendsTimeline() {
		// Timeline
		jList2.setModel(new AbstractListModel<Tweet>() {
			public int getSize() {
				return tweetsFriends.size();
			}

			public Tweet getElementAt(int i) {
				return tweetsFriends.get(i);
			}
		});
	}

	private void updateStatus() {
		try {
			String status = URLEncoder.encode(jTextField1.getText(), "UTF-8");
			client.updateStatus(status);
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(TwitterW.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public static TimelineClient getOAuthClient() {
		return client;
	}
}