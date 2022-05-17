package com.geekbrains.nasaexplorer.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.geekbrains.nasaexplorer.R
import com.geekbrains.nasaexplorer.common.EMPTY_INT
import com.geekbrains.nasaexplorer.common.THEME_ID
import com.geekbrains.nasaexplorer.databinding.FragmentSettingsBinding
import com.geekbrains.nasaexplorer.utils.convertRBIDtoTID
import com.geekbrains.nasaexplorer.utils.convertTIDtoRBID

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)
        activity?.let { activity ->
            with(binding.themesGroup) {
                check(
                    convertTIDtoRBID(
                        activity.getPreferences(Context.MODE_PRIVATE)
                            .getInt(THEME_ID, EMPTY_INT)
                    )
                )
                setOnCheckedChangeListener { _, checkedId ->
                    activity.getPreferences(Context.MODE_PRIVATE)
                        .edit()
                        .putInt(THEME_ID, convertRBIDtoTID(checkedId))
                        .apply()
                    activity.setTheme(checkedId)
                    activity.recreate()
                }
            }
        }

    }
}