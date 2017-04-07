package com.turlington;

/**
 * For testing right now.
 * Created by Mitchell on 7/18/2016.
 */
public class Notify implements iNotify {
	private final String fromEmail, password, toEmail;


	public Notify(String fromEmail, String password, String toEmail) {
		this.fromEmail = fromEmail;
		this.password = password;
		this.toEmail = toEmail;
	}

    @Override
    public void announce(JobListing jobListing) {
        EmailUtil.sendEmail(fromEmail, password, toEmail, jobListing);
    }
}
