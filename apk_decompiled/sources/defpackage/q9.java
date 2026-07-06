package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q9 extends bd0 implements tp, ah0, qu0 {
    public long s;
    public zv0 t;
    public long u;
    public m40 v;
    public g30 w;
    public zv0 x;
    public g30 y;

    @Override // defpackage.ah0
    public final void P() {
        this.u = 9205357640488583168L;
        this.v = null;
        this.w = null;
        this.x = null;
        o20.t(this);
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        g30 g30Var;
        b50 b50Var2;
        b50 b50Var3 = b50Var;
        yc ycVar = b50Var3.e;
        if (this.t == o20.o) {
            if (!se.c(this.s, se.g)) {
                d3.q(b50Var, this.s, 0L, 0.0f, 0, 126);
                b50Var3 = b50Var;
            }
        } else {
            yr yrVar = yr.s;
            if (mw0.a(ycVar.f.v(), this.u) && b50Var3.getLayoutDirection() == this.v && o20.e(this.x, this.t)) {
                g30Var = this.w;
                g30Var.getClass();
            } else {
                o30.u(this, new f9(1, this, b50Var3));
                g30Var = this.y;
                this.y = null;
            }
            this.w = g30Var;
            this.u = ycVar.f.v();
            this.v = b50Var3.getLayoutDirection();
            this.x = this.t;
            g30Var.getClass();
            if (!se.c(this.s, se.g)) {
                long j = this.s;
                if (g30Var instanceof gj0) {
                    wo0 wo0Var = ((gj0) g30Var).a;
                    float f = wo0Var.a;
                    float f2 = wo0Var.b;
                    long floatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L);
                    float f3 = wo0Var.c - wo0Var.a;
                    float f4 = wo0Var.d - wo0Var.b;
                    b50Var.a0(j, floatToRawIntBits, (4294967295L & Float.floatToRawIntBits(f4)) | (Float.floatToRawIntBits(f3) << 32), 1.0f, yrVar, 3);
                    b50Var2 = b50Var;
                } else {
                    b50Var2 = b50Var3;
                    if (g30Var instanceof hj0) {
                        hj0 hj0Var = (hj0) g30Var;
                        y5 y5Var = hj0Var.b;
                        if (y5Var != null) {
                            b50Var2.v(y5Var, j, yrVar);
                        } else {
                            gr0 gr0Var = hj0Var.a;
                            float f5 = gr0Var.b;
                            float f6 = gr0Var.a;
                            float intBitsToFloat = Float.intBitsToFloat((int) (gr0Var.h >> 32));
                            long floatToRawIntBits2 = (Float.floatToRawIntBits(f6) << 32) | (Float.floatToRawIntBits(f5) & 4294967295L);
                            float f7 = gr0Var.c - f6;
                            float f8 = gr0Var.d - f5;
                            long floatToRawIntBits3 = (Float.floatToRawIntBits(f8) & 4294967295L) | (Float.floatToRawIntBits(f7) << 32);
                            long floatToRawIntBits4 = (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
                            int i = (int) (floatToRawIntBits2 >> 32);
                            int i2 = (int) (floatToRawIntBits2 & 4294967295L);
                            ycVar.e.c.g(Float.intBitsToFloat(i), Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (floatToRawIntBits3 >> 32)) + Float.intBitsToFloat(i), Float.intBitsToFloat((int) (floatToRawIntBits3 & 4294967295L)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (floatToRawIntBits4 >> 32)), Float.intBitsToFloat((int) (floatToRawIntBits4 & 4294967295L)), yc.r(ycVar, j, yrVar, 1.0f, 3));
                        }
                    } else if (g30Var instanceof fj0) {
                        b50Var2.v(((fj0) g30Var).a, j, yrVar);
                    } else {
                        v7.k();
                        return;
                    }
                }
                b50Var2.r();
            }
        }
        b50Var2 = b50Var3;
        b50Var2.r();
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        zv0 zv0Var = this.t;
        t30[] t30VarArr = zu0.a;
        av0 av0Var = wu0.M;
        t30 t30Var = zu0.a[30];
        bv0Var.a(av0Var, zv0Var);
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean h0() {
        return false;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean i0() {
        return false;
    }

    @Override // defpackage.qu0
    public final boolean u() {
        return false;
    }

    @Override // defpackage.tp
    public final /* synthetic */ void m0() {
    }
}
