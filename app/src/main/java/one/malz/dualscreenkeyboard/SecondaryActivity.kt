package one.malz.dualscreenkeyboard

import android.R
import android.app.Presentation
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.Display
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.ThemeUtils
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
    }

    fun setText(s: String) {
        try {
            binding.editTextTextMultiLine.setText(s)
        }
        catch (ex: Exception) {
            print(ex.message)
        }
    }
}
