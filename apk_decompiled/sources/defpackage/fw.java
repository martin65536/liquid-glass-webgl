package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fw {
    public final ArrayList a;
    public final int b;
    public int c;
    public final ArrayList d;
    public final he0 e;
    public final a01 f;

    public fw(int i, ArrayList arrayList) {
        this.a = arrayList;
        this.b = i;
        if (i < 0) {
            cn0.a("Invalid start index");
        }
        this.d = new ArrayList();
        he0 he0Var = new he0();
        int size = arrayList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            w30 w30Var = (w30) this.a.get(i3);
            int i4 = w30Var.c;
            int i5 = w30Var.d;
            he0Var.h(i4, new tx(i3, i2, i5));
            i2 += i5;
        }
        this.e = he0Var;
        this.f = new a01(new ew(this));
    }

    public final boolean a(int i, int i2) {
        tx txVar;
        int i3;
        int i4;
        he0 he0Var = this.e;
        tx txVar2 = (tx) he0Var.b(i);
        if (txVar2 == null) {
            return false;
        }
        int i5 = txVar2.b;
        int i6 = i2 - txVar2.c;
        txVar2.c = i2;
        if (i6 != 0) {
            Object[] objArr = he0Var.c;
            long[] jArr = he0Var.a;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i7 = 0;
                while (true) {
                    long j = jArr[i7];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i8 = 8 - ((~(i7 - length)) >>> 31);
                        for (int i9 = 0; i9 < i8; i9++) {
                            if ((255 & j) < 128 && (i3 = (txVar = (tx) objArr[(i7 << 3) + i9]).b) >= i5 && txVar != txVar2 && (i4 = i3 + i6) >= 0) {
                                txVar.b = i4;
                            }
                            j >>= 8;
                        }
                        if (i8 != 8) {
                            return true;
                        }
                    }
                    if (i7 != length) {
                        i7++;
                    } else {
                        return true;
                    }
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
