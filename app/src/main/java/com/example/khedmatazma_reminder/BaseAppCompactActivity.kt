package com.example.khedmatazma_reminder
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.khedmatazma_reminder.R
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper


open class BaseAppCompactActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        ViewPump.init(ViewPump.builder()
                .addInterceptor( CalligraphyInterceptor(
                         CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/dana_medium.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build())
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}