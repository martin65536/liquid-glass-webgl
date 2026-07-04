package defpackage;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.view.View;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q51 extends View {
    public static final o51 o = new o51(0);
    public final sp e;
    public final zc f;
    public final yc g;
    public boolean h;
    public Outline i;
    public boolean j;
    public mm k;
    public m40 l;
    public gv m;
    public hx n;

    public q51(sp spVar, zc zcVar, yc ycVar) {
        super(spVar.getContext());
        this.e = spVar;
        this.f = zcVar;
        this.g = ycVar;
        setOutlineProvider(o);
        this.j = true;
        this.k = k81.b;
        this.l = m40.e;
        jx.a.getClass();
        this.m = w3.v;
        setWillNotDraw(false);
        setClipBounds(null);
    }

    @Override // android.view.View
    public final void dispatchDraw(Canvas canvas) {
        zc zcVar = this.f;
        i3 i3Var = zcVar.a;
        Canvas canvas2 = i3Var.a;
        i3Var.a = canvas;
        mm mmVar = this.k;
        m40 m40Var = this.l;
        float width = getWidth();
        float height = getHeight();
        long floatToRawIntBits = (Float.floatToRawIntBits(height) & 4294967295L) | (Float.floatToRawIntBits(width) << 32);
        hx hxVar = this.n;
        gv gvVar = this.m;
        yc ycVar = this.g;
        mm s = ycVar.J().s();
        m40 u = ycVar.J().u();
        uc q = ycVar.J().q();
        long v = ycVar.J().v();
        hx hxVar2 = (hx) ycVar.J().g;
        r7 J = ycVar.J();
        J.E(mmVar);
        J.F(m40Var);
        J.D(i3Var);
        J.G(floatToRawIntBits);
        J.g = hxVar;
        i3Var.h();
        try {
            gvVar.e(ycVar);
            i3Var.f();
            r7 J2 = ycVar.J();
            J2.E(s);
            J2.F(u);
            J2.D(q);
            J2.G(v);
            J2.g = hxVar2;
            zcVar.a.a = canvas2;
            this.h = false;
        } catch (Throwable th) {
            i3Var.f();
            r7 J3 = ycVar.J();
            J3.E(s);
            J3.F(u);
            J3.D(q);
            J3.G(v);
            J3.g = hxVar2;
            throw th;
        }
    }

    public final boolean getCanUseCompositingLayer$ui_graphics() {
        return this.j;
    }

    public final zc getCanvasHolder() {
        return this.f;
    }

    public final View getOwnerView() {
        return this.e;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return this.j;
    }

    @Override // android.view.View
    public final void invalidate() {
        if (!this.h) {
            this.h = true;
            super.invalidate();
        }
    }

    public final void setCanUseCompositingLayer$ui_graphics(boolean z) {
        if (this.j != z) {
            this.j = z;
            invalidate();
        }
    }

    public final void setInvalidated(boolean z) {
        this.h = z;
    }

    @Override // android.view.View
    public final void forceLayout() {
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }
}
