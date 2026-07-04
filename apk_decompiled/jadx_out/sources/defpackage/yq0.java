package defpackage;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.animation.AnimationUtils;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yq0 extends View {
    public static final int[] j = {R.attr.state_pressed, R.attr.state_enabled};
    public static final int[] k = new int[0];
    public b41 e;
    public Boolean f;
    public Long g;
    public n h;
    public f6 i;

    private final void setRippleState(boolean z) {
        long j2;
        int[] iArr;
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        Runnable runnable = this.h;
        if (runnable != null) {
            removeCallbacks(runnable);
            runnable.run();
        }
        Long l = this.g;
        if (l != null) {
            j2 = l.longValue();
        } else {
            j2 = 0;
        }
        long j3 = currentAnimationTimeMillis - j2;
        if (!z && j3 < 5) {
            n nVar = new n(7, this);
            this.h = nVar;
            postDelayed(nVar, 50L);
        } else {
            if (z) {
                iArr = j;
            } else {
                iArr = k;
            }
            b41 b41Var = this.e;
            if (b41Var != null) {
                b41Var.setState(iArr);
            }
        }
        this.g = Long.valueOf(currentAnimationTimeMillis);
    }

    public static final void setRippleState$lambda$1(yq0 yq0Var) {
        b41 b41Var = yq0Var.e;
        if (b41Var != null) {
            b41Var.setState(k);
        }
        yq0Var.h = null;
    }

    public final void b(on0 on0Var, boolean z, long j2, int i, long j3, f6 f6Var) {
        if (this.e == null || !Boolean.valueOf(z).equals(this.f)) {
            b41 b41Var = new b41(z);
            setBackground(b41Var);
            this.e = b41Var;
            this.f = Boolean.valueOf(z);
        }
        b41 b41Var2 = this.e;
        b41Var2.getClass();
        this.i = f6Var;
        e(i, j2, j3);
        if (z) {
            b41Var2.setHotspot(Float.intBitsToFloat((int) (on0Var.a >> 32)), Float.intBitsToFloat((int) (on0Var.a & 4294967295L)));
        } else {
            b41Var2.setHotspot(b41Var2.getBounds().centerX(), b41Var2.getBounds().centerY());
        }
        setRippleState(true);
    }

    public final void c() {
        this.i = null;
        n nVar = this.h;
        if (nVar != null) {
            removeCallbacks(nVar);
            n nVar2 = this.h;
            nVar2.getClass();
            nVar2.run();
        } else {
            b41 b41Var = this.e;
            if (b41Var != null) {
                b41Var.setState(k);
            }
        }
        b41 b41Var2 = this.e;
        if (b41Var2 == null) {
            return;
        }
        b41Var2.setVisible(false, false);
        unscheduleDrawable(b41Var2);
    }

    public final void d() {
        setRippleState(false);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        if (!isAttachedToWindow()) {
            c();
        } else {
            super.draw(canvas);
        }
    }

    public final void e(int i, long j2, long j3) {
        float f;
        boolean c;
        b41 b41Var = this.e;
        if (b41Var == null) {
            return;
        }
        if (b41Var.getRadius() != i) {
            b41Var.setRadius(i);
        }
        if (Build.VERSION.SDK_INT < 28) {
            f = 0.2f;
        } else {
            f = 0.1f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        long b = se.b(j3, f);
        se seVar = b41Var.f;
        if (seVar == null) {
            c = false;
        } else {
            c = se.c(seVar.a, b);
        }
        if (!c) {
            b41Var.f = new se(b);
            b41Var.setColor(ColorStateList.valueOf(f31.P(b)));
        }
        Rect rect = new Rect(0, 0, jc0.G(Float.intBitsToFloat((int) (j2 >> 32))), jc0.G(Float.intBitsToFloat((int) (j2 & 4294967295L))));
        setLeft(rect.left);
        setTop(rect.top);
        setRight(rect.right);
        setBottom(rect.bottom);
        b41Var.setBounds(rect);
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        f6 f6Var = this.i;
        if (f6Var != null) {
            f6Var.a();
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    @Override // android.view.View
    public final void refreshDrawableState() {
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }
}
