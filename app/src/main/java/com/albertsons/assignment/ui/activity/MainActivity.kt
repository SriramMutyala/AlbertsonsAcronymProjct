package com.albertsons.assignment.ui.activity

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.albertsons.assignment.R
import com.albertsons.assignment.data.model.LFS
import com.albertsons.assignment.data.network.Result
import com.albertsons.assignment.databinding.ActivityMainBinding
import com.albertsons.assignment.ui.adapter.MeaningsAdapter
import com.albertsons.assignment.util.gone
import com.albertsons.assignment.util.hideKeyboard
import com.albertsons.assignment.util.visible
import com.albertsons.assignment.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        registerListeners()
    }

    private fun initObservers() {
        mainActivityViewModel.results().observe(this) {
            with(binding) {
                when (it.status) {
                    Result.Status.LOADING -> {
                        progressBar.visible()
                    }
                    Result.Status.SUCCESS -> {
                        progressBar.gone()
                        if (it?.data?.isNotEmpty() == true) {
                            errorTextView.gone()
                            setAdapter(it.data[0].list)

                        } else {
                            showError(getString(R.string.no_meanings_found))
                        }
                    }
                    Result.Status.ERROR -> {
                        progressBar.gone()
                        showError(
                            it.message ?: getString(R.string.failed_to_fetch_items_please_retry)
                        )
                    }
                }
            }

        }
    }

    private fun registerListeners() {
        binding.btnSubmit.setOnClickListener {
            binding.inputEditText.hideKeyboard()
            val enteredTExt = binding.inputEditText.text.toString()
            if (enteredTExt.isNotBlank()) {
                mainActivityViewModel.getMeanings(enteredTExt)
            } else {
                Toast.makeText(this, getString(R.string.please_enter_some_text), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setAdapter(meaningsList: List<LFS>) {
        val meaningsAdapter = MeaningsAdapter(meaningsList)
        binding.recyclerMeanings.run {
            adapter = meaningsAdapter
            visible()
        }
    }

    private fun showError(errorMessage: String) {
        binding.run {
            showDialog(errorMessage)
            errorTextView.visible()
            recyclerMeanings.gone()

        }
    }

    private fun showDialog(errorMessage: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(errorMessage)
        builder.setPositiveButton(android.R.string.ok)
        { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}
