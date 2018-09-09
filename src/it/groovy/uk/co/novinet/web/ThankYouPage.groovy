package uk.co.novinet.web

import geb.Page

class ThankYouPage extends Page {

    static url = "http://localhost:8484/thankYou"

    static at = { title == "Loan Charge Action Group Fighting Fund Company | Thank You" }

    static content = {
        paymentReference { $("#paymentReference") }
    }
}
