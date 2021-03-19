package one.malz.dualscreenkeyboard

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.display.DisplayManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import one.malz.dualscreenkeyboard.databinding.ActivityMainBinding
import java.io.*
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {

    private var hasAdditionalDisplays: Boolean = false
    private lateinit var presentation: SecondaryActivity
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        //window.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(view)

        //requestWindowFeature(Window.FEATURE_NO_TITLE)

        //setContentView(R.layout.activity_main)

        //Set full screen after setting layout content
//        @Suppress("DEPRECATION")
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            val controller = window.insetsController
//
//            if(controller != null) {
//                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        } else {
//            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        }

        hideSystemUI();



        init(view.context)

        binding.editTextTextMultiLine.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (hasAdditionalDisplays) {
                    val text = binding.editTextTextMultiLine.text.toString()
                    val selectionStart = binding.editTextTextMultiLine.selectionStart
                    presentation.setText(text, selectionStart)
                }
            }
        })

        binding.editTextTextMultiLine.requestFocus()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }


    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    fun onClick(p0: View?) {
        val context: Context = p0?.context ?: return

        init(context)
    }

    private fun init(context: Context) {
        val displayManager = context.getSystemService(DISPLAY_SERVICE) as DisplayManager

        val presentationDisplays: Array<Display> = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION)

        Toast.makeText(this, "I am View.OnClickListener Toast " + presentationDisplays.size, Toast.LENGTH_LONG).show()

        if (presentationDisplays.isNotEmpty()) {
            hasAdditionalDisplays = true
            // If there is more than one suitable presentation display, then we could consider
            // giving the user a choice.  For this example, we simply choose the first display
            // which is the one the system recommends as the preferred presentation display.
            val display = presentationDisplays[0]

            //val theme = getTheme().

            presentation = SecondaryActivity(context, display, 0)
            presentation.setDarkTheme(theme)
            presentation.show()
        }
    }

    fun onClick2(p0: View?) {
try {
    val text = binding.editTextTextMultiLine.text.toString()
    val selectionStart = binding.editTextTextMultiLine.selectionStart
    presentation.setText(text, selectionStart)
    //runOnUiThread(Runnable { presentation.setText("Hello World") })
}
catch (ex: Exception)
{

}

    }

    fun onClickSearch(view: View) {
        val query = binding.editTextTextMultiLine.text.toString()
        val escapedQuery: String = URLEncoder.encode(query, "UTF-8")
        val uri: Uri = Uri.parse("https://www.google.com/search?q=$escapedQuery")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)

    }

    fun onClickSave(view: View) {
        val text = binding.editTextTextMultiLine.text.toString()
        val dataFile = File((this as Context).getExternalFilesDir(null), "dualscreen-keyboard.txt")
        if (!dataFile.exists()) dataFile.createNewFile()

        val writer = BufferedWriter(FileWriter(dataFile, false))
        writer.write(text)
        writer.close()
    }

    fun onClickLoad(view: View) {
        val dataFile = File((this as Context).getExternalFilesDir(null), "dualscreen-keyboard.txt")
        if (!dataFile.exists()) return
        val stringBuilder = StringBuilder()

        dataFile.forEachLine { stringBuilder.appendln(it) }


        val text = stringBuilder.toString()

        binding.editTextTextMultiLine.setText(text)
        //presentation.setText("test")
        //binding.editTextTextMultiLine.text =  Editable.Factory.getInstance().newEditable(text)

        //runOnUiThread(Runnable {
        //    binding.editTextTextMultiLine.setText()
        //})
    }

    fun onClickWhatsApp(view: View) {
        val pm = this@MainActivity.packageManager
        try {
            val waIntent = Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            val text = binding.editTextTextMultiLine.text.toString()
            val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (e: PackageManager.NameNotFoundException) {
           Toast.makeText(this@MainActivity, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                   .show();
        }
    }

    fun onClickShare() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, binding.editTextTextMultiLine.text.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
