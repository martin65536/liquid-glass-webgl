package defpackage;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import com.kyant.backdrop.catalog.R;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l61 extends o61 {
    public static final PathInterpolator e = new PathInterpolator(0.0f, 1.1f, 0.0f, 1.0f);
    public static final wr f = new Object();
    public static final DecelerateInterpolator g = new DecelerateInterpolator(1.5f);
    public static final AccelerateInterpolator h = new AccelerateInterpolator(1.5f);

    public static void f(p61 p61Var, View view) {
        g61 j = j(view);
        if (j != null) {
            j.b(p61Var);
            if (j.f == 0) {
                return;
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                f(p61Var, viewGroup.getChildAt(i));
            }
        }
    }

    public static void g(View view, p61 p61Var, k71 k71Var, boolean z) {
        g61 j = j(view);
        if (j != null) {
            j.e = k71Var;
            if (!z) {
                j.c();
                if (j.f == 0) {
                    z = true;
                } else {
                    z = false;
                }
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                g(viewGroup.getChildAt(i), p61Var, k71Var, z);
            }
        }
    }

    public static void h(View view, k71 k71Var, List list) {
        g61 j = j(view);
        if (j != null) {
            k71Var = j.d(k71Var, list);
            if (j.f == 0) {
                return;
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                h(viewGroup.getChildAt(i), k71Var, list);
            }
        }
    }

    public static void i(View view, p61 p61Var, c4 c4Var) {
        g61 j = j(view);
        if (j != null) {
            j.e(p61Var, c4Var);
            if (j.f == 0) {
                return;
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                i(viewGroup.getChildAt(i), p61Var, c4Var);
            }
        }
    }

    public static g61 j(View view) {
        Object tag = view.getTag(R.id.tag_window_insets_animation_callback);
        if (tag instanceof k61) {
            return ((k61) tag).a;
        }
        return null;
    }
}
