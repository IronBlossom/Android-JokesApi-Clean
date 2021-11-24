package ishtiaq.codingchallenge.randomjokes

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun AppCompatActivity.changeFragment(
    fragment: Fragment,
    fragmentContainerId: Int,
    replace: Boolean = true,
    backStackTag: String? = null
) {
    with(supportFragmentManager.beginTransaction()) {
        if (replace) {
            replace(fragmentContainerId, fragment)
        } else {
            add(fragmentContainerId, fragment)
        }

        backStackTag?.let { addToBackStack(it) }

        commit()
    }
}

fun AppCompatActivity.makeFullScreen(activity: AppCompatActivity) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, activity.window.decorView.rootView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun AppCompatActivity.makeDefaultScreen(activity: AppCompatActivity) {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(
        window,
        activity.window.decorView.rootView
    ).show(WindowInsetsCompat.Type.systemBars())
    activity.actionBar?.show()
}