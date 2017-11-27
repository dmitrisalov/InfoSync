import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.Account;
import com.dropbox.core.v2.users.FullAccount;

import java.io.File;
import java.util.Scanner;

/**
 * The Main class of InfoSync. Contains the running logic of the command line application.
 *
 * @author Dmitri Salov
 */
public class Main {
    public static final String TOKEN_PATH = "token.txt";
    public static final String VERSION = "1.0";
    public static final String CLIENT_ID = "infosync/" + VERSION;

    /**
     * Returns whether or not a file at a specific path exists.
     *
     * @param filePath
     * @return Whether or not the file exists.
     */
    private static boolean fileExists(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            return true;
        }

        return false;
    }

    /**
     * Reads the token file and returns the access token.
     *
     * @return Access token.
     */
    private static String getAccessToken() {
        DbxAuthInfo authInfo;

        try {
            authInfo = DbxAuthInfo.Reader.readFromFile(TOKEN_PATH);
        }
        catch (Exception ex) {
            System.out.println("Error: could not read token from file");
            System.exit(1);
            return null;
        }

        return authInfo.getAccessToken();
    }

    public static void main(String[] args) {
        System.out.println("InfoSync, v" + VERSION);
        System.out.println("Created by Dmitri Salov, 2017");
        System.out.println("-----------------------------");

        if (!fileExists(TOKEN_PATH)) {
            FirstLaunch.obtainTokenFile();
        }

        //Creating the client used for API calls.
        DbxRequestConfig requestConfig = new DbxRequestConfig(CLIENT_ID);
        DbxClientV2 client = new DbxClientV2(requestConfig, getAccessToken());

        //Getting user's account info to print out their name.
        FullAccount account;

        try {
            account = client.users().getCurrentAccount();
        }
        catch (Exception ex) {
            System.out.println("Error: could not obtain account info");
            System.exit(1);
            return;
        }

        System.out.println("User: " + account.getName().getDisplayName());

        //Scanner used to take in user input commands.
        Scanner scan = new Scanner(System.in);
        String command;

        //Main loop to handle user input commands.
        while (true) {
            System.out.print("(InfoSync) ");
            command = scan.nextLine().trim();

            if (command.equals("exit")) {
                break;
            }
            else {
                System.out.println("Invalid command: \"" + command + "\"");
            }
        }
    }
}