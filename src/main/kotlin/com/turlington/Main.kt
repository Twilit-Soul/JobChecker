package com.turlington

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object Main {

    @JvmStatic fun main(args: Array<String>) {
        var fromEmail: String? = null
        var password: String? = null
        var toEmail: String? = null
        val emailInfoPath = Paths.get("emailInfo.txt")
        if (Files.exists(emailInfoPath)) {
            val lines = Files.readAllLines(emailInfoPath)
            if (lines.size != 3) {
                explainEmailInfoAndQuit()
            } else {
                fromEmail = lines[0]
                password = lines[1]
                toEmail = lines[2]
            }
        } else {
            explainEmailInfoAndQuit()
        }
        //TODO: move this to Wyzant constructor?
        val wyzantInfoPath = Paths.get("wyzantInfo.txt")
        var wyzantUsername: String? = null
        var wyzantPassword: String? = null
        if (Files.exists(wyzantInfoPath)) {
            val lines = Files.readAllLines(wyzantInfoPath)
            if (lines.size != 2) {
                explainWyzantInfoAndQuit()
            } else {
                wyzantUsername = lines[0]
                wyzantPassword = lines[1]
            }
        }

        val jobSites = HashSet<JobSite>()
        //jobSites.add(new EdJoin(506));
        //jobSites.add(new EdJoin(517));
        //jobSites.add(new WyzantJob(wyzantUsername, wyzantPassword));
        //jobSites.add(new Indeed("Java", "Irvine", "fulltime"));
        jobSites.add(EdJoin())
        val jobChecker = JobChecker(jobSites, FileLoader(), Notify(fromEmail!!, password!!, toEmail!!))
        jobChecker.checkSites()
    }

    internal fun explainWyzantInfoAndQuit() {
        System.err.println("Need a wyzantInfo.txt in this format:")
        System.err.println("wyzantUserName")
        System.err.println("wyzantPassword")
        System.exit(1)
    }

    private fun explainEmailInfoAndQuit() {
        System.err.println("Need an emailInfo.txt in this format:")
        System.err.println("fromEmailAddress (must be gmail)")
        System.err.println("passwordForFromEmail")
        System.err.println("toEmailAddress")
        System.exit(1)
    }

    internal fun waitMillis(milliseconds: Long) {
        try {
            Thread.sleep(milliseconds)
        } catch (e: InterruptedException) {
            //Who cares
        }
    }
}
