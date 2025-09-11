import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project1_wordle.R

class ThemeAdapter(
    private val themes: List<String>,
    private var selectedTheme: String,
    private val onThemeSelected: (String) -> Unit
) : RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {

    inner class ThemeViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        init {
            textView.setOnClickListener {
                val newTheme = themes[adapterPosition]
                val previousTheme = selectedTheme
                selectedTheme = newTheme
                notifyDataSetChanged()  // Refresh all items to update highlights
                onThemeSelected(newTheme)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_theme, parent, false) as TextView
        return ThemeViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        val theme = themes[position]
        holder.textView.text = theme
        val isSelected = theme == selectedTheme
        holder.textView.setBackgroundResource(
            if (isSelected) R.drawable.theme_item_selected_background
            else R.drawable.theme_item_background
        )
    }

    override fun getItemCount(): Int = themes.size
}
