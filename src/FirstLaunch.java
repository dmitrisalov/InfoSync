import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The class that contains the fields and methods required for authorizing the
 * user during their first launch of the application.
 *
 * @author Dmitri Salov
 */
public class FirstLaunch {
    private static final String APP_KEY = "l82kg1o906zsf11";
    //I am unaware of how to authorize the user without storing the app secret.
    private static final String APP_SECRET = "iy9lq5xv7apd7d5";

    private static DbxWebAuth webAuth;

    /**
     * Leads the user through the process of obtaining and saving their access
     * token for authorization.
     */
    public static void obtainTokenFile() {
        //Sets up a request configuration to be used for requests.
        String authURL = getAuthorizationURL();

        System.out.println("First launch authorization:");
        System.out.println("1. Visit this URL: " + authURL);
        System.out.println("2. If prompted, log in. Click \"Allow\"");
        System.out.println("3. Copy the access token and paste it below:");
        System.out.print("(InfoSync) ");

        //Obtaining the access token from user input.
        Scanner scan = new Scanner(System.in);
        String accessToken = scan.nextLine().trim();

        /*This finishes the authorization and is required to get the correct
            access token.*/
        DbxAuthFinish authFinish;

        try {
            authFinish = webAuth.finishFromCode(accessToken);
        }
        catch (Exception ex) {
            System.out.println("Error: access token invalid");
            System.exit(1);
            return;
        }
        /*The access token stored in authFinish is different than what the
            user obtained through the URL.*/
        accessToken = authFinish.getAccessToken();

        writeTokenToFile(accessToken, Main.TOKEN_PATH);
    }

    /**
     * Returns a URL for the user to visit to obtain an access token.
     *
     * @return A URL.
     */
    private static String getAuthorizationURL() {
        //Obtaining the app info to create an authorization request.
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        //Creating a configuration used for creating requests.
        DbxRequestConfig requestConfig = new DbxRequestConfig(Main.CLIENT_ID);
        //Creating the web authorization for requesting an access token.
        webAuth = new DbxWebAuth(requestConfig, appInfo);
        //Building a request for web authorization.
        DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder()
                .withNoRedirect().build();
        //The URL that the user must visit to obtain an access token.
        String authURL = webAuth.authorize(webAuthRequest);

        return authURL;
    }

    /**
     * Writes an access token to a file in JSON format.
     *
     * @param accessToken
     * @param filePath
     */
    private static void writeTokenToFile(String accessToken, String filePath) {
        PrintWriter file;

        try {
            file = new PrintWriter(filePath);
        }
        catch (Exception ex) {
            System.out.println("Error: could not write to file");
            System.exit(1);
            return;
        }

        file.println("{\"access_token\" : \"" + accessToken + "\"}");
        file.close();
        System.out.println("Access token saved.\n");
    }
}
