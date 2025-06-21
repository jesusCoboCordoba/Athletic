package com.politecnico.athleticapp.ui.models

import com.politecnico.athleticapp.R

object RoutineDataProvider {
    fun getRoutineAExercises(): List<Exercise> {
        return listOf(
            Exercise(name = "Squats", setsAndReps = "3 Sets x 12 Reps", imageResId = R.drawable.how_to_do_squats, description = "A squat is a strength exercise in which the trainee lowers their hips from a standing position and then stands back up."),
            Exercise(name = "Dumbbell Row", setsAndReps = "3 Sets x 12 Reps", imageResId = R.drawable.how_to_do_one_arm_dumbbell_row, description = "The dumbbell row is a classic back-building exercise that can be done with one or two dumbbells."),
            Exercise(name = "Lunge", setsAndReps = "3 Sets x 12 Reps", imageResId = R.drawable.how_to_do_jumping_lunges, description = "A lunge can refer to any position of the human body where one leg is positioned forward with knee bent and foot flat on the ground while the other leg is positioned behind."),
            Exercise(name = "Push-ups", setsAndReps = "3 Sets x 10 Reps", imageResId = R.drawable.how_to_do_push_up, description = "A push-up is a common calisthenics exercise beginning from the prone position. By raising and lowering the body using the arms, push-ups exercise the pectoral muscles, triceps, and anterior deltoids.")
        )
    }

    fun getChallengeExercises(): List<Exercise> {
        return listOf(
            Exercise(name = "Jumping Jacks", setsAndReps = "3 Sets x 30 Reps", imageResId = R.drawable.how_to_do_jump_squat, description = "Jumping jacks are a physical jumping exercise performed by jumping to a position with the legs spread wide and the hands going overhead."),
            Exercise(name = "High Knees", setsAndReps = "3 Sets x 20 Reps", imageResId = R.drawable.how_to_do_dumbbell_lateral_raise, description = "High knees is a cardiovascular exercise that is done at a fast pace. It involves running in place, but lifting the knees higher than you normally would when running."),
            Exercise(name = "Burpees", setsAndReps = "3 Sets x 10 Reps", imageResId = R.drawable.how_to_do_mountain_climbers, description = "The burpee is a full body exercise used in strength training and as an aerobic exercise. The basic movement is performed in four steps and known as a four-count burpee.")
        )
    }

    fun getExerciseByName(name: String): Exercise? {
        return getRoutineAExercises().find { it.name == name } ?: getChallengeExercises().find { it.name == name }
    }
} 