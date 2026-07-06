package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ge0 {
    public int[] a;
    public int b;

    public ge0(int i) {
        int[] iArr;
        if (i == 0) {
            iArr = b20.a;
        } else {
            iArr = new int[i];
        }
        this.a = iArr;
    }

    public final void a(int i) {
        int i2 = this.b + 1;
        int[] iArr = this.a;
        if (iArr.length < i2) {
            this.a = Arrays.copyOf(iArr, Math.max(i2, (iArr.length * 3) / 2));
        }
        int[] iArr2 = this.a;
        int i3 = this.b;
        iArr2[i3] = i;
        this.b = i3 + 1;
    }

    public final int b(int i) {
        if (i >= 0 && i < this.b) {
            return this.a[i];
        }
        v7.f("Index must be between 0 and size");
        return 0;
    }

    public final void c(int i) {
        int i2;
        if (i >= 0 && i < (i2 = this.b)) {
            int[] iArr = this.a;
            int i3 = iArr[i];
            if (i != i2 - 1) {
                i8.L(iArr, iArr, i, i + 1, i2);
            }
            this.b--;
            return;
        }
        v7.f("Index must be between 0 and size");
    }

    public final void d(int i, int i2) {
        if (i >= 0 && i < this.b) {
            int[] iArr = this.a;
            int i3 = iArr[i];
            iArr[i] = i2;
            return;
        }
        v7.f("Index must be between 0 and size");
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ge0) {
            ge0 ge0Var = (ge0) obj;
            int i = ge0Var.b;
            int i2 = this.b;
            if (i == i2) {
                int[] iArr = this.a;
                int[] iArr2 = ge0Var.a;
                y10 K = n30.K(0, i2);
                int i3 = K.e;
                int i4 = K.f;
                if (i3 <= i4) {
                    while (iArr[i3] == iArr2[i3]) {
                        if (i3 != i4) {
                            i3++;
                        } else {
                            return true;
                        }
                    }
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int[] iArr = this.a;
        int i = this.b;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3] * 31;
        }
        return i2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) "[");
        int[] iArr = this.a;
        int i = this.b;
        int i2 = 0;
        while (true) {
            if (i2 < i) {
                int i3 = iArr[i2];
                if (i2 == -1) {
                    sb.append((CharSequence) "...");
                    break;
                }
                if (i2 != 0) {
                    sb.append((CharSequence) ", ");
                }
                sb.append(i3);
                i2++;
            } else {
                sb.append((CharSequence) "]");
                break;
            }
        }
        return sb.toString();
    }

    public /* synthetic */ ge0() {
        this(16);
    }
}
