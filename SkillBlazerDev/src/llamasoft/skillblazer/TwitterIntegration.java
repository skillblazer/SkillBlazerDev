/************************************************************
 * Application Name: skillblazer
 * File Name: TwitterIntegration.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/8/2018
 * 
 * Description:
 * 
 * This class represents the integration of Twitter into the
 * skillblazer application that will allow the user to access
 * Twitter and tweet out accomplishments that they have met
 * or when they have completed a task. The functionality 
 * will be available to them from the start using the Options
 * menu and selecting the "Tweet" button.
 ***********************************************************/

package llamasoft.skillblazer;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterIntegration {
	
	/*
	 * STILL REQUIRES A LOT OF WORK
	 * 
	 * Includes main method solely for testing purposes.
	 * 
	 * Might integrate separate FX window here for this
	 * Confirm with GUI design first!!!!!
	 */
	
	//
	private final static String CONSUMER_KEY = "34d1J2WyuSLYbvu4zeGLVmGRv";
	//
	private final static String CONSUMER_KEY_SECRET = "rmHY2LIUzvMVjGn2AGMx54rAGPx9zKSzlNpY0DSNl05mmqPjzW";
	
	//private Stage window;
	//private Button button;
	private boolean isEnabled; //

	/*
	 * @Override public void start(Stage primaryStage) throws Exception {
	 * 
	 * }
	 */

	/*
	 * 
	 */
	public void start() throws TwitterException, IOException {

		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		RequestToken requestToken = twitter.getOAuthRequestToken();
		System.out.println("Authorization URL: \n" + requestToken.getAuthorizationURL());

		AccessToken accessToken = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == accessToken) {
			try {
				System.out.print("Input PIN here: ");
				String pin = br.readLine();

				accessToken = twitter.getOAuthAccessToken(requestToken, pin);

			} catch (TwitterException te) {

				System.out.println("Failed to get access token, caused by: " + te.getMessage());

				System.out.println("Retry input PIN");

			}
		}

		System.out.println("Access Token: " + accessToken.getToken());
		System.out.println("Access Token Secret: " + accessToken.getTokenSecret());

		twitter.updateStatus("First Tweet using skillblazer application #LlamasForLife");

	}
	
	/*
	 * 
	 */
	public void enableTwitterIntegration(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public static void main(String[] args) throws Exception {
		new TwitterIntegration().start();// run the Twitter client
	}
}
