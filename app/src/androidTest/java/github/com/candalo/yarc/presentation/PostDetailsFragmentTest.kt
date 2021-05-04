package github.com.candalo.yarc.presentation

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import github.com.candalo.yarc.post
import github.com.candalo.yarc.testInfrastructure
import github.com.candalo.yarc.waitItems
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.*

internal class PostDetailsFragmentTest : KoinTest {
    private val mockWebServer: MockWebServer by inject()

    @Before
    fun setup() {
        loadKoinModules(testInfrastructure)
    }

    @After
    fun teardown() {
        unloadKoinModules(testInfrastructure)
    }

    @Test
    fun postDetailsShouldBeShowWhenScreenIsOpen() {
        val fragmentArgs = bundleOf("post" to post)

        launchFragmentInContainer<PostDetailsFragment>(fragmentArgs)

        onView(
            withText("Apple M1 Chip & Android Emulator: Working")
        ).check(matches(isDisplayed()))

        onView(
            withText("Hi all, I was not able to find any news about this. So I just want to share it with you.")
        ).check(matches(isDisplayed()))

        onView(
            withText("candalo")
        ).check(matches(isDisplayed()))

        onView(
            withText("100")
        ).check(matches(isDisplayed()))

        onView(
            withText("1")
        ).check(matches(isDisplayed()))

        onView(
            withText("1h")
        ).check(matches(isDisplayed()))
    }

    @Test
    fun postCommentsShouldBeShowWhenScreenIsOpen() {
        mockWebServer.start()

        mockSuccessfullyResponse()
        val fragmentArgs = bundleOf("post" to post)

        launchFragmentInContainer<PostDetailsFragment>(fragmentArgs)

        waitItems()

        onView(
            withText("That's great. Thanks for sharing. Meanwhile, how is the performance of the same? Is it closer to how xcode performs ?")
        ).check(matches(isDisplayed()))

        mockWebServer.shutdown()
    }

    private fun mockSuccessfullyResponse() {
        val inputStream = javaClass.classLoader?.getResourceAsStream("post_comments.json")
        val jsonResponse = Scanner(inputStream).useDelimiter("\\A").next()
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))
    }
}