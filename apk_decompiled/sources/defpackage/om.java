package defpackage;

import android.content.ContextWrapper;
import android.graphics.Rect;
import android.view.WindowManager;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class om implements nm, u71 {
    public static final om e = new Object();
    public static final om f = new Object();

    @Override // defpackage.u71
    public q71 c(ContextWrapper contextWrapper, nm nmVar) {
        WindowManager windowManager;
        nmVar.getClass();
        if (contextWrapper.isUiContext()) {
            windowManager = (WindowManager) contextWrapper.getSystemService(WindowManager.class);
        } else {
            windowManager = (WindowManager) contextWrapper.getApplicationContext().getSystemService(WindowManager.class);
        }
        Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
        bounds.getClass();
        return new q71(bounds, windowManager.getCurrentWindowMetrics().getDensity());
    }

    @Override // defpackage.nm
    public float f(ContextWrapper contextWrapper) {
        return ((WindowManager) contextWrapper.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getDensity();
    }
}
