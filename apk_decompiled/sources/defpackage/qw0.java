package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qw0 {
    public final rw0 a;
    public final int[] b;
    public final int c;
    public Object[] d;
    public final int e;
    public boolean f;
    public int g;
    public int h;
    public int i;
    public final e20 j;
    public int k;
    public int l;
    public int m;
    public boolean n;

    public qw0(rw0 rw0Var) {
        this.a = rw0Var;
        this.b = rw0Var.e;
        int i = rw0Var.f;
        this.c = i;
        this.d = rw0Var.g;
        this.e = rw0Var.h;
        this.h = i;
        this.i = -1;
        this.j = new e20();
    }

    public final wv a(int i) {
        ArrayList arrayList = this.a.m;
        int e = tw0.e(arrayList, i, this.c);
        if (e < 0) {
            wv wvVar = new wv(i);
            arrayList.add(-(e + 1), wvVar);
            return wvVar;
        }
        return (wv) arrayList.get(e);
    }

    public final Object b(int[] iArr, int i) {
        int bitCount;
        int i2 = i * 5;
        int i3 = iArr[i2 + 1];
        if ((268435456 & i3) != 0) {
            Object[] objArr = this.d;
            if (i2 >= iArr.length) {
                bitCount = iArr.length;
            } else {
                bitCount = iArr[i2 + 4] + Integer.bitCount(i3 >> 29);
            }
            return objArr[bitCount];
        }
        return ph.a;
    }

    public final void c() {
        this.f = true;
        if (this.a.i <= 0) {
            rh.a("Unexpected reader close()");
        }
        r0.i--;
        this.d = new Object[0];
    }

    public final boolean d(int i) {
        if ((this.b[(i * 5) + 1] & 67108864) != 0) {
            return true;
        }
        return false;
    }

    public final void e() {
        int i;
        int i2;
        if (this.k == 0) {
            if (this.g != this.h) {
                rh.a("endGroup() not called at the end of a group");
            }
            int i3 = (this.i * 5) + 2;
            int[] iArr = this.b;
            int i4 = iArr[i3];
            this.i = i4;
            int i5 = this.c;
            if (i4 < 0) {
                i = i5;
            } else {
                i = iArr[(i4 * 5) + 3] + i4;
            }
            this.h = i;
            int b = this.j.b();
            if (b < 0) {
                this.l = 0;
                this.m = 0;
                return;
            }
            this.l = b;
            if (i4 >= i5 - 1) {
                i2 = this.e;
            } else {
                i2 = iArr[((i4 + 1) * 5) + 4];
            }
            this.m = i2;
        }
    }

    public final Object f() {
        int i = this.g;
        if (i < this.h) {
            return b(this.b, i);
        }
        return 0;
    }

    public final int g() {
        int i = this.g;
        if (i < this.h) {
            return this.b[i * 5];
        }
        return 0;
    }

    public final Object h(int i, int i2) {
        int i3;
        int[] iArr = this.b;
        int b = tw0.b(iArr, i);
        int i4 = i + 1;
        if (i4 < this.c) {
            i3 = iArr[(i4 * 5) + 4];
        } else {
            i3 = this.e;
        }
        int i5 = b + i2;
        if (i5 < i3) {
            return this.d[i5];
        }
        return ph.a;
    }

    public final int i(int i) {
        return this.b[i * 5];
    }

    public final boolean j(int i) {
        if ((this.b[(i * 5) + 1] & 134217728) != 0) {
            return true;
        }
        return false;
    }

    public final boolean k(int i) {
        if ((this.b[(i * 5) + 1] & 536870912) != 0) {
            return true;
        }
        return false;
    }

    public final boolean l(int i) {
        if ((this.b[(i * 5) + 1] & 1073741824) != 0) {
            return true;
        }
        return false;
    }

    public final Object m() {
        int i;
        if (this.k <= 0 && (i = this.l) < this.m) {
            this.n = true;
            Object[] objArr = this.d;
            this.l = i + 1;
            return objArr[i];
        }
        this.n = false;
        return ph.a;
    }

    public final Object n(int i) {
        int i2 = i * 5;
        int[] iArr = this.b;
        int i3 = iArr[i2 + 1] & 1073741824;
        if (i3 != 0) {
            if (i3 != 0) {
                return this.d[iArr[i2 + 4]];
            }
            return ph.a;
        }
        return null;
    }

    public final int o(int i) {
        return this.b[(i * 5) + 1] & 67108863;
    }

    public final Object p(int[] iArr, int i) {
        int i2 = i * 5;
        int i3 = iArr[i2 + 1];
        if ((536870912 & i3) != 0) {
            return this.d[Integer.bitCount(i3 >> 30) + iArr[i2 + 4]];
        }
        return null;
    }

    public final int q(int i) {
        return this.b[(i * 5) + 2];
    }

    public final void r(int i) {
        int i2;
        if (this.k != 0) {
            rh.a("Cannot reposition while in an empty region");
        }
        this.g = i;
        int[] iArr = this.b;
        int i3 = this.c;
        if (i < i3) {
            i2 = iArr[(i * 5) + 2];
        } else {
            i2 = -1;
        }
        if (i2 != this.i) {
            this.i = i2;
            if (i2 < 0) {
                this.h = i3;
            } else {
                this.h = iArr[(i2 * 5) + 3] + i2;
            }
            this.l = 0;
            this.m = 0;
        }
    }

    public final int s() {
        int i;
        if (this.k != 0) {
            rh.a("Cannot skip while in an empty region");
        }
        int i2 = this.g;
        int i3 = i2 * 5;
        int[] iArr = this.b;
        int i4 = iArr[i3 + 1];
        if ((1073741824 & i4) != 0) {
            i = 1;
        } else {
            i = i4 & 67108863;
        }
        this.g = iArr[i3 + 3] + i2;
        return i;
    }

    public final void t() {
        boolean z;
        if (this.k == 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            rh.a("Cannot skip the enclosing group while in an empty region");
        }
        this.g = this.h;
        this.l = 0;
        this.m = 0;
    }

    public final String toString() {
        return "SlotReader(current=" + this.g + ", key=" + g() + ", parent=" + this.i + ", end=" + this.h + ')';
    }

    public final void u() {
        int i;
        if (this.k <= 0) {
            int i2 = this.i;
            int i3 = this.g;
            int i4 = i3 * 5;
            int[] iArr = this.b;
            if (iArr[i4 + 2] != i2) {
                cn0.a("Invalid slot table detected");
            }
            int i5 = this.l;
            int i6 = this.m;
            e20 e20Var = this.j;
            if (i5 == 0 && i6 == 0) {
                e20Var.c(-1);
            } else {
                e20Var.c(i5);
            }
            this.i = i3;
            this.h = iArr[i4 + 3] + i3;
            int i7 = i3 + 1;
            this.g = i7;
            this.l = tw0.b(iArr, i3);
            if (i3 >= this.c - 1) {
                i = this.e;
            } else {
                i = iArr[(i7 * 5) + 4];
            }
            this.m = i;
        }
    }
}
