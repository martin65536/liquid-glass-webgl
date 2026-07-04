package defpackage;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.PathInterpolator;
import java.util.Collections;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h61 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ p61 a;
    public final /* synthetic */ k71 b;
    public final /* synthetic */ k71 c;
    public final /* synthetic */ int d;
    public final /* synthetic */ View e;

    public h61(p61 p61Var, k71 k71Var, k71 k71Var2, int i, View view) {
        this.a = p61Var;
        this.b = k71Var;
        this.c = k71Var2;
        this.d = i;
        this.e = view;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        x61 q61Var;
        float f;
        p61 p61Var;
        k71 k71Var;
        g10 b;
        float animatedFraction = valueAnimator.getAnimatedFraction();
        p61 p61Var2 = this.a;
        o61 o61Var = p61Var2.a;
        o61Var.e(animatedFraction);
        float c = o61Var.c();
        PathInterpolator pathInterpolator = l61.e;
        int i = Build.VERSION.SDK_INT;
        k71 k71Var2 = this.b;
        if (i >= 36) {
            q61Var = new w61(k71Var2);
        } else if (i >= 35) {
            q61Var = new v61(k71Var2);
        } else if (i >= 34) {
            q61Var = new u61(k71Var2);
        } else if (i >= 31) {
            q61Var = new t61(k71Var2);
        } else if (i >= 30) {
            q61Var = new s61(k71Var2);
        } else if (i >= 29) {
            q61Var = new r61(k71Var2);
        } else {
            q61Var = new q61(k71Var2);
        }
        int i2 = 1;
        while (i2 <= 512) {
            int i3 = this.d & i2;
            g71 g71Var = k71Var2.a;
            if (i3 == 0) {
                q61Var.d(i2, g71Var.h(i2));
                f = c;
                p61Var = p61Var2;
                k71Var = k71Var2;
            } else {
                g10 h = g71Var.h(i2);
                g10 h2 = this.c.a.h(i2);
                int i4 = h.a;
                int i5 = h.d;
                int i6 = h.c;
                int i7 = h.b;
                float f2 = 1.0f - c;
                int i8 = (int) (((i4 - h2.a) * f2) + 0.5d);
                int i9 = (int) (((i7 - h2.b) * f2) + 0.5d);
                f = c;
                p61Var = p61Var2;
                int i10 = (int) (((i6 - h2.c) * f2) + 0.5d);
                float f3 = (i5 - h2.d) * f2;
                k71Var = k71Var2;
                int i11 = (int) (f3 + 0.5d);
                int max = Math.max(0, i4 - i8);
                int max2 = Math.max(0, i7 - i9);
                int max3 = Math.max(0, i6 - i10);
                int max4 = Math.max(0, i5 - i11);
                if (max == i8 && max2 == i9 && max3 == i10 && max4 == i11) {
                    b = h;
                } else {
                    b = g10.b(max, max2, max3, max4);
                }
                q61Var.d(i2, b);
            }
            i2 <<= 1;
            p61Var2 = p61Var;
            k71Var2 = k71Var;
            c = f;
        }
        l61.h(this.e, q61Var.b(), Collections.singletonList(p61Var2));
    }
}
