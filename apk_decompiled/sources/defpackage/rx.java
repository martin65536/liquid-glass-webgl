package defpackage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RenderEffect;
import android.os.Build;
import android.view.View;
import android.view.ViewParent;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rx implements jx {
    public static final qx A = new Canvas();
    public final sp b;
    public final zc c;
    public final q51 d;
    public final Resources e;
    public final Rect f;
    public Paint g;
    public int h;
    public int i;
    public long j;
    public boolean k;
    public boolean l;
    public boolean m;
    public int n;
    public te o;
    public int p;
    public float q;
    public boolean r;
    public float s;
    public float t;
    public float u;
    public float v;
    public long w;
    public long x;
    public float y;
    public gh z;

    public rx(sp spVar) {
        zc zcVar = new zc();
        yc ycVar = new yc();
        this.b = spVar;
        this.c = zcVar;
        q51 q51Var = new q51(spVar, zcVar, ycVar);
        this.d = q51Var;
        this.e = spVar.getResources();
        this.f = new Rect();
        spVar.addView(q51Var);
        q51Var.setClipBounds(null);
        this.j = 0L;
        View.generateViewId();
        this.n = 3;
        this.p = 0;
        this.q = 1.0f;
        this.s = 1.0f;
        this.t = 1.0f;
        long j = se.b;
        this.w = j;
        this.x = j;
    }

    @Override // defpackage.jx
    public final float A() {
        return this.d.getCameraDistance() / this.e.getDisplayMetrics().densityDpi;
    }

    @Override // defpackage.jx
    public final void B() {
        this.b.removeViewInLayout(this.d);
    }

    @Override // defpackage.jx
    public final float C() {
        return this.u;
    }

    @Override // defpackage.jx
    public final void D(uc ucVar) {
        Rect rect;
        boolean z = this.k;
        q51 q51Var = this.d;
        if (z) {
            if ((this.m || q51Var.getClipToOutline()) && !this.l) {
                rect = this.f;
                rect.left = 0;
                rect.top = 0;
                rect.right = q51Var.getWidth();
                rect.bottom = q51Var.getHeight();
            } else {
                rect = null;
            }
            q51Var.setClipBounds(rect);
        }
        if (j3.a(ucVar).isHardwareAccelerated()) {
            this.b.a(ucVar, q51Var, q51Var.getDrawingTime());
        }
    }

    @Override // defpackage.jx
    public final int E() {
        return this.p;
    }

    @Override // defpackage.jx
    public final float F() {
        return 0.0f;
    }

    @Override // defpackage.jx
    public final te G() {
        return this.o;
    }

    @Override // defpackage.jx
    public final void H(int i) {
        this.p = i;
        Q();
    }

    @Override // defpackage.jx
    public final Matrix I() {
        return this.d.getMatrix();
    }

    @Override // defpackage.jx
    public final void J(int i, int i2, long j) {
        boolean a = c20.a(this.j, j);
        q51 q51Var = this.d;
        if (!a) {
            if (this.m || q51Var.getClipToOutline()) {
                this.k = true;
            }
            int i3 = (int) (j >> 32);
            int i4 = (int) (4294967295L & j);
            q51Var.layout(i, i2, i + i3, i2 + i4);
            this.j = j;
            if (this.r) {
                q51Var.setPivotX(i3 / 2.0f);
                q51Var.setPivotY(i4 / 2.0f);
            }
        } else {
            int i5 = this.h;
            if (i5 != i) {
                q51Var.offsetLeftAndRight(i - i5);
            }
            int i6 = this.i;
            if (i6 != i2) {
                q51Var.offsetTopAndBottom(i2 - i6);
            }
        }
        this.h = i;
        this.i = i2;
    }

    @Override // defpackage.jx
    public final float K() {
        return 0.0f;
    }

    @Override // defpackage.jx
    public final float L() {
        return 0.0f;
    }

    @Override // defpackage.jx
    public final /* synthetic */ boolean M() {
        return true;
    }

    @Override // defpackage.jx
    public final float N() {
        return this.y;
    }

    @Override // defpackage.jx
    public final void O(long j) {
        long j2 = 9223372034707292159L & j;
        q51 q51Var = this.d;
        if (j2 == 9205357640488583168L) {
            if (Build.VERSION.SDK_INT >= 28) {
                q51Var.resetPivot();
                return;
            }
            this.r = true;
            q51Var.setPivotX(((int) (this.j >> 32)) / 2.0f);
            q51Var.setPivotY(((int) (this.j & 4294967295L)) / 2.0f);
            return;
        }
        this.r = false;
        q51Var.setPivotX(Float.intBitsToFloat((int) (j >> 32)));
        q51Var.setPivotY(Float.intBitsToFloat((int) (j & 4294967295L)));
    }

    @Override // defpackage.jx
    public final long P() {
        return this.w;
    }

    public final void Q() {
        int i = this.p;
        if (i != 1 && this.n == 3 && this.o == null) {
            j(i);
        } else {
            j(1);
        }
    }

    @Override // defpackage.jx
    public final void a() {
        this.d.setRotationX(0.0f);
    }

    @Override // defpackage.jx
    public final void b(float f) {
        this.q = f;
        this.d.setAlpha(f);
    }

    @Override // defpackage.jx
    public final float c() {
        return this.s;
    }

    @Override // defpackage.jx
    public final void d(te teVar) {
        ColorFilter colorFilter;
        this.o = teVar;
        Paint paint = this.g;
        if (paint == null) {
            paint = new Paint();
            this.g = paint;
        }
        if (teVar != null) {
            colorFilter = teVar.a;
        } else {
            colorFilter = null;
        }
        paint.setColorFilter(colorFilter);
        Q();
    }

    @Override // defpackage.jx
    public final void e(float f) {
        this.y = f;
        this.d.setRotation(f);
    }

    @Override // defpackage.jx
    public final void f() {
        this.d.setRotationY(0.0f);
    }

    @Override // defpackage.jx
    public final void g(float f) {
        this.v = f;
        this.d.setTranslationY(f);
    }

    @Override // defpackage.jx
    public final void h(long j) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.w = j;
            this.d.setOutlineAmbientShadowColor(f31.P(j));
        }
    }

    @Override // defpackage.jx
    public final void i(float f) {
        this.s = f;
        this.d.setScaleX(f);
    }

    public final void j(int i) {
        q51 q51Var = this.d;
        boolean z = true;
        if (i == 1) {
            q51Var.setLayerType(2, this.g);
        } else {
            Paint paint = this.g;
            if (i == 2) {
                q51Var.setLayerType(0, paint);
                z = false;
            } else {
                q51Var.setLayerType(0, paint);
            }
        }
        q51Var.setCanUseCompositingLayer$ui_graphics(z);
    }

    @Override // defpackage.jx
    public final void k(int i) {
        this.n = i;
        Paint paint = this.g;
        if (paint == null) {
            paint = new Paint();
            this.g = paint;
        }
        paint.setXfermode(new PorterDuffXfermode(f31.S(i)));
        Q();
    }

    @Override // defpackage.jx
    public final void l() {
        this.d.setElevation(0.0f);
    }

    @Override // defpackage.jx
    public final void m(boolean z) {
        boolean z2;
        boolean z3 = false;
        if (z && !this.l) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.m = z2;
        this.k = true;
        if (z && this.l) {
            z3 = true;
        }
        this.d.setClipToOutline(z3);
    }

    @Override // defpackage.jx
    public final void n(float f) {
        this.u = f;
        this.d.setTranslationX(f);
    }

    @Override // defpackage.jx
    public final void o(long j) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.x = j;
            this.d.setOutlineSpotShadowColor(f31.P(j));
        }
    }

    @Override // defpackage.jx
    public final void p(gh ghVar) {
        RenderEffect renderEffect;
        this.z = ghVar;
        if (Build.VERSION.SDK_INT >= 31) {
            if (ghVar != null) {
                renderEffect = ghVar.c();
            } else {
                renderEffect = null;
            }
            this.d.setRenderEffect(renderEffect);
        }
    }

    @Override // defpackage.jx
    public final void q(float f) {
        this.t = f;
        this.d.setScaleY(f);
    }

    @Override // defpackage.jx
    public final float r() {
        return this.q;
    }

    @Override // defpackage.jx
    public final void s(float f) {
        this.d.setCameraDistance(f * this.e.getDisplayMetrics().densityDpi);
    }

    @Override // defpackage.jx
    public final float t() {
        return this.t;
    }

    @Override // defpackage.jx
    public final int u() {
        return this.n;
    }

    @Override // defpackage.jx
    public final void v(mm mmVar, m40 m40Var, hx hxVar, q2 q2Var) {
        q51 q51Var = this.d;
        ViewParent parent = q51Var.getParent();
        sp spVar = this.b;
        if (parent == null) {
            spVar.addView(q51Var);
        }
        q51Var.k = mmVar;
        q51Var.l = m40Var;
        q51Var.m = q2Var;
        q51Var.n = hxVar;
        if (q51Var.isAttachedToWindow()) {
            q51Var.setVisibility(4);
            q51Var.setVisibility(0);
            try {
                zc zcVar = this.c;
                qx qxVar = A;
                i3 i3Var = zcVar.a;
                Canvas canvas = i3Var.a;
                i3Var.a = qxVar;
                spVar.a(i3Var, q51Var, q51Var.getDrawingTime());
                zcVar.a.a = canvas;
            } catch (ClassCastException unused) {
            }
        }
    }

    @Override // defpackage.jx
    public final float w() {
        return this.v;
    }

    @Override // defpackage.jx
    public final gh x() {
        return this.z;
    }

    @Override // defpackage.jx
    public final long y() {
        return this.x;
    }

    @Override // defpackage.jx
    public final void z(Outline outline, long j) {
        q51 q51Var = this.d;
        q51Var.i = outline;
        q51Var.invalidateOutline();
        boolean z = false;
        if ((this.m || q51Var.getClipToOutline()) && outline != null) {
            q51Var.setClipToOutline(true);
            if (this.m) {
                this.m = false;
                this.k = true;
            }
        }
        if (outline != null) {
            z = true;
        }
        this.l = z;
    }
}
