package com.example.translate

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.translate.databinding.FragmentHomeBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions


class HomeFragment : Fragment() {
    private lateinit var binding :FragmentHomeBinding
    private var items= listOf("English","Bengali","Tamil")
    private var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        val itemsAdapter: ArrayAdapter<String>
        = ArrayAdapter(requireActivity(),
            R.layout.simple_dropdown_item_1line, items)

        binding.languageFrom.setAdapter(itemsAdapter)
        binding.languageTo.setAdapter(itemsAdapter)
        binding.translate.setOnClickListener {

            val options = TranslatorOptions.Builder()
                .setSourceLanguage(selectFrom())
                .setTargetLanguage(selectTo())
                .build()

            val englishGermanTranslator = Translation.getClient(options)

            englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {

                    englishGermanTranslator.translate(binding.input.text.toString())
                        .addOnSuccessListener { translatedText ->

                            binding.output.text=translatedText

                        }
                        .addOnFailureListener { exception ->

                            Toast.makeText(requireActivity(), exception.message, Toast.LENGTH_SHORT).show()

                        }


                }
                .addOnFailureListener { exception ->

                    Toast.makeText(requireActivity(), exception.message, Toast.LENGTH_SHORT).show()

                }


        }


        return binding.root
    }



    private fun selectFrom(): String {

        return when(binding.languageFrom.text.toString()){

            ""-> TranslateLanguage.ENGLISH

            "English"->TranslateLanguage.ENGLISH
            "Bengali"->TranslateLanguage.BENGALI
            "Tamil"->TranslateLanguage.TAMIL
            else->TranslateLanguage.ENGLISH

        }

    }
    private fun selectTo(): String {

        return when(binding.languageTo.text.toString()){

            ""-> TranslateLanguage.HINDI

            "English"->TranslateLanguage.ENGLISH

            "Bengali"->TranslateLanguage.BENGALI

            "Tamil"->TranslateLanguage.TAMIL

            else->TranslateLanguage.HINDI

        }


    }

}