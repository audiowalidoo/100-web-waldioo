package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.WebsiteCatalog
import com.example.model.WebsiteCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Popular Websites", appName)
  }

  @Test
  fun `verify catalog contains over 100 websites including Apple`() {
    val list = WebsiteCatalog.websites
    assertTrue("Should have over 100 websites, but has ${list.size}", list.size >= 100)
    assertTrue("Should contain Apple", list.any { it.name.equals("Apple", ignoreCase = true) })
    assertTrue("Should contain Google", list.any { it.name.equals("Google", ignoreCase = true) })
    assertTrue("All websites should have valid URLs", list.all { it.url.startsWith("http") })
  }
}

