package com.medanis.phpmysqlapp

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.*

object InnoDev_Test {
    var Link = "http://10.0.2.2/store/innodev/add_cars.php"
    var retour = ""

    class Sending(var context: Context) : AsyncTask<String?, Void?, ArrayList<Int>?>() {
        var name = "BMW"
        var price = "1000"
        var description = "rien a dire"
        var progressDialogV: ProgressDialog? = null
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialogV = ProgressDialog.show(context, "Envoi En cours", "Veuillez patienter S.V.P...", false, false)
        }

        override fun doInBackground(vararg params: String?): ArrayList<Int>? {
            val link = Link + "name=" + name +
                    "&price=" + price + "&description=" + description
            var url: URL? = null
            try {
                url = URL(link)
                val con = url.openConnection()
                val bufferedReader = BufferedReader(InputStreamReader(con.getInputStream()))
                val result = bufferedReader.readLine()
                retour = result
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        public override fun onPostExecute(result: ArrayList<Int>?) {
            progressDialogV!!.dismiss()
            Log.e("innodev-france", retour)
        }

    }
}