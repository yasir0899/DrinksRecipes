package com.example.drinksrecipes.modules

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.drinksrecipes.R
import com.example.drinksrecipes.activities.MainActivity
import com.example.drinksrecipes.models.responseModel.DrinksItem
import com.example.drinksrecipes.modules.adapters.DrinksFavRcvAdapter
import com.example.drinksrecipes.modules.adapters.DrinksRecycleViewAdapter
import com.example.drinksrecipes.modules.home.vm.DrinksVM
import com.example.drinksrecipes.modules.room.DrinksTableModel
import com.example.drinksrecipes.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favourites.*


class FavouritesFragment : Fragment() {
    private val drinksVM by lazy {
        ViewModelProvider(this).get(DrinksVM::class.java)
    }
    private  var drinksdata= ArrayList<DrinksTableModel>()
    private lateinit var adapter: DrinksFavRcvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).toolbar_title.text = getString(R.string.favourite_recipie)
        adapter = DrinksFavRcvAdapter(drinksdata, requireContext())
        rcvDrinksFav.adapter=adapter
        pbFav.visibility=View.VISIBLE
        drinksVM.getDrinksFromRoom(requireContext())?.observe(viewLifecycleOwner, Observer {
            pbFav.visibility=View.INVISIBLE
           if (it!=null) {
               drinksdata.addAll(it)
               adapter.notifyDataSetChanged()
           }

        })

    }

}