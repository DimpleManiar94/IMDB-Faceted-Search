package imdb;

import java.sql.*;
import java.util.ArrayList;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;



import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

//import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.JTextArea;


public class hw3 {

	private JFrame jFrame_imdb;
	static Connection con = null;
	static PreparedStatement prepStatement = null;


	private JList<String> jList_genre, jList_country, jList_tags, jList_result, jList_location;

	DefaultListModel genreModel = new DefaultListModel();
	DefaultListModel countryModel = new DefaultListModel();
	DefaultListModel locationModel = new DefaultListModel();
	DefaultListModel tagModel = new DefaultListModel();
	DefaultListModel resultModel = new DefaultListModel();

	ArrayList<String> selectedGenreList = new ArrayList();
	ArrayList<String> selectedCountryList = new ArrayList();
	ArrayList<String> selectedLocationList = new ArrayList();
	ArrayList<String> selectedTagsList = new ArrayList();
	private String yearFrom = "", yearTo = "", genreQuery ="", countryQuery ="", tagWeight = "", countryResultQuery = "", tagsResultQuery ="", resultQuery = "", stars = "", reviews = "";
	private JTextField jTextField_yearFrom;
	private JTextField jTextField_yearTo;
	private JTextField jTextField_tagWeight;
	private JTextField jTextField_stars;
	private JTextField jTextField_reviews;
	private JTextArea JTextArea_queryDisplay;
	private JComboBox comboBox_AndOr, comboBox_tagWeight, comboBox_Stars, comboBox_Reviews;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hw3 window = new hw3();
					window.jFrame_imdb.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public hw3() {
		initialize();
		connectToDB();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	public void connectToDB(){
		try{
			con = openConnection();
			populateGenre();
		} catch (SQLException e){
			System.err.println("Error occured when communicating with the database server: " + e.getMessage());
		} catch (ClassNotFoundException e){
			System.err.println("Cannot find the database driver");
		} finally{

		}
	}

	private void initialize() {
		jFrame_imdb = new JFrame();
		jFrame_imdb.setTitle("IMDB");
		jFrame_imdb.getContentPane().setBackground(new Color(58, 198, 200));
		jFrame_imdb.setBounds(100, 100, 891, 750);
		jFrame_imdb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame_imdb.getContentPane().setLayout(null);

		JPanel panel_genre = new JPanel();
		panel_genre.setBackground(Color.LIGHT_GRAY);
		panel_genre.setBounds(0, 0, 141, 38);
		jFrame_imdb.getContentPane().add(panel_genre);
		panel_genre.setLayout(null);

		JLabel lbl_genres = new JLabel("Genres");
		lbl_genres.setBounds(0, 0, 141, 36);
		panel_genre.add(lbl_genres);
		lbl_genres.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lbl_genres.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 38, 141, 185);
		jFrame_imdb.getContentPane().add(scrollPane);

		jList_genre = new JList<>();
		jList_genre.setBackground(SystemColor.menu);

		jList_genre.setBounds(0, 184, 139, -184);
		JPanel genrePanel = new JPanel();
		scrollPane.setViewportView(genrePanel);
		genrePanel.setBackground(new Color(240, 240, 240));
		genrePanel.setForeground(new Color(0, 128, 128));
		genrePanel.add(jList_genre);

		JPanel panel_yearRange = new JPanel();
		panel_yearRange.setBackground(Color.LIGHT_GRAY);
		panel_yearRange.setBounds(-17, 223, 158, 30);
		jFrame_imdb.getContentPane().add(panel_yearRange);
		panel_yearRange.setLayout(null);

		JLabel lbl_yearRange = new JLabel("Movie Year Range");
		lbl_yearRange.setBounds(10, 0, 135, 27);
		panel_yearRange.add(lbl_yearRange);
		lbl_yearRange.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lbl_yearRange.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_yearToFrom = new JPanel();
		panel_yearToFrom.setBounds(-31, 251, 172, 71);
		jFrame_imdb.getContentPane().add(panel_yearToFrom);
		panel_yearToFrom.setLayout(null);

		JLabel lbl_yearFrom = new JLabel("From");
		lbl_yearFrom.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_yearFrom.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lbl_yearFrom.setBounds(27, 3, 56, 29);
		panel_yearToFrom.add(lbl_yearFrom);

		JLabel lbl_yearTo = new JLabel("To");
		lbl_yearTo.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_yearTo.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lbl_yearTo.setBounds(27, 39, 56, 23);
		panel_yearToFrom.add(lbl_yearTo);

		jTextField_yearFrom = new JTextField();
		jTextField_yearFrom.setBounds(83, 6, 79, 23);
		panel_yearToFrom.add(jTextField_yearFrom);
		jTextField_yearFrom.setColumns(10);
		jTextField_yearFrom.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				yearFrom = jTextField_yearFrom.getText();
			}
			public void keyReleased( KeyEvent e){
				yearFrom = jTextField_yearFrom.getText();
			}
			public void keyTyped( KeyEvent e){
				yearFrom = jTextField_yearFrom.getText();
			}
		});

		jTextField_yearTo = new JTextField();
		jTextField_yearTo.setBounds(83, 39, 79, 22);
		panel_yearToFrom.add(jTextField_yearTo);
		jTextField_yearTo.setColumns(10);
		jTextField_yearTo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				yearTo = jTextField_yearTo.getText();
			}
			public void keyReleased( KeyEvent e){
				yearTo = jTextField_yearTo.getText();
			}
			public void keyTyped( KeyEvent e){
				yearTo = jTextField_yearTo.getText();
			}
		});

		JButton btnGetCountry = new JButton("Show Countries");
		btnGetCountry.setBounds(0, 324, 141, 41);
		jFrame_imdb.getContentPane().add(btnGetCountry);
		btnGetCountry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				populateCountry(evt);
				//JTextArea_queryDisplay.setText(resultQuery);
			}
		});

		JPanel panel_country = new JPanel();
		panel_country.setBackground(Color.LIGHT_GRAY);
		panel_country.setBounds(142, 0, 141, 38);
		jFrame_imdb.getContentPane().add(panel_country);
		panel_country.setLayout(null);

		JLabel lbl_country = new JLabel("Countries");
		lbl_country.setBounds(0, 0, 141, 33);
		panel_country.add(lbl_country);
		lbl_country.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lbl_country.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane_country = new JScrollPane();
		scrollPane_country.setBounds(142, 38, 141, 284);
		jFrame_imdb.getContentPane().add(scrollPane_country);

		jList_country = new JList();
		jList_country.setBackground(SystemColor.menu);
		JPanel panel_countryList = new JPanel();
		scrollPane_country.setViewportView(panel_countryList);
		panel_countryList.add(jList_country);

		JButton btnGetFilmingLocation = new JButton("Show Locations");
		btnGetFilmingLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				populateLocation(evt);
				//JTextArea_queryDisplay.setText(resultQuery);
			}
		});
		btnGetFilmingLocation.setBounds(142, 324, 141, 41);
		jFrame_imdb.getContentPane().add(btnGetFilmingLocation);

		JPanel panel_location = new JPanel();
		panel_location.setLayout(null);
		panel_location.setBackground(Color.LIGHT_GRAY);
		panel_location.setBounds(284, 0, 141, 55);
		jFrame_imdb.getContentPane().add(panel_location);

		JLabel lbl_location = new JLabel("Filming Location");
		lbl_location.setBounds(0, 0, 147, 32);
		panel_location.add(lbl_location);
		lbl_location.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_location.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		JScrollPane scrollPane_location = new JScrollPane();
		scrollPane_location.setBounds(284, 54, 141, 300);
		jFrame_imdb.getContentPane().add(scrollPane_location);

		JPanel panel_locationList = new JPanel();
		scrollPane_location.setViewportView(panel_locationList);

		jList_location = new JList();
		jList_location.setBackground(SystemColor.menu);
		panel_locationList.add(jList_location);

		JPanel panel_rating = new JPanel();
		panel_rating.setLayout(null);
		panel_rating.setBackground(Color.LIGHT_GRAY);
		panel_rating.setBounds(426, 0, 174, 38);
		jFrame_imdb.getContentPane().add(panel_rating);

		JLabel lbl_rating = new JLabel("Critics Rating");
		lbl_rating.setBounds(0, 0, 151, 33);
		panel_rating.add(lbl_rating);
		lbl_rating.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_rating.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		JPanel panel_stars = new JPanel();
		panel_stars.setBounds(426, 39, 174, 127);
		jFrame_imdb.getContentPane().add(panel_stars);
		panel_stars.setLayout(null);

		JLabel lbl_stars = new JLabel("Stars:");
		lbl_stars.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lbl_stars.setBounds(5, 30, 61, 16);
		panel_stars.add(lbl_stars);

		comboBox_Stars = new JComboBox();
		comboBox_Stars.setModel(new DefaultComboBoxModel(new String[] {"=", "<", ">", "<=", ">="}));
		comboBox_Stars.setMaximumRowCount(5);
		comboBox_Stars.setBounds(15, 47, 130, 24);
		panel_stars.add(comboBox_Stars);

		JLabel lbl_starsValue = new JLabel("Value:");
		lbl_starsValue.setToolTipText("");
		lbl_starsValue.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lbl_starsValue.setBounds(5, 76, 61, 16);
		panel_stars.add(lbl_starsValue);

		jTextField_stars = new JTextField();
		jTextField_stars.setBounds(15, 93, 107, 26);
		panel_stars.add(jTextField_stars);
		jTextField_stars.setColumns(10);
		jTextField_stars.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				stars= jTextField_stars.getText();
			}
			public void keyReleased( KeyEvent e){
				stars = jTextField_stars.getText();
			}
			public void keyTyped( KeyEvent e){
				stars = jTextField_stars.getText();
			}
		});

		JPanel panel_reviews = new JPanel();
		panel_reviews.setLayout(null);
		panel_reviews.setBounds(426, 168, 174, 130);
		jFrame_imdb.getContentPane().add(panel_reviews);

		JLabel lblNumberOfReviews = new JLabel("Number of Reviews");
		lblNumberOfReviews.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNumberOfReviews.setBounds(15, 11, 143, 16);
		panel_reviews.add(lblNumberOfReviews);

		comboBox_Reviews = new JComboBox();
		comboBox_Reviews.setModel(new DefaultComboBoxModel(new String[] {"=", "<", ">", "<=", ">="}));
		comboBox_Reviews.setMaximumRowCount(5);
		comboBox_Reviews.setBounds(25, 35, 110, 24);
		panel_reviews.add(comboBox_Reviews);

		JLabel label_reviewsValue = new JLabel("Value");
		label_reviewsValue.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		label_reviewsValue.setBounds(10, 70, 61, 16);
		panel_reviews.add(label_reviewsValue);

		jTextField_reviews = new JTextField();
		jTextField_reviews.setColumns(10);
		jTextField_reviews.setBounds(25, 87, 118, 26);
		panel_reviews.add(jTextField_reviews);
		jTextField_reviews.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				reviews= jTextField_reviews.getText();
			}
			public void keyReleased( KeyEvent e){
				reviews = jTextField_reviews.getText();
			}
			public void keyTyped( KeyEvent e){
				reviews = jTextField_reviews.getText();
			}
		});

		JButton btnShowTags = new JButton("Show Tags");
		btnShowTags.setBounds(436, 311, 119, 52);
		jFrame_imdb.getContentPane().add(btnShowTags);
		btnShowTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateTags(e);
				//JTextArea_queryDisplay.setText(resultQuery);
			}
		});

		JPanel panel_tags = new JPanel();
		panel_tags.setBackground(Color.LIGHT_GRAY);
		panel_tags.setBounds(599, 0, 292, 38);
		jFrame_imdb.getContentPane().add(panel_tags);
		panel_tags.setLayout(null);

		JLabel lbl_tagValues = new JLabel("Tag Values");
		lbl_tagValues.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lbl_tagValues.setBackground(SystemColor.activeCaption);
		lbl_tagValues.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_tagValues.setBounds(0, 6, 227, 33);
		panel_tags.add(lbl_tagValues);

		JScrollPane scrollPane_tags = new JScrollPane();
		scrollPane_tags.setBounds(599, 38, 292, 263);
		jFrame_imdb.getContentPane().add(scrollPane_tags);

		JPanel panel_tagsList = new JPanel();
		scrollPane_tags.setViewportView(panel_tagsList);

		jList_tags = new JList();
		panel_tagsList.add(jList_tags);

		JPanel panel_tagWeight = new JPanel();
		panel_tagWeight.setBounds(574, 302, 174, 87);
		jFrame_imdb.getContentPane().add(panel_tagWeight);
		panel_tagWeight.setLayout(null);

		JLabel lblTagWeight = new JLabel("Tag Weight");
		lblTagWeight.setBounds(0, 5, 55, 23);
		lblTagWeight.setHorizontalAlignment(SwingConstants.CENTER);
		lblTagWeight.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		panel_tagWeight.add(lblTagWeight);

		comboBox_tagWeight = new JComboBox();
		comboBox_tagWeight.setBounds(79, 4, 85, 24);
		comboBox_tagWeight.setMaximumRowCount(3);
		comboBox_tagWeight.setModel(new DefaultComboBoxModel(new String[] {"=", ">", "<"}));
		panel_tagWeight.add(comboBox_tagWeight);

		JLabel lbl_tagValue = new JLabel("Value");
		lbl_tagValue.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_tagValue.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lbl_tagValue.setBounds(0, 34, 55, 23);
		panel_tagWeight.add(lbl_tagValue);

		jTextField_tagWeight = new JTextField();
		jTextField_tagWeight.setBounds(79, 35, 85, 23);
		panel_tagWeight.add(jTextField_tagWeight);
		jTextField_tagWeight.setColumns(10);
		jTextField_tagWeight.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				tagWeight = jTextField_tagWeight.getText();
			}
			public void keyReleased( KeyEvent e){
				tagWeight = jTextField_tagWeight.getText();
			}
			public void keyTyped( KeyEvent e){
				tagWeight = jTextField_tagWeight.getText();
				System.out.println(tagWeight);
			}
		});

		JButton btn_filterTags = new JButton("Filter Tags");
		btn_filterTags.setBounds(758, 313, 133, 64);
		jFrame_imdb.getContentPane().add(btn_filterTags);
		btn_filterTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				filterTags(evt);
				//JTextArea_queryDisplay.setText(resultQuery);
			}
		});

		JPanel panel_andOr = new JPanel();
		panel_andOr.setBackground(Color.LIGHT_GRAY);
		panel_andOr.setBounds(0, 365, 425, 64);
		jFrame_imdb.getContentPane().add(panel_andOr);
		panel_andOr.setLayout(null);

		JLabel lbl_andOr = new JLabel("Search Between \nAttributes' Values:");
		lbl_andOr.setForeground(Color.DARK_GRAY);
		lbl_andOr.setBounds(21, 0, 360, 35);
		panel_andOr.add(lbl_andOr);
		lbl_andOr.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lbl_andOr.setHorizontalAlignment(SwingConstants.CENTER);

		comboBox_AndOr = new JComboBox();
		comboBox_AndOr.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		comboBox_AndOr.setBounds(60, 35, 284, 23);
		comboBox_AndOr.setMaximumRowCount(3);
		comboBox_AndOr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select AND, OR between attributes", "AND", "OR" }));
		panel_andOr.add(comboBox_AndOr);

		JLabel lbl_query = new JLabel("Query Display:");
		lbl_query.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lbl_query.setForeground(Color.WHITE);
		lbl_query.setBackground(Color.DARK_GRAY);
		lbl_query.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_query.setBounds(10, 440, 144, 37);
		jFrame_imdb.getContentPane().add(lbl_query);

		JScrollPane scrollPane_query = new JScrollPane();
		scrollPane_query.setBounds(12, 488, 431, 167);
		jFrame_imdb.getContentPane().add(scrollPane_query);

		JTextArea_queryDisplay = new JTextArea();
		scrollPane_query.setViewportView(JTextArea_queryDisplay);

		JButton btn_query = new JButton("Run Query");
		btn_query.setBackground(new Color(64, 224, 208));
		btn_query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				populateResults(evt);
			}
		});
		btn_query.setBounds(136, 666, 158, 38);
		jFrame_imdb.getContentPane().add(btn_query);

		JPanel panel_result = new JPanel();
		panel_result.setLayout(null);
		panel_result.setBackground(SystemColor.activeCaption);
		panel_result.setBounds(447, 389, 444, 39);
		jFrame_imdb.getContentPane().add(panel_result);

		JLabel lbl_results = new JLabel("Result");
		lbl_results.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_results.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lbl_results.setBackground(SystemColor.activeCaption);
		lbl_results.setBounds(88, 0, 200, 39);
		panel_result.add(lbl_results);

		JScrollPane scrollPane_result = new JScrollPane();
		scrollPane_result.setBounds(447, 428, 444, 284);
		jFrame_imdb.getContentPane().add(scrollPane_result);

		JPanel panel_resultList = new JPanel();
		scrollPane_result.setViewportView(panel_resultList);
		jList_result = new JList();
		panel_resultList.add(jList_result);

	}
	private Connection openConnection() throws SQLException, ClassNotFoundException {
		// Load the Oracle database driver
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

	    /*
	    Here is the information needed when connecting to a database
	    server. These values are now hard-coded in the program. In
	    general, they should be stored in some configuration file and
	    read at connectToDB time.
	    */
		String host = "127.0.0.1";
		String port = "1521";
		String dbName = "orcl";
		String userName = "hr";
		String password = "oracle";

		// Construct the JDBC URL
		String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
		return DriverManager.getConnection(dbURL, userName, password);
	}

	/**
	 * Close the database connection
	 * @param con
	 */
	private void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("Cannot close connection: " + e.getMessage());
		}
	}

	private void populateGenre() {

		String genre = "SELECT DISTINCT G.GENRE FROM MOVIE_GENRES G";
		try {
			ResultSet rS = null;
			prepStatement=con.prepareStatement(genre);
			rS = prepStatement.executeQuery(genre);
			while(rS.next())
			{
				if(!genreModel.contains(rS.getString("genre")))
				{
					genreModel.addElement(rS.getString("genre"));
				}
			}
			prepStatement.close();
			rS.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		jList_genre.setModel(genreModel);

		MouseListener mouseListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 1)
				{
					selectedGenreList = (ArrayList<String>) jList_genre.getSelectedValuesList();
				}
			}
		};
		jList_genre.addMouseListener(mouseListener);
	}

	private void populateCountry(ActionEvent evt) {

		countryResultQuery = "";
		genreQuery = "";
		// TODO Auto-generated method stub

		if(selectedGenreList.size()!=0)
		{
			//String countryResultQuery = "";
			String bAttr = "";

			if(comboBox_AndOr.getSelectedIndex()==1)
			{
				bAttr = "INTERSECT";
			}
			else
			{
				if(comboBox_AndOr.getSelectedIndex()==0 || comboBox_AndOr.getSelectedIndex()==2)
				{
					bAttr = "UNION";
				}
			}

			//Genre Within attributes
			int i=0;
			if(yearFrom.length() == 4 && yearTo.length() == 4) {
				for(i=0; i< selectedGenreList.size()-1; i++)
				{
					//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
					genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				}
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
			}
			else {
				for(i=0; i< selectedGenreList.size()-1; i++)
				{
					//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
					genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND MG.GENRE = '"+ selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				}
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
			}


			countryResultQuery += "SELECT DISTINCT C.COUNTRY FROM MOVIE_COUNTRIES C \n WHERE C.MOVIEID IN ( " + genreQuery +" ) \n";

			System.out.println(countryResultQuery);

			resultQuery = "SELECT DISTINCT MR.TITLE, MGR.GENRE, MR.MYEAR, MCR.COUNTRY, MLR.LOCATION, (MR.RTALLCRITICSRATING + MR.RTTOPCRITICRATING + MR.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, (MR.RTALLCRITICSNUMREVIEWS + MR.RTTOPCRICTICNUMREVIEWS + MR.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR\nWHERE MR.MOVIEID = MCR.MOVIEID AND MR.MOVIEID = MLR.MOVIEID AND MR.MOVIEID IN ( " + genreQuery + " ) \n";
			//resultQuery2 = "SELECT DISTINCT AVG(MR.RTALLCRITICSRATING + MR.RTTOPCRITICRATING + MR.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, AVG(MR.RTALLCRITICSNUMREVIEWS + MR.RTTOPCRICTICNUMREVIEWS + MR.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR\nWHERE MR.MOVIEID = MCR.MOVIEID AND MR.MOVIEID = MLR.MOVIEID AND MR.MOVIEID IN ( " + genreQuery + " )\n";
			JTextArea_queryDisplay.setText(resultQuery);
			ResultSet rS = null;
			countryModel.clear();

			try
			{
				prepStatement = con.prepareStatement(countryResultQuery);
				rS =prepStatement.executeQuery(countryResultQuery);


				while(rS.next())
				{

					if(!countryModel.contains(rS.getString("COUNTRY")))
					{
						countryModel.addElement(rS.getString("COUNTRY"));
					}
				}
				prepStatement.close();
				rS.close();

			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			jList_country.setModel(countryModel);

			MouseListener mouseListener = new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (e.getClickCount() == 1)
					{
						selectedCountryList = (ArrayList<String>) jList_country.getSelectedValuesList();
						System.out.print(selectedCountryList);
					}
				}
			};
			jList_country.addMouseListener(mouseListener);
		}
	}

	private void populateLocation(ActionEvent evt) {

		String location = "";
		String bAttr = "";
		countryQuery = "";

		if(comboBox_AndOr.getSelectedIndex()==1)
		{
			bAttr = "INTERSECT";
		}
		else
		{
			if(comboBox_AndOr.getSelectedIndex()==0 || comboBox_AndOr.getSelectedIndex()==2)
			{
				bAttr = "UNION";
			}
		}

		if(selectedCountryList.size() == 0)
		{
			int start = 0;
			int end = jList_country.getModel().getSize()-1;
			jList_country.setSelectionInterval(start, end);
			selectedCountryList = (ArrayList<String>) jList_country.getSelectedValuesList();
		}

		//Genre Within attributes
		int i=0;

		for(i=0; i< selectedCountryList.size()-1; i++)
		{
			location += "SELECT DISTINCT ML.LOCATION \nFROM MOVIE_LOCATIONS ML, MOVIE_COUNTRIES MMC\nWHERE ML.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+ selectedCountryList.get(i)+"' AND ML.MOVIEID IN("+ genreQuery +")"+"\n"+bAttr+"\n";
			countryQuery += "SELECT DISTINCT ML.MOVIEID\nFROM MOVIE_LOCATIONS ML, MOVIE_COUNTRIES MMC\nWHERE ML.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+ selectedCountryList.get(i)+"' AND ML.MOVIEID IN("+ genreQuery +")"+"\n"+bAttr+"\n";
		}

		location += "SELECT DISTINCT ML.LOCATION\nFROM MOVIE_LOCATIONS ML, MOVIE_COUNTRIES MMC\nWHERE ML.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+ selectedCountryList.get(i)+"' AND ML.MOVIEID IN("+ genreQuery +")"+"\n";
		countryQuery += "SELECT DISTINCT ML.MOVIEID\nFROM MOVIE_LOCATIONS ML, MOVIE_COUNTRIES MMC\nWHERE ML.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+ selectedCountryList.get(i)+"' AND ML.MOVIEID IN("+ genreQuery +")"+"\n";

		System.out.println(countryQuery);
		//String oldResult = resultQuery;
		resultQuery = "";
		for(i=0; i< selectedCountryList.size()-1; i++)
		{
			resultQuery += "SELECT DISTINCT MR.TITLE, MGR.GENRE, MR.MYEAR, MCR.COUNTRY, MLR.LOCATION, (MR.RTALLCRITICSRATING + MR.RTTOPCRITICRATING + MR.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, (MR.RTALLCRITICSNUMREVIEWS + MR.RTTOPCRICTICNUMREVIEWS + MR.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR\nWHERE MR.MOVIEID = MCR.MOVIEID AND MR.MOVIEID = MLR.MOVIEID AND MCR.COUNTRY='"+ selectedCountryList.get(i)+"' AND MR.MOVIEID IN("+ genreQuery +")"+"\n"+bAttr+"\n";
			//countryQuery += "SELECT DISTINCT ML.MOVIEID\nFROM MOVIE_LOCATIONS ML, MOVIE_COUNTRIES MMC\nWHERE ML.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+selectedCountryList.get(i)+"' AND ML.MOVIEID IN("+genreQuery+")"+"\n"+bAttr+"\n";
		}
		resultQuery += "SELECT DISTINCT MR.TITLE, MGR.GENRE, MR.MYEAR, MCR.COUNTRY, MLR.LOCATION, (MR.RTALLCRITICSRATING + MR.RTTOPCRITICRATING + MR.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, (MR.RTALLCRITICSNUMREVIEWS + MR.RTTOPCRICTICNUMREVIEWS + MR.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR\nWHERE MR.MOVIEID = MCR.MOVIEID AND MR.MOVIEID = MLR.MOVIEID AND MCR.COUNTRY='"+ selectedCountryList.get(i)+"' AND MR.MOVIEID IN("+ genreQuery +")"+"\n";
		JTextArea_queryDisplay.setText(resultQuery);
		ResultSet rS = null;
		locationModel.clear();

		try
		{
			prepStatement = con.prepareStatement(location);
			rS =prepStatement.executeQuery(location);


			while(rS.next())
			{
				locationModel.addElement(rS.getString("LOCATION"));

			}
			prepStatement.close();
			rS.close();

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		jList_location.setModel(locationModel);

		MouseListener mouseListener = new MouseAdapter()
		{
			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 1)
				{
					selectedLocationList = (ArrayList<String>) jList_location.getSelectedValuesList();
				}
			}
		};
		jList_location.addMouseListener(mouseListener);
		//}
	}

	private void populateResults(ActionEvent evt) {
		ResultSet rS = null;
		System.out.println(resultQuery);

		try
		{
			prepStatement = con.prepareStatement(resultQuery);
			rS =prepStatement.executeQuery(resultQuery);

			resultModel.clear();
			resultModel.addElement("TITLE - Genre - YEAR - COUNTRY - FILMING LOCATION - AVG RATING - AVG NUM OF REVIEWS");
			while(rS.next())
			{
				resultModel.addElement(rS.getString("TITLE") + " - "+ rS.getString("GENRE") + " - " + rS.getString("MYEAR")+ " - " + rS.getString("COUNTRY")+ " - " + rS.getString("LOCATION" ) + " - " + rS.getString("AVERAGERATING") + " - " + rS.getString("AVERAGEREVIEWS") );
			}

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		jList_result.setModel(resultModel);
	}

	public void filterTags(ActionEvent evt){
		String bAttr = "";

		if(comboBox_AndOr.getSelectedIndex()==1)
		{
			bAttr = "INTERSECT";
		}
		else
		{
			if(comboBox_AndOr.getSelectedIndex()==0 || comboBox_AndOr.getSelectedIndex()==2)
			{
				bAttr = "UNION";
			}
		}
		String val = "";

		if(comboBox_tagWeight.getSelectedIndex() == 0) {
			val = "=";
		}
		else if(comboBox_tagWeight.getSelectedIndex() == 1) {
			val = ">";
		}
		else {
			val = "<";
		}

		if(selectedTagsList.size() == 0)
		{
			int start = 0;
			int end = jList_tags.getModel().getSize()-1;
			jList_tags.setSelectionInterval(start, end);
			selectedTagsList = (ArrayList<String>) jList_tags.getSelectedValuesList();
		}

		String oldResult = "";
		int k;
		for(k=0; k< selectedLocationList.size()-1; k++)
		{
			oldResult += "SELECT DISTINCT MR.MOVIEID\nFROM MOVIE MR, MOVIE_LOCATIONS ML\nWHERE ML.MOVIEID = MR.MOVIEID AND ML.LOCATION = '"+ selectedLocationList.get(k)+"' AND MR.MOVIEID IN("+ countryQuery +") \n"+bAttr+"\n";
		}
		oldResult += "SELECT DISTINCT MR.MOVIEID\nFROM MOVIE MR, MOVIE_LOCATIONS ML\nWHERE ML.MOVIEID = MR.MOVIEID AND ML.LOCATION = '"+ selectedLocationList.get(k)+"' AND MR.MOVIEID IN("+ countryQuery +") \n";

		resultQuery = "";
		int i;
		if(tagWeight.length() > 0) {
			for(i = 0; i < selectedTagsList.size() - 1; i++){
				resultQuery += "SELECT DISTINCT MR2.TITLE, MGR.GENRE, MR2.MYEAR, MCR.COUNTRY, MLR.LOCATION, (MR2.RTALLCRITICSRATING + MR2.RTTOPCRITICRATING + MR2.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, (MR2.RTALLCRITICSNUMREVIEWS + MR2.RTTOPCRICTICNUMREVIEWS + MR2.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR2, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR, TAGS T, MOVIE_TAGS MT\nWHERE MR2.MOVIEID = MT.MOVIEID AND T.TAGID = MT.TAGID AND MR2.MOVIEID = MCR.MOVIEID AND MR2.MOVIEID = MLR.MOVIEID AND T.VALUE = '" + selectedTagsList.get(i) + "' AND MT.TAGWEIGHT " + val + " '" + tagWeight + "' AND MR2.MOVIEID IN("+oldResult+") \n"+bAttr+"\n";
			}
			resultQuery += "SELECT DISTINCT MR2.TITLE, MGR.GENRE, MR2.MYEAR, MCR.COUNTRY, MLR.LOCATION, (MR2.RTALLCRITICSRATING + MR2.RTTOPCRITICRATING + MR2.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, (MR2.RTALLCRITICSNUMREVIEWS + MR2.RTTOPCRICTICNUMREVIEWS + MR2.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR2, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR, TAGS T, MOVIE_TAGS MT\nWHERE MR2.MOVIEID = MT.MOVIEID AND T.TAGID = MT.TAGID AND MR2.MOVIEID = MCR.MOVIEID AND MR2.MOVIEID = MLR.MOVIEID AND T.VALUE = '" + selectedTagsList.get(i) + "' AND MT.TAGWEIGHT " + val + " '" + tagWeight + "' AND MR2.MOVIEID IN("+oldResult+")";
		}
		else {
			for(i = 0; i < selectedTagsList.size() - 1; i++){
				resultQuery += "SELECT DISTINCT MR2.TITLE, MGR.GENRE, MR2.MYEAR, MCR.COUNTRY, MLR.LOCATION, (MR2.RTALLCRITICSRATING + MR2.RTTOPCRITICRATING + MR2.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, (MR2.RTALLCRITICSNUMREVIEWS + MR2.RTTOPCRICTICNUMREVIEWS + MR2.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR2, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR, TAGS T, MOVIE_TAGS MT\nWHERE MR2.MOVIEID = MT.MOVIEID AND T.TAGID = MT.TAGID AND MR2.MOVIEID = MCR.MOVIEID AND MR2.MOVIEID = MLR.MOVIEID AND T.VALUE = '" + selectedTagsList.get(i) + "' AND MR2.MOVIEID IN("+oldResult+") \n"+bAttr+"\n";
			}
			resultQuery += "SELECT DISTINCT MR2.TITLE, MGR.GENRE, MR2.MYEAR, MCR.COUNTRY, MLR.LOCATION, (MR2.RTALLCRITICSRATING + MR2.RTTOPCRITICRATING + MR2.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, (MR2.RTALLCRITICSNUMREVIEWS + MR2.RTTOPCRICTICNUMREVIEWS + MR2.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR2, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR, TAGS T, MOVIE_TAGS MT\nWHERE MR2.MOVIEID = MT.MOVIEID AND T.TAGID = MT.TAGID AND MR2.MOVIEID = MCR.MOVIEID AND MR2.MOVIEID = MLR.MOVIEID AND T.VALUE = '" + selectedTagsList.get(i) + "' AND MR2.MOVIEID IN("+oldResult+")";
		}
		JTextArea_queryDisplay.setText(resultQuery);
	}

	private void populateTags(ActionEvent evt) {
		// TODO Auto-generated method stub

//  		if(actorList.size()!=0)
//  		{

		String rate = "";

		if(comboBox_Stars.getSelectedIndex() == 0) {
			rate = "=";
		}
		else if(comboBox_Stars.getSelectedIndex() == 1) {
			rate = "<";
		}
		else if(comboBox_Stars.getSelectedIndex() == 2) {
			rate = ">";
		}
		else if(comboBox_Stars.getSelectedIndex() == 3) {
			rate = "<=";
		}
		else {
			rate = ">=";
		}

		String rev = "";
		if(comboBox_Reviews.getSelectedIndex() == 0) {
			rev = "=";
		}
		else if(comboBox_Reviews.getSelectedIndex() == 1) {
			rev = "<";
		}
		else if(comboBox_Reviews.getSelectedIndex() == 2) {
			rev = ">";
		}
		else if(comboBox_Reviews.getSelectedIndex() == 3) {
			rev = "<=";
		}
		else {
			rev = ">=";
		}
		String bAttr = "";

		if(comboBox_AndOr.getSelectedIndex()==1)
		{
			bAttr = "INTERSECT";
		}
		else
		{
			if(comboBox_AndOr.getSelectedIndex()==0 || comboBox_AndOr.getSelectedIndex()==2)
			{
				bAttr = "UNION";
			}
		}

		if(selectedLocationList.size() == 0)
		{
			int start = 0;
			int end = jList_location.getModel().getSize()-1;
			jList_location.setSelectionInterval(start, end);
			selectedLocationList = (ArrayList<String>) jList_location.getSelectedValuesList();
			//countryJList.addMouseListener(mouseListener);
		}

		String location = "";
		countryQuery = "";
		tagsResultQuery = "";
		int i;
		genreQuery = "";
		if(yearFrom.length() == 4 && yearTo.length() == 4 && stars.length() == 1 && reviews.length() > 0) {
			for(i=0; i< selectedGenreList.size()-1; i++)
			{
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSNUMREVIEWS " + rev + " '" + reviews + "' AND M.RTALLCRITICSRATING " + rate + " '" + stars + "' AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'\n"+bAttr+"\n";
			}
			//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
			genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSNUMREVIEWS " + rev + " '" + reviews + "' AND M.RTALLCRITICSRATING " + rate + " '" + stars + "' AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
		}
		else if(yearFrom.length() == 4 && yearTo.length() == 4 && stars.length() == 1) {
			for(i=0; i< selectedGenreList.size()-1; i++)
			{
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSRATING " + rate + " '" + stars + "' AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'\n"+bAttr+"\n";
			}
			//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
			genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSRATING " + rate + " '" + stars + "' AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
		}
		else if(yearFrom.length() == 4 && yearTo.length() == 4 && reviews.length() > 0) {
			for(i=0; i< selectedGenreList.size()-1; i++)
			{
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSNUMREVIEWS " + rev + " '" + reviews + "' AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'\n"+bAttr+"\n";
			}
			//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
			genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSNUMREVIEWS " + rev + " '" + reviews + "' AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
		}
		else if(stars.length() == 1 && reviews.length() > 0) {
			for(i=0; i< selectedGenreList.size()-1; i++)
			{
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSNUMREVIEWS " + rev + " '" + reviews + "' AND M.RTALLCRITICSRATING " + rate + " '" + stars + "' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'"+ "\n"+bAttr+"\n";
			}
			//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
			genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSNUMREVIEWS " + rev + " '" + reviews + "' AND M.RTALLCRITICSRATING " + rate + " '" + stars + "' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
		}

		else if(stars.length() == 1){
			for(i=0; i< selectedGenreList.size()-1; i++)
			{
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSRATING " + rate + " '" + stars + "' AND MG.GENRE = '"+ selectedGenreList.get(i)+ "'\n"+bAttr+"\n";
			}
			//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
			genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSRATING " + rate + " '" + stars + "' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
		}
		else if(reviews.length() > 0){
			for(i=0; i< selectedGenreList.size()-1; i++)
			{
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSNUMREVIEWS " + rev + " '" + reviews + "' AND MG.GENRE = '"+ selectedGenreList.get(i) + "'\n"+bAttr+"\n";
			}
			//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
			genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.RTALLCRITICSNUMREVIEWS " + rev + " '" + reviews + "' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
		} else if(yearFrom.length() == 4 && yearTo.length() == 4) {
			for(i=0; i< selectedGenreList.size()-1; i++)
			{
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'\n"+bAttr+"\n";
			}
			//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
			genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+ yearTo +"' AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
		}
		else {
			for(i=0; i< selectedGenreList.size()-1; i++)
			{
				//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'\n"+bAttr+"\n";
				genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND MG.GENRE = '"+ selectedGenreList.get(i)+"'\n"+bAttr+"\n";
			}
			//countryResultQuery += "SELECT DISTINCT MC.COUNTRY\nFROM MOVIE_GENRES MG, MOVIES M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND M.MYEAR >='"+ yearFrom +"' AND M.MYEAR <='"+yearTo+"' AND MG.GENRE = '"+selectedGenreList.get(i)+"'";
			genreQuery += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIE M, MOVIE_COUNTRIES MC\nWHERE M.MOVIEID=MG.MOVIEID AND M.MOVIEID=MC.MOVIEID AND MG.GENRE = '"+ selectedGenreList.get(i)+"'";
		}
		int j = 0;
		for(j=0; j< selectedCountryList.size()-1; j++)
		{
			location += "SELECT DISTINCT ML.LOCATION \nFROM MOVIE_LOCATIONS ML, MOVIE_COUNTRIES MMC\nWHERE ML.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+ selectedCountryList.get(j)+"' AND ML.MOVIEID IN("+ genreQuery +")"+"\n"+bAttr+"\n";
			//director += "SELECT DISTINCT D.DIRECTORNAME\nFROM MOVIE_DIRECTORS MD, MOVIES MM\nWHERE MM.MOVIEID=D.MOVIEID AND MM.MOVIEID IN("+genreQuery+")";
			countryQuery += "SELECT DISTINCT ML.MOVIEID\nFROM MOVIE_LOCATIONS ML, MOVIE_COUNTRIES MMC\nWHERE ML.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+ selectedCountryList.get(j)+"' AND ML.MOVIEID IN("+ genreQuery +")"+"\n"+bAttr+"\n";
		}

		location += "SELECT DISTINCT ML.MOVIEID\nFROM MOVIE_LOCATIONS ML, MOVIE_COUNTRIES MMC\nWHERE ML.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+ selectedCountryList.get(j)+"' AND ML.MOVIEID IN("+ genreQuery +")"+"\n";
		countryQuery += "SELECT DISTINCT ML.MOVIEID\nFROM MOVIE_LOCATIONS ML, MOVIE_COUNTRIES MMC\nWHERE ML.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+ selectedCountryList.get(j)+"' AND ML.MOVIEID IN("+ genreQuery +")"+"\n";

		//Genre Within attributes
		int k = 0;
		for(k=0; k< selectedLocationList.size()-1; k++)
		{
			tagsResultQuery += "SELECT DISTINCT T.VALUE\nFROM TAGS T, MOVIE_TAGS MT, MOVIE_LOCATIONS ML\nWHERE ML.MOVIEID = MT.MOVIEID AND T.TAGID = MT.TAGID AND ML.LOCATION = '"+ selectedLocationList.get(k)+"' AND MT.MOVIEID IN("+ countryQuery +") \n"+bAttr+"\n";
		}
		tagsResultQuery += "SELECT DISTINCT T.VALUE\nFROM TAGS T, MOVIE_TAGS MT, MOVIE_LOCATIONS ML\nWHERE ML.MOVIEID = MT.MOVIEID AND T.TAGID = MT.TAGID AND ML.LOCATION = '"+ selectedLocationList.get(k)+"' AND MT.MOVIEID IN("+ countryQuery +") \n";

		System.out.println(tagsResultQuery);
		String oldResult = resultQuery;
		resultQuery = "";
		for(k=0; k< selectedLocationList.size()-1; k++)
		{
			resultQuery += "SELECT DISTINCT MR.TITLE, MGR.GENRE, MR.MYEAR, MCR.COUNTRY, MLR.LOCATION, (MR.RTALLCRITICSRATING + MR.RTTOPCRITICRATING + MR.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, (MR.RTALLCRITICSNUMREVIEWS + MR.RTTOPCRICTICNUMREVIEWS + MR.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR\nWHERE MR.MOVIEID = MCR.MOVIEID AND MR.MOVIEID = MLR.MOVIEID AND MLR.LOCATION = '"+ selectedLocationList.get(k)+"' AND MR.MOVIEID IN("+ countryQuery +") \n"+bAttr+"\n";
		}
		resultQuery += "SELECT DISTINCT MR.TITLE, MGR.GENRE, MR.MYEAR, MCR.COUNTRY, MLR.LOCATION, (MR.RTALLCRITICSRATING + MR.RTTOPCRITICRATING + MR.RTAUDIENCERATING) / 3.0 AS AVERAGERATING, (MR.RTALLCRITICSNUMREVIEWS + MR.RTTOPCRICTICNUMREVIEWS + MR.RTAUDIENCENUMRATING) / 3.0 AS AVERAGEREVIEWS\nFROM MOVIE MR, MOVIE_GENRES MGR, MOVIE_COUNTRIES MCR, MOVIE_LOCATIONS MLR\nWHERE MR.MOVIEID = MCR.MOVIEID AND MR.MOVIEID = MLR.MOVIEID AND MLR.LOCATION = '"+ selectedLocationList.get(k)+"' AND MR.MOVIEID IN("+ countryQuery +") \n";
		JTextArea_queryDisplay.setText(resultQuery);
		//resultQuery = "SELECT DISTINCT MR.TITLE, MR.MYEAR, MG.GENRE\n FROM MOVIE MR, MOVIE_TAGS MC, MOVIE_GENRES MG \n WHERE MR.MOVIEID = MC.MOVIEID AND MR.MOVIEID = MG.MOVIEID AND MC.TAGID IN ( SELECT TG.TAGID\nFROM TAGS TG\nWHERE TG.VALUE IN ( " + tagsResultQuery + ")) \n";
		ResultSet rS = null;

		try
		{
			prepStatement = con.prepareStatement(tagsResultQuery);
			rS =prepStatement.executeQuery(tagsResultQuery);

			tagModel.clear();
			while(rS.next())
			{
				tagModel.addElement(rS.getString("VALUE"));
			}
			prepStatement.close();
			rS.close();



		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		jList_tags.setModel(tagModel);

		MouseListener mouseListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 1)
				{
					selectedTagsList = (ArrayList<String>) jList_tags.getSelectedValuesList();
				}
			}
		};
		jList_tags.addMouseListener(mouseListener);
		//}
	}
}

