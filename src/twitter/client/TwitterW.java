package twitter.client;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TwitterW extends JFrame {

	private JButton jButton1;
	private static JLabel jLabel1;
	private JTextField jTextField1;
	private JScrollPane jScrollPane2;
	private static JList jList1;

	private static OAuthClient client;
	public static List<Tweet> tweets = new ArrayList<Tweet>();

	public TwitterW() {
		initComponents();
		initUserInfo();
	}

	private void initComponents() {

		jButton1 = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jScrollPane2 = new javax.swing.JScrollPane();
		jList1 = new javax.swing.JList();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jButton1.setText("Update");

		jLabel1.setText("jLabel1");
		jLabel1.setPreferredSize(new java.awt.Dimension(48, 48));
		jLabel1.setRequestFocusEnabled(false);

		jScrollPane2.setViewportView(jList1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jLabel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jTextField1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										445, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jButton1).addContainerGap())
				.addComponent(jScrollPane2,
						javax.swing.GroupLayout.Alignment.TRAILING));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(jScrollPane2,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										284, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jButton1)
												.addComponent(
														jLabel1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jTextField1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));

		jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus();
				jLabel1.setText("");
			}
		});

		pack();
	}

	private void initUserInfo() {
		client = new OAuthClient();
		client.login();

	}

	@SuppressWarnings({ "serial", "unchecked" })
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

	private void updateStatus() {
		try {
			String status = URLEncoder.encode(jTextField1.getText(), "UTF-8");
			getOAuthClient().updateStatus(status);
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(TwitterW.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public static OAuthClient getOAuthClient() {
		return client;
	}
}