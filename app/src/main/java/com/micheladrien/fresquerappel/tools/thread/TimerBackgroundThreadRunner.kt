package com.micheladrien.fresquerappel.tools.thread

import android.content.Context
import android.content.Intent
import android.util.Log
import com.micheladrien.fresquerappel.datas.TimerModel
import kotlin.collections.ArrayList

/* Options pour faire le run en arriere plan :
Pour faire tourner en arrière plan :
Utilisation des Services. Le plus simple.
ou Utilisation de RxJava. Je ne pense pas que cela soit necessaire.
RxJava a pour but de simplifier la communication des valeurs entre le thread principal et secondaire.
Est ce que une communication bilatérale est necessaire ? Je pense que non

Utilisation de timer et timertask pour gérer les temps.
 */
class TimerBackgroundThreadRunner() {
    private var listeTimer : ArrayList<TimerModel> = ArrayList<TimerModel>()

    fun changeListTimer(listeTimer: ArrayList<TimerModel>){
        this.listeTimer= listeTimer
    }

    fun start(context: Context){
        Log.d("backGround", "2")
        val mIntent = Intent(context, TimerService::class.java)
        //mIntent.putExtra("maxCountValue", 1000)
        TimerService.enqueueWork(context, mIntent)

    }

    //TODO A utiliser
    fun stop(context: Context){
        val intent_service = Intent(context, TimerService::class.java)
        context.stopService(intent_service)
    }


}