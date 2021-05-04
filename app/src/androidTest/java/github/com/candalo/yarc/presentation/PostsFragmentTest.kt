package github.com.candalo.yarc.presentation

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import com.google.common.truth.Truth.assertThat
import github.com.candalo.yarc.R
import github.com.candalo.yarc.presentation.adapter.PostsViewHolder
import github.com.candalo.yarc.testInfrastructure
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

internal class PostsFragmentTest : KoinTest {
    private val mockWebServer: MockWebServer by inject()

    @Before
    fun setup() {
        loadKoinModules(testInfrastructure)
        mockWebServer.start()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        unloadKoinModules(testInfrastructure)
    }

    @Test
    fun postsShouldBeVisibleWhenFragmentIsOpen() {
        mockSuccessfullyResponse()

        launchFragmentInContainer<PostsFragment>()

        waitItems()

        onView(withText("Weekly Who's Hiring Thread - May 03, 2021")).check(matches(isDisplayed()))
        onView(withText("App Feedback Thread - May 01, 2021")).check(matches(isDisplayed()))
        onView(withText("Apple M1 Chip &amp; Android Emulator: Working")).check(matches(isDisplayed()))
    }

    @Test
    fun whenClickOnPostShouldOpenPostDetailsScreen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        mockSuccessfullyResponse()

        launchFragmentInContainer<PostsFragment>().onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        waitItems()

        onView(withId(R.id.rvPosts)).perform(actionOnItemAtPosition<PostsViewHolder>(0, click()))
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.postDetailsFragment)
    }

    private fun mockSuccessfullyResponse() {
        val inputStream = javaClass.classLoader?.getResourceAsStream("posts.json")
        val jsonResponse = Scanner(inputStream).useDelimiter("\\A").next()
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))
    }

    private fun waitItems() {
        // Handle this situation with an idling resource
        Thread.sleep(1000)
    }
}