package dev.hong481.pixo.test.ui.base.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    /**
     * 새로운 인텐트 설정.
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
