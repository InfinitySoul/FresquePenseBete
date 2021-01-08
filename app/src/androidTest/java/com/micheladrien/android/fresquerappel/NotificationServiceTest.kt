package com.micheladrien.android.fresquerappel

import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.*
import com.micheladrien.fresquerappel.Main_activity
import com.micheladrien.fresquerappel.tools.notification.NotificationService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

//https://developer.android.com/training/testing/ui-automator
//https://proandroiddev.com/testing-android-notifications-f147a572b257

//https://proandroiddev.com/testing-android-notifications-f147a572b257

@RunWith(AndroidJUnit4::class)
class NotificationServiceTest {

    private val clearAllNotificationRes = "com.android.systemui:id/dismiss_text"
    private val timeout = 1000L

    private val titleNotTest = "TitleTest"
    private val textNotTest = "TextTest"

    private lateinit var mDevice : UiDevice
    private lateinit var context: Context

    @get:Rule
    val mainActivityTestRule : ActivityTestRule<Main_activity> = ActivityTestRule<Main_activity>(
            Main_activity::class.java
    )

    @Before
    fun setUp()
    {
        //injectInstrumentation(InstrumentationRegistry.getInstrumentation())
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        context = ApplicationProvider.getApplicationContext()
    }

    /*
    //Appuyer sur le bouton clear all
    val clearAll: UiObject2 = mDevice.findObject(By.res(clearAllNotificationRes))
    clearAll.click()
    */

    //Vérifie si il existe une notification (titre, texte)
    fun checkNotification(expectedTitle:String, expectedText:String){
        //Recupere titre, texte et le bouton d'action de la notification
        val title: UiObject2 = mDevice.findObject(By.text(expectedTitle))
        val text: UiObject2 = mDevice.findObject(By.textStartsWith(expectedText))
        //val boutonAction: UiObject2 = uiDevice.findObject(By.res("Bouton action"))

        assertEquals(expectedTitle, title.text)
        assertTrue(text.text.startsWith(expectedText))
        //assertEquals(expectedAllCities.toLowerCase(), allCities.text.toLowerCase())
    }

    //Nous verifions que la notification soit bien créée, puis nous l'affichons à la main
    @Test
    fun createAndPublishNotification() {

        mDevice.openNotification()
        mDevice.wait(Until.hasObject(By.pkg("com.android.systemui")), 10000)

        val notification = NotificationService.createTimerNotification(context , titleNotTest, textNotTest)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(NotificationService.NOTIFICATION_ID_TIMER /*+ num_notification*/, notification)
        }

        checkNotification(titleNotTest, textNotTest)
    }

    //Nous laissons la création et l'affichage de la notification au programme.
    @Test
    fun defineNotification(){

        val intent = Intent(context, NotificationService::class.java)
        intent.putExtra(NotificationService.STRING_NOT_ID, 1)
        intent.putExtra(NotificationService.INTENT_TITLE, titleNotTest)
        intent.putExtra(NotificationService.INTENT_TEXT, textNotTest)

        val notService = NotificationService()
        notService.onReceive(context, intent)

        checkNotification(titleNotTest, textNotTest)

    }

    //TODO Verifier que nous avons une notification à la fin
    fun createTimerNotification(){

    }

    //TODO Notification dispear when we click on it
    fun tapTimerNotificationToMakeItDisapear(){

    }

    //TODO Est ce que cela appel bien la creation de notification ?
    fun onReceiveCreateNotification(){

    }

    //TODO Notification shows correct informations
    fun checkInfoOnTimerNotification(){

    }

    /* La fonction va appuyer sur le bouton qui lancera le processus de notification
    private fun clickOnSendNotification() : ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Click on the send notification button"
            }

            override fun getConstraints(): Matcher<View> {
                return Matchers.allOf(isDisplayed(), isAssignableFrom(Button::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                view?.findViewById<View>(R.id.send_button)?.performClick()
            }
        }
    }
    */
}