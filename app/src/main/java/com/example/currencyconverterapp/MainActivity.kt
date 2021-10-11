package com.example.currencyconverterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import retrofit2.create
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var currencyModel : CurrencyModel? = null

    private var selected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var tvDate: TextView
        lateinit var tvResult: TextView


        val edValue = findViewById<View>(R.id.edValue) as EditText
        val convertBtn = findViewById<View>(R.id.convertBtn) as Button
        val spinner = findViewById<View>(R.id.spMain) as Spinner


        tvDate = findViewById(R.id.tvDate)
        tvResult = findViewById(R.id.tvResult)


        val list =
            arrayListOf("Select to which to convert", "inr", "usd", "aud", "sar", "cny", "jpy")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(parent.getItemAtPosition(position) == "Select to which to convert"){}
                else{
                    selected = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        convertBtn.setOnClickListener {

         val value = edValue.text.toString().toDouble()


            getCurrency {
                currencyModel = it
                tvDate.text = "As of Date " + currencyModel?.date
                when(selected){
                    "inr" -> tvResult.text = "Result " + (currencyModel?.eur?.inr?.times(value)).toString()
                    "aud" -> tvResult.text = "Result " + (currencyModel?.eur?.aud?.times(value)).toString()
                    "usd" -> tvResult.text = "Result " + (currencyModel?.eur?.usd?.times(value)).toString()
                    "sar" -> tvResult.text = "Result " + (currencyModel?.eur?.sar?.times(value)).toString()
                    "cny" -> tvResult.text = "Result " + (currencyModel?.eur?.cny?.times(value)).toString()
                    "jpy" -> tvResult.text = "Result " + (currencyModel?.eur?.jpy?.times(value)).toString()
                }
            }

        }

    }

    private fun getCurrency(onResult : (CurrencyModel?) -> Unit){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if(apiInterface != null){
            apiInterface.getCurrency()?.enqueue(object : Callback<CurrencyModel>{
                override fun onResponse(
                    call: Call<CurrencyModel>,
                    response: Response<CurrencyModel>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<CurrencyModel>, t: Throwable) {
                    onResult(null)
                    Toast.makeText(applicationContext,"" + t.message,Toast.LENGTH_SHORT).show()
                }

            })
        }

    }
}
