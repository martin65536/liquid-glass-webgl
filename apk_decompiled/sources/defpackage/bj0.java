package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bj0 extends y20 {
    public int b;
    public int d;
    public int f;
    public yi0[] a = new yi0[16];
    public int[] c = new int[16];
    public Object[] e = new Object[16];

    public final void D() {
        this.b = 0;
        this.d = 0;
        Arrays.fill(this.e, 0, this.f, (Object) null);
        this.f = 0;
    }

    public final void E(t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        if (this.b != 0) {
            aj0 aj0Var = new aj0(this);
            while (true) {
                bj0 bj0Var = aj0Var.d;
                yi0 yi0Var = bj0Var.a[aj0Var.a];
                wv b = yi0Var.b(aj0Var);
                t7 t7Var2 = t7Var;
                uw0 uw0Var2 = uw0Var;
                mp0 mp0Var2 = mp0Var;
                zi0 zi0Var2 = zi0Var;
                try {
                    yi0Var.a(aj0Var, t7Var2, uw0Var2, mp0Var2, zi0Var2);
                    int i = aj0Var.a;
                    int i2 = bj0Var.b;
                    if (i < i2) {
                        yi0 yi0Var2 = bj0Var.a[i];
                        aj0Var.b += yi0Var2.a;
                        aj0Var.c += yi0Var2.b;
                        int i3 = i + 1;
                        aj0Var.a = i3;
                        if (i3 >= i2) {
                            break;
                        }
                        t7Var = t7Var2;
                        uw0Var = uw0Var2;
                        mp0Var = mp0Var2;
                        zi0Var = zi0Var2;
                    } else {
                        break;
                    }
                } finally {
                }
            }
        }
        D();
    }

    public final boolean F() {
        if (this.b == 0) {
            return true;
        }
        return false;
    }

    public final void G(yi0 yi0Var) {
        int i;
        int i2;
        int i3 = this.b;
        yi0[] yi0VarArr = this.a;
        int i4 = 1024;
        if (i3 == yi0VarArr.length) {
            if (i3 > 1024) {
                i2 = 1024;
            } else {
                i2 = i3;
            }
            yi0[] yi0VarArr2 = new yi0[i2 + i3];
            System.arraycopy(yi0VarArr, 0, yi0VarArr2, 0, i3);
            this.a = yi0VarArr2;
        }
        int i5 = this.d;
        int i6 = yi0Var.a;
        int i7 = yi0Var.b;
        int i8 = i5 + i6;
        int[] iArr = this.c;
        int length = iArr.length;
        if (i8 > length) {
            if (length > 1024) {
                i = 1024;
            } else {
                i = length;
            }
            int i9 = i + length;
            if (i9 >= i8) {
                i8 = i9;
            }
            int[] iArr2 = new int[i8];
            i8.L(iArr, iArr2, 0, 0, length);
            this.c = iArr2;
        }
        int i10 = this.f + i7;
        Object[] objArr = this.e;
        int length2 = objArr.length;
        if (i10 > length2) {
            if (length2 <= 1024) {
                i4 = length2;
            }
            int i11 = i4 + length2;
            if (i11 >= i10) {
                i10 = i11;
            }
            Object[] objArr2 = new Object[i10];
            System.arraycopy(objArr, 0, objArr2, 0, length2);
            this.e = objArr2;
        }
        yi0[] yi0VarArr3 = this.a;
        int i12 = this.b;
        this.b = i12 + 1;
        yi0VarArr3[i12] = yi0Var;
        this.d += yi0Var.a;
        this.f += i7;
    }
}
