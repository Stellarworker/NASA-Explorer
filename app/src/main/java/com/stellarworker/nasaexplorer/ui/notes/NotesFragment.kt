package com.stellarworker.nasaexplorer.ui.notes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.stellarworker.nasaexplorer.R
import com.stellarworker.nasaexplorer.databinding.FragmentNotesBinding
import com.stellarworker.nasaexplorer.domain.Note
import com.stellarworker.nasaexplorer.utils.hideKeyboard
import com.stellarworker.nasaexplorer.utils.makeSnackbar

private const val ZERO_INT = 0
private const val POSITION_OFFSET = 1

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val adapter = NotesFragmentAdapter(
        onNoteInserted = { viewHolder ->
            viewHolder.itemView.requestFocus()
        },
        onNoteSaved = { position, text, adapter, viewHolder ->
            adapter.editNote(text, position)
            binding.fragmentNotesDataContainer.hideKeyboard()
            viewHolder.itemView.clearFocus()
            binding.root.makeSnackbar(
                text = getString(R.string.notes_fragment_note_saved),
                length = Snackbar.LENGTH_SHORT,
                anchor = activity?.findViewById(R.id.main_activity_bottom_navigation)
            )
        },
        onNoteRemoved = { position, adapter ->
            adapter.removeNote(position)
            binding.root.makeSnackbar(
                text = getString(R.string.notes_fragment_note_deleted),
                length = Snackbar.LENGTH_SHORT,
                anchor = activity?.findViewById(R.id.main_activity_bottom_navigation)
            )
        },
        onUpPressed = { position, adapter ->
            if (position != ZERO_INT) {
                adapter.swapNotes(position, position - POSITION_OFFSET)
            }
        },
        onDownPressed = { position, adapter ->
            if (position != adapter.itemCount - POSITION_OFFSET) {
                adapter.swapNotes(position, position + POSITION_OFFSET)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentNotesList.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        adapter.addNote(Note(addedByUser = true))
        binding.fragmentNotesList.scrollToPosition(adapter.itemCount - POSITION_OFFSET)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}