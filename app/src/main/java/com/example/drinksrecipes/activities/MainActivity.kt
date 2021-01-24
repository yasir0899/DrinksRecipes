package com.example.drinksrecipes.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.drinksrecipes.R
import com.example.drinksrecipes.modules.home.vm.DrinksVM
import com.example.drinksrecipes.modules.room.DrinksTableModel
import com.example.drinksrecipes.utils.AppConstants
import com.example.drinksrecipes.utils.NotificationReceiver
import com.example.providerportal.utils.InternetConnectionUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private val drinksVM by lazy {
        ViewModelProvider(this).get(DrinksVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {

        bottomNavigationHome.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.mainNavFragment
            )
        )

        if (!InternetConnectionUtil(this).isConnectedToInternet()) {

            drinksVM.getDrinksFromRoom(this)?.observe(this, androidx.lifecycle.Observer {
                if (it != null) {

                    if (it[0] != null) {
                        val item = it[0]
                        myAlarm(item)
                    }
                } else {
                    myAlarm(DrinksTableModel("", "Need some drinks open", "", "", "test"))
                }
            })
        } else {

            //calling
            myAlarm(DrinksTableModel("", "Need some drinks open", "", "", "test"))
        }

    }

    private fun myAlarm(item: DrinksTableModel) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 14)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 1)
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        intent.putExtra(AppConstants.NOTIFICATION_TITLE, item.title)
        intent.putExtra(AppConstants.NOTIFICATION_DESC, item.desc)
        intent.putExtra(AppConstants.NOTIFICATION_IMG, item.img)
        intent.action = "com.example.drinksrecipes"
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}