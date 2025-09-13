package com.example.mailapp

class EmailFetcher {
    companion object {
        private val senders = listOf(
            "Dahlia Cline",
            "Kevin Miranda",
            "Kaya Austin",
            "Laila Calderon",
            "Marquise Rhodes",
            "Fletcher Patel",
            "Luz Barron",
            "Kamren Dudley",
            "Jairo Foster",
            "Lilah Sandoval",
            "Ansley Blake",
            "Slade Sawyer",
            "Jaelyn Holmes",
            "Phoenix Bright",
            "Ernesto Gould"
        )

        private val titles = listOf(
            "Welcome to Kotlin!",
            "Your Weekly Newsletter",
            "Class Reminder: Android 101",
            "Don't Miss Out on This Deal",
            "Upcoming Maintenance Alert",
            "You're Invited to an Event!",
            "Security Alert: New Login",
            "Team Standup Summary",
            "Tips to Boost Productivity",
            "Course Update Available",
            "Feedback Requested",
            "Your Code Review is Ready",
            "Important: Update Required",
            "Thanks for Joining Us!",
            "Survey: We Value Your Input"
        )

        private val summaries = listOf(
            "We're excited to have you learn Kotlin. Here's what you need to get started.",
            "Here are the latest updates, tips, and resources for the week.",
            "Reminder: Your Android class starts tomorrow at 10 AM.",
            "Hurry! This exclusive offer expires tonight at midnight.",
            "Scheduled maintenance will occur this weekend. Plan accordingly.",
            "Join us for a special webinar on advanced Android practices.",
            "We detected a new login to your account. Please verify.",
            "Today’s standup covered key blockers and next steps.",
            "Feeling stuck? These 5 tips will help you code faster.",
            "A new version of your course has been released. Check it out.",
            "Please take 2 minutes to complete this feedback form.",
            "Your PR has been reviewed. See comments and follow-ups.",
            "Please update your app before Sep 15 to avoid disruptions.",
            "Thank you for being a part of our Android course community!",
            "Your feedback helps us improve. Let us know how we're doing."
        )

        private val dates = listOf(
            "Sep 1", "Sep 2", "Sep 3", "Sep 4", "Sep 5",
            "Sep 6", "Sep 7", "Sep 8", "Sep 9", "Sep 10",
            "Sep 11", "Sep 12", "Sep 13", "Sep 14", "Sep 15"
        )

        fun getEmails(): MutableList<Email> {
            val emails = mutableListOf<Email>()
            for (i in 0 until 10) {
                val email = Email(
                    sender = senders[i],
                    title = titles[i],
                    summary = summaries[i],
                    date = dates[i],
                    isUnread = (i % 2 == 0)
                )
                emails.add(email)
            }
            return emails
        }

        fun getNext5Emails(): MutableList<Email> {
            val newEmails = mutableListOf<Email>()
            for (i in 10 until 15) {
                val email = Email(
                    sender = senders[i],
                    title = titles[i],
                    summary = summaries[i],
                    date = dates[i],
                    isUnread = true // or random for more realism
                )
                newEmails.add(email)
            }
            return newEmails
        }
    }
}
