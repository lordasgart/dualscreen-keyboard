package one.malz.dualscreenkeyboard

import android.app.Presentation
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Display
import android.view.View
import androidx.annotation.RequiresApi
import one.malz.dualscreenkeyboard.databinding.ActivitySecondaryBinding
import java.lang.Exception

class SecondaryActivity(outerContext: Context?, display: Display?, theme: Int) :
    Presentation(outerContext, display, theme) {

    private lateinit var binding: ActivitySecondaryBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setTitle("Dual Screen Keyboard Editor")
        binding.editTextTextMultiLine.setBackgroundColor(Color.BLACK)
        binding.editTextTextMultiLine.setTextColor(Color.WHITE);

        hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window!!.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    fun setText(s: String) {
        try {
            binding.editTextTextMultiLine.setText(s)
        }
        catch (ex: Exception) {
            print(ex.message)
        }
    }

    fun setDarkTheme(theme: Resources.Theme) {

    }
}
