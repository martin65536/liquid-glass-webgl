package defpackage;

import android.graphics.Path;
import android.graphics.PathMeasure;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lk0 extends h41 {
    public jc0 b;
    public float c = 1.0f;
    public List d;
    public float e;
    public float f;
    public jc0 g;
    public int h;
    public int i;
    public float j;
    public float k;
    public float l;
    public float m;
    public boolean n;
    public boolean o;
    public boolean p;
    public cz0 q;
    public final y5 r;
    public y5 s;
    public y5 t;
    public final q50 u;

    public lk0() {
        int i = o41.a;
        this.d = er.e;
        this.e = 1.0f;
        this.h = 0;
        this.i = 0;
        this.j = 4.0f;
        this.l = 1.0f;
        this.n = true;
        this.o = true;
        y5 a = a6.a();
        this.r = a;
        this.s = a;
        this.u = n30.z(ba0.m);
    }

    @Override // defpackage.h41
    public final void a(up upVar) {
        up upVar2;
        if (this.n) {
            t20.R(this.d, this.r);
            e();
        } else if (this.p) {
            e();
        }
        this.n = false;
        this.p = false;
        jc0 jc0Var = this.b;
        if (jc0Var != null) {
            upVar2 = upVar;
            d3.p(upVar2, this.s, jc0Var, this.c, null, 56);
        } else {
            upVar2 = upVar;
        }
        jc0 jc0Var2 = this.g;
        if (jc0Var2 != null) {
            cz0 cz0Var = this.q;
            if (this.o || cz0Var == null) {
                cz0Var = new cz0(this.f, this.j, this.h, this.i);
                this.q = cz0Var;
                this.o = false;
            }
            d3.p(upVar2, this.s, jc0Var2, this.e, cz0Var, 48);
        }
    }

    public final void e() {
        boolean z;
        Path path;
        float f = this.k;
        y5 y5Var = this.r;
        if (f == 0.0f && this.l == 1.0f) {
            this.s = y5Var;
            return;
        }
        if (o20.e(this.s, y5Var)) {
            this.s = a6.a();
        } else {
            Path.FillType fillType = this.s.a.getFillType();
            Path.FillType fillType2 = Path.FillType.EVEN_ODD;
            if (fillType == fillType2) {
                z = true;
            } else {
                z = false;
            }
            this.s.a.rewind();
            Path path2 = this.s.a;
            if (!z) {
                fillType2 = Path.FillType.WINDING;
            }
            path2.setFillType(fillType2);
        }
        q50 q50Var = this.u;
        PathMeasure pathMeasure = ((z5) q50Var.getValue()).a;
        if (y5Var != null) {
            path = y5Var.a;
        } else {
            path = null;
        }
        pathMeasure.setPath(path, false);
        float length = ((z5) q50Var.getValue()).a.getLength();
        float f2 = this.k;
        float f3 = this.m;
        float f4 = ((f2 + f3) % 1.0f) * length;
        float f5 = ((this.l + f3) % 1.0f) * length;
        if (f4 > f5) {
            y5 y5Var2 = this.t;
            if (y5Var2 == null) {
                y5Var2 = a6.a();
                this.t = y5Var2;
            }
            Path path3 = y5Var2.a;
            path3.reset();
            ((z5) q50Var.getValue()).a(f4, length, y5Var2);
            d3.k(this.s, y5Var2);
            path3.reset();
            ((z5) q50Var.getValue()).a(0.0f, f5, y5Var2);
            d3.k(this.s, y5Var2);
            return;
        }
        ((z5) q50Var.getValue()).a(f4, f5, this.s);
    }

    public final String toString() {
        return this.r.toString();
    }
}
