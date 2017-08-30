package com.turlington

import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator
import java.nio.file.Files
import java.nio.file.Paths

object Main {
    private val logger = Logger.getLogger(javaClass)

    @JvmStatic fun main(args: Array<String>) {
        PropertyConfigurator.configure("log4j.properties")
        logger.trace("Entering program.")
        val emailInfo = getEmailInfo()
        //val wyzantInfo = getWyzantInfo()

        val jobSites: MutableSet<JobSite> = HashSet()
        if (args.contains("edjoin")) {
            jobSites.add(EdJoin())
            logger.info("Added edjoin to job site list.")
        }
        if (args.contains("blizzard")) {
            jobSites.add(Blizzard())
            logger.info("Added blizzard to job site list.")
        }
        JobChecker(jobSites, FileLoader(), Notify(emailInfo)).checkSites()
    }

    private fun getWyzantInfo(): WyzantInfo {
        val wyzantInfoPath = Paths.get("wyzantInfo.txt")
        if (Files.exists(wyzantInfoPath)) {
            val lines = Files.readAllLines(wyzantInfoPath)
            if (lines.size == 2) return WyzantInfo(lines[0], lines[1])
        }
        logger.fatal("Need a wyzantInfo.txt in this format:\nwyzantUserName\nwyzantPassword")
        System.exit(1)
        return WyzantInfo("", "")
    }

    private fun getEmailInfo(): EmailInfo {
        val emailInfoPath = Paths.get("emailInfo.txt")
        if (Files.exists(emailInfoPath)) {
            val lines = Files.readAllLines(emailInfoPath)
            if (lines.size == 3) return EmailInfo(lines[0], lines[1], lines[2])
        }
        logger.fatal("Need an emailInfo.txt in this format:\nfromEmailAddress (must be gmail)\npasswordForFromEmail\ntoEmailAddress")
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
