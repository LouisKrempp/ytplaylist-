import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.text.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Point;

public class ytplaylist {

	private JFrame frame;
	private JTextField txtUser;
	private ArrayList<String> playlistsChecked = new ArrayList();
	private JList<String> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Restore Windows Look & Feel
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(ytplaylist.class.getName()).log(Level.SEVERE, null, ex);
				} catch (InstantiationException ex) {
					Logger.getLogger(ytplaylist.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IllegalAccessException ex) {
					Logger.getLogger(ytplaylist.class.getName()).log(Level.SEVERE, null, ex);
				} catch (UnsupportedLookAndFeelException ex) {
					Logger.getLogger(ytplaylist.class.getName()).log(Level.SEVERE, null, ex);
				}

				try {
					ytplaylist window = new ytplaylist();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ytplaylist() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setLocation(new Point(500, 500));
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(51, 51, 51));
		frame.setBackground(new Color(102, 102, 102));
		frame.setBounds(100, 100, 450, 342);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtUser = new JTextField();
		txtUser.setForeground(new Color(255, 255, 255));
		txtUser.setBackground(new Color(51, 51, 51));
		txtUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					// Enter was pressed. Your code goes here.
					try {
						listFiller();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		txtUser.setBounds(119, 79, 215, 20);
		frame.getContentPane().add(txtUser);
		txtUser.setColumns(10);

		JLabel lblYoutubecomuser = new JLabel("youtube.com/user/");
		lblYoutubecomuser.setForeground(new Color(255, 255, 255));
		lblYoutubecomuser.setBounds(10, 82, 104, 14);
		frame.getContentPane().add(lblYoutubecomuser);

		list = new JList();
		list.setSelectionBackground(new Color(255, 0, 0));
		list.setForeground(new Color(255, 255, 255));
		list.setBackground(new Color(102, 102, 102));
		list.setBounds(10, 78, 422, 151);
		list.setVisibleRowCount(8);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBackground(new Color(0, 0, 0));
		btnSearch.setForeground(new Color(0, 0, 0));
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					listFiller();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnSearch.setBounds(344, 78, 89, 23);
		frame.getContentPane().add(btnSearch);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setToolTipText("You can select multiple playlist by pressing while clicking SHIFT to select multiple playlists from a one to another or CTRL to select and add to your selection");
		btnConfirm.setBackground(new Color(0, 0, 0));
		btnConfirm.setForeground(new Color(0, 0, 0));
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					confirmPlaylists();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnConfirm.setBounds(175, 283, 89, 23);
		frame.getContentPane().add(btnConfirm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 104, 423, 168);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(list);

		BufferedImage logo;
		try {
			logo = ImageIO.read(new File("img\\logo.png"));
			JLabel picLabel = new JLabel(new ImageIcon(logo));
			picLabel.setLocation(77, 11);
			picLabel.setSize(299, 60);
			frame.getContentPane().add(picLabel);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File font_file = new File("img\\Roboto-Light.ttf");
		Font roboto;
		try {
			roboto = Font.createFont(Font.TRUETYPE_FONT, font_file);
			roboto = roboto.deriveFont(12f);
			frame.setFont(roboto);
			txtUser.setFont(roboto);
			lblYoutubecomuser.setFont(roboto);
			btnSearch.setFont(roboto);
			list.setFont(roboto);
			btnConfirm.setFont(roboto);

		} catch (FontFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	private boolean isListEmpty(JList list) {
		return list.getModel().getSize() == 0;
	}

	private void listFiller() throws Exception {
		// Grabber init
		Grabber g1 = new Grabber(txtUser.getText());
		
		if(!g1.isSuccess()) {
			JOptionPane.showMessageDialog(frame,
				    "Youtube account name provided not found, please verify !",
				    "Account not found",
				    JOptionPane.WARNING_MESSAGE);
			throw new Exception("Failure : invalid account name");
		}

		if (isListEmpty(list)) {
			list.setModel(new DefaultListModel<String>());
		}
		DefaultListModel<String> dlmA = (DefaultListModel) list.getModel();

		// Adding to table playlist names
		for (String s : g1.getPlaylistsTitleURLHM().keySet()) {
			dlmA.addElement(s);
		}

		list.setModel(dlmA);
	}

	private void confirmPlaylists() throws IOException {
		final String dest = System.getProperty("user.home") + "\\Documents\\ytplaylist++\\";
		
		playlistsChecked.clear();
		int[] selectedIx = list.getSelectedIndices();

		// Get all the selected items using the indices
		for (int i = 0; i < selectedIx.length; i++) {
			playlistsChecked.add(list.getModel().getElementAt(selectedIx[i]));
		}
		Grabber grabConfirm = GrabberForEachPlaylist();

		// filing @ c:/users/winda/playlistyt++/[playlistname].txt
		FilingForEachPlaylist(grabConfirm, dest);
		Desktop.getDesktop().open(new File(dest));
	}

	private Grabber GrabberForEachPlaylist() throws IOException {
		Grabber gFor = new Grabber(txtUser.getText());
		gFor.saveVideos(playlistsChecked);

		for (String s : gFor.getVideosByPlHM().keySet()) {
			System.out.println("Playlist Title : " + s + "\n\tVideos : ");
			for (String video : gFor.getVideosByPlHM().get(s)) {
				System.out.println("\n\t\t" + video);
			}
		}
		return gFor;
	}

	private void FilingForEachPlaylist(Grabber gFor, String dest) {
		
		try {
			File dir = new File(dest);
			boolean bool = dir.mkdir();
			if (bool)
				System.out.println("\nytplaylist++ directory created");
			else
				System.out.println("\nytplaylist++ directory already created");

			for (String s : playlistsChecked) {
				// Creates file
				String name = dest + s + ".txt";
				File playlistFile = new File(name);
				if (!playlistFile.exists()) {
					playlistFile.createNewFile();
					System.out.println("File created !");
				} else {
					System.out.println("File already created");
				}

				// Writes in file
				FileWriter fw = new FileWriter(playlistFile.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				String sep = System.getProperty("line.separator");
				int ite = 0;
				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("E dd.MM.yyyy 'at' hh:mm a zzz");

				// Writing Header
				bw.write("Playlist : \t" + s + sep);
				bw.write("Date : \t\t" + ft.format(dNow) + sep);
				bw.write("# of videos : \t" + gFor.getVideosByPlHM().get(s).size() + sep);
				bw.write("------------------------------------------------------" + sep + sep);

				// Writing videos
				for (String video : gFor.getVideosByPlHM().get(s)) {
					ite++;
					bw.write(ite + " - " + video + sep);
				}
				bw.close();
				System.out.println("File written successfully : " + name);
			}
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}
	}
}
