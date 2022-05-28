package com.geekbrains.nasaexplorer.ui.settings

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.geekbrains.nasaexplorer.R
import com.geekbrains.nasaexplorer.databinding.FragmentSettingsBinding
import com.geekbrains.nasaexplorer.utils.convertRBIDtoTID
import com.geekbrains.nasaexplorer.utils.convertTIDtoRBID

private const val EMPTY_INT = -1
private const val THEME_ID = "THEME_ID"

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val INITIAL_SIZE = 0f
    private val DURATION = 500L

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
                    binding.animationRadiogroup1.transitionToEnd()
                    activity.getPreferences(Context.MODE_PRIVATE)
                        .edit()
                        .putInt(THEME_ID, convertRBIDtoTID(checkedId))
                        .apply()
                    activity.setTheme(checkedId)
                }
            }
        }

        val transitionListener = object : MotionLayout.TransitionListener {

            override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {}

            override fun onTransitionChange(
                p0: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                activity?.recreate()
            }

            override fun onTransitionTrigger(
                p0: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        }
        binding.animationRadiogroup1.setTransitionListener(transitionListener)
        ValueAnimator.ofFloat(INITIAL_SIZE, resources.getDimension(R.dimen.common_header_small))
            .apply {
                duration = DURATION
                addUpdateListener {
                    binding.settingsFragmentThemesHeader.textSize = it.animatedValue as Float
                }
            }.start()
    }
}