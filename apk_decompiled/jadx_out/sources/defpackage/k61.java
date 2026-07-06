package defpackage;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import com.kyant.backdrop.catalog.R;
import java.util.Objects;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k61 implements View.OnApplyWindowInsetsListener {
    public final g61 a;
    public k71 b;

    public k61(View view, g61 g61Var) {
        k71 k71Var;
        x61 q61Var;
        this.a = g61Var;
        int i = j51.a;
        k71 a = f51.a(view);
        if (a != null) {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 36) {
                q61Var = new w61(a);
            } else if (i2 >= 35) {
                q61Var = new v61(a);
            } else if (i2 >= 34) {
                q61Var = new u61(a);
            } else if (i2 >= 31) {
                q61Var = new t61(a);
            } else if (i2 >= 30) {
                q61Var = new s61(a);
            } else if (i2 >= 29) {
                q61Var = new r61(a);
            } else {
                q61Var = new q61(a);
            }
            k71Var = q61Var.b();
        } else {
            k71Var = null;
        }
        this.b = k71Var;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        Interpolator interpolator;
        long j;
        int[] iArr;
        boolean z;
        boolean z2;
        if (!view.isLaidOut()) {
            this.b = k71.b(windowInsets, view);
            if (view.getTag(R.id.tag_on_apply_window_listener) != null) {
                return windowInsets;
            }
            return view.onApplyWindowInsets(windowInsets);
        }
        k71 b = k71.b(windowInsets, view);
        g71 g71Var = b.a;
        if (this.b == null) {
            int i = j51.a;
            this.b = f51.a(view);
        }
        if (this.b == null) {
            this.b = b;
            if (view.getTag(R.id.tag_on_apply_window_listener) == null) {
                return view.onApplyWindowInsets(windowInsets);
            }
        } else {
            g61 j2 = l61.j(view);
            if (j2 != null && Objects.equals(j2.e, b)) {
                if (view.getTag(R.id.tag_on_apply_window_listener) == null) {
                    return view.onApplyWindowInsets(windowInsets);
                }
            } else {
                int[] iArr2 = new int[1];
                int[] iArr3 = new int[1];
                k71 k71Var = this.b;
                int i2 = 1;
                while (i2 <= 512) {
                    g10 h = g71Var.h(i2);
                    g10 h2 = k71Var.a.h(i2);
                    int i3 = h.a;
                    int i4 = h.d;
                    int i5 = h.c;
                    int i6 = h.b;
                    int i7 = h2.a;
                    int i8 = h2.d;
                    int[] iArr4 = iArr2;
                    int i9 = h2.c;
                    int i10 = h2.b;
                    if (i3 <= i7 && i6 <= i10 && i5 <= i9 && i4 <= i8) {
                        iArr = iArr3;
                        z = false;
                    } else {
                        iArr = iArr3;
                        z = true;
                    }
                    if (i3 >= i7 && i6 >= i10 && i5 >= i9 && i4 >= i8) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (z != z2) {
                        if (z) {
                            iArr4[0] = iArr4[0] | i2;
                        } else {
                            iArr[0] = iArr[0] | i2;
                        }
                    }
                    i2 <<= 1;
                    iArr2 = iArr4;
                    iArr3 = iArr;
                }
                int i11 = iArr2[0];
                int i12 = iArr3[0];
                int i13 = i11 | i12;
                if (i13 == 0) {
                    this.b = b;
                    if (view.getTag(R.id.tag_on_apply_window_listener) == null) {
                        return view.onApplyWindowInsets(windowInsets);
                    }
                } else {
                    k71 k71Var2 = this.b;
                    if ((i11 & 8) != 0) {
                        interpolator = l61.e;
                    } else if ((i12 & 8) != 0) {
                        interpolator = l61.f;
                    } else if ((i11 & 519) != 0) {
                        interpolator = l61.g;
                    } else if ((i12 & 519) != 0) {
                        interpolator = l61.h;
                    } else {
                        interpolator = null;
                    }
                    if ((i13 & 8) != 0) {
                        j = 160;
                    } else {
                        j = 250;
                    }
                    p61 p61Var = new p61(i13, interpolator, j);
                    p61Var.a.e(0.0f);
                    ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(p61Var.a.b());
                    g10 h3 = g71Var.h(i13);
                    g10 h4 = k71Var2.a.h(i13);
                    int min = Math.min(h3.a, h4.a);
                    int i14 = h3.b;
                    int i15 = h4.b;
                    int min2 = Math.min(i14, i15);
                    int i16 = h3.c;
                    int i17 = h4.c;
                    int min3 = Math.min(i16, i17);
                    int i18 = h3.d;
                    int i19 = h4.d;
                    c4 c4Var = new c4(26, g10.b(min, min2, min3, Math.min(i18, i19)), g10.b(Math.max(h3.a, h4.a), Math.max(i14, i15), Math.max(i16, i17), Math.max(i18, i19)));
                    l61.g(view, p61Var, b, false);
                    duration.addUpdateListener(new h61(p61Var, b, k71Var2, i13, view));
                    duration.addListener(new i61(p61Var, view));
                    qh0 qh0Var = new qh0(view, new j61(view, p61Var, c4Var, duration));
                    view.getViewTreeObserver().addOnPreDrawListener(qh0Var);
                    view.addOnAttachStateChangeListener(qh0Var);
                    this.b = b;
                    if (view.getTag(R.id.tag_on_apply_window_listener) == null) {
                        return view.onApplyWindowInsets(windowInsets);
                    }
                }
            }
        }
        return windowInsets;
    }
}
