package com.termux.controlcenter

import android.content.Intent
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Set language preference
        setAppLanguage()
        
        // Setup button click listeners
        setupButtons()
        
        // Handle USB intents if any
        handleUsbIntent(intent)
    }
    
    private fun setAppLanguage() {
        val sharedPrefs = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val language = sharedPrefs.getString("app_language", "mr") ?: "mr"
        
        val resources = resources
        val configuration = resources.configuration
        val locale = when (language) {
            "mr" -> Locale("mr", "IN")
            else -> Locale.ENGLISH
        }
        
        Locale.setDefault(locale)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
    
    private fun setupButtons() {
        // USB Manager Button
        findViewById<Button>(R.id.btn_usb).setOnClickListener {
            // Open USB Manager (placeholder - implement fragment navigation)
            showMessage("USB Manager Opening Soon...")
        }
        
        // Network Tools Button
        findViewById<Button>(R.id.btn_network).setOnClickListener {
            // Open Network Tools (placeholder - implement fragment navigation)
            showMessage("Network Tools Opening Soon...")
        }
        
        // System Info Button
        findViewById<Button>(R.id.btn_system).setOnClickListener {
            // Open System Info (placeholder - implement fragment navigation)
            showMessage("System Information Opening Soon...")
        }
    }
    
    private fun handleUsbIntent(intent: Intent) {
        if (intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
            showMessage("USB Device Connected!")
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_language -> {
                toggleLanguage()
                true
            }
            R.id.menu_settings -> {
                showMessage("Settings Opening Soon...")
                true
            }
            R.id.menu_about -> {
                showAboutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun toggleLanguage() {
        val sharedPrefs = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val currentLang = sharedPrefs.getString("app_language", "mr") ?: "mr"
        val newLang = if (currentLang == "mr") "en" else "mr"
        
        sharedPrefs.edit().putString("app_language", newLang).apply()
        recreate() // Restart activity to apply language change
    }
    
    private fun showMessage(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun showAboutDialog() {
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("About Termux Control Center")
            .setMessage("Bugjaeger Replacement App\nDeveloped with Kotlin & Marathi Support")
            .setPositiveButton("OK", null)
            .create()
        dialog.show()
    }
}
