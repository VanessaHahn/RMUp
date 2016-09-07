package mi.ur.de.android.runnersmeetup;

/**
 * Created by Vanessa on 07.09.2016.
 */
public class ActivityManager {
    private static boolean mIsVisible = false;

    public static void setIsVisible(boolean visible) {
        mIsVisible = visible;
    }

    public static boolean getIsVisible() {
        return mIsVisible;
    }
}
