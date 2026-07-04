package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xd0 {
    public int a;
    public int b;
    public Object c;
    public Object d;
    public Object e;

    public int a(long j) {
        int i = this.a + 1;
        long[] jArr = (long[]) this.c;
        int length = jArr.length;
        if (i > length) {
            int i2 = length * 2;
            long[] jArr2 = new long[i2];
            int[] iArr = new int[i2];
            i8.M(jArr, jArr2, 0, 0, jArr.length);
            i8.O((int[]) this.d, iArr, 0, 0, 14);
            this.c = jArr2;
            this.d = iArr;
        }
        int i3 = this.a;
        this.a = i3 + 1;
        int length2 = ((int[]) this.e).length;
        if (this.b >= length2) {
            int i4 = length2 * 2;
            int[] iArr2 = new int[i4];
            int i5 = 0;
            while (i5 < i4) {
                int i6 = i5 + 1;
                iArr2[i5] = i6;
                i5 = i6;
            }
            i8.O((int[]) this.e, iArr2, 0, 0, 14);
            this.e = iArr2;
        }
        int i7 = this.b;
        int[] iArr3 = (int[]) this.e;
        this.b = iArr3[i7];
        long[] jArr3 = (long[]) this.c;
        jArr3[i3] = j;
        ((int[]) this.d)[i3] = i7;
        iArr3[i7] = i3;
        while (i3 > 0) {
            int i8 = ((i3 + 1) >> 1) - 1;
            if (o20.j(jArr3[i8], j) <= 0) {
                break;
            }
            c(i8, i3);
            i3 = i8;
        }
        return i7;
    }

    public void b(int i) {
        int i2 = this.b;
        boolean z = false;
        if (i >= 0 && i < i2) {
            z = true;
        }
        if (!z) {
            r00.a("lineIndex(" + i + ") is out of bounds [0, " + i2 + ')');
        }
    }

    public void c(int i, int i2) {
        long[] jArr = (long[]) this.c;
        int[] iArr = (int[]) this.d;
        int[] iArr2 = (int[]) this.e;
        long j = jArr[i];
        jArr[i] = jArr[i2];
        jArr[i2] = j;
        int i3 = iArr[i];
        int i4 = iArr[i2];
        iArr[i] = i4;
        iArr[i2] = i3;
        iArr2[i4] = i;
        iArr2[i3] = i2;
    }
}
