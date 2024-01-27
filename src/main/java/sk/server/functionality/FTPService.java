package sk.server.functionality;

import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPService {
    private FTPClient ftpClient;

    public FTPService(String server, int port, String user, String pass) {
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        } catch (IOException e) {
            System.err.println("An error has been occurred trying to connect to the server: " + e.getMessage());
        }
    }

    public void uploadFile(String localFilePath, String remoteFilePath) {
        try (FileInputStream upload = new FileInputStream(localFilePath)) {
            ftpClient.storeFile(remoteFilePath, upload);
        } catch (IOException e) {
            System.err.println("An error occurred trying to upload the file from local path: " + localFilePath + " to remote path: " + remoteFilePath + ". Error: " + e.getMessage());
        }
    }

    public void downloadFile(String remoteFilePath, String localFilePath) {
        try (FileOutputStream download = new FileOutputStream(localFilePath)) {
            ftpClient.retrieveFile(remoteFilePath, download);
        } catch (IOException e) {
            System.err.println("An error occurred trying to download the file from remote path: " + remoteFilePath + " to local path: " + localFilePath + ". Error: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            System.err.println("An error has been occurred trying to disconnect from the server: " + e.getMessage());

        }
    }
}
