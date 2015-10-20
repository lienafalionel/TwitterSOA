package twitter.client;

import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class TweetCellRenderer extends JLabel implements ListCellRenderer<Tweet> {

	public TweetCellRenderer() {
		setOpaque(true);
		setIconTextGap(12);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Tweet> list, Tweet value,
			int index, boolean isSelected, boolean cellHasFocus) {
		try {
			setIcon(new ImageIcon(new URL(value.getUser().getProfileImageUrl())));
			setText(value.getUser().getName() + ": " + value.getText());
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return this;
	}
}
