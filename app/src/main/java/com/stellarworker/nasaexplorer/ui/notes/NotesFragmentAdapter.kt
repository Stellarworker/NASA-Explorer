package com.stellarworker.nasaexplorer.ui.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.stellarworker.nasaexplorer.R
import com.stellarworker.nasaexplorer.domain.Note
import com.stellarworker.nasaexplorer.domain.Notes
import com.stellarworker.nasaexplorer.utils.hide
import com.stellarworker.nasaexplorer.utils.show
import java.util.*

private const val POSITION_OFFSET = 1

class NotesFragmentAdapter(
    private val onNoteRemoved: ((position: Int, adapter: NotesFragmentAdapter) -> Unit)? = null,
    private val onNoteInserted: ((viewHolder: RecyclerView.ViewHolder) -> Unit)? = null,
    private val onNoteSaved: ((
        position: Int, text: String, adapter: NotesFragmentAdapter,
        viewHolder: RecyclerView.ViewHolder
    ) -> Unit)? = null,
    private val onUpPressed: ((position: Int, adapter: NotesFragmentAdapter) -> Unit)? = null,
    private val onDownPressed: ((position: Int, adapter: NotesFragmentAdapter) -> Unit)? = null
) : RecyclerView.Adapter<NotesFragmentAdapter.NotesViewHolder>() {

    private var notes = Notes()

    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textEdit: TextInputEditText =
            itemView.findViewById(R.id.fragment_notes_recycler_item_note_text)
        private val delete: AppCompatImageView =
            itemView.findViewById(R.id.fragment_notes_recycler_item_note_delete)
        private val save: AppCompatImageView =
            itemView.findViewById(R.id.fragment_notes_recycler_item_note_save)
        private val up: AppCompatImageView =
            itemView.findViewById(R.id.fragment_notes_recycler_item_note_up)
        private val down: AppCompatImageView =
            itemView.findViewById(R.id.fragment_notes_recycler_item_note_down)

        init {

            delete.setOnClickListener {
                onNoteRemoved?.invoke(adapterPosition, this@NotesFragmentAdapter)
            }

            save.setOnClickListener {
                onNoteSaved?.invoke(
                    adapterPosition,
                    textEdit.text.toString(),
                    this@NotesFragmentAdapter,
                    this
                )
            }

            up.setOnClickListener {
                onUpPressed?.invoke(adapterPosition, this@NotesFragmentAdapter)
            }

            down.setOnClickListener {
                onDownPressed?.invoke(adapterPosition, this@NotesFragmentAdapter)
            }

            textEdit.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    delete.hide()
                    save.show()
                } else {
                    (v as? TextInputEditText)?.setText(notes.noteList[adapterPosition].text)
                    save.hide()
                    delete.show()
                }
            }
        }

        fun bind(note: Note) {
            textEdit.setText(note.text)
            if (note.addedByUser) {
                note.addedByUser = note.addedByUser.not()
                onNoteInserted?.invoke(this@NotesViewHolder)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(data: Notes) {
        notes = data
        notifyDataSetChanged()
    }

    fun addNote(note: Note) {
        notes.noteList.add(note)
        notifyItemInserted(itemCount - POSITION_OFFSET)
    }

    fun editNote(text: String, position: Int) {
        notes.noteList[position].text = text
        notifyItemChanged(position)
    }

    fun removeNote(position: Int) {
        notes.noteList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun swapNotes(from: Int, to: Int) {
        Collections.swap(notes.noteList, from, to)
        notifyItemMoved(from, to)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_notes_recycler_item,
                parent, false
            ) as View
        )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes.noteList[position])
    }

    override fun getItemCount() = notes.noteList.size
}