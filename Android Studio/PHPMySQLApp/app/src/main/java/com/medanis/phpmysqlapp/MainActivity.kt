package com.medanis.phpmysqlapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var listView: ListView? = null
    var adapter: ArrayAdapter<String>? = null
    var requestQueue: RequestQueue? = null


    var context: Context = this@MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build())

        // Adapting the listView
        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        listView?.adapter = adapter

        // Executing the Connection Process
        Connection().execute()
        requestQueue = Volley.newRequestQueue(this)
        val sendButton = findViewById<Button>(R.id.sendButton)
        sendButton.setOnClickListener {
            val n = "audi"
            val p = "900"
            val d = " la description "
            Sending(n, p, d).execute()
        }

        viewImage.setOnClickListener {
            imageView.visibility = View.VISIBLE
            listView?.visibility = View.GONE
            Glide.with(context)
                    .load("http://192.168.1.19/MedMax/cardio/ECGs/plot-ecg.png")
//                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.img_not_found)
                    .into(imageView)
        }


    }

    var host = "http://192.168.1.19/store/cars.php"

    @SuppressLint("StaticFieldLeak")
    inner class Connection : AsyncTask<String?, String?, String>() {
        override fun doInBackground(vararg params: String?): String? {
            var result = ""
            try {
                val client: HttpClient = DefaultHttpClient()
                val request = HttpGet()
                request.uri = URI(host)
                val response = client.execute(request)
                val reader = BufferedReader(InputStreamReader(response.entity.content))
                val stringBuffer = StringBuffer("")
                var line: String? = ""
                while (reader.readLine().also { line = it } != null) {
                    stringBuffer.append(line)
                    break
                }
                reader.close()
                result = stringBuffer.toString()
            } catch (e: Exception) {
                return "There exception " + e.message
            }
            return result
        }

        override fun onPostExecute(result: String) {
//            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            // Parsing json data
            try {
                adapter!!.clear()
                val jsonResult = JSONObject(result)
                val success = jsonResult.getInt("success")
                if (success == 1) {
//                    Toast.makeText(getApplicationContext(),"OK there cars is sote",Toast.LENGTH_SHORT).show();
                    val cars = jsonResult.getJSONArray("cars")
                    for (i in 0 until cars.length()) {
                        val car = cars.getJSONObject(i)
                        val id = car.getInt("id")
                        val name = car.getString("name")
                        val price = car.getDouble("price")
                        val Description = car.getString("description")
                        val line = "$id-$name-$price"
                        adapter!!.add(line)
                    }
                } else {
                    Toast.makeText(applicationContext, "There is no car yet!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Innodev
    @SuppressLint("StaticFieldLeak")
    inner class Sending(nm: String, pri: String, desc: String) : AsyncTask<String?, Void?, ArrayList<Int>?>() {
        var name = ""
        var price = ""
        var description = ""
        var progressDialogV: ProgressDialog? = null
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialogV = ProgressDialog.show(context, "Envoi En cours", "Veuillez patienter S.V.P...", false, false)
        }

        override fun doInBackground(vararg params: String?): ArrayList<Int>? {


            /*String link = Link+"?name="+name+
                    "&price="+price+"&description="+description;
            */
            val link = Uri.parse(Link)
                    .buildUpon()
                    .appendQueryParameter("name", name)
                    .appendQueryParameter("price", price)
                    .appendQueryParameter("description", description)
                    .build().toString()
            var url: URL? = null
            try {
                url = URL(link)
                val con = url.openConnection()
                val bufferedReader = BufferedReader(InputStreamReader(con.getInputStream()))
                val result = bufferedReader.readLine()
                Log.i("RESULTAT ", result)
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
            Connection().execute()
        }

        init {
            name = nm
            price = pri
            description = desc
        }
    }

    companion object {
        var Link = "http://192.168.1.19/store/innodev/add_cars.php"
        var retour = ""
    }
}