package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ji0 extends yi0 {
    public static final ji0 c = new yi0(1, 0, 2);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        int[] iArr;
        wv wvVar;
        int c2;
        int i;
        int a = aj0Var.a(0);
        if (uw0Var.n != 0) {
            rh.a("Cannot move a group while inserting");
        }
        if (a < 0) {
            rh.a("Parameter offset is out of bounds");
        }
        if (a != 0) {
            int i2 = uw0Var.t;
            int i3 = uw0Var.v;
            int i4 = uw0Var.u;
            int i5 = i2;
            while (true) {
                iArr = uw0Var.b;
                if (a <= 0) {
                    break;
                }
                i5 += iArr[(uw0Var.r(i5) * 5) + 3];
                if (i5 > i4) {
                    rh.a("Parameter offset is out of bounds");
                }
                a--;
            }
            int i6 = iArr[(uw0Var.r(i5) * 5) + 3];
            int g = uw0Var.g(uw0Var.b, uw0Var.r(uw0Var.t));
            int g2 = uw0Var.g(uw0Var.b, uw0Var.r(i5));
            int i7 = i5 + i6;
            int g3 = uw0Var.g(uw0Var.b, uw0Var.r(i7));
            int i8 = g3 - g2;
            uw0Var.x(i8, Math.max(uw0Var.t - 1, 0));
            uw0Var.w(i6);
            int[] iArr2 = uw0Var.b;
            int r = uw0Var.r(i7) * 5;
            i8.L(iArr2, iArr2, uw0Var.r(i2) * 5, r, (i6 * 5) + r);
            if (i8 > 0) {
                Object[] objArr = uw0Var.c;
                int h = uw0Var.h(g2 + i8);
                System.arraycopy(objArr, h, objArr, g, uw0Var.h(g3 + i8) - h);
            }
            int i9 = g2 + i8;
            int i10 = i9 - g;
            int i11 = uw0Var.k;
            int i12 = uw0Var.l;
            int length = uw0Var.c.length;
            int i13 = uw0Var.m;
            int i14 = i2 + i6;
            int i15 = i2;
            while (i15 < i14) {
                int r2 = uw0Var.r(i15);
                int i16 = i10;
                int g4 = uw0Var.g(iArr2, r2) - i16;
                if (i13 < r2) {
                    i = 0;
                } else {
                    i = i11;
                }
                int[] iArr3 = iArr2;
                iArr3[(r2 * 5) + 4] = uw0.i(uw0.i(g4, i, i12, length), uw0Var.k, uw0Var.l, uw0Var.c.length);
                i15++;
                i10 = i16;
                iArr2 = iArr3;
                i11 = i11;
            }
            int i17 = i7 + i6;
            int p = uw0Var.p();
            int a2 = tw0.a(uw0Var.d, i7, p);
            ArrayList arrayList = new ArrayList();
            if (a2 >= 0) {
                while (a2 < uw0Var.d.size() && (c2 = uw0Var.c((wvVar = (wv) uw0Var.d.get(a2)))) >= i7 && c2 < i17) {
                    arrayList.add(wvVar);
                }
            }
            int i18 = i2 - i7;
            int size = arrayList.size();
            for (int i19 = 0; i19 < size; i19++) {
                wv wvVar2 = (wv) arrayList.get(i19);
                int c3 = uw0Var.c(wvVar2) + i18;
                if (c3 >= uw0Var.g) {
                    wvVar2.a = -(p - c3);
                } else {
                    wvVar2.a = c3;
                }
                uw0Var.d.add(tw0.a(uw0Var.d, c3, p), wvVar2);
            }
            if (uw0Var.I(i7, i6)) {
                rh.a("Unexpectedly removed anchors");
            }
            uw0Var.m(i3, uw0Var.u, i2);
            if (i8 > 0) {
                uw0Var.J(i9, i8, i7 - 1);
            }
        }
    }
}
