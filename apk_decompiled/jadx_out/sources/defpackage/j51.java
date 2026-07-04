package defpackage;

import android.os.Build;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.kyant.backdrop.catalog.R;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class j51 {
    public static final /* synthetic */ int a = 0;

    static {
        new WeakHashMap();
    }

    public static void a(View view, g61 g61Var) {
        View.OnApplyWindowInsetsListener onApplyWindowInsetsListener = null;
        m61 m61Var = null;
        if (Build.VERSION.SDK_INT >= 30) {
            if (g61Var != null) {
                m61Var = new m61(g61Var);
            }
            view.setWindowInsetsAnimationCallback(m61Var);
            return;
        }
        PathInterpolator pathInterpolator = l61.e;
        if (g61Var != null) {
            onApplyWindowInsetsListener = new k61(view, g61Var);
        }
        view.setTag(R.id.tag_window_insets_animation_callback, onApplyWindowInsetsListener);
        if (view.getTag(R.id.tag_compat_insets_dispatch) == null && view.getTag(R.id.tag_on_apply_window_listener) == null) {
            view.setOnApplyWindowInsetsListener(onApplyWindowInsetsListener);
        }
    }
}
