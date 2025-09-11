import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project1_wordle.R

class ThemeAdapter(
    private val themes: List<String>,
    private val onThemeSelected: (String) -> Unit
) : RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class ThemeViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        init {
            textView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onThemeSelected(themes[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_theme, parent, false) as TextView
        return ThemeViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.textView.text = themes[position]
        // Highlight selected item
        holder.textView.setBackgroundResource(
            if (position == selectedPosition) R.drawable.theme_item_selected_background else R.drawable.theme_item_background
        )
    }

    override fun getItemCount() = themes.size
}
