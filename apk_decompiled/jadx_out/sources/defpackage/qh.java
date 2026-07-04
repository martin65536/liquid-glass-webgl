package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qh {
    public final bw a;
    public cd b;
    public boolean c;
    public int f;
    public int g;
    public int l;
    public final e20 d = new e20();
    public boolean e = true;
    public final ArrayList h = new ArrayList();
    public int i = -1;
    public int j = -1;
    public int k = -1;

    public qh(bw bwVar, cd cdVar) {
        this.a = bwVar;
        this.b = cdVar;
    }

    public final void a() {
        c();
        ArrayList arrayList = this.h;
        if (!arrayList.isEmpty()) {
            arrayList.remove(arrayList.size() - 1);
        } else {
            this.g++;
        }
    }

    public final void b() {
        int i = this.g;
        if (i > 0) {
            bj0 bj0Var = this.b.w;
            bj0Var.G(wi0.c);
            bj0Var.c[bj0Var.d - bj0Var.a[bj0Var.b - 1].a] = i;
            this.g = 0;
        }
        ArrayList arrayList = this.h;
        if (!arrayList.isEmpty()) {
            cd cdVar = this.b;
            int size = arrayList.size();
            Object[] objArr = new Object[size];
            for (int i2 = 0; i2 < size; i2++) {
                objArr[i2] = arrayList.get(i2);
            }
            cdVar.getClass();
            if (size != 0) {
                bj0 bj0Var2 = cdVar.w;
                bj0Var2.G(zh0.c);
                t20.O(bj0Var2, 0, objArr);
            }
            arrayList.clear();
        }
    }

    public final void c() {
        int i = this.l;
        if (i > 0) {
            int i2 = this.i;
            if (i2 >= 0) {
                b();
                bj0 bj0Var = this.b.w;
                bj0Var.G(oi0.c);
                int i3 = bj0Var.d - bj0Var.a[bj0Var.b - 1].a;
                int[] iArr = bj0Var.c;
                iArr[i3] = i2;
                iArr[i3 + 1] = i;
                this.i = -1;
            } else {
                int i4 = this.k;
                int i5 = this.j;
                b();
                bj0 bj0Var2 = this.b.w;
                bj0Var2.G(ki0.c);
                int i6 = bj0Var2.d - bj0Var2.a[bj0Var2.b - 1].a;
                int[] iArr2 = bj0Var2.c;
                iArr2[i6 + 1] = i4;
                iArr2[i6] = i5;
                iArr2[i6 + 2] = i;
                this.j = -1;
                this.k = -1;
            }
            this.l = 0;
        }
    }

    public final void d(boolean z) {
        int i;
        qw0 qw0Var = this.a.G;
        if (z) {
            i = qw0Var.i;
        } else {
            i = qw0Var.g;
        }
        int i2 = i - this.f;
        if (i2 < 0) {
            rh.a("Tried to seek backward");
        }
        if (i2 > 0) {
            bj0 bj0Var = this.b.w;
            bj0Var.G(sh0.c);
            bj0Var.c[bj0Var.d - bj0Var.a[bj0Var.b - 1].a] = i2;
            this.f = i;
        }
    }

    public final void e(int i, int i2) {
        boolean z;
        if (i2 > 0) {
            if (i >= 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                rh.a("Invalid remove index " + i);
            }
            if (this.i == i) {
                this.l += i2;
                return;
            }
            c();
            this.i = i;
            this.l = i2;
        }
    }
}
