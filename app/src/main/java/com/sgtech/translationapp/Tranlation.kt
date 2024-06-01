package com.sgtech.translationapp

import android.os.AsyncTask
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import okhttp3.OkHttpClient
import okhttp3.Request

object Translation {

    fun translate(translationParameters: TranslationParameters): String {
        val translationParameters = TranslationParameters(
            translationParameters.sourceLanguage,
            translationParameters.targetLanguage,
            translationParameters.text
        )
        val translationTask = TranslationTask()
        val result = translationTask.execute(translationParameters).get()
        return result
    }


}

data class TranslationParameters(
    val sourceLanguage: String, val targetLanguage: String, val text: String
)

class TranslationTask : AsyncTask<TranslationParameters, String, String>() {
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: TranslationParameters): String {
        val translationParameters = params[0]
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url("https://translate.googleapis.com/translate_a/single?client=gtx&sl=${translationParameters.sourceLanguage}&tl=${translationParameters.targetLanguage}&dt=t&q=${translationParameters.text}")
            .build()
        val response = okHttpClient.newCall(request).execute()
        val body = Json.parseToJsonElement(
            response.body?.string().toString()
        ).jsonArray[0].jsonArray[0].jsonArray[0]
        return body.toString()
    }


}