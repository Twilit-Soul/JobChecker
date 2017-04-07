package com.turlington;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
    	String fromEmail = null, password = null, toEmail = null;
    	Path emailInfoPath = Paths.get("emailInfo.txt");
    	if (Files.exists(emailInfoPath)) {
			List<String> lines = Files.readAllLines(emailInfoPath);
			if (lines.size() != 3) {
				explainEmailInfoAndQuit();
			} else {
				fromEmail = lines.get(0);
				password = lines.get(1);
				toEmail = lines.get(2);
			}
		} else {
			explainEmailInfoAndQuit();
		}
		Path wyzantInfoPath = Paths.get("wyzantInfo.txt");
    	String wyzantUsername = null, wyzantPassword = null;
		if (Files.exists(wyzantInfoPath)) {
			List<String> lines = Files.readAllLines(wyzantInfoPath);
			if (lines.size() != 2) {
				explainWyzantInfoAndQuit();
			} else {
				wyzantUsername = lines.get(0);
				wyzantPassword = lines.get(1);
			}
		}

        final Set<JobSite> jobSites = new HashSet<>();
        //jobSites.add(new EdJoin(506));
        //jobSites.add(new EdJoin(517));
        jobSites.add(new WyzantJob(wyzantUsername, wyzantPassword));
        jobSites.add(new Indeed("Java", "Irvine", "fulltime"));
        final iJobChecker jobChecker = new JobChecker(jobSites, new FileLoader(), new Notify(fromEmail, password, toEmail));
        jobChecker.checkSites();
    }

	static void explainWyzantInfoAndQuit() {
		System.err.println("Need a wyzantInfo.txt in this format:");
		System.err.println("wyzantUserName");
		System.err.println("wyzantPassword");
		System.exit(1);
	}

    private static void explainEmailInfoAndQuit() {
		System.err.println("Need an emailInfo.txt in this format:");
		System.err.println("fromEmailAddress (must be gmail)");
		System.err.println("passwordForFromEmail");
		System.err.println("toEmailAddress");
		System.exit(1);
	}

    static void waitMillis(long milliseconds) {
    	try {
    		Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
    		//Who cares
		}
	}
}
