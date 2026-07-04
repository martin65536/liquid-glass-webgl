package defpackage;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class tw0 {
    public static final int a(ArrayList arrayList, int i, int i2) {
        int e = e(arrayList, i, i2);
        if (e >= 0) {
            return e;
        }
        return -(e + 1);
    }

    public static final int b(int[] iArr, int i) {
        int i2 = i * 5;
        return Integer.bitCount(iArr[i2 + 1] >> 28) + iArr[i2 + 4];
    }

    public static final void c(int[] iArr, int i, int i2) {
        if (i2 >= 0) {
        }
        int i3 = (i * 5) + 1;
        iArr[i3] = i2 | (iArr[i3] & (-67108864));
    }

    public static final rw0 d(rw0 rw0Var) {
        if (!(rw0Var instanceof rw0)) {
            rw0Var = null;
        }
        if (rw0Var != null) {
            return rw0Var;
        }
        rh.b("Inconsistent composition");
        throw new RuntimeException();
    }

    public static final int e(ArrayList arrayList, int i, int i2) {
        int size = arrayList.size() - 1;
        int i3 = 0;
        while (i3 <= size) {
            int i4 = (i3 + size) >>> 1;
            int i5 = ((wv) arrayList.get(i4)).a;
            if (i5 < 0) {
                i5 += i2;
            }
            int i6 = o20.i(i5, i);
            if (i6 < 0) {
                i3 = i4 + 1;
            } else if (i6 > 0) {
                size = i4 - 1;
            } else {
                return i4;
            }
        }
        return -(i3 + 1);
    }

    public static final void f() {
        throw new ConcurrentModificationException();
    }
}
