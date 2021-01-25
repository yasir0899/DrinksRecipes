package com.example.drinksrecipes.modules.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.drinksrecipes.R
import com.example.drinksrecipes.models.responseModel.DrinksItem
import com.example.drinksrecipes.models.responseModel.GetDrinksResponseModel
import com.example.drinksrecipes.modules.adapters.DrinksRecycleViewAdapter
import com.example.drinksrecipes.modules.adapters.RecyclerViewItemClickListener
import com.example.drinksrecipes.modules.home.vm.DrinksVM
import com.example.drinksrecipes.restAPI.ErrorDto
import com.example.drinksrecipes.utils.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL


class HomeFragment : Fragment(), RecyclerViewItemClickListener {

    private var fromNameSearch: Boolean = true
    private val drinksVM by lazy {
        ViewModelProvider(this).get(DrinksVM::class.java)
    }

    private lateinit var drinksRecycleViewAdapter: DrinksRecycleViewAdapter
    private var drinksdata = ArrayList<DrinksItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etSearch.setText("margarita")
        drinksRecycleViewAdapter =
            DrinksRecycleViewAdapter(drinksdata, requireContext())
        rcvDrinks.adapter = drinksRecycleViewAdapter
        drinksRecycleViewAdapter.onAdapterClickListener(this)
        if (DataHandler.getBooleanPreferences(AppConstants.SEARCH_STATE, requireContext())) {
            rbFirstAlphabet.isChecked = true
            etSearch.text.clear()
        } else {
            rbNameSearch.isChecked = true
        }
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                initView()
                AppUtils().hideKeyboard(etSearch)
                true
            }
            false
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            if (checkedId == rbNameSearch.id) {
                fromNameSearch = true
                DataHandler.updatePreferences(AppConstants.SEARCH_STATE, false, requireContext())
            } else {
                fromNameSearch = false
                DataHandler.updatePreferences(AppConstants.SEARCH_STATE, true, requireContext())
            }
        }
        initView()
    }

    private fun initView() {
        pbHome.visibility = View.VISIBLE
        if (fromNameSearch) {
            val data: HashMap<String, String> = HashMap()
            data["s"] = etSearch.text.toString().trim()
            apiHit(data)
        } else {
            val data: HashMap<String, String> = HashMap()
            data["f"] = etSearch.text.toString().trim()
            apiHit(data)

        }


    }

    override fun onClick(position: Int) {
        pbHome.visibility = View.VISIBLE
        val item = drinksRecycleViewAdapter.getItem(position)
        try {
            val url = URL(item.strDrinkThumb)
            val result: Deferred<Bitmap?> = GlobalScope.async {
                url.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main) {
                // show bitmap on image view when available

                if (InternalStorageProvider(requireContext()).saveBitmap(
                        result.await(),
                        item.strDrink.toString()
                    )
                ) {

                    drinksVM.insertData(
                        requireContext(),
                        item.idDrink,
                        item.strDrink, item.strInstructions, item.strAlcoholic, item.strDrink
                    )

                }
                pbHome.visibility = View.INVISIBLE
                ToastUtil.showShortToast(requireContext(), "Added to favourite")
            }


        } catch (e: IOException) {
            println(e)
        }

    }

    private fun apiHit(data: HashMap<String, String>) {
        drinksVM.getDrinks(data).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                pbHome.visibility = View.INVISIBLE
                if (it is GetDrinksResponseModel) {
                    drinksdata.clear()
                    if (it.drinks != null) {
                        drinksdata.addAll(it.drinks)
                        drinksRecycleViewAdapter.notifyDataSetChanged()
                    } else {
                        drinksRecycleViewAdapter.notifyDataSetChanged()
                    }


                } else {

                    if (it is ErrorDto) {

                        ToastUtil.showLongToast(requireContext(),it.message)

                    }
                }

            }
        })


    }


    fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        } catch (e: IOException) {
            null
        }
    }
}