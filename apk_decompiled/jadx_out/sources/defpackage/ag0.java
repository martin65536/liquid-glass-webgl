package defpackage;

import java.util.Arrays;
import java.util.HashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ag0 extends ze0 {
    public final ze0 o;
    public boolean p;

    public ag0(long j, ax0 ax0Var, gv gvVar, gv gvVar2, ze0 ze0Var) {
        super(j, ax0Var, gvVar, gvVar2);
        this.o = ze0Var;
        ze0Var.k();
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final void c() {
        if (!this.c) {
            super.c();
            if (!this.p) {
                this.p = true;
                this.o.l();
            }
        }
    }

    @Override // defpackage.ze0
    public final y20 w() {
        HashMap hashMap;
        ag0 ag0Var;
        ze0 ze0Var = this.o;
        if (!ze0Var.m && !ze0Var.c) {
            we0 we0Var = this.h;
            long j = this.b;
            if (we0Var != null) {
                hashMap = cx0.b(ze0Var.g(), this, this.o.d());
            } else {
                hashMap = null;
            }
            synchronized (cx0.c) {
                try {
                    cx0.c(this);
                    if (we0Var == null || we0Var.d == 0) {
                        ag0Var = this;
                        ag0Var.a();
                    } else {
                        ag0Var = this;
                        y20 z = ag0Var.z(this.o.g(), we0Var, hashMap, this.o.d());
                        if (!z.equals(yw0.a)) {
                            return z;
                        }
                        we0 x = ag0Var.o.x();
                        if (x != null) {
                            x.j(we0Var);
                        } else {
                            ag0Var.o.C(we0Var);
                            ag0Var.h = null;
                        }
                    }
                    if (o20.j(ag0Var.o.g(), j) < 0) {
                        ag0Var.o.v();
                    }
                    ze0 ze0Var2 = ag0Var.o;
                    ze0Var2.r(ze0Var2.d().b(j).a(ag0Var.j));
                    ag0Var.o.A(j);
                    ze0 ze0Var3 = ag0Var.o;
                    int i = ag0Var.d;
                    ag0Var.d = -1;
                    if (i >= 0) {
                        int[] iArr = ze0Var3.k;
                        iArr.getClass();
                        int length = iArr.length;
                        int[] copyOf = Arrays.copyOf(iArr, length + 1);
                        copyOf[length] = i;
                        ze0Var3.k = copyOf;
                    } else {
                        ze0Var3.getClass();
                    }
                    ag0Var.o.B(ag0Var.j);
                    ze0 ze0Var4 = ag0Var.o;
                    int[] iArr2 = ag0Var.k;
                    ze0Var4.getClass();
                    if (iArr2.length != 0) {
                        int[] iArr3 = ze0Var4.k;
                        if (iArr3.length != 0) {
                            int length2 = iArr3.length;
                            int length3 = iArr2.length;
                            int[] copyOf2 = Arrays.copyOf(iArr3, length2 + length3);
                            System.arraycopy(iArr2, 0, copyOf2, length2, length3);
                            iArr2 = copyOf2;
                        }
                        ze0Var4.k = iArr2;
                    }
                    ag0Var.m = true;
                    if (!ag0Var.p) {
                        ag0Var.p = true;
                        ag0Var.o.l();
                    }
                    return yw0.a;
                } finally {
                }
            }
        }
        return new xw0(this);
    }
}
