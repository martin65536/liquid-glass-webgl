package defpackage;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.view.DisplayListCanvas;
import android.view.RenderNode;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nx implements jx {
    public static final AtomicBoolean A = new AtomicBoolean(true);
    public final zc b;
    public final yc c;
    public final RenderNode d;
    public long e;
    public Paint f;
    public Matrix g;
    public boolean h;
    public long i;
    public int j;
    public int k;
    public te l;
    public float m;
    public boolean n;
    public float o;
    public float p;
    public float q;
    public float r;
    public long s;
    public long t;
    public float u;
    public float v;
    public boolean w;
    public boolean x;
    public boolean y;
    public gh z;

    public nx(b4 b4Var, zc zcVar, yc ycVar) {
        this.b = zcVar;
        this.c = ycVar;
        RenderNode create = RenderNode.create("Compose", b4Var);
        this.d = create;
        this.e = 0L;
        this.i = 0L;
        if (A.getAndSet(false)) {
            create.setScaleX(create.getScaleX());
            create.setScaleY(create.getScaleY());
            create.setTranslationX(create.getTranslationX());
            create.setTranslationY(create.getTranslationY());
            create.setElevation(create.getElevation());
            create.setRotation(create.getRotation());
            create.setRotationX(create.getRotationX());
            create.setRotationY(create.getRotationY());
            create.setCameraDistance(create.getCameraDistance());
            create.setPivotX(create.getPivotX());
            create.setPivotY(create.getPivotY());
            create.setClipToOutline(create.getClipToOutline());
            create.setClipToBounds(false);
            create.setAlpha(create.getAlpha());
            create.isValid();
            create.setLeftTopRightBottom(0, 0, 0, 0);
            create.offsetLeftAndRight(0);
            create.offsetTopAndBottom(0);
            int i = Build.VERSION.SDK_INT;
            if (i >= 28) {
                up0.c(create, up0.a(create));
                up0.d(create, up0.b(create));
            }
            if (i >= 24) {
                tp0.a(create);
            } else {
                sp0.a(create);
            }
            create.setLayerType(0);
            create.setHasOverlappingRendering(create.hasOverlappingRendering());
        }
        create.setClipToBounds(false);
        Q(0);
        this.j = 0;
        this.k = 3;
        this.m = 1.0f;
        this.o = 1.0f;
        this.p = 1.0f;
        long j = se.b;
        this.s = j;
        this.t = j;
        this.v = 8.0f;
    }

    @Override // defpackage.jx
    public final float A() {
        return this.v;
    }

    @Override // defpackage.jx
    public final void B() {
        int i = Build.VERSION.SDK_INT;
        RenderNode renderNode = this.d;
        if (i >= 24) {
            tp0.a(renderNode);
        } else {
            sp0.a(renderNode);
        }
    }

    @Override // defpackage.jx
    public final float C() {
        return this.q;
    }

    @Override // defpackage.jx
    public final void D(uc ucVar) {
        DisplayListCanvas a = j3.a(ucVar);
        a.getClass();
        a.drawRenderNode(this.d);
    }

    @Override // defpackage.jx
    public final int E() {
        return this.j;
    }

    @Override // defpackage.jx
    public final float F() {
        return 0.0f;
    }

    @Override // defpackage.jx
    public final te G() {
        return this.l;
    }

    @Override // defpackage.jx
    public final void H(int i) {
        this.j = i;
        R();
    }

    @Override // defpackage.jx
    public final Matrix I() {
        Matrix matrix = this.g;
        if (matrix == null) {
            matrix = new Matrix();
            this.g = matrix;
        }
        this.d.getMatrix(matrix);
        return matrix;
    }

    @Override // defpackage.jx
    public final void J(int i, int i2, long j) {
        int i3 = (int) (j >> 32);
        int i4 = (int) (4294967295L & j);
        this.d.setLeftTopRightBottom(i, i2, i + i3, i2 + i4);
        if (!c20.a(this.e, j)) {
            if (this.n) {
                this.d.setPivotX(i3 / 2.0f);
                this.d.setPivotY(i4 / 2.0f);
            }
            this.e = j;
        }
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
    public final boolean M() {
        return this.d.isValid();
    }

    @Override // defpackage.jx
    public final float N() {
        return this.u;
    }

    @Override // defpackage.jx
    public final void O(long j) {
        if ((9223372034707292159L & j) == 9205357640488583168L) {
            this.n = true;
            this.d.setPivotX(((int) (this.e >> 32)) / 2.0f);
            this.d.setPivotY(((int) (4294967295L & this.e)) / 2.0f);
        } else {
            this.n = false;
            this.d.setPivotX(Float.intBitsToFloat((int) (j >> 32)));
            this.d.setPivotY(Float.intBitsToFloat((int) (j & 4294967295L)));
        }
    }

    @Override // defpackage.jx
    public final long P() {
        return this.s;
    }

    public final void Q(int i) {
        RenderNode renderNode = this.d;
        if (i == 1) {
            renderNode.setLayerType(2);
            renderNode.setLayerPaint(this.f);
            renderNode.setHasOverlappingRendering(true);
        } else if (i == 2) {
            renderNode.setLayerType(0);
            renderNode.setLayerPaint(this.f);
            renderNode.setHasOverlappingRendering(false);
        } else {
            renderNode.setLayerType(0);
            renderNode.setLayerPaint(this.f);
            renderNode.setHasOverlappingRendering(true);
        }
    }

    public final void R() {
        int i = this.j;
        if (i != 1 && this.k == 3 && this.l == null) {
            Q(i);
        } else {
            Q(1);
        }
    }

    @Override // defpackage.jx
    public final void a() {
        this.d.setRotationX(0.0f);
    }

    @Override // defpackage.jx
    public final void b(float f) {
        this.m = f;
        this.d.setAlpha(f);
    }

    @Override // defpackage.jx
    public final float c() {
        return this.o;
    }

    @Override // defpackage.jx
    public final void d(te teVar) {
        this.l = teVar;
        if (teVar != null) {
            Q(1);
            RenderNode renderNode = this.d;
            Paint paint = this.f;
            if (paint == null) {
                paint = new Paint();
                this.f = paint;
            }
            paint.setColorFilter(teVar.a);
            renderNode.setLayerPaint(paint);
            return;
        }
        R();
    }

    @Override // defpackage.jx
    public final void e(float f) {
        this.u = f;
        this.d.setRotation(f);
    }

    @Override // defpackage.jx
    public final void f() {
        this.d.setRotationY(0.0f);
    }

    @Override // defpackage.jx
    public final void g(float f) {
        this.r = f;
        this.d.setTranslationY(f);
    }

    @Override // defpackage.jx
    public final void h(long j) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.s = j;
            up0.c(this.d, f31.P(j));
        }
    }

    @Override // defpackage.jx
    public final void i(float f) {
        this.o = f;
        this.d.setScaleX(f);
    }

    public final void j() {
        boolean z;
        boolean z2 = this.w;
        boolean z3 = false;
        if (z2 && !this.h) {
            z = true;
        } else {
            z = false;
        }
        if (z2 && this.h) {
            z3 = true;
        }
        if (z != this.x) {
            this.x = z;
            this.d.setClipToBounds(z);
        }
        if (z3 != this.y) {
            this.y = z3;
            this.d.setClipToOutline(z3);
        }
    }

    @Override // defpackage.jx
    public final void k(int i) {
        if (this.k == i) {
            return;
        }
        this.k = i;
        Paint paint = this.f;
        if (paint == null) {
            paint = new Paint();
            this.f = paint;
        }
        paint.setXfermode(new PorterDuffXfermode(f31.S(i)));
        R();
    }

    @Override // defpackage.jx
    public final void l() {
        this.d.setElevation(0.0f);
    }

    @Override // defpackage.jx
    public final void m(boolean z) {
        this.w = z;
        j();
    }

    @Override // defpackage.jx
    public final void n(float f) {
        this.q = f;
        this.d.setTranslationX(f);
    }

    @Override // defpackage.jx
    public final void o(long j) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.t = j;
            up0.d(this.d, f31.P(j));
        }
    }

    @Override // defpackage.jx
    public final void p(gh ghVar) {
        this.z = ghVar;
    }

    @Override // defpackage.jx
    public final void q(float f) {
        this.p = f;
        this.d.setScaleY(f);
    }

    @Override // defpackage.jx
    public final float r() {
        return this.m;
    }

    @Override // defpackage.jx
    public final void s(float f) {
        this.v = f;
        this.d.setCameraDistance(-f);
    }

    @Override // defpackage.jx
    public final float t() {
        return this.p;
    }

    @Override // defpackage.jx
    public final int u() {
        return this.k;
    }

    @Override // defpackage.jx
    public final void v(mm mmVar, m40 m40Var, hx hxVar, q2 q2Var) {
        Canvas start = this.d.start(Math.max((int) (this.e >> 32), (int) (this.i >> 32)), Math.max((int) (this.e & 4294967295L), (int) (4294967295L & this.i)));
        try {
            i3 i3Var = this.b.a;
            Canvas canvas = i3Var.a;
            i3Var.a = start;
            yc ycVar = this.c;
            r7 r7Var = ycVar.f;
            long J = d20.J(this.e);
            mm s = r7Var.s();
            m40 u = r7Var.u();
            uc q = r7Var.q();
            long v = r7Var.v();
            hx hxVar2 = (hx) r7Var.g;
            r7Var.E(mmVar);
            r7Var.F(m40Var);
            r7Var.D(i3Var);
            r7Var.G(J);
            r7Var.g = hxVar;
            i3Var.h();
            try {
                q2Var.e(ycVar);
                i3Var.f();
                r7Var.E(s);
                r7Var.F(u);
                r7Var.D(q);
                r7Var.G(v);
                r7Var.g = hxVar2;
                i3Var.a = canvas;
            } catch (Throwable th) {
                i3Var.f();
                r7 r7Var2 = ycVar.f;
                r7Var2.E(s);
                r7Var2.F(u);
                r7Var2.D(q);
                r7Var2.G(v);
                r7Var2.g = hxVar2;
                throw th;
            }
        } finally {
            this.d.end(start);
        }
    }

    @Override // defpackage.jx
    public final float w() {
        return this.r;
    }

    @Override // defpackage.jx
    public final gh x() {
        return this.z;
    }

    @Override // defpackage.jx
    public final long y() {
        return this.t;
    }

    @Override // defpackage.jx
    public final void z(Outline outline, long j) {
        boolean z;
        this.i = j;
        this.d.setOutline(outline);
        if (outline != null) {
            z = true;
        } else {
            z = false;
        }
        this.h = z;
        j();
    }
}
