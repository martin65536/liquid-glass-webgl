package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.os.Build;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class px implements jx {
    public final zc b;
    public final yc c;
    public final RenderNode d;
    public long e;
    public Paint f;
    public Matrix g;
    public boolean h;
    public float i;
    public int j;
    public te k;
    public float l;
    public float m;
    public float n;
    public float o;
    public long p;
    public long q;
    public float r;
    public float s;
    public boolean t;
    public boolean u;
    public boolean v;
    public gh w;
    public int x;

    public px() {
        zc zcVar = new zc();
        yc ycVar = new yc();
        this.b = zcVar;
        this.c = ycVar;
        RenderNode e = ox.e();
        this.d = e;
        this.e = 0L;
        e.setClipToBounds(false);
        Q(e, 0);
        this.i = 1.0f;
        this.j = 3;
        this.l = 1.0f;
        this.m = 1.0f;
        long j = se.b;
        this.p = j;
        this.q = j;
        this.s = 8.0f;
        this.x = 0;
    }

    @Override // defpackage.jx
    public final float A() {
        return this.s;
    }

    @Override // defpackage.jx
    public final void B() {
        this.d.discardDisplayList();
    }

    @Override // defpackage.jx
    public final float C() {
        return this.n;
    }

    @Override // defpackage.jx
    public final void D(uc ucVar) {
        j3.a(ucVar).drawRenderNode(this.d);
    }

    @Override // defpackage.jx
    public final int E() {
        return this.x;
    }

    @Override // defpackage.jx
    public final float F() {
        return 0.0f;
    }

    @Override // defpackage.jx
    public final te G() {
        return this.k;
    }

    @Override // defpackage.jx
    public final void H(int i) {
        this.x = i;
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
        this.d.setPosition(i, i2, ((int) (j >> 32)) + i, ((int) (4294967295L & j)) + i2);
        this.e = d20.J(j);
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
        boolean hasDisplayList;
        hasDisplayList = this.d.hasDisplayList();
        return hasDisplayList;
    }

    @Override // defpackage.jx
    public final float N() {
        return this.r;
    }

    @Override // defpackage.jx
    public final void O(long j) {
        long j2 = 9223372034707292159L & j;
        RenderNode renderNode = this.d;
        if (j2 == 9205357640488583168L) {
            renderNode.resetPivot();
        } else {
            renderNode.setPivotX(Float.intBitsToFloat((int) (j >> 32)));
            this.d.setPivotY(Float.intBitsToFloat((int) (j & 4294967295L)));
        }
    }

    @Override // defpackage.jx
    public final long P() {
        return this.p;
    }

    public final void Q(RenderNode renderNode, int i) {
        Paint paint = this.f;
        if (i == 1) {
            renderNode.setUseCompositingLayer(true, paint);
            renderNode.setHasOverlappingRendering(true);
        } else if (i == 2) {
            renderNode.setUseCompositingLayer(false, paint);
            renderNode.setHasOverlappingRendering(false);
        } else {
            renderNode.setUseCompositingLayer(false, paint);
            renderNode.setHasOverlappingRendering(true);
        }
    }

    public final void R() {
        int i = this.x;
        if (i != 1 && this.j == 3 && this.k == null && this.w == null) {
            Q(this.d, i);
        } else {
            Q(this.d, 1);
        }
    }

    @Override // defpackage.jx
    public final void a() {
        this.d.setRotationX(0.0f);
    }

    @Override // defpackage.jx
    public final void b(float f) {
        this.i = f;
        this.d.setAlpha(f);
    }

    @Override // defpackage.jx
    public final float c() {
        return this.l;
    }

    @Override // defpackage.jx
    public final void d(te teVar) {
        ColorFilter colorFilter;
        this.k = teVar;
        Paint paint = this.f;
        if (paint == null) {
            paint = new Paint();
            this.f = paint;
        }
        if (teVar != null) {
            colorFilter = teVar.a;
        } else {
            colorFilter = null;
        }
        paint.setColorFilter(colorFilter);
        R();
    }

    @Override // defpackage.jx
    public final void e(float f) {
        this.r = f;
        this.d.setRotationZ(f);
    }

    @Override // defpackage.jx
    public final void f() {
        this.d.setRotationY(0.0f);
    }

    @Override // defpackage.jx
    public final void g(float f) {
        this.o = f;
        this.d.setTranslationY(f);
    }

    @Override // defpackage.jx
    public final void h(long j) {
        this.p = j;
        this.d.setAmbientShadowColor(f31.P(j));
    }

    @Override // defpackage.jx
    public final void i(float f) {
        this.l = f;
        this.d.setScaleX(f);
    }

    public final void j() {
        boolean z;
        boolean z2 = this.t;
        boolean z3 = false;
        if (z2 && !this.h) {
            z = true;
        } else {
            z = false;
        }
        if (z2 && this.h) {
            z3 = true;
        }
        if (z != this.u) {
            this.u = z;
            this.d.setClipToBounds(z);
        }
        if (z3 != this.v) {
            this.v = z3;
            this.d.setClipToOutline(z3);
        }
    }

    @Override // defpackage.jx
    public final void k(int i) {
        this.j = i;
        Paint paint = this.f;
        if (paint == null) {
            paint = new Paint();
            this.f = paint;
        }
        paint.setBlendMode(f31.O(i));
        R();
    }

    @Override // defpackage.jx
    public final void l() {
        this.d.setElevation(0.0f);
    }

    @Override // defpackage.jx
    public final void m(boolean z) {
        this.t = z;
        j();
    }

    @Override // defpackage.jx
    public final void n(float f) {
        this.n = f;
        this.d.setTranslationX(f);
    }

    @Override // defpackage.jx
    public final void o(long j) {
        this.q = j;
        this.d.setSpotShadowColor(f31.P(j));
    }

    @Override // defpackage.jx
    public final void p(gh ghVar) {
        RenderEffect renderEffect;
        this.w = ghVar;
        if (Build.VERSION.SDK_INT >= 31) {
            RenderNode renderNode = this.d;
            if (ghVar != null) {
                renderEffect = ghVar.c();
            } else {
                renderEffect = null;
            }
            renderNode.setRenderEffect(renderEffect);
        }
    }

    @Override // defpackage.jx
    public final void q(float f) {
        this.m = f;
        this.d.setScaleY(f);
    }

    @Override // defpackage.jx
    public final float r() {
        return this.i;
    }

    @Override // defpackage.jx
    public final void s(float f) {
        this.s = f;
        this.d.setCameraDistance(f);
    }

    @Override // defpackage.jx
    public final float t() {
        return this.m;
    }

    @Override // defpackage.jx
    public final int u() {
        return this.j;
    }

    @Override // defpackage.jx
    public final void v(mm mmVar, m40 m40Var, hx hxVar, q2 q2Var) {
        RecordingCanvas beginRecording;
        yc ycVar = this.c;
        beginRecording = this.d.beginRecording();
        try {
            zc zcVar = this.b;
            i3 i3Var = zcVar.a;
            Canvas canvas = i3Var.a;
            i3Var.a = beginRecording;
            r7 r7Var = ycVar.f;
            r7Var.E(mmVar);
            r7Var.F(m40Var);
            r7Var.g = hxVar;
            r7Var.G(this.e);
            r7Var.D(i3Var);
            q2Var.e(ycVar);
            zcVar.a.a = canvas;
        } finally {
            this.d.endRecording();
        }
    }

    @Override // defpackage.jx
    public final float w() {
        return this.o;
    }

    @Override // defpackage.jx
    public final gh x() {
        return this.w;
    }

    @Override // defpackage.jx
    public final long y() {
        return this.q;
    }

    @Override // defpackage.jx
    public final void z(Outline outline, long j) {
        boolean z;
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
