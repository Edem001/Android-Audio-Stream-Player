package com.example.streamingaudiotest

import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Assert.*
import org.junit.Test


class ExampleUnitTest {
    @Test
    fun is_link_available() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://online.hitfm.ua/HitFM_HD")
            .build()

        assertTrue(client.newCall(request).execute().isSuccessful)
    }

    @Test
    fun test_junit (){
        assertTrue(true)

        assertEquals((1+5), 6)
        assertNotEquals("Hello world", "hello\n")
    }
}
