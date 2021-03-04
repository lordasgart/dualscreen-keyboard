package one.malz.dualscreenkeyboard

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.view.Display
import one.malz.dualscreenkeyboard.databinding.ActivitySecondaryBinding
import java.lang.Exception

class SecondaryActivity(outerContext: Context?, display: Display?) :
    Presentation(outerContext, display) {

    private lateinit var binding: ActivitySecondaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_secondary)

        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        //window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //    WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun setText(s: String) {
        try {

            binding.editTextTextMultiLine.setText(s);
        }
        catch (ex: Exception) {
            print(ex.message)
        }
    }
}
