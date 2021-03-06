package com.gpillaca.mapa19.ui.map

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.util.isInt
import com.gpillaca.mapa19.util.showMessage
import com.gpillaca.mapa19.databinding.BottomSheetPersonBinding
import com.gpillaca.mapa19.domain.VulnerablePerson
import timber.log.Timber

class PersonBottomSheet : BottomSheetDialogFragment(),
    View.OnClickListener,
    DialogInterface.OnShowListener{

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    private var vulnerablePerson: VulnerablePerson? = null
    private lateinit var binding: BottomSheetPersonBinding

    companion object {
        private const val ARG_PERSON = "PersonBottomSheet:person"

        @JvmStatic
        fun newInstance(vulnerablePerson: VulnerablePerson) = PersonBottomSheet().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PERSON, vulnerablePerson)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vulnerablePerson = arguments?.getParcelable(ARG_PERSON)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetPersonBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonHelp.setOnClickListener(this)
        binding.viewPhone.setOnClickListener(this)
        showData()
    }

    private fun showData() {
        vulnerablePerson?.let {
            binding.textViewName.text = it.name
            binding.textViewPhoneNumber.text = it.phoneNumber
            binding.textViewMessage.text = it.message
        } ?: kotlin.run {
            Timber.e("Movies is null")
            dismiss()
        }
    }

    override fun onShow(dialog: DialogInterface?) {
        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onClick(v: View?) {
        val id = v?.id ?: 0

        when (id) {
            R.id.buttonHelp -> {
            }
            R.id.viewPhone -> {
                callVulnerablePerson()
            }
        }
    }

    private fun callVulnerablePerson() {
        if (vulnerablePerson == null ||
            vulnerablePerson!!.phoneNumber.isEmpty() ||
            !vulnerablePerson!!.phoneNumber.isInt()
        ) {
            context?.showMessage(getString(R.string.phone_number_invalid))
            return
        }

        val phoneNumber = vulnerablePerson!!.phoneNumber.toInt()

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        activity?.startActivity(intent)
    }
}