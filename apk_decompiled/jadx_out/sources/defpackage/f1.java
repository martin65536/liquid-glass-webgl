package defpackage;

import android.graphics.Insets;
import android.os.ext.SdkExtensions;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.accessibility.AccessibilityNodeInfo;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class f1 {
    public static void a(int i) {
        SdkExtensions.getExtensionVersion(i);
    }

    public static CharSequence b(AccessibilityNodeInfo accessibilityNodeInfo) {
        return accessibilityNodeInfo.getStateDescription();
    }

    public static Insets c(DisplayCutout displayCutout) {
        return displayCutout.getWaterfallInsets();
    }

    public static void d(Window window) {
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 256);
        window.setDecorFitsSystemWindows(false);
    }

    public static void e(Window window) {
        window.setDecorFitsSystemWindows(false);
    }

    public static void f(View view) {
        view.setImportantForContentCapture(1);
    }

    public static void g(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
        accessibilityNodeInfo.setStateDescription(charSequence);
    }
}
