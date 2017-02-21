package com.turlington;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        final Set<JobSite> jobSites = new HashSet<>();
        jobSites.add(new EdJoin(506));
        jobSites.add(new EdJoin(517));
        final iJobChecker jobChecker = new JobChecker(jobSites, new FileLoader(), new Notify());
        jobChecker.checkSites();
    }
}
