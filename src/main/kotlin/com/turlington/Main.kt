package com.turlington

import java.nio.file.Files
import java.nio.file.Paths

object Main {

    @JvmStatic fun main(args: Array<String>) {
        val emailInfo = getEmailInfo()
        //val wyzantInfo = getWyzantInfo()

        val jobSites: MutableSet<JobSite> = HashSet()
        if (args.contains("edjoin")) jobSites.add(EdJoin())
        if (args.contains("blizzard")) jobSites.add(Blizzard())
        JobChecker(jobSites, FileLoader(), Notify(emailInfo)).checkSites()
    }

    private fun getWyzantInfo(): WyzantInfo {
        val wyzantInfoPath = Paths.get("wyzantInfo.txt")
        if (Files.exists(wyzantInfoPath)) {
            val lines = Files.readAllLines(wyzantInfoPath)
            if (lines.size == 2) return WyzantInfo(lines[0], lines[1])
        }
        System.err.println("Need a wyzantInfo.txt in this format:")
        System.err.println("wyzantUserName")
        System.err.println("wyzantPassword")
        System.exit(1)
        return WyzantInfo("", "")
    }

    private fun getEmailInfo(): EmailInfo {
        val emailInfoPath = Paths.get("emailInfo.txt")
        if (Files.exists(emailInfoPath)) {
            val lines = Files.readAllLines(emailInfoPath)
            if (lines.size == 3) return EmailInfo(lines[0], lines[1], lines[2])
        }
        System.err.println("Need an emailInfo.txt in this format:")
        System.err.println("fromEmailAddress (must be gmail)")
        System.err.println("passwordForFromEmail")
        System.err.println("toEmailAddress")
        System.exit(1)
        return EmailInfo("", "", "")
    }

    internal fun waitMillis(milliseconds: Long) {
        try {
            Thread.sleep(milliseconds)
        } catch (e: InterruptedException) {
            //Who cares
        }
    }
}

data class EmailInfo(val fromEmail: String, val password: String, val toEmail: String)
