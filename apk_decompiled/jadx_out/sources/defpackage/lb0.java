package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lb0 implements mm {
    public boolean e;
    public long f = 9223372034707292159L;
    public long g = 0;
    public final /* synthetic */ ob0 h;

    public lb0(ob0 ob0Var) {
        this.h = ob0Var;
    }

    @Override // defpackage.mm
    public final float B() {
        return this.h.B();
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return this.h.B() * f;
    }

    @Override // defpackage.mm
    public final /* synthetic */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.mm
    public final /* synthetic */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ float d0(long j) {
        return d3.g(this, j);
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(o0(f), this);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / this.h.B();
    }

    public final void r(ty tyVar, float f) {
        ob0 ob0Var = this.h;
        rr0 rr0Var = ob0Var.q;
        if (rr0Var == null) {
            rr0Var = new rr0();
            ob0Var.q = rr0Var;
        }
        int U = i8.U(rr0Var.b, tyVar);
        if (U < 0) {
            int i = rr0Var.a;
            ty[] tyVarArr = rr0Var.b;
            if (i == tyVarArr.length) {
                int i2 = i * 2;
                rr0Var.b = (ty[]) Arrays.copyOf(tyVarArr, i2);
                rr0Var.c = Arrays.copyOf(rr0Var.c, i2);
                rr0Var.d = Arrays.copyOf(rr0Var.d, i2);
            }
            rr0Var.b[i] = tyVar;
            rr0Var.d[i] = 3;
            rr0Var.c[i] = f;
            rr0Var.a++;
            return;
        }
        float[] fArr = rr0Var.c;
        if (fArr[U] == f) {
            byte[] bArr = rr0Var.d;
            if (bArr[U] == 2) {
                bArr[U] = 0;
                return;
            }
            return;
        }
        fArr[U] = f;
        rr0Var.d[U] = 1;
    }

    @Override // defpackage.mm
    public final float y() {
        return this.h.y();
    }
}
