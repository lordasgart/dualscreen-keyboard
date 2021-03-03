package one.malz.dualscreenkeyboard

import android.app.Presentation
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.Window
import android.view.WindowManager

class SecondaryActivity(outerContext: Context?, display: Display?) :
    Presentation(outerContext, display) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)

        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        //window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //    WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }
}