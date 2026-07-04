package defpackage;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yz0 extends bd0 implements ym0, mm, xm0 {
    public pm0 A;
    public long B;
    public Object s;
    public Object t;
    public PointerInputEventHandler u;
    public dy0 v;
    public pm0 w = uz0.a;
    public final ef0 x;
    public final ef0 y;
    public final ef0 z;

    public yz0(Object obj, Object obj2, PointerInputEventHandler pointerInputEventHandler) {
        this.s = obj;
        this.t = obj2;
        this.u = pointerInputEventHandler;
        ef0 ef0Var = new ef0(new xz0[16]);
        this.x = ef0Var;
        this.y = ef0Var;
        this.z = new ef0(new xz0[16]);
        this.B = 0L;
    }

    @Override // defpackage.xm0
    public final long A() {
        return o4.i;
    }

    @Override // defpackage.mm
    public final float B() {
        return k81.E(this).A.B();
    }

    public final void D0(pm0 pm0Var, qm0 qm0Var) {
        pc pcVar;
        pc pcVar2;
        synchronized (this.y) {
            ef0 ef0Var = this.z;
            ef0Var.c(ef0Var.g, this.x);
        }
        try {
            int ordinal = qm0Var.ordinal();
            if (ordinal != 0) {
                if (ordinal != 1) {
                    if (ordinal != 2) {
                        throw new RuntimeException();
                    }
                } else {
                    ef0 ef0Var2 = this.z;
                    int i = ef0Var2.g - 1;
                    Object[] objArr = ef0Var2.e;
                    if (i < objArr.length) {
                        while (i >= 0) {
                            xz0 xz0Var = (xz0) objArr[i];
                            if (qm0Var == xz0Var.h && (pcVar2 = xz0Var.g) != null) {
                                xz0Var.g = null;
                                pcVar2.u(pm0Var);
                            }
                            i--;
                        }
                    }
                    this.z.g();
                }
            }
            ef0 ef0Var3 = this.z;
            Object[] objArr2 = ef0Var3.e;
            int i2 = ef0Var3.g;
            for (int i3 = 0; i3 < i2; i3++) {
                xz0 xz0Var2 = (xz0) objArr2[i3];
                if (qm0Var == xz0Var2.h && (pcVar = xz0Var2.g) != null) {
                    xz0Var2.g = null;
                    pcVar.u(pm0Var);
                }
            }
            this.z.g();
        } catch (Throwable th) {
            this.z.g();
            throw th;
        }
    }

    public final void E0() {
        dy0 dy0Var = this.v;
        if (dy0Var != null) {
            dy0Var.E(new lm0("Pointer input was reset", 2));
            this.v = null;
        }
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return B() * f;
    }

    @Override // defpackage.xm0
    public final void N(pm0 pm0Var, qm0 qm0Var, long j) {
        this.B = j;
        if (qm0Var == qm0.e) {
            this.w = pm0Var;
        }
        ij ijVar = null;
        if (this.v == null) {
            this.v = f31.G(p0(), null, new m8(this, ijVar, 8), 1);
        }
        D0(pm0Var, qm0Var);
        List list = pm0Var.a;
        int size = list.size();
        int i = 0;
        while (true) {
            if (i < size) {
                if (!g30.n((um0) list.get(i))) {
                    break;
                } else {
                    i++;
                }
            } else {
                pm0Var = null;
                break;
            }
        }
        this.A = pm0Var;
    }

    @Override // defpackage.mm
    public final /* synthetic */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.xm0
    public final /* synthetic */ boolean X() {
        return false;
    }

    @Override // defpackage.mm
    public final /* synthetic */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.xm0
    public final void c0() {
        E0();
    }

    @Override // defpackage.mm
    public final /* synthetic */ float d0(long j) {
        return d3.g(this, j);
    }

    @Override // defpackage.xm0
    public final void g0() {
        pm0 pm0Var = this.A;
        if (pm0Var != null) {
            List list = pm0Var.a;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (((um0) list.get(i)).d) {
                    ArrayList arrayList = new ArrayList(list.size());
                    int size2 = list.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        um0 um0Var = (um0) list.get(i2);
                        long j = um0Var.a;
                        long j2 = um0Var.c;
                        long j3 = um0Var.b;
                        float f = um0Var.e;
                        boolean z = um0Var.d;
                        arrayList.add(new um0(j, j3, j2, false, f, j3, j2, z, z, um0Var.i, 0L, 1.0f, 0L));
                    }
                    pm0 pm0Var2 = new pm0(arrayList, null);
                    this.w = pm0Var2;
                    D0(pm0Var2, qm0.e);
                    D0(pm0Var2, qm0.f);
                    D0(pm0Var2, qm0.g);
                    this.A = null;
                    return;
                }
            }
        }
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(o0(f), this);
    }

    @Override // defpackage.xm0
    public final /* synthetic */ boolean n0() {
        return false;
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / B();
    }

    @Override // defpackage.bd0
    public final void u0() {
        E0();
    }

    @Override // defpackage.bd0
    public final void v0() {
        E0();
    }

    @Override // defpackage.mm
    public final float y() {
        return k81.E(this).A.y();
    }
}
