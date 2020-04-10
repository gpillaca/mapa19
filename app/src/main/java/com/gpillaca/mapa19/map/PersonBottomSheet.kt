package com.gpillaca.mapa19.map

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gpillaca.mapa19.R
import kotlinx.android.synthetic.main.bottom_sheet_person.*
import timber.log.Timber

class PersonBottomSheet : BottomSheetDialogFragment(),
    View.OnClickListener,
    DialogInterface.OnShowListener{

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    private var vulnerablePerson: VulnerablePerson? = null

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
        return View.inflate(context, R.layout.bottom_sheet_person, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonHelp.setOnClickListener(this)
        viewPhone.setOnClickListener(this)
        showData()
    }

    private fun showData() {
        vulnerablePerson?.let {
            textViewName.text = it.name
            textViewPhoneNumber.text = it.phoneNumber
            textViewMessage.text = it.message
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
                dismiss()
            }
        }
    }
}