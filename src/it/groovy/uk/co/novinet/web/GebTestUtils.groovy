package uk.co.novinet.web

import geb.Browser
import org.openqa.selenium.Keys
import org.springframework.format.number.CurrencyStyleFormatter
import uk.co.novinet.rest.PaymentType
import uk.co.novinet.service.ContributionType

import java.text.SimpleDateFormat

import static uk.co.novinet.e2e.TestUtils.getEmails
import static uk.co.novinet.e2e.TestUtils.getEmails
import static uk.co.novinet.e2e.TestUtils.getEmails

class GebTestUtils {
    static boolean switchToGuestVerificationTabIfNecessaryAndAssertGridHasNRows(Browser browser, int expectedNumberOfRows) {
        if (expectedNumberOfRows == 0) {
            assert browser.guestsAwaitingVerificationTab.text() == "Guests Awaiting Verification"
        } else {
            assert browser.guestsAwaitingVerificationTab.text() == "Guests Awaiting Verification *"
        }

        if (!browser.verificationGrid.displayed) {
            browser.guestsAwaitingVerificationTab.click()
        }

        browser.waitFor { browser.verificationGridRows.size() == expectedNumberOfRows + 1 }

        return true
    }

    static boolean switchToMemberTabIfNecessaryAndAssertGridHasNRows(Browser browser, int expectedNumberOfRows) {
        if (!browser.memberGrid.displayed) {
            browser.waitFor { browser.memberTab.click() }
        }

        browser.waitFor { browser.memberGridRows.size() == expectedNumberOfRows + 1 }

        return true
    }

    static boolean switchToPaymentsTabIfNecessaryAndAssertGridHasNRows(Browser browser, int expectedNumberOfRows) {
        if (!browser.paymentsTab.displayed) {
            browser.waitFor { browser.paymentsTab.click() }
        }

        browser.waitFor { browser.paymentsGridRows.size() == expectedNumberOfRows + 1 }

        return true
    }

    static void enterCardDetails(Browser browser, String creditCardNumber, String expiryMMYY, String cv2, String postalCode) {
        browser.withFrame(browser.$("iframe")[0]) {
            browser.page.creditCardInput = creditCardNumber
            browser.page.expiryDateInput = expiryMMYY
            browser.page.c2vInput = cv2
            browser.page.paymentFormPostalCodeInput = postalCode
        }
    }

    static boolean checkboxValue(Object checkboxElement) {
        return checkboxElement.value() == "on"
    }

    static void verifyInitialPaymentFormQuestionsDisplayed(Browser browser) {
        browser.waitFor { browser.page.paymentDeclinedSection.displayed == false }
        browser.waitFor { browser.page.acceptTermsAndConditionsButton.attr("disabled") == "true" }
        browser.waitFor { browser.page.paymentFormSection.displayed == true }
        browser.waitFor { browser.page.existingLcagAccountYes.displayed == false }
        browser.waitFor { browser.page.existingLcagAccountNo.displayed == false }
        browser.waitFor { browser.page.existingLcagAccountAnonymous.displayed == false }
        browser.waitFor { browser.page.existingLcagAccountYes.value() == null }
        browser.waitFor { browser.page.existingLcagAccountNo.value() == null }
        browser.waitFor { browser.page.existingLcagAccountAnonymous.value() == null }
        browser.waitFor { browser.page.payNowButton.displayed == false }
        browser.waitFor { browser.page.amountInput.displayed == true }
        browser.waitFor { browser.page.donationInfoSection.displayed == false }
        browser.waitFor { browser.page.usernameInput.displayed == false }
        browser.waitFor { browser.page.firstNameInput.displayed == false }
        browser.waitFor { browser.page.lastNameInput.displayed == false }
        browser.waitFor { browser.page.emailAddressInput.displayed == false }
        browser.waitFor { browser.page.newLcagJoinerInfoSection.displayed == false }
        browser.waitFor { browser.page.contributionAgreementInfoSection.displayed == false }
    }

    static void verifyHappyInitialPaymentFormState(Browser browser) {
        browser.waitFor { browser.page.paymentDeclinedSection.present == false }
        browser.waitFor { browser.page.termsAndConditionsSection.displayed == true }
        browser.waitFor { browser.page.acceptTermsAndConditionsButton.displayed == true }
        browser.waitFor { browser.page.paymentFormSection.displayed == false }
        browser.waitFor { browser.page.payNowButton.displayed == false }
    }

    static void anonymousPaymentCreditCardFormDisplayed(Browser browser) {
        browser.waitFor { browser.page.donationInfoSection.displayed == true }
        browser.waitFor { browser.page.usernameInput.displayed == false }
        browser.waitFor { browser.page.contributionAgreementInfoSection.displayed == false }
        browser.waitFor { browser.page.contributionTypeDonation.displayed == false }
        browser.waitFor { browser.page.contributionTypeContributionAgreement.displayed == false }
        browser.waitFor { browser.page.contributionTypeContributionAgreement.value() == null }
        browser.waitFor { browser.page.payNowButton.displayed == true }
    }

    static void existingLcagUserAccountPaymentCreditCardFormDisplayed(Browser browser) {
        browser.waitFor { browser.page.donationInfoSection.displayed == false }
        browser.waitFor { browser.page.usernameInput.displayed == true }
        browser.waitFor { browser.page.contributionAgreementInfoSection.displayed == false }
        browser.waitFor { browser.page.contributionTypeDonation.displayed == true }
        browser.waitFor { browser.page.contributionTypeContributionAgreement.displayed == true }
        browser.waitFor { browser.page.contributionTypeDonation.value() == null }
        browser.waitFor { browser.page.contributionTypeContributionAgreement.value() == null }
        browser.waitFor { browser.page.contributionTypeDonation.attr("disabled") == "" }
        browser.waitFor { browser.page.contributionTypeContributionAgreement.attr("disabled") == "" }
        browser.waitFor { browser.page.payNowButton.displayed == true }
    }

    static void newLcagUserAccountPaymentCreditCardFormDisplayed(Browser browser) {
        browser.waitFor { browser.page.donationInfoSection.displayed == false }
        browser.waitFor { browser.page.usernameInput.displayed == false }
        browser.waitFor { browser.page.firstNameInput.displayed == true }
        browser.waitFor { browser.page.lastNameInput.displayed == true }
        browser.waitFor { browser.page.emailAddressInput.displayed == true }
        browser.waitFor { browser.page.newLcagJoinerInfoSection.displayed == true }
        browser.waitFor { browser.page.contributionAgreementInfoSection.displayed == false }
        browser.waitFor { browser.page.contributionTypeDonation.displayed == true }
        browser.waitFor { browser.page.contributionTypeContributionAgreement.displayed == true }
        browser.waitFor { browser.page.contributionTypeDonation.value() == null }
        browser.waitFor { browser.page.contributionTypeContributionAgreement.value() == null }
        browser.waitFor { browser.page.contributionTypeDonation.attr("disabled") == "" }
        browser.waitFor { browser.page.contributionTypeContributionAgreement.attr("disabled") == "" }
        browser.waitFor { browser.page.payNowButton.displayed == true }
    }

    static void contributionAgreementAddressFieldsAreDisplayed(Browser browser, boolean displayed) {
        browser.waitFor { browser.page.addressLine1Input.displayed == displayed }
        browser.waitFor { browser.page.addressLine2Input.displayed == displayed }
        browser.waitFor { browser.page.cityInput.displayed == displayed }
        browser.waitFor { browser.page.postalCodeInput.displayed == displayed }
        browser.waitFor { browser.page.countryInput.displayed == displayed }
        browser.waitFor { browser.page.contributionAgreementInfoSection.displayed == displayed }
        browser.waitFor { browser.page.donationInfoSection.displayed == !displayed }
    }

    static boolean verifyInvoice(
            Browser browser,
            String reference,
            Date date,
            String paymentMethod,
            String recipientName,
            String recipientEmail,
            String contributionType,
            String netAmount,
            String vatPercentage,
            String vatAmount,
            String grossAmount,
            String vatNumber) {
        assert browser.page.reference.text() == reference
        assert browser.page.invoiceCreatedDate.text() == new SimpleDateFormat("dd MMM yyyy").format(date)
        assert browser.page.paymentReceivedDate.text() == new SimpleDateFormat("dd MMM yyyy").format(date)
        assert browser.page.paymentMethod.text() == paymentMethod
        assert browser.page.invoiceRecipientName.text() == recipientName
        assert browser.page.invoiceRecipientEmailAddress.text() == recipientEmail
        assert browser.page.contributionType.text() == contributionType
        assert browser.page.netAmount.text() == netAmount
        assert browser.page.vatPercentage.text() == vatPercentage
        assert browser.page.vatAmount.text() == vatAmount
        assert browser.page.grossAmount.text() == grossAmount
        assert browser.page.vatNumber.text() == "VAT number: " + vatNumber

        return true
    }

    static boolean verifyNoVatNumberInvoice(
            Browser browser,
            String reference,
            Date date,
            String paymentMethod,
            String recipientName,
            String recipientEmail,
            String contributionType,
            String grossAmount) {
        assert browser.page.reference.text() == reference
        assert browser.page.invoiceCreatedDate.text() == new SimpleDateFormat("dd MMM yyyy").format(date)
        assert browser.page.paymentReceivedDate.text() == new SimpleDateFormat("dd MMM yyyy").format(date)
        assert browser.page.paymentMethod.text() == paymentMethod
        assert browser.page.invoiceRecipientName.text() == recipientName
        assert browser.page.invoiceRecipientEmailAddress.text() == recipientEmail
        assert browser.page.contributionType.text() == contributionType
        assert browser.page.netAmount.text() == grossAmount
        assert browser.page.vatPercentage.empty == true
        assert browser.page.vatAmount.empty == true
        assert browser.page.grossAmount.text() == grossAmount
        assert browser.page.vatNumber.text() == "A VAT invoice will be issued once LCAG FFC has finalised VAT registration."
        return true
    }

    static void enterContributionAgreementAddressDetails(Browser browser, String firstName, String lastName, String emailAddress) {
        browser.page.firstNameInput = firstName
        browser.page.lastNameInput = lastName
        browser.page.emailAddressInput = emailAddress
        browser.page.addressLine1Input = "10 Some Street"
        browser.page.addressLine2Input = "Some Village"
        browser.page.cityInput = "Some City"
        browser.page.postalCodeInput = "Some Postcode"
        browser.page.countryInput = "Some Country"
    }

    static boolean verifyAttachment(String emailAddress, int attachmentIndex, int expectedNumberOfAttachments, String expectedFileName, int emailIndex = 0) {
        assert getEmails(emailAddress, "Inbox").get(emailIndex).getAttachments().size() == expectedNumberOfAttachments
        assert getEmails(emailAddress, "Inbox").get(emailIndex).getAttachments().get(attachmentIndex).getFilename().equals(expectedFileName)
        assert getEmails(emailAddress, "Inbox").get(emailIndex).getAttachments().get(attachmentIndex).getBytes().length > 0
        return true
    }

    static boolean verifyNoAttachments(String emailAddress, int index = 0) {
        return getEmails(emailAddress, "Inbox").get(index).getAttachments().size() == 0
    }

    static boolean verifyContributionAgreement(
            Browser browser,
            Date date,
            String name,
            String addressLine1,
            String addressLine2,
            String city,
            String postalCode,
            String country,
            String contributionAmount) {
        assert browser.page.contributionAgreementDate == new SimpleDateFormat("dd MMM yyyy").format(date)

        assert browser.page.addressLine1 == addressLine1
        assert browser.page.addressLine2 == addressLine2
        assert browser.page.city == city
        assert browser.page.postalCode == postalCode
        assert browser.page.country == country

        return true
    }

    static void driveToPaymentType(Browser browser, String paymentAmount, PaymentType paymentType, ContributionType contributionType = ContributionType.DONATION) {
        browser.go("http://localhost:8484")
        browser.waitFor { browser.at LcagFfcFormPage }
        verifyHappyInitialPaymentFormState(browser)
        browser.page.acceptTermsAndConditionsButton.click()
        verifyInitialPaymentFormQuestionsDisplayed(browser)
        browser.page.amountInput = paymentAmount
        browser.page.amountInput << Keys.TAB

        if (Double.parseDouble(paymentAmount) >= 600) {
            switch (contributionType) {
                case (ContributionType.CONTRIBUTION_AGREEMENT):
                    browser.page.contributionTypeContributionAgreement.click()
                    break
                case (ContributionType.DONATION):
                    browser.page.contributionTypeDonation.click()
                    break
            }
        }

        switch (paymentType) {
            case (PaymentType.NEW_LCAG_MEMBER):
                browser.page.existingLcagAccountNo.click()
                break
            case (PaymentType.EXISTING_LCAG_MEMBER):
                browser.page.existingLcagAccountYes.click()
                break
            case (PaymentType.ANONYMOUS):
                browser.page.existingLcagAccountAnonymous.click()
                anonymousPaymentCreditCardFormDisplayed(browser)
                return
        }




//        newLcagUserAccountPaymentCreditCardFormDisplayed(browser)
//        existingLcagUserAccountPaymentCreditCardFormDisplayed(browser)
//        anonymousPaymentCreditCardFormDisplayed(browser)
    }
}
