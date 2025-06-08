package com.politecnico.athleticapp.ui.mealplans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.politecnico.athleticapp.MainActivity
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentMealPlansBinding
import com.politecnico.athleticapp.databinding.ItemMealCategoryBinding
import com.politecnico.athleticapp.databinding.ItemMealPlanEntryBinding

data class Meal(
    val title: String,
    val description: String,
    val calories: String,
    val iconResId: Int,
    var isChecked: Boolean = false
)

data class MealCategory(
    val name: String,
    val imageResId: Int
)

class MealPlansFragment : Fragment() {

    private var _binding: FragmentMealPlansBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealPlansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoryCarousel()
        setupMealsRecyclerView()

        binding.viewStatsButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_meal_plans_to_nutritionStatsFragment)
        }

        // Hide the loading indicator from MainActivity
        view.post { (activity as? MainActivity)?.hideLoading() }
    }

    private fun setupCategoryCarousel() {
        val categories = listOf(
            MealCategory("Breakfast", R.drawable.breakfast),
            MealCategory("Coffee break", R.drawable.fruit_salad_amico),
            MealCategory("Lunch", R.drawable.lunch_time_bro),
            MealCategory("Dinner", R.drawable.fruit_salad_pana),
            MealCategory("Snack", R.drawable.breakfast_food_rafiki)
        )

        val categoryAdapter = MealCategoryAdapter(categories) { category ->
            binding.dayTitle.text = category.name.uppercase()
            // Here you would typically filter the list of meals below
        }

        binding.categoryCarouselRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }
    
    private fun setupMealsRecyclerView() {
        val mealList = listOf(
            Meal("BREAKFAST", "Oatmeal with Berries", "350Kcal", R.drawable.breakfast),
            Meal("GRILLED CHICKEN SALAD", "Chicken, spinach", "450Kcal", R.drawable.lunch_time_bro),
            Meal("GRILLED CHICKEN SALAD", "Chicken, spinach", "450Kcal", R.drawable.deconstructed_food_amico)
        )

        val mealAdapter = MealAdapter(mealList)
        binding.mealsRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mealAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class MealAdapter(private val mealList: List<Meal>) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealPlanEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealList[position]
        holder.bind(meal)
    }

    override fun getItemCount() = mealList.size

    class MealViewHolder(private val binding: ItemMealPlanEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.mealTitle.text = meal.title
            binding.mealDescription.text = meal.description
            binding.mealCalories.text = meal.calories
            binding.mealIcon.setImageResource(meal.iconResId)
            binding.mealCheckbox.isChecked = meal.isChecked
        }
    }
}

class MealCategoryAdapter(
    private val categories: List<MealCategory>,
    private val onItemClicked: (MealCategory) -> Unit
) : RecyclerView.Adapter<MealCategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemMealCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
        holder.itemView.setOnClickListener { onItemClicked(category) }
    }

    override fun getItemCount() = categories.size

    class CategoryViewHolder(private val binding: ItemMealCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: MealCategory) {
            binding.categoryName.text = category.name
            binding.categoryImage.setImageResource(category.imageResId)
        }
    }
} 